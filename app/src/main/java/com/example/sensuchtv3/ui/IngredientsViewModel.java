package com.example.sensuchtv3.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IngredientsViewModel extends ViewModel {

    private MutableLiveData<String> mText, morbussy;
    private MutableLiveData<Boolean> hasPotato, hasMilk, hasToast, hasSkillet, hasOven;

    public IngredientsViewModel() {
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
