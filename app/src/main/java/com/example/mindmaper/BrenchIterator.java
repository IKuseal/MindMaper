package com.example.mindmaper;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class BrenchIterator {
    ArrayDeque<ChildNode> processed;
    public BrenchIterator(ChildNode node){
        initiate(node);
    }
    public boolean atEnd(){
        return processed.isEmpty();
    }
    public ChildNode next(){
        if(atEnd()) return null;
        ChildNode node = processed.pollFirst();
        ArrayList<ChildNode> list = node.getChildren();
        if(!list.isEmpty()) processed.addAll(list);
        return node;
    }
    public void setNewBrench(ChildNode node){
        initiate(node);
    }
    private void initiate(ChildNode node){
        if(processed == null) processed = new ArrayDeque<>();
        else processed.clear();
        processed.addLast(node);
    }
}
