package com.example.mindmaper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mindmaper.Database.EMap;

import java.util.List;

public class MapsListViewAdapter extends ArrayAdapter {

    private LayoutInflater inflater;
    private int layout;
    private List<EMap> maps;
    private Context context;

    public MapsListViewAdapter(Context context, int resource, List<EMap> maps) {
        super(context, resource, maps);
        this.context = context;
        this.maps = maps;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        EMap map = maps.get(position);

        Log.d("KKKKK",map.name+" ");
        viewHolder.textMapName.setText(map.name);

        return convertView;
    }
    private class ViewHolder {
        TextView textMapName;
        ViewHolder(View view){
            textMapName = (TextView)view.findViewById(R.id.textMapName);

        }
    }
}
