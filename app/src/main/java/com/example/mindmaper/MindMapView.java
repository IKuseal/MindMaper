package com.example.mindmaper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;


import java.util.ArrayList;
import java.util.Stack;

public class MindMapView extends AbsoluteLayout {
    public enum Direction{UP,DOWN,LEFT,RIGHT};
    final int verticalMargin = 300;
    final int horizontalMargin = 200;
    private final GestureDetector gestureDetector;
    private Pair<MainViewModel.Operation,Node> operation;
    private CentralNode centralNode;
    private ArrayList<ChildNode> mapNodes;
    private boolean isOnMeasureFirsTime = true;

    private ActionsPanel actionsPanel;
    private int actonsPanelYGap = 40;
    Node focusedNode;

    final int yGap = 20;
    final int xGap = 80;

    public MindMapView(Context context){
        super(context);
        gestureDetector = new GestureDetector(context, new MyGestureListener());
    }
    public MindMapView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        gestureDetector = new GestureDetector(context, new MyGestureListener());

    }


    @Override
    public void addView(View child) {
        super.addView(child);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("Ku","OnMeasureBeg");
        if(operation == null || isOnMeasureFirsTime == false) {
            Log.d("Ku", "operation.first = null");
            setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
            return;
        }
        Log.d("Ku","OnMeasure2");
        Node node = operation.second;

        switch (operation.first){
            case MapOpened:{
                arrangeMap();
                Log.d("Ku","arrangeMapDone");
                break;
            }
            case MainTextEdited:{
                if(node == centralNode){
                    rearrangeMapAfterCentralNodeSizeChanged();
                }
                else{
                    rearrangeBranchAfterSizeChanged(node);
                }
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
        Log.d("Ku","onLayoutBegin");
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

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        Log.d("Ki","OnTouchEvent");
        setFocusedNode(null);
        if (gestureDetector.onTouchEvent(event)) return true;
        return true;
    }
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener
    {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
            scrollBy((int)distanceX, (int)distanceY);
            return true;
        }
    }
    public void setMap(final CentralNode centralNode, final ArrayList<ChildNode> mapNodes){
        Log.d("Ku","SetMapStarted ");
        this.centralNode = centralNode;
        this.mapNodes = mapNodes;
        this.operation = new Pair<>(MainViewModel.Operation.MapOpened,null);

        getNodeGraphicModule(centralNode).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setFocusedNode(centralNode);
            }
        });
        for (int i = 0; i < mapNodes.size(); i++) {
            getNodeGraphicModule(mapNodes.get(i)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setFocusedNode(((NodeGraphicModule)view).getOwner());
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
            BranchIterator iterator = new BranchIterator((ChildNode)node);
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
            BranchIterator iterator = new BranchIterator((ChildNode)node);
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

    public Node getFocusedNode() {
        return focusedNode;
    }

    public void setFocusedNode(Node focusedNode) {
        this.focusedNode = focusedNode;
        if(focusedNode == null){
            actionsPanel.setVisibility(GONE);
        }
        else{
            NodeGraphicModule focusedNodeGM = getNodeGraphicModule(focusedNode);
            actionsPanel.setVisibility(VISIBLE);
            actionsPanel.bringToFront();

            if(focusedNode == centralNode) actionsPanel.setCentralNodeActions();
            else actionsPanel.setChildNodeActions();

            int left = focusedNodeGM.getLeft() - (actionsPanel.getMeasuredWidth()-focusedNodeGM.getMeasuredWidth())/2;
            int top = focusedNodeGM.getTop() - actionsPanel.getMeasuredHeight() - actonsPanelYGap;

            actionsPanel.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
            actionsPanel.layout(left,top,
                    left + actionsPanel.getMeasuredWidth(),top+actionsPanel.getMeasuredHeight());
        }
    }

    public void setActionsPanel(ActionsPanel actionsPanel) {
        this.actionsPanel = actionsPanel;
        addView(actionsPanel);
        actionsPanel.setVisibility(GONE);
    }

    public void update(Pair<MainViewModel.Operation,Node> operation){
        this.operation = operation;

        switch (this.operation.first){
            case MainTextEdited:{
                this.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
            }
        }
    }

    private void rearrangeMapAfterCentralNodeSizeChanged(){
        NodeGraphicModule centralNodeGM = getNodeGraphicModule(centralNode);

        centralNodeGM.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

        int shiftX = (centralNodeGM.getMeasuredWidth()-centralNodeGM.getWidth())/2;
        Log.d("Kp","Shift " + shiftX);

        rearrangeNodeAfterSizeChanged(centralNode);

        ArrayList<ChildNode> children = centralNode.getRightChildren();
        Direction dir;
        if(shiftX > 0) dir = Direction.RIGHT;
        else dir = Direction.LEFT;
        for (int i = 0; i < children.size(); i++) {
            shiftBranch(children.get(i),Math.abs(shiftX),dir,true);
        }

        children = centralNode.getLeftChildren();
        if(shiftX > 0) dir = Direction.LEFT;
        else dir = Direction.RIGHT;
        for (int i = 0; i < children.size(); i++) {
            shiftBranch(children.get(i),Math.abs(shiftX),dir,true);
        }
    }
    private void rearrangeBranchAfterSizeChanged(Node node){
        ChildNode branchHead = (ChildNode)node;
        NodeGraphicModule branchHeadGM = getNodeGraphicModule(branchHead);

        branchHeadGM.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

        int shiftX = branchHeadGM.getMeasuredWidth()-branchHeadGM.getWidth();

        //rearrangeNode
        Log.d("Kh","SizeChange " +branchHeadGM.getMeasuredWidth()+ " "+branchHeadGM.getWidth());
        rearrangeNodeAfterSizeChanged(node);

        //rearrangeBranch по X
        Direction dir;
        if(branchHeadGM.getIsOnTheRightSide() && shiftX>0 || !branchHeadGM.getIsOnTheRightSide() && shiftX<0) dir = Direction.RIGHT;
        else dir = Direction.LEFT;

        Log.d("Kh", "shiftX" + shiftX);
        shiftBranch(branchHead,Math.abs(shiftX),dir,false);

        //rearrangeBranch по y
        int oldFamilySize = branchHeadGM.getFamilySize();

        calculateNodeFamilySize(branchHead);
        int newFamilySize = branchHeadGM.getFamilySize();

        int familySizeChange = newFamilySize-oldFamilySize;

        rearrangeBranchAfterAncestorFamilySizeChanged(branchHead,familySizeChange);

    }

    private void rearrangeNodeAfterSizeChanged(Node node){
        if(node == centralNode){
            NodeGraphicModule nodeGM = getNodeGraphicModule(node);

            int shiftX = (nodeGM.getMeasuredWidth()-nodeGM.getWidth())/2;
            int shiftY = (nodeGM.getMeasuredHeight()-nodeGM.getHeight())/2;

            nodeGM.setLeft(nodeGM.getLeft()-shiftX);
            nodeGM.setTop(nodeGM.getTop()-shiftY);

        }
        else{
            ChildNode childNode = (ChildNode)node;
            NodeGraphicModule childNodeGM = getNodeGraphicModule(childNode);

            int shiftX = childNodeGM.getMeasuredWidth()-childNodeGM.getWidth();
            Log.d("Kh", "shiftX" + shiftX);
            int shiftY = (childNodeGM.getMeasuredHeight()-childNodeGM.getHeight())/2;

            if(!childNodeGM.getIsOnTheRightSide()) childNodeGM.setLeft(childNodeGM.getLeft()-shiftX);
            childNodeGM.setTop(childNodeGM.getTop()-shiftY);
        }
    }
    private void shiftBranch(ChildNode head, int shiftSize,Direction dir,boolean isWithHead){
        if(dir == Direction.UP || dir == Direction.LEFT){
            shiftSize*=-1;
        }

        BranchIterator iterator = new BranchIterator(head);
        NodeGraphicModule tempGM;
        if(!isWithHead) iterator.next();
        if(dir == Direction.LEFT || dir == Direction.RIGHT){
            while (!iterator.atEnd()){
                tempGM = getNodeGraphicModule(iterator.next());
                Log.d("Kh","WasHere"+tempGM.getText());
                tempGM.setLeft(tempGM.getLeft()+shiftSize);
            }
        }
        else{
            while (!iterator.atEnd()){
                tempGM = getNodeGraphicModule(iterator.next());
                tempGM.setTop(tempGM.getTop()+shiftSize);
            }
        }
    }

    private void rearrangeBranchAfterAncestorFamilySizeChanged(ChildNode ancestor, int changeSize){
        Node parent = ancestor.getParent();

        boolean isCentralNodeParent = parent instanceof CentralNode;

        ArrayList<ChildNode> children;
        ChildNode childParent = ancestor; //because doesn't allow not initialized;
        if(isCentralNodeParent){
            if(getNodeGraphicModule(ancestor).getIsOnTheRightSide())
                children = centralNode.getRightChildren();
            else  children = centralNode.getLeftChildren();
        }
        else{
            childParent = (ChildNode)parent;
            children = childParent.getChildren();
        }

        int indexOfAncestor = children.indexOf(ancestor);

        Direction dir;
        if(changeSize>0) dir = Direction.UP;
        else dir = Direction.DOWN;
        for(int i =0;i<indexOfAncestor;++i){
            shiftBranch(children.get(i),Math.abs(changeSize/2),dir,true);
        }

        if(changeSize>0) dir = Direction.DOWN;
        else dir = Direction.UP;
        for(int i =indexOfAncestor+1;i<children.size();++i){
            shiftBranch(children.get(i),Math.abs(changeSize/2),dir,true);
        }

        if(isCentralNodeParent) return;

        NodeGraphicModule childParetnGM = getNodeGraphicModule(childParent);
        int oldFamilySize = childParetnGM.getFamilySize();

        calculateNodeFamilySize(childParent);
        int newFamilySize = childParetnGM.getFamilySize();

        int familySizeChange = newFamilySize-oldFamilySize;

        rearrangeBranchAfterAncestorFamilySizeChanged(childParent,familySizeChange);

    }
}
