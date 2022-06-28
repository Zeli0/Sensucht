package com.example.sensuchtv3.ui.budget;

import android.os.Bundle;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import com.example.sensuchtv3.R;
import com.example.sensuchtv3.databinding.FragmentBudgetBinding;
import com.example.sensuchtv3.kitchenLogic.infoCenters.IngredientsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BudgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentBudgetBinding binding;
    private IngredientsViewModel ingredientsViewModel;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BudgetFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BudgetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BudgetFragment newInstance(String param1, String param2) {
        BudgetFragment fragment = new BudgetFragment();
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

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        //The chosen time frame is indicated in the ingredientsviewmodel, where the budget is also stored
            //the ingredientsviewmodel can then run correct calculations
        ingredientsViewModel.changeType(pos);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ingredientsViewModel =
                new ViewModelProvider(this.getActivity()).get(IngredientsViewModel.class);
        binding = FragmentBudgetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Money Portion
        final TextView budgetDisplay = binding.moneyDisplay;
        final Button editBudget = binding.budgetEdit;
        ingredientsViewModel.getBudget().observe(this, item -> {
            String temp = "$" + String.valueOf(item.getMoney());
            budgetDisplay.setText(temp);
        });
        String temp = "$" + String.valueOf(ingredientsViewModel.getBudget().getValue().getMoney());
        budgetDisplay.setText(temp);

        //editBudget is the edit button
        editBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigation component moves the user to the next fragment, or the modalbottomfragment in this case
                NavDirections nav = BudgetFragmentDirections.actionBudgetFragmentToBudgetInputListDialogFragment();
                Navigation.findNavController(view).navigate(nav);
            }
        });

        //Time Fram Portion
        final Spinner frame = binding.timeFrame;
        frame.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.time_frames, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        frame.setAdapter(adapter);
        frame.setSelection(ingredientsViewModel.getBudget().getValue().getType());
        return root;
    }

}