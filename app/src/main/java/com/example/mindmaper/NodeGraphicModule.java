package com.example.mindmaper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

public class NodeGraphicModule extends AppCompatTextView{
    private int familySize = 0;
    private Node owner;
    private boolean isOnTheRightSide;
    private Path path;

    public NodeGraphicModule(Context context){
        super(context);
    }
    public NodeGraphicModule(Context context, Node owner){
        super(context);
        this.setText(owner.getMainText());
        this.owner = owner;
        defineIsOnTheRight();
        setBackgroundColor(Color.GREEN);
        path = new Path();

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

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
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

    public Node getOwner() {
        return owner;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        path.reset();
        if(owner instanceof CentralNode) return;
        Node parent = ((ChildNode)owner).getParent();

        NodeGraphicModule parentGM = NodeGraphicModule.extractNodeGraphicModule(parent.getGraphicModule());
        NodeGraphicModule ownerGM = NodeGraphicModule.extractNodeGraphicModule(owner.getGraphicModule());

        if(getIsOnTheRightSide()){
            path.moveTo(parentGM.getLeft()+parentGM.getMeasuredWidth(),
                    parentGM.getTop()+parentGM.getMeasuredHeight()/2);

            path.lineTo(ownerGM.getLeft(),ownerGM.getTop()+ownerGM.getMeasuredHeight()/2);
        }
        else{
            path.moveTo(parentGM.getLeft(),
                    parentGM.getTop()+parentGM.getMeasuredHeight()/2);

            path.lineTo(ownerGM.getLeft()+ownerGM.getMeasuredWidth(),ownerGM.getTop()+ownerGM.getMeasuredHeight()/2);
        }
    }


}
