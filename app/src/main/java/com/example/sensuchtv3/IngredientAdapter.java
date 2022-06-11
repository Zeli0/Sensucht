package com.example.sensuchtv3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Any views that will be rendered per row
        public TextView nameTextView;
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            //nameTextView = (TextView) itemView.findViewById(R.id.ingDisplay);
            nameTextView = (TextView) itemView.findViewById(R.id.restrictDisplay);
        }
    }

    private List<Ingredient> leftovers;

    public IngredientAdapter(List<Ingredient> leftovers) {
        this.leftovers = leftovers;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.ingtool_layout, parent, false);
        //View contactView = inflater.inflate(R.layout.test_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(IngredientAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Ingredient beef = leftovers.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        String display;
        if (beef == Ingredient.EMPTY) {
            display = "None";
        } else {
            display = beef.getNumber() + " " + beef.getQuantifier() + " " + beef.getName();
        }

        textView.setText(display);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return leftovers.size();
    }
}
