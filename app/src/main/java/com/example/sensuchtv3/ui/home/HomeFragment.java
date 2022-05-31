package com.example.sensuchtv3.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.example.sensuchtv3.R;
import com.example.sensuchtv3.databinding.FragmentHomeBinding;
import com.example.sensuchtv3.ui.IngredientsViewModel;

public class HomeFragment extends Fragment {

    private IngredientsViewModel ingredientsViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ingredientsViewModel =
                new ViewModelProvider(this.getActivity()).get(IngredientsViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        final ImageView potat = binding.potatoPic;
        final ImageView toasty = binding.toastPic;

        ingredientsViewModel.canScallop().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean s) {
                if (s) {
                    potat.setVisibility(View.VISIBLE);
                } else {
                    potat.setVisibility(View.INVISIBLE);
                }
            }
        });

        ingredientsViewModel.canFrench().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean s) {
                if (s) {
                    toasty.setVisibility(View.VISIBLE);
                } else {
                    toasty.setVisibility(View.INVISIBLE);
                }
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