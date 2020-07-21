package com.example.mindmaper;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class MapIterator {
    ArrayDeque<ChildNode> processed;
    BrenchIterator brenchIterator;
    public MapIterator(CentralNode centralNode){
        initiate(centralNode);
    }
    public boolean atEnd(){
        if(brenchIterator == null) return true;
        return (processed.isEmpty() && brenchIterator.atEnd());
    }
    public ChildNode next(){
        if(atEnd()) return null;
        if(brenchIterator.atEnd()){
            brenchIterator.setNewBrench(processed.pollFirst());
        }
        return brenchIterator.next();
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
            if (brenchIterator == null) brenchIterator = new BrenchIterator(processed.pollFirst());
            else brenchIterator.setNewBrench(processed.pollFirst());
        }
    }
}
