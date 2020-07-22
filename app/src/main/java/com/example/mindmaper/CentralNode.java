package com.example.mindmaper;

import java.util.ArrayList;

public class CentralNode extends Node {
    private ArrayList<ChildNode> rightChildren;
    private ArrayList<ChildNode> leftChildren;

    public CentralNode(int nodeId, int styleId, String mainText, String attachedText) {
        super(nodeId,styleId,mainText,attachedText);
        rightChildren = new ArrayList<>();
        leftChildren = new ArrayList<>();
    }

    public ArrayList<ChildNode> getRightChildren() {
        return rightChildren;
    }

    public ArrayList<ChildNode> getLeftChildren() {
        return leftChildren;
    }

    public ArrayList<ChildNode> getChildren(){
        ArrayList<ChildNode> children = new ArrayList<>();
        children.addAll(getRightChildren());
        children.addAll(getLeftChildren());
        return children;
    }

    public void addRightSon(ChildNode node){
        if(getRightChildren().contains(node)) return;
        node.setParent(this);
        getRightChildren().add(node);
    }

    public void addLeftSon(ChildNode node){
        if(getLeftChildren().contains(node)) return;
        node.setParent(this);
        getLeftChildren().add(node);
    }

    @Override
    public void delSon(ChildNode childNode){
        if(getLeftChildren().contains(childNode)) getLeftChildren().remove(childNode);
        else getRightChildren().remove(childNode);
    }
}
