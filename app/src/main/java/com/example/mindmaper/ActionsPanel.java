package com.example.mindmaper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class ActionsPanel extends FrameLayout {
    private Button btnDel;
    public ActionsPanel(Context context){
        super(context);
        inflate(context,R.layout.actions_panel,this);
        btnDel = (Button)findViewById(R.id.delBtn);
    }

    public Button getBtnDel() {
        return btnDel;
    }

    public void setBtnDel(Button btnDel) {
        this.btnDel = btnDel;
    }
}
