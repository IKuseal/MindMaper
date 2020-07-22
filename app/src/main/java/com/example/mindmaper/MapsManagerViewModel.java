package com.example.mindmaper;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapsManagerViewModel extends ViewModel {
    private int id = 0;

    private MutableLiveData<ArrayList<Map>> maps = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Map>> getMaps() {
        return maps;
    }

    public void setMaps(MutableLiveData<ArrayList<Map>> maps) {
        this.maps = maps;
    }

    public void temporaryGenerationMap(){
        ArrayList<Map> tempMaps = new ArrayList<>();

        ++id;
        Map map = new Map(id,"first");
        tempMaps.add(map);

        ++id;
        map = new Map(id,"second");
        tempMaps.add(map);

        ++id;
        map = new Map(id,"third");
        tempMaps.add(map);

        ++id;
        map = new Map(id,"fourth");
        tempMaps.add(map);

        getMaps().setValue(tempMaps);
    }

    public void loadMapsList(){
        //доработать
        temporaryGenerationMap();
    }

}