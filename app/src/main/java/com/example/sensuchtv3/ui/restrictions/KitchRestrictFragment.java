package com.example.sensuchtv3.ui.restrictions;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sensuchtv3.*;
import com.example.sensuchtv3.databinding.FragmentKitchRestrictBinding;
import com.example.sensuchtv3.ui.IngredientsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KitchRestrictFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KitchRestrictFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int type;

    private IngredientsViewModel ingredientsViewModel;
    private FragmentKitchRestrictBinding binding;

    private LiveData<List<Diet>> bondage;

    public KitchRestrictFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KitchRestrictFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KitchRestrictFragment newInstance(String param1, String param2) {
        KitchRestrictFragment fragment = new KitchRestrictFragment();
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
        binding = FragmentKitchRestrictBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        RecyclerView rvRestrict = (RecyclerView) root.findViewById(R.id.rvRestrict);
        bondage = ingredientsViewModel.getDiet();
        DietAdapter adapter = new DietAdapter(bondage.getValue(), ingredientsViewModel);
        rvRestrict.setAdapter(adapter);
        rvRestrict.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return root;
    }

}