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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.sensuchtv3.R;
import com.example.sensuchtv3.databinding.FragmentHomeBinding;
import com.example.sensuchtv3.kitchenLogic.RecipieAPI.RecipieRequest;
import com.example.sensuchtv3.kitchenLogic.infoCenters.IngredientsViewModel;
import com.example.sensuchtv3.kitchenLogic.ingredients.Ingredient;
import com.example.sensuchtv3.kitchenLogic.tools.Tool;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HomeFragment extends Fragment {

    private TextView textViewResult1;
    private TextView textViewResult2;
    private TextView textViewResult3;
    private TextView inputOffset;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private int number = 3;
    private int offset = 0;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Tool> tools;
    private String names = "";
    private String toolName = "";

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

        ingredients = ingredientsViewModel.getIngredients().getValue();
        tools = ingredientsViewModel.getTools().getValue();

        textViewResult1 = root.findViewById(R.id.title1);
        textViewResult2 = root.findViewById(R.id.title2);
        textViewResult3 = root.findViewById(R.id.title3);
        inputOffset = root.findViewById(R.id.ingNameDisplay);
        imageView1 = root.findViewById(R.id.Rpic1);
        imageView2 = root.findViewById(R.id.Rpic2);
        imageView3 = root.findViewById(R.id.Rpic3);
        Button searchapi = (Button) root.findViewById(R.id.SearchButton);
        Button forwardSearch = (Button) root.findViewById(R.id.forwardSearch);
        Button backwardSearch = (Button) root.findViewById(R.id.BackwardSearch);


        searchapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ingredients.size()==1 && ingredients.get(0).getName()=="None"){
                    Toast toast= Toast.makeText(HomeFragment.super.getContext() ,"Please input some ingredients!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    searchRecipe(ingredients, tools, offset);
                }
            }
        });
        forwardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ingredients.size()==1 && ingredients.get(0).getName()=="None"){
                    Toast toast= Toast.makeText(HomeFragment.super.getContext() ,"Please input some ingredients!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    offset += 3;
                    searchRecipe(ingredients, tools, offset);
                }

            }
        });
        backwardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(offset>0){
                    offset -= 3;
                    searchRecipe(ingredients, tools, offset);
                }
            }
        });

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void searchRecipe(ArrayList<Ingredient> ingredient, ArrayList<Tool> tool, int offset){
        OkHttpClient client = new OkHttpClient();
        names = "";
        toolName = "";

        for (int i=0; i<ingredient.size(); i++){
            if(i==0){names = names + ingredient.get(i).getName();}
            else{names = names + "," + ingredient.get(i).getName();}
        }
        for (int i=0; i<tool.size(); i++){
            if(i==0){toolName = toolName + tool.get(i).getName();}
            else{toolName = toolName + "," + tool.get(i).getName();}
        }

        String url = "https://api.spoonacular.com/recipes/complexSearch?apiKey=df736318d07c48819d4999729fa24e66";

        Request request = new Request.Builder()
                .url(url+ "&includeIngredients=" + names
                        + "&number=3"
                        + "&offset=" + String.valueOf(offset)
                        + "&equipment=" + toolName
                        + "&instructionsRequired=true")
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
                            inputOffset.setText(names + toolName);
                        }
                    });
                    displayRecipe(data);
                }
            }
        });
    }

    public void displayRecipe(RecipieRequest json){
        RecipieRequest data = json;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if(data.getRecipes().size()==1){
                    textViewResult1.setText(data.getRecipes().get(0).getTitle());
                    Glide.with(HomeFragment.this).load(data.getRecipes().get(0).getImageURL()).into(imageView1);
                }
                else if(data.getRecipes().size()==2){
                    textViewResult1.setText(data.getRecipes().get(0).getTitle());
                    textViewResult2.setText(data.getRecipes().get(1).getTitle());
                    textViewResult3.setText("");
                    Glide.with(HomeFragment.this).load(data.getRecipes().get(0).getImageURL()).into(imageView1);
                    Glide.with(HomeFragment.this).load(data.getRecipes().get(1).getImageURL()).into(imageView2);
                    imageView3.setImageDrawable(null);
                }
                else if(data.getRecipes().size()==3){
                    textViewResult1.setText(data.getRecipes().get(0).getTitle());
                    textViewResult2.setText(data.getRecipes().get(1).getTitle());
                    textViewResult3.setText(data.getRecipes().get(2).getTitle());
                    Glide.with(HomeFragment.this).load(data.getRecipes().get(0).getImageURL()).into(imageView1);
                    Glide.with(HomeFragment.this).load(data.getRecipes().get(1).getImageURL()).into(imageView2);
                    Glide.with(HomeFragment.this).load(data.getRecipes().get(2).getImageURL()).into(imageView3);
                }
                else{
                    Toast toast= Toast.makeText(HomeFragment.super.getContext() ,"Last Results!", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }
}