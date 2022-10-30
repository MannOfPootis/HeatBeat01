package com.example.heatbeat.ui.main;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.heatbeat.MainActivity;
import com.example.heatbeat.ModelAudio;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            //ArrayList<ModelAudio> x = MainActivity.audioArrayList;



            return "Hello world from segfhgdfcgfdxction: "+ input ;


        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}