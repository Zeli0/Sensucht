package com.example.sensuchtv3.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.sensuchtv3.R;
import com.example.sensuchtv3.databinding.FragmentHomeBinding;
import com.example.sensuchtv3.kitchenLogic.RecipieAPI.RecipieRequest;
import com.example.sensuchtv3.kitchenLogic.infoCenters.IngredientsViewModel;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HomeFragment extends Fragment {

    private TextView textViewResult1;
    private TextView textViewResult2;
    private TextView textViewResult3;
    private TextView offsetText;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private int number = 0;
    private int offset = 0;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    Gson gson = new Gson();

    private IngredientsViewModel ingredientsViewModel;
    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ingredientsViewModel =
                new ViewModelProvider(this.getActivity()).get(IngredientsViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        textViewResult1 = root.findViewById(R.id.title1);
        textViewResult2 = root.findViewById(R.id.title2);
        textViewResult3 = root.findViewById(R.id.title3);
        imageView1 = root.findViewById(R.id.Rpic1);
        imageView2 = root.findViewById(R.id.Rpic2);
        imageView3 = root.findViewById(R.id.Rpic3);
        Button searchapi = (Button) root.findViewById(R.id.SearchButton);

        searchapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offset += 3;
                searchRecipe("potato", 3, offset);
            }
        });

        return root;
        
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void searchRecipe(String ingredient, int number, int offset){
        OkHttpClient client = new OkHttpClient();

        String url = "https://api.spoonacular.com/recipes/complexSearch?apiKey=df736318d07c48819d4999729fa24e66";


        Request request = new Request.Builder()
                .url(url+ "&ingredients=" + ingredient + "&number=" + String.valueOf(number) + "&offset" + String.valueOf(offset))
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();

                    RecipieRequest data = gson.fromJson(myResponse, RecipieRequest.class);



                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            textViewResult1.setText(data.getRecipes().get(0).getTitle());
                            textViewResult2.setText(data.getRecipes().get(1).getTitle());
                            textViewResult3.setText(data.getRecipes().get(2).getTitle());
                            Glide.with(HomeFragment.this).load(data.getRecipes().get(0).getImageURL()).into(imageView1);
                            Glide.with(HomeFragment.this).load(data.getRecipes().get(1).getImageURL()).into(imageView2);
                            Glide.with(HomeFragment.this).load(data.getRecipes().get(2).getImageURL()).into(imageView3);

                        }
                    });
                }
            }
        });
    }
}