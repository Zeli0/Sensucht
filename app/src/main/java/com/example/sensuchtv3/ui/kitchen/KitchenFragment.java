package com.example.sensuchtv3.ui.kitchen;

import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import com.example.sensuchtv3.R;
import com.example.sensuchtv3.databinding.FragmentDashboardBinding;
import com.example.sensuchtv3.databinding.FragmentHomeBinding;
import com.example.sensuchtv3.databinding.FragmentKitchenBinding;
import com.example.sensuchtv3.ui.IngredientsViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KitchenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KitchenFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private IngredientsViewModel ingredientsViewModel;
    private FragmentKitchenBinding binding;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public KitchenFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KitchenFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KitchenFragment newInstance(String param1, String param2) {
        KitchenFragment fragment = new KitchenFragment();
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
        binding = FragmentKitchenBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final Button ingredients = binding.ingButt;
        final Button diet = binding.dietButt;
        final Button tools = binding.toolsButt;
        ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KitchenFragmentDirections.ActionNavigationDashboardToIngredientsFragment action
                        = KitchenFragmentDirections.actionNavigationDashboardToIngredientsFragment();
                action.setItemType(1);
                Navigation.findNavController(view).navigate(action);
            }
        });
        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action
                        = KitchenFragmentDirections.actionNavigationDashboardToKitchRestrictFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
        tools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KitchenFragmentDirections.ActionNavigationDashboardToIngredientsFragment action
                        = KitchenFragmentDirections.actionNavigationDashboardToIngredientsFragment();
                action.setItemType(2);
                Navigation.findNavController(view).navigate(action);
            }
        });
        return root;
    }
}