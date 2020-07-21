package com.example.mindmaper;

import android.util.Log;
import android.util.Pair;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    public enum Operation{ MapOpened};

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
        int i = 0;
        ArrayList<ChildNode> tempMapNodes = new ArrayList<>();
        CentralNode tempCentralNode = new CentralNode(i,0,"Central","Central");


        //A
        ++i;
        ChildNode A = new ChildNode(i,0,"A","A");
        tempCentralNode.addRightSon(A);
        tempMapNodes.add(A);

        //B
        ++i;
        ChildNode B = new ChildNode(i,0,"B","B");
        tempCentralNode.addRightSon(B);
        tempMapNodes.add(B);

        //C
        ++i;
        ChildNode C = new ChildNode(i,0,"C","C");
        tempCentralNode.addRightSon(C);
        tempMapNodes.add(C);

        //LA
        ++i;
        ChildNode LA = new ChildNode(i,0,"LA","LA");
        tempCentralNode.addLeftSon(LA);
//        tempMapNodes.put(LA.getNodeId(),LA);
        tempMapNodes.add(LA);

        //LB
        ++i;
        ChildNode LB = new ChildNode(i,0,"LB","LB");
        tempCentralNode.addLeftSon(LB);
//        tempMapNodes.put(LB.getNodeId(),LB);
        tempMapNodes.add(LB);

        //LC
        ++i;
        ChildNode LC = new ChildNode(i,0,"LC","LC");
        tempCentralNode.addLeftSon(LC);
        //tempMapNodes.put(LC.getNodeId(),LC);
        tempMapNodes.add(LC);

        //AA
        ++i;
        ChildNode AA = new ChildNode(i,0,"AA","AA");
        A.addSon(AA);
//        tempMapNodes.put(AA.getNodeId(),AA);
        tempMapNodes.add(AA);

        //AB
        ++i;
        ChildNode AB = new ChildNode(i,0,"AB","AB");
        A.addSon(AB);
//        tempMapNodes.put(AB.getNodeId(),AB);
        tempMapNodes.add(AB);

        //AC
        ++i;
        ChildNode AC = new ChildNode(i,0,"AC","AC");
        A.addSon(AC);
//        tempMapNodes.put(AC.getNodeId(),AC);
        tempMapNodes.add(AC);


        //AAA
        ++i;
        ChildNode AAA = new ChildNode(i,0,"AAA","AAA");
        AA.addSon(AAA);
//        tempMapNodes.put(AAA.getNodeId(),AAA);
        tempMapNodes.add(AAA);

        //AAB
        ++i;
        ChildNode AAB = new ChildNode(i,0,"AAB","AAB");
        AA.addSon(AAB);
//        tempMapNodes.put(AAB.getNodeId(),AAB);
        tempMapNodes.add(AAB);

        //ACA
        ++i;
        ChildNode ACA = new ChildNode(i,0,"ACA","ACA");
        AC.addSon(ACA);
//        tempMapNodes.put(ACA.getNodeId(),ACA);
        tempMapNodes.add(ACA);

        //ACB
        ++i;
        ChildNode ACB = new ChildNode(i,0,"ACB","ACB");
        AC.addSon(ACB);
//        tempMapNodes.put(ACB.getNodeId(),ACB);
        tempMapNodes.add(ACB);

        //ACC
        ++i;
        ChildNode ACC = new ChildNode(i,0,"ACC","ACC");
        AC.addSon(ACC);
//        tempMapNodes.put(ACC.getNodeId(),ACC);
        tempMapNodes.add(ACC);

        //BA
        ++i;
        ChildNode BA = new ChildNode(i,0,"BA","BA");
        B.addSon(BA);
//        tempMapNodes.put(BA.getNodeId(),BA);
        tempMapNodes.add(BA);

        //BB
        ++i;
        ChildNode BB = new ChildNode(i,0,"BB","BB");
        B.addSon(BB);
//        tempMapNodes.put(BB.getNodeId(),BB);
        tempMapNodes.add(BB);

        //BBA
        ++i;
        ChildNode BBA = new ChildNode(i,0,"BBA","BBA");
        BB.addSon(BBA);
//        tempMapNodes.put(BBA.getNodeId(),BBA);
        tempMapNodes.add(BBA);

        //LAA
        ++i;
        ChildNode LAA = new ChildNode(i,0,"LAA","LAA");
        LA.addSon(LAA);
//        tempMapNodes.put(LAA.getNodeId(),LAA);
        tempMapNodes.add(LAA);

        //LAB
        ++i;
        ChildNode LAB = new ChildNode(i,0,"LAB","LAB");
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

}
