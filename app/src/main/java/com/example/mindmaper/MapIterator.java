package com.example.mindmaper;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class MapIterator {
    ArrayDeque<ChildNode> processed;
    BranchIterator branchIterator;
    public MapIterator(CentralNode centralNode){
        initiate(centralNode);
    }
    public boolean atEnd(){
        if(branchIterator == null) return true;
        return (processed.isEmpty() && branchIterator.atEnd());
    }
    public ChildNode next(){
        if(atEnd()) return null;
        if(branchIterator.atEnd()){
            branchIterator.setNewBrench(processed.pollFirst());
        }
        return branchIterator.next();
    }

    public void setNewMap(CentralNode centralNode){
        initiate(centralNode);
    }
    private void initiate(CentralNode centralNode){
        if(processed == null) processed = new ArrayDeque<>();
        else processed.clear();

        ArrayList<ChildNode> temp = centralNode.getRightChildren();
        if(!temp.isEmpty()) processed.addAll(temp);

        temp = centralNode.getLeftChildren();
        if(!temp.isEmpty()) processed.addAll(temp);

        if(!processed.isEmpty()) {
            if (branchIterator == null) branchIterator = new BranchIterator(processed.pollFirst());
            else branchIterator.setNewBrench(processed.pollFirst());
        }
    }
}
