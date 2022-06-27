package com.example.sensuchtv3.ui.profile;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import com.example.sensuchtv3.databinding.FragmentProfileBinding;
import com.example.sensuchtv3.kitchenLogic.locationLogic.ColdStorage;
import com.example.sensuchtv3.kitchenLogic.locationLogic.Fairprice;
import com.example.sensuchtv3.kitchenLogic.locationLogic.Giant;
import com.example.sensuchtv3.kitchenLogic.locationLogic.ShengShiong;
import com.example.sensuchtv3.ui.kitchen.KitchenFragmentDirections;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;


public class ProfileFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentProfileBinding binding;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final Button locationButt = binding.locationButt;
        final Button historyButt = binding.historyButt;
        locationButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action
                        = ProfileFragmentDirections.actionNavigationNotificationsToHomeLocationFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        historyButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*//ColdStorage temp = new ColdStorage(null);
                //ShengShiong temp = new ShengShiong(null);
                //Fairprice temp = new Fairprice(null);
                Giant temp = new Giant(null);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    System.out.println(temp.scrape(new String[]{"steak"}).join());
                }*/
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}