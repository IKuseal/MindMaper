package com.example.mindmaper;

import android.util.Log;

import java.util.ArrayList;

public class ChildNode extends Node{
    private Node parent;
    private ArrayList<ChildNode> children;

    public ChildNode(int nodeId, int styleId, String mainText, String attachedText) {
        super(nodeId,styleId,mainText,attachedText);
        children = new ArrayList<>();
    }

    public ArrayList<ChildNode> getChildren() {
        return children;
    }

    public void addSon(ChildNode node){
        if(this.getChildren().contains(node)) return;
        this.getChildren().add(node);
        node.parent = this;
    }

    public void addBrotherUp(ChildNode node){
        Node parent = this.getParent();
        if(parent instanceof CentralNode){
            CentralNode centralNode = (CentralNode)parent;
            if(centralNode.getRightChildren().contains(this)){
                int index = centralNode.getRightChildren().indexOf(this);
                centralNode.getRightChildren().add(index,node);
            }
            else{
                int index = centralNode.getLeftChildren().indexOf(this);
                centralNode.getLeftChildren().add(index,node);
            }
        }
        else{
            ChildNode childParent = (ChildNode)parent;
            int index = childParent.getChildren().indexOf(this);
            childParent.getChildren().add(index,node);
        }
        node.setParent(parent);
    }

    public void addBrotherDown(ChildNode node){
        Node parent = this.getParent();
        if(parent instanceof CentralNode){
            CentralNode centralNode = (CentralNode)parent;
            if(centralNode.getRightChildren().contains(this)){
                int index = centralNode.getRightChildren().indexOf(this);
                if(index+1 == centralNode.getRightChildren().size()) centralNode.getRightChildren().add(node);
                else centralNode.getRightChildren().add(index+1,node);
            }
            else{
                int index = centralNode.getLeftChildren().indexOf(this);
                if(index+1 == centralNode.getLeftChildren().size()) centralNode.getLeftChildren().add(node);
                else centralNode.getLeftChildren().add(index+1,node);
            }
        }
        else{
            ChildNode childParent = (ChildNode)parent;
            int index = childParent.getChildren().indexOf(this);
            if(index+1 == childParent.getChildren().size()) childParent.getChildren().add(node);
            else childParent.getChildren().add(index+1,node);
        }
        node.setParent(parent);
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
