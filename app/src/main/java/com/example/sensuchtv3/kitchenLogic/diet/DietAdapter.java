package com.example.sensuchtv3.kitchenLogic.diet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sensuchtv3.R;
import com.example.sensuchtv3.kitchenLogic.infoCenters.IngredientsViewModel;

import java.util.List;

public class DietAdapter extends RecyclerView.Adapter<DietAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Switch nameSwitch;
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            nameSwitch= itemView.findViewById(R.id.dietLabel);
        }
    }
    private List<Diet> habits;
    private IngredientsViewModel infoStockade;

    public DietAdapter(List<Diet> habits, IngredientsViewModel infoStockade) {
        this.habits = habits;
        this.infoStockade = infoStockade;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public DietAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.diet_layout, parent, false);
        //View contactView = inflater.inflate(R.layout.test_layout, parent, false);

        // Return a new holder instance
        DietAdapter.ViewHolder viewHolder = new DietAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(DietAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Diet fatso = habits.get(position);
        int pos = position;
        // Set item views based on your views and data model
        Switch textView = holder.nameSwitch;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoStockade.toggleDiet(pos);
            }
        });
        String display = fatso.getName();
        textView.setText(display);
        textView.setChecked(fatso.getChecked());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return habits.size();
    }
}
