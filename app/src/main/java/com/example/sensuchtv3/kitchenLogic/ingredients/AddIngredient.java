package com.example.sensuchtv3.kitchenLogic.ingredients;

import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.sensuchtv3.databinding.FragmentAddIngredientBinding;
import com.example.sensuchtv3.kitchenLogic.infoCenters.IngredientsViewModel;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import static java.lang.Integer.parseInt;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddIngredient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddIngredient extends Fragment {
    private IngredientsViewModel ingredientsViewModel;
    private FragmentAddIngredientBinding binding;
    // TODO: Rename parameter arguments, choose names that match

    // TODO: Rename and change types of parameters

    public AddIngredient() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddIngredient.
     */
    // TODO: Rename and change types and number of parameters
    public static AddIngredient newInstance(String param1, String param2) {
        AddIngredient fragment = new AddIngredient();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ingredientsViewModel =
                new ViewModelProvider(this.getActivity()).get(IngredientsViewModel.class);

        //Gets the fragment, allows code to access the elements in the fragment
        binding = FragmentAddIngredientBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final TextInputEditText name = binding.ingName;
        final TextInputEditText number = binding.ingNumber;
        final TextInputEditText quantifier = binding.ingQuant;
        Button addIng = binding.addButt;
        addIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check if empty
                if (name.getText().length() == 0
                        || number.getText().length() == 0
                        || quantifier.getText().length() == 0) {
                    //Display an error message
                    Snackbar temp = Snackbar.make(root, "Please fill in all fields.", BaseTransientBottomBar.LENGTH_SHORT);
                    temp.show();
                //Check if the number is actually a number
                } else if (!number.getText().toString().matches("-?(0|[1-9]\\d*)")) {
                    Snackbar temp = Snackbar.make(root, "Incorrect input.", BaseTransientBottomBar.LENGTH_SHORT);
                    temp.show();
                } else {
                    ingredientsViewModel.addIngredient(
                            new Ingredient(name.getText().toString(),
                                    parseInt(number.getText().toString()),
                                    quantifier.getText().toString()));
                    //Return to previous page
                    Navigation.findNavController(view).popBackStack();
                }
            }
        });
        return root;
    }
}