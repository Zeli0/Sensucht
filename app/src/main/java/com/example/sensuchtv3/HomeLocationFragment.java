package com.example.sensuchtv3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.sensuchtv3.databinding.ActivityMainBinding;
import com.example.sensuchtv3.databinding.FragmentHomeLocationBinding;
import com.example.sensuchtv3.kitchenLogic.infoCenters.IngredientsViewModel;
import com.example.sensuchtv3.kitchenLogic.infoCenters.LocationViewModel;
import com.example.sensuchtv3.kitchenLogic.locationLogic.Supermarket;
import com.example.sensuchtv3.kitchenLogic.locationLogic.placesAPIJacksonFiles.PlacesAPIVessel;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import okhttp3.*;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.fail;

public class HomeLocationFragment extends Fragment implements OnMapReadyCallback{

    // Register the permissions callback, which handles the user's response to the
    // system permissions dialog. Save the return value, an instance of
    // ActivityResultLauncher, as an instance variable.
    private LocationViewModel locationViewModel;
    private FusedLocationProviderClient fusedLocationClient;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private boolean canGetLocation;
    private GoogleMap mapInsideTheView;
    private boolean mapReady = false;
    private Location userLocation = null;
    private OkHttpClient client = new OkHttpClient();
    private FragmentHomeLocationBinding binding;
    private MapView locationDisplay;

    private String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/";

    private String API_KEY = "AIzaSyCvu_2DwED1fj8TuGCUM9Lz8_gDjw5QPtY";

    private boolean requestMade = false;

    //Code for actually sending the permission request and storing its results
    private ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                Boolean fineLocationGranted = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    fineLocationGranted = result.getOrDefault(
                            Manifest.permission.ACCESS_FINE_LOCATION, false);
                }
                Boolean coarseLocationGranted = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
                }
                if (fineLocationGranted != null && fineLocationGranted) {
                            // Precise location access granted.
                            canGetLocation = true;
                        } else if (coarseLocationGranted != null && coarseLocationGranted) {
                            // Only approximate location access granted.
                            canGetLocation = false;
                        } else {
                            // No location access granted.
                            canGetLocation = false;
                        }
                    }
            );




        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near markerToPlace, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            //These two are called when the map has been loaded and is ready
            //mapReady boolean to designate that the map has been loaded
            mapReady = true;
            //mapInsideTheView to store the map so that other functions can change how the map looks
            mapInsideTheView = googleMap;
            LatLng markerToPlace;
            //Check if location has been obtained
            if (userLocation != null) {
                markerToPlace = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
                //Store it in the viewmodel
                locationViewModel.setUserLocation(userLocation);
                if (!requestMade) {
                    try {
                        requestForNearbySupermarkets();
                        requestMade = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                markerToPlace = new LatLng(-33.852, 151.211);
            }
            googleMap.addMarker(new MarkerOptions()
                    .position(markerToPlace)
                    .title("Morb"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(markerToPlace));
            googleMap.moveCamera(CameraUpdateFactory.zoomIn());
            googleMap.setMinZoomPreference(15);
        }


    @SuppressLint("MissingPermission")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationViewModel = new ViewModelProvider(this.getActivity()).get(LocationViewModel.class);
        userLocation = null;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        //Checks if permission is already obtained
        if (ContextCompat.checkSelfPermission(
                this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            if (ContextCompat.checkSelfPermission(this.getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                //Boolean value indicating that the location can be obtained
                    //Mainly used because of timing issues between onCreate and onMapReady
                canGetLocation = true;
            }
        //Checks if the educational UI should be displayed
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
            System.out.println("will do later");
        } else {
            // Request for the permission
            locationPermissionRequest.launch(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }

    }
    @SuppressLint("MissingPermission")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeLocationBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        if (canGetLocation) {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this.getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Location of the user is stored into the viewmodel
                                userLocation = location;
                                locationViewModel.setUserLocation(userLocation);
                                //Check if onMapReady has been called
                                if (mapReady) {
                                    //Just in case a marker was already added, clear it first
                                    mapInsideTheView.clear();
                                    LatLng theUser = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
                                    mapInsideTheView.addMarker(new MarkerOptions()
                                            .position(theUser)
                                            .title("Morb"));
                                    mapInsideTheView.moveCamera(CameraUpdateFactory.newLatLng(theUser));
                                    if (!requestMade) {
                                        try {
                                            requestForNearbySupermarkets();
                                            requestMade = true;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                System.out.println(userLocation);
                            }
                        }
                    });
        }
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        locationDisplay = binding.homeMapDisplay;
        locationDisplay.onCreate(mapViewBundle);

        locationDisplay.getMapAsync(this);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        locationDisplay.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        locationDisplay.onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
        locationDisplay.onStop();
    }
    @Override
    public void onPause() {
        locationDisplay.onPause();
        super.onPause();
    }
    @Override
    public void onDestroy() {
        locationDisplay.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        locationDisplay.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        locationDisplay.onSaveInstanceState(mapViewBundle);
    }

    //Code handling requests to GoogleMapsAPI
    public void requestForNearbySupermarkets() throws IOException {


        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(BASE_URL).newBuilder();
        urlBuilder.addPathSegment("json")
                .addQueryParameter("location", userLocation.getLatitude()+ "," + userLocation.getLongitude())
                .addQueryParameter("keyword","supermarket")
                .addQueryParameter("radius", "1500")
                .addQueryParameter("key", API_KEY);




        ObjectMapper mapper = new ObjectMapper();
        /* As seen in the screenshots, some of the listed fields in the JSON response are not implemented in their
            corresponding classes. The disable method below is used to force the mapper to ignore and skip these fields
            when it cannot find the right object to map to, so that we can save time not having to create a plethora
            of completely unecessary classes.
         */
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        String supermarketURL = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(supermarketURL)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            public void onResponse(Call call, Response response)
                    throws IOException {
                // ...
                String result = response.body().string();
                PlacesAPIVessel chalice = mapper.readValue(result, PlacesAPIVessel.class);
                ArrayList<Supermarket> marts = chalice.filter();
                //locationViewModel.setNearbyMarts(marts);
            }

            public void onFailure(Call call, IOException e) {
                fail("Failed to get Supermarkets");
            }
        });
    }
}