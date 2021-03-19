package com.ksu.icecreamrewardsystem.ui.dailypreorders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DailypreordersViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public DailypreordersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
