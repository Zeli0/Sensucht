package com.example.sensuchtv3.kitchenLogic.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sensuchtv3.R;
import com.example.sensuchtv3.kitchenLogic.ingredients.Ingredient;

import java.util.List;

public class ToolAdapter extends RecyclerView.Adapter<ToolAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView nameTextView;
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.restrictDisplay);
        }
        @Override
        public void onClick(View v) {
            removeItem(getAbsoluteAdapterPosition());
        }
    }

    private List<Tool> tools;
    private MutableLiveData<Boolean> canDelete;

    public ToolAdapter(List<Tool> tools, MutableLiveData<Boolean> canDelete) {
        this.tools = tools;
        this.canDelete = canDelete;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ToolAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.ingtool_layout, parent, false);

        // Return a new holder instance
        ToolAdapter.ViewHolder viewHolder = new ToolAdapter.ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ToolAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Tool law = tools.get(position);


        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        String display;
        if (law == Tool.EMPTY) {
            display = "None";
        } else {
            display = law.getName();
        }

        textView.setText(display);
    }
    public void removeItem(int pos) {
        if (canDelete.getValue()) {
            tools.remove(pos);
            notifyItemRemoved(pos);
            notifyItemRangeChanged(pos, tools.size());
            if (tools.isEmpty()) {
                tools.add(Tool.EMPTY);
                notifyItemInserted(0);
                notifyItemRangeChanged(0, tools.size());
            }
        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return tools.size();
    }
}
