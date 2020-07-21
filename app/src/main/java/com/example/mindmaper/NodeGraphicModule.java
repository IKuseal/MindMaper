package com.example.mindmaper;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

public class NodeGraphicModule extends AppCompatTextView{
    private int familySize = 0;
    Node owner;
    private boolean isOnTheRightSide;

    public NodeGraphicModule(Context context){
        super(context);
    }
    public NodeGraphicModule(Context context, Node owner){
        super(context);
        this.setText(owner.getMainText());
        this.owner = owner;
        defineIsOnTheRight();
        setBackgroundColor(Color.GREEN);


    }
    static NodeGraphicModule extractNodeGraphicModule(Object object){
        return (NodeGraphicModule)object;
    }

    public int getFamilySize() {
        return familySize;
    }

    public void setFamilySize(int familySize) {
        this.familySize = familySize;
    }
    public boolean getIsOnTheRightSide(){
        return isOnTheRightSide;
    }

    //можно использовать только в случае, если у parent уже вычислено
    private void defineIsOnTheRight(){
        if(owner instanceof CentralNode) isOnTheRightSide = false;
        else{
            Node parent = ((ChildNode)owner).getParent();
            if(parent instanceof CentralNode){
                CentralNode temp = (CentralNode)parent;
                isOnTheRightSide = temp.getRightChildren().contains(owner);
            }
            else{
                ChildNode ownerParent = (ChildNode)parent;
                if(ownerParent == null) Log.d("Ku","defineIsOnThe" + owner.getMainText());
                TextView ownerParentGM = NodeGraphicModule.extractNodeGraphicModule(ownerParent.getGraphicModule());
                this.isOnTheRightSide = ((NodeGraphicModule) ownerParentGM).getIsOnTheRightSide();
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        defineIsOnTheRight();

    }


}
