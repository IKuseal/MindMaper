package com.example.mindmaper;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.TextView;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;

public class MindMapView extends AbsoluteLayout {

    final int verticalMargin = 300;
    final int horizontalMargin = 200;
    private final GestureDetector gestureDetector;
    private Pair<MainViewModel.Operation,Node> operation;
    private CentralNode centralNode;
    private ArrayList<ChildNode> mapNodes;
    private boolean isOnMeasureFirsTime = true;
    private ActionsPanel actionsPanel;

    final int yGap = 20;
    final int xGap = 80;

    public MindMapView(Context context){
        super(context);
        gestureDetector = new GestureDetector(context, new MyGestureListener());
        actionsPanel = new ActionsPanel(context);
        addView(actionsPanel);
    }
    public MindMapView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        gestureDetector = new GestureDetector(context, new MyGestureListener());
        actionsPanel = new ActionsPanel(context);
        addView(actionsPanel);
        actionsPanel.getBtnDel().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Kl","Click");
            }

        });

    }


    @Override
    public void addView(View child) {
        super.addView(child);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("Ku","OnMeasure");
        if(operation == null || isOnMeasureFirsTime == false) {
            Log.d("Ku", "operation.first = null");
            setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
            return;
        }
        Log.d("Ku","OnMeasure2");

        switch (operation.first){
            case MapOpened:{
                arrangeMap();
                Log.d("Ku","arrangeMapDone");
                break;
            }
        }
        //finding measuredDim of mindMapView
        Pair<Integer,Integer> maxDistances = findMaxDistances();
        Log.d("Ku","findMaxDistancesDone");

        int width = 2*maxDistances.first+2*horizontalMargin;
        int height = 2*maxDistances.second+2*verticalMargin;
        width = width>=getMinimumWidth()?width:getMinimumWidth();
        height = height>=getMinimumHeight()?height:getMinimumHeight();
        Log.d("Ku","rightBeforeSerMeasuredDim");

        setMeasuredDimension(width,height);
        Log.d("Ku","rightAfterSerMeasuredDim");
        isOnMeasureFirsTime = false;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(operation == null) return;

        actionsPanel.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
        int centerX = this.getMeasuredWidth()/2;
        int centerY = this.getMeasuredHeight()/2;

        NodeGraphicModule tempGM = getNodeGraphicModule(centralNode);
        int centerLabelX = centerX - tempGM.getMeasuredWidth()/2;
        int centerLabelY = centerY - tempGM.getMeasuredHeight()/2;

        int shiftX =  centerLabelX - (int)tempGM.getLeft();
        int shiftY =  centerLabelY - (int)tempGM.getTop();

        int newPosX = (int)tempGM.getLeft() + shiftX;
        int newPosY = (int)tempGM.getTop()+shiftY;
        tempGM.layout(newPosX,newPosY,
                newPosX+tempGM.getMeasuredWidth(),newPosY+tempGM.getMeasuredHeight());
        Log.d("Ku","CentralNodePos = " + tempGM.getLeft() + " " + tempGM.getTop());
        Log.d("Ku","CentralNodeXYPos = " + tempGM.getX() + " " + tempGM.getY());

        NodeGraphicModule centralLabelGM = tempGM;
        actionsPanel.layout(centralLabelGM.getLeft()-200,centralLabelGM.getTop()-200,
                centralLabelGM.getLeft()-200+actionsPanel.getMeasuredWidth(),
                centralLabelGM.getTop()-200+actionsPanel.getMeasuredHeight());

        int LBLeft = 0;
        for (int i = 0; i < mapNodes.size(); i++) {
            tempGM = NodeGraphicModule.extractNodeGraphicModule(mapNodes.get(i).getGraphicModule());
            newPosX = (int)tempGM.getLeft() + shiftX;
            newPosY = (int)tempGM.getTop()+shiftY;
            tempGM.layout(newPosX,newPosY,
                    newPosX+tempGM.getMeasuredWidth(),newPosY+tempGM.getMeasuredHeight());
            Log.d("Ki",tempGM.getText().toString());
            if(tempGM.getText().equals("LA")){
               LBLeft = tempGM.getLeft();
               Log.d("Ki","LBLeft " + LBLeft);
            }
            if(tempGM.getText().equals("LAB")){
                Log.d("Ki","Dist " + (LBLeft - (tempGM.getLeft()+tempGM.getMeasuredWidth())));
            }
        }
        Log.d("Ku","ParentSizes = " + this.getMeasuredWidth() + " " + this.getMeasuredHeight());

        switch (operation.first){
            case MapOpened:{
                int scrollY = 0;
                int scrollX = 0;
                if(getMeasuredHeight()<=getMinimumHeight()){
                }
                else{
                    scrollY = (getMeasuredHeight() - getMinimumHeight())/2;
                }

                if(getMeasuredWidth()<=getMinimumWidth()){
                }
                else{
                    scrollX = (getMeasuredWidth() - getMinimumWidth())/2;
                }
                scrollBy(scrollX,scrollY);
                break;
            }
            default:{
                this.scrollBy(shiftX,shiftY);
                break;
            }
        }
        isOnMeasureFirsTime = true;
        operation = null;
        Log.d("Ku","OnLayoutDone");
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event)
//    {
//        if (gestureDetector.onTouchEvent(event)) return true;
//        return true;
//    }
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            scrollBy((int)distanceX, (int)distanceY);
            return true;
        }
    }
    public void setMap(CentralNode centralNode,ArrayList<ChildNode> mapNodes){
        Log.d("Ku","SetMapStarted ");
        this.centralNode = centralNode;
        this.mapNodes = mapNodes;
        this.operation = new Pair<>(MainViewModel.Operation.MapOpened,null);

        for (int i = 0; i < mapNodes.size(); i++) {
            getNodeGraphicModule(mapNodes.get(i)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Kl","Click");
                }
            });
        }

        addNodeOnMap(centralNode);
        for (int i = 0; i < mapNodes.size(); i++) {
            addNodeOnMap(mapNodes.get(i));
        }

        this.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
    }

    private void addNodeOnMap(Node node){
        addView(NodeGraphicModule.extractNodeGraphicModule(node.getGraphicModule()));
    }

    private void measureNodes(Node node){
        NodeGraphicModule tempGM;
        if(node == centralNode){
            tempGM = NodeGraphicModule.extractNodeGraphicModule(centralNode.getGraphicModule());
            tempGM.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);

            for (int i = 0; i < mapNodes.size(); i++) {
                tempGM = getNodeGraphicModule(mapNodes.get(i));
                tempGM.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
            }
        }
        else{
            BrenchIterator iterator = new BrenchIterator((ChildNode)node);
            while (!iterator.atEnd()){
                tempGM = getNodeGraphicModule(iterator.next());
                tempGM.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
            }
        }
    }

    private void arrangeMap(){
        measureNodes(centralNode);
        calculateNodesFamiliySize(centralNode);

        getNodeGraphicModule(centralNode).setLeft(0);
        getNodeGraphicModule(centralNode).setTop(0);

        ArrayList<ChildNode> children = centralNode.getRightChildren();
        arrangeChildren(children,true);

        children = centralNode.getLeftChildren();
        arrangeChildren(children,false);

        MapIterator iterator = new MapIterator(centralNode);
        while(!iterator.atEnd()){
            ChildNode temp = iterator.next();
            arrangeChildren(temp.getChildren(),getNodeGraphicModule(temp).getIsOnTheRightSide());
        }

    }
    private void arrangeChildren(ArrayList<ChildNode> children, boolean isRight){
        if(children.isEmpty()) return;
        Node parent = children.get(0).getParent();
        NodeGraphicModule parentGM = getNodeGraphicModule(parent);

        int xChildrenEdge;
        if(isRight){
            xChildrenEdge = (int)parentGM.getLeft() + parentGM.getMeasuredWidth()+xGap;
        }
        else{
            xChildrenEdge = (int)parentGM.getLeft() - xGap;
        }

        int yParentCenter = (int)parentGM.getTop() + parentGM.getMeasuredHeight()/2;
        int yChildrenTopEdge = yParentCenter - calculateAncestorsSize(children)/2;
        int yChildTopEdge = yChildrenTopEdge;

        for (int i = 0; i < children.size(); i++) {
            ChildNode childNode = children.get(i);
            NodeGraphicModule childNodeGM = getNodeGraphicModule(childNode);

            // x
            if(isRight) childNodeGM.setLeft(xChildrenEdge);
            else {
                childNodeGM.setLeft(xChildrenEdge-childNodeGM.getMeasuredWidth());
            Log.d("Ke",childNode.getMainText()+" " + (childNodeGM.getLeft()+childNodeGM.getMeasuredWidth()-parentGM.getLeft()));}

            //y
            childNodeGM.setTop(yChildTopEdge + childNodeGM.getFamilySize()/2 - childNodeGM.getMeasuredHeight()/2);
            yChildTopEdge+=childNodeGM.getFamilySize();
        }

    }
    private void calculateNodesFamiliySize(Node node){
        Stack<ChildNode> mapNodesOrdEdge = new Stack<>();

        if(node == centralNode){
            MapIterator iterator = new MapIterator(centralNode);
            while (!iterator.atEnd()){
                mapNodesOrdEdge.push(iterator.next());
            }
        }
        else{
            BrenchIterator iterator = new BrenchIterator((ChildNode)node);
            while (!iterator.atEnd()){
                mapNodesOrdEdge.push(iterator.next());
            }
        }

        while(!mapNodesOrdEdge.isEmpty()){
            calculateNodeFamilySize(mapNodesOrdEdge.pop());
        }
    }

    private void calculateNodeFamilySize(ChildNode node){
        int ancestorsSize = calculateAncestorsSize(node.getChildren());
        int ownSize = getNodeGraphicModule(node).getMeasuredHeight() + 2*yGap;
        int nodeFamilySize = ancestorsSize>=ownSize ? ancestorsSize:ownSize;
        getNodeGraphicModule(node).setFamilySize(nodeFamilySize);
        //Log.d("Ku", "FamiliSize of node = " + node.getMainText() + " " + nodeFamilySize);
    }

    private int calculateAncestorsSize(ArrayList<ChildNode> children){
        int ancestorsSize = 0;
        for (int i = 0; i < children.size(); i++) {
            ancestorsSize+= getNodeGraphicModule(children.get(i)).getFamilySize();
        }
        return ancestorsSize;
    }

    private NodeGraphicModule getNodeGraphicModule(Node node){
        return NodeGraphicModule.extractNodeGraphicModule(node.getGraphicModule());
    }

    private Pair<Integer,Integer> findMaxDistances(){

        NodeGraphicModule centralNodeGM = getNodeGraphicModule(centralNode);
        int centerX = (int)centralNodeGM.getLeft() + (int)centralNodeGM.getMeasuredWidth()/2;
        int centerY = (int)centralNodeGM.getTop() + (int)centralNodeGM.getMeasuredHeight()/2;

        int yDistMax = 0, xDistMax = 0;
        int tempYDist, tempXDist;
        NodeGraphicModule tempGM;
        for (int i = 0; i < mapNodes.size(); i++) {
            tempGM = getNodeGraphicModule(mapNodes.get(i));

            tempXDist = Math.max(Math.abs(centerX - (int)tempGM.getLeft()),
                    Math.abs(centerX - ((int)tempGM.getLeft()+tempGM.getMeasuredWidth())));
            tempYDist = Math.max(Math.abs(centerY - (int)tempGM.getTop()),
                    Math.abs(centerY - ((int)tempGM.getTop()+tempGM.getMeasuredHeight())));

            xDistMax = Math.max(tempXDist,xDistMax);
            yDistMax = Math.max(tempYDist,yDistMax);
        }

        return new Pair<>(xDistMax,yDistMax);
    }
}
