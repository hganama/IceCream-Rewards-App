package com.ksu.icecreamrewardsystem.ui.managevip;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManagevipViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ManagevipViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}