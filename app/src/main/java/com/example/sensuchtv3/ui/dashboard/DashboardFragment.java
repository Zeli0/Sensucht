package com.example.sensuchtv3.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.sensuchtv3.R;
import com.example.sensuchtv3.databinding.FragmentDashboardBinding;
import com.example.sensuchtv3.ui.IngredientsViewModel;

public class DashboardFragment extends Fragment {
    private IngredientsViewModel ingredientsViewModel;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ingredientsViewModel =
                new ViewModelProvider(this.getActivity()).get(IngredientsViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final ToggleButton potato = binding.potato;
        final ToggleButton toast = binding.toast;
        final ToggleButton milk = binding.milk;
        final ToggleButton oven = binding.oven;
        final ToggleButton skillet = binding.skillet;

        potato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientsViewModel.hasPotato();
            }
        });

        toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientsViewModel.hasToast();
            }
        });

        milk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientsViewModel.hasMilk();
            }
        });

        oven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientsViewModel.hasOven();
            }
        });

        skillet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientsViewModel.hasSkillet();
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}