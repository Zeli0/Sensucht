package com.example.sensuchtv3.kitchenLogic.RecipieAPI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import com.bumptech.glide.Glide;
import com.example.sensuchtv3.R;
import com.google.gson.Gson;


import okhttp3.OkHttpClient;
import okhttp3.Request;


public class RecipieDisplay extends AppCompatActivity {

    private TextView textViewResult1;
    private TextView textViewResult2;
    private TextView textViewResult3;
    private TextView offsetText;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private Button searchapi;
    private int number = 0;
    private int offset = 0;

    Gson gson = new Gson();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie_display);

        textViewResult1 = findViewById(R.id.text_view_result);
        textViewResult2 = findViewById(R.id.text_view_result2);
        textViewResult3 = findViewById(R.id.text_view_result3);
        offsetText = findViewById(R.id.Offset);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        searchapi = (Button) findViewById(R.id.searchAPI);

        searchapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewResult1.clearComposingText();
                textViewResult2.clearComposingText();
                textViewResult3.clearComposingText();
                offset += 3;
                searchRecipe("potato", 3, offset);
            }
        });


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



                    RecipieDisplay.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textViewResult1.setText(data.getRecipes().get(0).getTitle());
                            textViewResult2.setText(data.getRecipes().get(1).getTitle());
                            textViewResult3.setText(data.getRecipes().get(2).getTitle());
                            offsetText.setText(String.valueOf(offset));
                            Glide.with(RecipieDisplay.this).load(data.getRecipes().get(0).getImageURL()).into(imageView1);
                            Glide.with(RecipieDisplay.this).load(data.getRecipes().get(1).getImageURL()).into(imageView2);
                            Glide.with(RecipieDisplay.this).load(data.getRecipes().get(2).getImageURL()).into(imageView3);

                        }
                    });
                }
            }
        });
    }
}