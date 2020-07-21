package com.example.mindmaper;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class WorkActivity extends AppCompatActivity {
    private MainViewModel viewModel;
    private MindMapView mindMapView;
    private CentralNode centralNode;
    private ArrayList<ChildNode> mapNodes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mindMapView = (MindMapView)findViewById(R.id.mindMapView);
        Pair<Integer,Integer> minSize = getWorkFieldSize();
        Log.d("Ku","WorkFieldSize = " + minSize.first + " " + minSize.second);

        mindMapView.setMinimumWidth(minSize.first);
        mindMapView.setMinimumHeight(minSize.second);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getLastOperation().observe(this, new Observer<Pair<MainViewModel.Operation, Node>>() {
            @Override
            public void onChanged(Pair<MainViewModel.Operation, Node> operationNodePair) {
                //Log.d("Ku",operationNodePair.first.name());
                MainViewModel.Operation operation = operationNodePair.first;
                Node node = operationNodePair.second;

                switch (operation){
                    case MapOpened:{
                        centralNode = viewModel.getCentralNode();
                        mapNodes = viewModel.getMapNodes();

                        doBaseThingsWithNode(centralNode);
                        MapIterator iterator = new MapIterator(centralNode);
                        while(!iterator.atEnd()){
                            ChildNode childNode = iterator.next();
                            doBaseThingsWithNode(childNode);
                        }


                        mindMapView.setMap(centralNode,mapNodes);
                        //почему 2 раза?
                        Log.d("Ku","MapOpened in OnChangedOperation");
                        break;
                    }
                    default:{
                        Log.d("Ku","Something Wrong in OnChanged Last");
                    }
                }

            }
        });

        viewModel.openMap();
    }


    private void doBaseThingsWithNode(Node node){
        final NodeGraphicModule nodeGM = new NodeGraphicModule(this,node);
        node.setGraphicModule(nodeGM);
    }

    private Pair<Integer,Integer> getWorkFieldSize(){
        //screenSize
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metricsB = new DisplayMetrics();
        display.getMetrics(metricsB);

        //actionBarHeight
        final TypedArray styledAttributes = this.getTheme().obtainStyledAttributes( new int[] { android.R.attr.actionBarSize });
        int mActionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        //строка состояния height
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) { result = getResources().getDimensionPixelSize(resourceId); }

        return new Pair<>(metricsB.widthPixels,metricsB.heightPixels-mActionBarSize-result);
    }
}
