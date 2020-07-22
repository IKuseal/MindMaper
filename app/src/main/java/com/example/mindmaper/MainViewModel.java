package com.example.mindmaper;

import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private int id;

    public enum Operation{ MapOpened,MainTextEdited,NodeAdded,NodeDeled};

    private ArrayList<ChildNode> mapNodes;
    private HashMap<Integer,Style> styles;
    private CentralNode centralNode;

    private MutableLiveData<Pair<Operation,Node>> lastOperation = new MutableLiveData<>();

    public ArrayList<ChildNode> getMapNodes() {
        return mapNodes;
    }

    public void setMapNodes(ArrayList<ChildNode> mapNodes) {
        this.mapNodes = mapNodes;
    }

    public HashMap<Integer, Style> getStyles() {
        return styles;
    }

    public void setStyles(HashMap<Integer, Style> styles) {
        this.styles = styles;
    }

    public CentralNode getCentralNode() {
        return centralNode;
    }

    public void setCentralNode(CentralNode centralNode) {
        this.centralNode = centralNode;
    }

    public MutableLiveData<Pair<Operation, Node>> getLastOperation() {
        return lastOperation;
    }

    public void setLastOperation(MutableLiveData<Pair<Operation, Node>> lastOperation) {
        this.lastOperation = lastOperation;
    }

    public void temporaryGenerationMap(){
        id = 0;
        ArrayList<ChildNode> tempMapNodes = new ArrayList<>();
        CentralNode tempCentralNode = new CentralNode(id,0,"Central","Central");


        //A
        ++id;
        ChildNode A = new ChildNode(id,0,"A","A");
        tempCentralNode.addRightSon(A);
        tempMapNodes.add(A);

        //B
        ++id;
        ChildNode B = new ChildNode(id,0,"B","B");
        tempCentralNode.addRightSon(B);
        tempMapNodes.add(B);

        //C
        ++id;
        ChildNode C = new ChildNode(id,0,"C","C");
        tempCentralNode.addRightSon(C);
        tempMapNodes.add(C);

        //LA
        ++id;
        ChildNode LA = new ChildNode(id,0,"LA","LA");
        tempCentralNode.addLeftSon(LA);
//        tempMapNodes.put(LA.getNodeId(),LA);
        tempMapNodes.add(LA);

        //LB
        ++id;
        ChildNode LB = new ChildNode(id,0,"LB","LB");
        tempCentralNode.addLeftSon(LB);
//        tempMapNodes.put(LB.getNodeId(),LB);
        tempMapNodes.add(LB);

        //LC
        ++id;
        ChildNode LC = new ChildNode(id,0,"LC","LC");
        tempCentralNode.addLeftSon(LC);
        //tempMapNodes.put(LC.getNodeId(),LC);
        tempMapNodes.add(LC);

        //AA
        ++id;
        ChildNode AA = new ChildNode(id,0,"AA","AA");
        A.addSon(AA);
//        tempMapNodes.put(AA.getNodeId(),AA);
        tempMapNodes.add(AA);

        //AB
        ++id;
        ChildNode AB = new ChildNode(id,0,"AB","AB");
        A.addSon(AB);
//        tempMapNodes.put(AB.getNodeId(),AB);
        tempMapNodes.add(AB);

        //AC
        ++id;
        ChildNode AC = new ChildNode(id,0,"AC","AC");
        A.addSon(AC);
//        tempMapNodes.put(AC.getNodeId(),AC);
        tempMapNodes.add(AC);


        //AAA
        ++id;
        ChildNode AAA = new ChildNode(id,0,"AAA","AAA");
        AA.addSon(AAA);
//        tempMapNodes.put(AAA.getNodeId(),AAA);
        tempMapNodes.add(AAA);

        //AAB
        ++id;
        ChildNode AAB = new ChildNode(id,0,"AAB","AAB");
        AA.addSon(AAB);
//        tempMapNodes.put(AAB.getNodeId(),AAB);
        tempMapNodes.add(AAB);

        //ACA
        ++id;
        ChildNode ACA = new ChildNode(id,0,"ACA","ACA");
        AC.addSon(ACA);
//        tempMapNodes.put(ACA.getNodeId(),ACA);
        tempMapNodes.add(ACA);

        //ACB
        ++id;
        ChildNode ACB = new ChildNode(id,0,"ACB","ACB");
        AC.addSon(ACB);
//        tempMapNodes.put(ACB.getNodeId(),ACB);
        tempMapNodes.add(ACB);

        //ACC
        ++id;
        ChildNode ACC = new ChildNode(id,0,"ACC","ACC");
        AC.addSon(ACC);
//        tempMapNodes.put(ACC.getNodeId(),ACC);
        tempMapNodes.add(ACC);

        //BA
        ++id;
        ChildNode BA = new ChildNode(id,0,"BA","BA");
        B.addSon(BA);
//        tempMapNodes.put(BA.getNodeId(),BA);
        tempMapNodes.add(BA);

        //BB
        ++id;
        ChildNode BB = new ChildNode(id,0,"BB","BB");
        B.addSon(BB);
//        tempMapNodes.put(BB.getNodeId(),BB);
        tempMapNodes.add(BB);

        //BBA
        ++id;
        ChildNode BBA = new ChildNode(id,0,"BBA","BBA");
        BB.addSon(BBA);
//        tempMapNodes.put(BBA.getNodeId(),BBA);
        tempMapNodes.add(BBA);

        //LAA
        ++id;
        ChildNode LAA = new ChildNode(id,0,"LAA","LAA");
        LA.addSon(LAA);
//        tempMapNodes.put(LAA.getNodeId(),LAA);
        tempMapNodes.add(LAA);

        //LAB
        ++id;
        ChildNode LAB = new ChildNode(id,0,"LAB","LAB");
        LA.addSon(LAB);
//        tempMapNodes.put(LAB.getNodeId(),LAB);
        tempMapNodes.add(LAB);

        MapIterator iterator = new MapIterator(tempCentralNode);
        while (!iterator.atEnd()){
            Log.d("Ka",iterator.next().getMainText());
        }

        this.setCentralNode(tempCentralNode);
        this.setMapNodes(tempMapNodes);
    }

    public void openMap(){
        //доработать
        temporaryGenerationMap();
        getLastOperation().setValue(new Pair<Operation,Node>(Operation.MapOpened,null));
    }

    public void editMainText(Node node,String text){
        node.setMainText(text);

        getLastOperation().setValue(new Pair<Operation,Node>(Operation.MainTextEdited,node));
    }

    public void edtiAttachedText(Node node,String text){
        node.setAttachedText(text);
    }

    public void addRightSonOfCentralNode(){
        ++id;
        ChildNode added = new ChildNode(id,0,"node","node");
        centralNode.addRightSon(added);
        mapNodes.add(added);
        getLastOperation().setValue(new Pair<Operation, Node>(Operation.NodeAdded,added));
    }

    public void addLeftSonOfCentralNode(){
        ++id;
        ChildNode added = new ChildNode(id,0,"node","node");
        centralNode.addLeftSon(added);
        mapNodes.add(added);
        getLastOperation().setValue(new Pair<Operation, Node>(Operation.NodeAdded,added));
    }

    public void addSon(ChildNode parent){
        ++id;
        ChildNode added = new ChildNode(id,0,"node","node");
        parent.addSon(added);
        mapNodes.add(added);
        Log.d("Kr",mapNodes.size()+" ");
        getLastOperation().setValue(new Pair<Operation, Node>(Operation.NodeAdded,added));

    }

    public void addUpBrother(ChildNode brother){
        ++id;
        ChildNode added = new ChildNode(id,0,"node","node");
        brother.addBrotherUp(added);
        mapNodes.add(added);
        getLastOperation().setValue(new Pair<Operation, Node>(Operation.NodeAdded,added));
    }

    public void addDownBrother(ChildNode brother){
        ++id;
        ChildNode added = new ChildNode(id,0,"node","node");
        brother.addBrotherDown(added);
        mapNodes.add(added);
        getLastOperation().setValue(new Pair<Operation, Node>(Operation.NodeAdded,added));
    }

    public void delNode(ChildNode node){
        node.getParent().delSon(node);

        BranchIterator iterator = new BranchIterator(node);
        while (!iterator.atEnd()){
            mapNodes.remove(iterator.next());
        }

        lastOperation.setValue(new Pair<Operation, Node>(Operation.NodeDeled,node));
    }
}
