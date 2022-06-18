package com.example.sensuchtv3.ui.budget;

import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import com.example.sensuchtv3.databinding.FragmentItemListDialogBinding;
import com.example.sensuchtv3.kitchenLogic.infoCenters.IngredientsViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.textfield.TextInputEditText;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     BudgetInputListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class BudgetInputDialogFragment extends BottomSheetDialogFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_ITEM_COUNT = "item_count";
    private FragmentItemListDialogBinding binding;
    private IngredientsViewModel ingredientsViewModel;

    // TODO: Customize parameters
    public static BudgetInputDialogFragment newInstance(int itemCount) {
        final BudgetInputDialogFragment fragment = new BudgetInputDialogFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_ITEM_COUNT, itemCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ingredientsViewModel =
                new ViewModelProvider(this.getActivity()).get(IngredientsViewModel.class);
        binding = FragmentItemListDialogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final Button confirm = binding.confirmButt;
        final TextInputEditText money = binding.newMoney;
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = Integer.parseInt(money.getText().toString());
                ingredientsViewModel.changeMoney(temp);
                dismiss();
            }
        });
        return root;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}