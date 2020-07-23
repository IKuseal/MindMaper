package com.example.mindmaper;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.example.mindmaper.Database.App;
import com.example.mindmaper.Database.AppDatabase;
import com.example.mindmaper.Database.EMap;
import com.example.mindmaper.Database.EMapDao;
import com.example.mindmaper.Database.ENode;
import com.example.mindmaper.Database.ENodeDao;
import com.example.mindmaper.Database.EStyle;
import com.example.mindmaper.Database.EStyleDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapsManagerViewModel extends ViewModel {
    private int id = 0;
    private boolean isMapWasCreated = false;
    private AppDatabase db = App.getInstance().getDatabase();


    private MutableLiveData<List<EMap>> maps;

    public MutableLiveData<List<EMap>> getMaps() {
        if(maps == null){
            maps = new MutableLiveData<>();
            loadMapsList();
        }
        return maps;
    }

    public void setMaps(MutableLiveData<List<EMap>> maps) {
        this.maps = maps;
    }

    public void loadMapsList(){
        //доработать
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                EMapDao eMapDao = db.eMapDao();
                getMaps().postValue(eMapDao.getAll());
            }
        });
    }


    public void createMap(final String name){
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                EMapDao eMapDao = db.eMapDao();
                EMap eMap = new EMap();
                eMap.name = name;
                long mapId = eMapDao.insert(eMap);

                EStyleDao eStyleDao = db.eStyleDao();
                EStyle eStyle = new EStyle();
                eStyle.color = 100;
                long styleId = eStyleDao.insert(eStyle);

                ENodeDao eNodeDao = db.eNodeDao();
                ENode eNode = new ENode();
                eNode.mapId = mapId;
                eNode.styleId = styleId;
                eNode.position = 0;
                eNode.mainText = "Central";
                eNode.attachedText = "";
                long centralNodeId = eNodeDao.insert(eNode);

                Log.d("LLLL",centralNodeId + " ");

                eMapDao.updateCentralNodeId(mapId,centralNodeId);

            }
        });
        loadMapsList();
        isMapWasCreated = true;
    }

    public boolean isMapWasCreated() {
        return isMapWasCreated;
    }
}