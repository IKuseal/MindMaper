package com.example.mindmaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.mindmaper.Database.EMap;

import java.util.ArrayList;
import java.util.List;

public class MapsManagerActivity extends AppCompatActivity implements EditTextDialogFragment.EditingTextResultReceiver {
    private MapsManagerViewModel viewModel;
    private MapsListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_manager);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.mapsManagerToolbar);
        setSupportActionBar(myToolbar);

        ListView listView = (ListView)findViewById(R.id.listViewMaps);

        adapter = new MapsListViewAdapter(this,R.layout.map_card,new ArrayList<EMap>());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                List<EMap> eMaps = viewModel.getMaps().getValue();
                EMap map = eMaps.get(position);
                Intent data = new Intent();
                Log.d("LLLL", "empa.id " + map.id);
                data.putExtra("id", map.id);
                setResult(RESULT_OK, data);
                finish();

            }
        });

        viewModel = new ViewModelProvider(this).get(MapsManagerViewModel.class);

        viewModel.getMaps().observe(this, new Observer<List<EMap>>() {
            @Override
            public void onChanged(List<EMap> eMaps) {
                Log.d("SSS","OnChanged ");

                if(viewModel.isMapWasCreated()){
                    EMap emap = eMaps.get(eMaps.size()-1);

                    Intent data = new Intent();
                    Log.d("LLLL", "empa.id " + emap.id);
                    data.putExtra("id", emap.id);
                    setResult(RESULT_OK, data);
                    finish();
                }
                else{
                    adapter.clear();
                    adapter.addAll(eMaps);
                    adapter.notifyDataSetChanged();
                }
            }
        });



        viewModel.loadMapsList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.maps_manager_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_create_map :{

                EditTextDialogFragment dialog = new EditTextDialogFragment();
                Bundle args = new Bundle();

                dialog.show(getSupportFragmentManager(), "custom");

                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void receiveEditingTextResult(String text) {

        viewModel.createMap(text);
    }
}