package com.example.mindmaper;

import android.content.Context;
import android.widget.Button;
import android.widget.FrameLayout;

public class ActionsPanel extends FrameLayout {
    private Button btnSon;
    private Button btnUpBrother;
    private Button btnDownBrother;
    private Button btnDel;
    private Button btnLeftSon;
    private Button btnRightSon;
    private Button btnEditMainText;
    private Button btnEditAttachedText;
    private Button btnStyle;
    private Button btnCopy;
    private Button btnPast;
    private Button btnCut;
    public ActionsPanel(Context context){
        super(context);
        inflate(context,R.layout.actions_panel,this);
        btnSon = (Button)findViewById(R.id.btnSon);
        btnUpBrother = (Button)findViewById(R.id.btnUpBrother);
        btnDownBrother = (Button)findViewById(R.id.btnDownBrother);
        btnDel = (Button)findViewById(R.id.btnDel);
        btnLeftSon = (Button)findViewById(R.id.btnLeftSon);
        btnRightSon = (Button)findViewById(R.id.btnRightSon);
        btnEditMainText = (Button)findViewById(R.id.btnEditMainText);
        btnEditAttachedText = (Button)findViewById(R.id.btnEditAttachedText);
        btnStyle = (Button)findViewById(R.id.btnStyle);
        btnCopy = (Button)findViewById(R.id.btnCopy);
        btnPast = (Button)findViewById(R.id.btnPast);
        btnCut = (Button)findViewById(R.id.btnCut);
    }

    public void setCentralNodeActions(){
        btnSon.setVisibility(GONE);
        btnDownBrother.setVisibility(GONE);
        btnUpBrother.setVisibility(GONE);
        btnDel.setVisibility(GONE);
        btnCut.setVisibility(GONE);

        btnLeftSon.setVisibility(VISIBLE);
        btnRightSon.setVisibility(VISIBLE);
    }

    public void setChildNodeActions(){
        btnSon.setVisibility(VISIBLE);
        btnDownBrother.setVisibility(VISIBLE);
        btnUpBrother.setVisibility(VISIBLE);
        btnDel.setVisibility(VISIBLE);
        btnCut.setVisibility(VISIBLE);

        btnLeftSon.setVisibility(GONE);
        btnRightSon.setVisibility(GONE);
    }

    public Button getBtnSon() {
        return btnSon;
    }

    public Button getBtnUpBrother() {
        return btnUpBrother;
    }

    public Button getBtnDownBrother() {
        return btnDownBrother;
    }

    public Button getBtnDel() {
        return btnDel;
    }

    public Button getBtnLeftSon() {
        return btnLeftSon;
    }

    public Button getBtnRightSon() {
        return btnRightSon;
    }

    public Button getBtnEditMainText() {
        return btnEditMainText;
    }

    public Button getBtnEditAttachedText() {
        return btnEditAttachedText;
    }

    public Button getBtnStyle() {
        return btnStyle;
    }

    public Button getBtnCopy() {
        return btnCopy;
    }

    public Button getBtnPast() {
        return btnPast;
    }

    public Button getBtnCut() {
        return btnCut;
    }
}
