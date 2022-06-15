package com.example.sensuchtv3.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.sensuchtv3.Budget;
import com.example.sensuchtv3.Diet;
import com.example.sensuchtv3.Ingredient;
import com.example.sensuchtv3.Tool;

import java.util.ArrayList;
import java.util.List;

public class IngredientsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Ingredient>> leftovers;
    private MutableLiveData<ArrayList<Tool>> tools;
    private MutableLiveData<List<Diet>> diet;
    private MutableLiveData<Budget> budget;



    public IngredientsViewModel() {
        leftovers = new MutableLiveData<>();
        leftovers.setValue(Ingredient.createIngredientList());
        tools = new MutableLiveData<>();
        tools.setValue(Tool.createRestrictionList());
        diet = new MutableLiveData<>();
        diet.setValue(Diet.getHabits());
        budget = new MutableLiveData<>();
        budget.setValue(new Budget(0, 0));
    }


    public void addIngredient(Ingredient newIng) {
        ArrayList<Ingredient> temp =  leftovers.getValue();
        if (temp.get(0) == Ingredient.EMPTY) {
            temp.remove(Ingredient.EMPTY);
        }
        temp.add(newIng);
        leftovers.setValue(temp);
    }

    public void toggleDiet(int pos) {
        List<Diet> temp = diet.getValue();
        temp.get(pos).flipToggle();
        diet.setValue(temp);
    }

    public void changeType(int newType) {
        Budget temp = budget.getValue();
        temp.setType(newType);
        budget.setValue(temp);
    }

    public void changeMoney(int cashMoney) {
        Budget temp = budget.getValue();
        temp.setMoney(cashMoney);
        budget.setValue(temp);
    }

    public void addRestriction(Tool newRestrict, int type) {
        ArrayList<Tool> temp;
        MutableLiveData<ArrayList<Tool>> source;
        temp = tools.getValue();
        if (temp.get(0) == Tool.EMPTY) {
            temp.remove(Tool.EMPTY);
        }
        temp.add(newRestrict);
        tools.setValue(temp);
    }

    public LiveData<ArrayList<Ingredient>> getIngredients() {
        return leftovers;
    }

    public LiveData<List<Diet>> getDiet() {
        return diet;
    }

    public LiveData<ArrayList<Tool>> getTools() {
        return tools;
    }

    public LiveData<Budget> getBudget() {
        return budget;
    }

}
