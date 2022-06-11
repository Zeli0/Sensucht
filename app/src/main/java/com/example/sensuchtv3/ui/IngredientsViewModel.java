package com.example.sensuchtv3.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.sensuchtv3.Diet;
import com.example.sensuchtv3.Ingredient;
import com.example.sensuchtv3.Tool;

import java.util.ArrayList;
import java.util.List;

public class IngredientsViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Ingredient>> leftovers;
    private MutableLiveData<ArrayList<Tool>> tools;
    private MutableLiveData<List<Diet>> diet;

    private MutableLiveData<String> mText, morbussy;
    private MutableLiveData<Boolean> hasPotato, hasMilk, hasToast, hasSkillet, hasOven;


    public IngredientsViewModel() {
        leftovers = new MutableLiveData<>();
        leftovers.setValue(Ingredient.createIngredientList());
        tools = new MutableLiveData<>();
        tools.setValue(Tool.createRestrictionList());
        diet = new MutableLiveData<>();
        diet.setValue(Diet.getHabits());

        //Below is all testing
        hasPotato = new MutableLiveData<>();
        hasPotato.setValue(false);
        hasMilk = new MutableLiveData<>();
        hasMilk.setValue(false);
        hasToast = new MutableLiveData<>();
        hasToast.setValue(false);
        hasSkillet = new MutableLiveData<>();
        hasSkillet.setValue(false);
        hasOven = new MutableLiveData<>();
        hasOven.setValue(false);
        mText = new MutableLiveData<>();
        mText.setValue("I could morb this all day");
        morbussy = new MutableLiveData<>();
        morbussy.setValue("It's morbin' time");
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

    //Below is also all for testing purposes, will clean up in the future
    public LiveData<Boolean> canScallop() {
        MutableLiveData<Boolean> temp = new MutableLiveData<>();
        temp.setValue((hasPotato.getValue() || hasMilk.getValue()) && hasSkillet.getValue());
        return temp;
    }
    public LiveData<Boolean> canFrench() {
        MutableLiveData<Boolean> temp = new MutableLiveData<>();
        temp.setValue((hasToast.getValue() || hasMilk.getValue()) && hasSkillet.getValue() && hasOven.getValue());
        return temp;
    }

    public LiveData<Boolean> hasPotato() {
        System.out.println("sothony sadtano");
        if (Boolean.TRUE.equals(hasPotato.getValue())) {
            hasPotato.setValue(false);
        } else {
            hasPotato.setValue(true);
        }
        return hasPotato;
    }

    public LiveData<Boolean> hasToast() {
        System.out.println("sothony sadtano");
        if (Boolean.TRUE.equals(hasToast.getValue())) {
            hasToast.setValue(false);
        } else {
            hasToast.setValue(true);
        }
        return hasToast;
    }

    public LiveData<Boolean> hasMilk() {
        System.out.println("sothony sadtano");
        if (Boolean.TRUE.equals(hasMilk.getValue())) {
            hasMilk.setValue(false);
        } else {
            hasMilk.setValue(true);
        }
        return hasMilk;
    }

    public LiveData<Boolean> hasOven() {
        System.out.println("sothony sadtano");
        if (Boolean.TRUE.equals(hasOven.getValue())) {
            hasOven.setValue(false);
        } else {
            hasOven.setValue(true);
        }
        return hasOven;
    }

    public LiveData<Boolean> hasSkillet() {
        System.out.println("sothony sadtano");
        if (Boolean.TRUE.equals(hasSkillet.getValue())) {
            hasSkillet.setValue(false);
        } else {
            hasSkillet.setValue(true);
        }
        return hasSkillet;
    }


}
