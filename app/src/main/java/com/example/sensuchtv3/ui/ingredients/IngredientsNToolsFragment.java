package com.example.sensuchtv3.ui.ingredients;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sensuchtv3.*;
import com.example.sensuchtv3.databinding.FragmentIngredientsAndToolsBinding;
import com.example.sensuchtv3.kitchenLogic.ingredients.Ingredient;
import com.example.sensuchtv3.kitchenLogic.ingredients.IngredientAdapter;
import com.example.sensuchtv3.kitchenLogic.tools.Tool;
import com.example.sensuchtv3.kitchenLogic.tools.ToolAdapter;
import com.example.sensuchtv3.kitchenLogic.infoCenters.IngredientsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientsNToolsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsNToolsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LiveData<ArrayList<Ingredient>> leftovers;
    LiveData<ArrayList<Tool>> tools;
    MutableLiveData<Boolean> canDelete;

    private IngredientsViewModel ingredientsViewModel;
    private FragmentIngredientsAndToolsBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int type;

    public IngredientsNToolsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IngredientsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IngredientsNToolsFragment newInstance(String param1, String param2) {
        IngredientsNToolsFragment fragment = new IngredientsNToolsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        ingredientsViewModel =
                new ViewModelProvider(this.getActivity()).get(IngredientsViewModel.class);
        binding = FragmentIngredientsAndToolsBinding.inflate(inflater, container, false);
        canDelete = new MutableLiveData<>();
        canDelete.setValue(false);

        View donScarra = binding.getRoot();


        RecyclerView rvIng = (RecyclerView) donScarra.findViewById(R.id.rvLeftovers);
        final FloatingActionButton addStuff = binding.addShit;
        final FloatingActionButton subtract = binding.subButt;

        type = IngredientsNToolsFragmentArgs.fromBundle(getArguments()).getItemType();

        if (type == 1) {
            addStuff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavDirections nav = IngredientsNToolsFragmentDirections.actionIngredientsFragmentToAddIngredient();
                    Navigation.findNavController(view).navigate(nav);
                }
            });
            // Initialize contacts
            leftovers = ingredientsViewModel.getIngredients();
            // Create adapter passing in the sample user data
            IngredientAdapter adapter = new IngredientAdapter(leftovers.getValue(), canDelete);
            // Attach the adapter to the recyclerview to populate items
            rvIng.setAdapter(adapter);
        } else {
            addStuff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavDirections nav = IngredientsNToolsFragmentDirections.actionIngredientsFragmentToToolsInput();
                    Navigation.findNavController(view).navigate(nav);
                }
            });
            // Initialize contacts
            tools = ingredientsViewModel.getTools();
            // Create adapter passing in the sample user data
            ToolAdapter adapter = new ToolAdapter(tools.getValue(), canDelete);
            // Attach the adapter to the recyclerview to populate items
            rvIng.setAdapter(adapter);
        }
        //rvIng.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar mySnackbar;
                if (canDelete.getValue()) {
                    mySnackbar = Snackbar.make(donScarra, "Delete Mode: Off", BaseTransientBottomBar.LENGTH_SHORT);
                } else {
                    mySnackbar = Snackbar.make(donScarra, "Delete Mode: On", BaseTransientBottomBar.LENGTH_SHORT);
                }
                canDelete.setValue(!canDelete.getValue());
                mySnackbar.show();
            }
        });
        rvIng.setLayoutManager(new GridLayoutManager(this.getActivity(), 3));
        return donScarra;
    }
}