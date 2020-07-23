package com.example.mindmaper;

import android.icu.text.LocaleDisplayNames;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.example.mindmaper.Database.App;
import com.example.mindmaper.Database.AppDatabase;
import com.example.mindmaper.Database.EMapDao;
import com.example.mindmaper.Database.ENode;
import com.example.mindmaper.Database.ENodeDao;
import com.example.mindmaper.Database.EStyleDao;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private int id = 30;

    private AppDatabase db = App.getInstance().getDatabase();
    private long mapId;

    public enum Operation{ MapOpened,MainTextEdited,NodeAdded,NodeDeled,PastingNode};

    private ArrayList<ChildNode> mapNodes = new ArrayList<>();
    private HashMap<Integer, Style> styles;
    private CentralNode centralNode;
    private ChildNode copiedNode;
    private long resultId;

    private MutableLiveData<Pair<Operation, Node>> lastOperation = new MutableLiveData<>();

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

    public void openMap(final long id){
        mapId = id;
        mapNodes.clear();
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                EMapDao eMapDao = db.eMapDao();
                ENodeDao eNodeDao = db.eNodeDao();
                EStyleDao eStyleDao = db.eStyleDao();

                Log.d("LLLL","ID " + id);
                long centralNodeId = eMapDao.getCentralNodeId(id);

                Log.d("LLLL","YEs" + centralNodeId );

                ENode eCentralNode = eNodeDao.getENodeWithId(centralNodeId);

                if(eCentralNode == null) Log.d("LLLL","YEs" + eCentralNode.mainText);

                centralNode = new CentralNode(eCentralNode.id,eCentralNode.styleId,eCentralNode.mainText,eCentralNode.attachedText);

                ArrayDeque<ChildNode> processedNodes = new ArrayDeque<>();

                List<ENode> list  = eNodeDao.getENodesWithParentId(centralNodeId);
                List<ENode> list1 = new ArrayList<>();
                List<ENode> list2 = new ArrayList<>();

                // разделяем left и right son
                for (int i = 0; i <list.size(); i++) {
                    if(list.get(i).position < 0){
                        list2.add(list.get(i));
                    }
                    else{
                        list1.add(list.get(i));
                    }
                }

                //обрабатываем right son
                ENode [] tempENodeArr = new ENode[list1.size()];

                ENode tempENode;
                for (int i = 0; i < list1.size(); i++) {
                    tempENodeArr[list1.get(i).position] = list1.get(i);
                }

                ChildNode tempChildNode;
                for (int i = 0; i < list1.size(); i++) {
                    tempENode = tempENodeArr[i];
                    tempChildNode = new ChildNode(tempENode.id,tempENode.styleId,tempENode.mainText,tempENode.attachedText);
                    mapNodes.add(tempChildNode);
                    processedNodes.addLast(tempChildNode);
                    centralNode.addRightSon(tempChildNode);
                }

                //обрабатываем left son
                tempENodeArr = new ENode[list2.size()];

                for (int i = 0; i < list2.size(); i++) {
                    tempENodeArr[-list2.get(i).position] = list2.get(i);
                }

                for (int i = 0; i < list2.size(); i++) {
                    tempENode = tempENodeArr[i];
                    tempChildNode = new ChildNode(tempENode.id,tempENode.styleId,tempENode.mainText,tempENode.attachedText);
                    mapNodes.add(tempChildNode);
                    processedNodes.addLast(tempChildNode);
                    centralNode.addLeftSon(tempChildNode);
                }

                ChildNode parent;
                while (!processedNodes.isEmpty()){
                    parent = processedNodes.pollFirst();

                    list = eNodeDao.getENodesWithParentId(parent.getNodeId());
                    tempENodeArr = new ENode[list.size()];

                    for (int i = 0; i < list.size(); i++) {
                        tempENodeArr[list.get(i).position] = list.get(i);
                    }

                    for (int i = 0; i < list.size(); i++) {
                        tempENode = tempENodeArr[i];
                        tempChildNode = new ChildNode(tempENode.id,tempENode.styleId,tempENode.mainText,tempENode.attachedText);
                        mapNodes.add(tempChildNode);
                        processedNodes.addLast(tempChildNode);
                        parent.addSon(tempChildNode);
                    }
                }

                getLastOperation().postValue(new Pair<Operation,Node>(Operation.MapOpened,null));
            }
        });

    }

    public void editMainText(final Node node,final String text){
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                ENodeDao eNodeDao = db.eNodeDao();

                eNodeDao.updateMainText(node.getNodeId(),text);
                node.setMainText(text);

                getLastOperation().postValue(new Pair<Operation, Node>(Operation.MainTextEdited,node));


            }
        });

    }

    public void edtiAttachedText(final Node node,final String text){

        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                ENodeDao eNodeDao = db.eNodeDao();

                eNodeDao.updateAttachedText(node.getNodeId(),text);
                node.setAttachedText(text);

            }
        });
    }

    public void addRightSonOfCentralNode(){
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                ENodeDao eNodeDao = db.eNodeDao();
                ENode eNode = new ENode();
                eNode.parentId = centralNode.getNodeId();
                eNode.styleId  = centralNode.getStyleId();
                eNode.mapId    = mapId;
                eNode.position = centralNode.getRightChildren().size();
                eNode.mainText = " ";
                eNode.attachedText = "";
                long id = eNodeDao.insert(eNode);

                ChildNode addedNode = new ChildNode(id,centralNode.getStyleId()," ","");
                centralNode.addRightSon(addedNode);
                mapNodes.add(addedNode);

                getLastOperation().postValue(new Pair<Operation, Node>(Operation.NodeAdded,addedNode));

            }
        });
    }

    public void addLeftSonOfCentralNode(){
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                ENodeDao eNodeDao = db.eNodeDao();
                ENode eNode = new ENode();
                eNode.parentId = centralNode.getNodeId();
                eNode.styleId  = centralNode.getStyleId();
                eNode.mapId    = mapId;
                eNode.position = centralNode.getLeftChildren().size();
                eNode.mainText = " ";
                eNode.attachedText = "";
                long id = eNodeDao.insert(eNode);

                ChildNode addedNode = new ChildNode(id,centralNode.getStyleId()," ","");
                centralNode.addLeftSon(addedNode);
                mapNodes.add(addedNode);

                getLastOperation().postValue(new Pair<Operation, Node>(Operation.NodeAdded,addedNode));

            }
        });
    }

    public void addSon(final ChildNode parent){
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                ENodeDao eNodeDao = db.eNodeDao();
                ENode eNode = new ENode();
                eNode.parentId = parent.getNodeId();
                eNode.styleId  = parent.getStyleId();
                eNode.mapId    = mapId;
                eNode.position = parent.getChildren().size();
                eNode.mainText = " ";
                eNode.attachedText = "";
                long id = eNodeDao.insert(eNode);

                ChildNode addedNode = new ChildNode(id,parent.getStyleId()," ","");
                parent.addSon(addedNode);
                mapNodes.add(addedNode);

                getLastOperation().postValue(new Pair<Operation, Node>(Operation.NodeAdded,addedNode));

            }
        });

    }

//    public void addSon( ChildNode parent){
//        ++id;
//        ChildNode addedNode = new ChildNode(id,parent.getStyleId()," ","");
//        parent.addSon(addedNode);
//        mapNodes.add(addedNode);
//
//        getLastOperation().setValue(new Pair<Operation, Node>(Operation.NodeAdded,addedNode));
//
//    }

    public void addUpBrother(final ChildNode brother){
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                ENodeDao eNodeDao = db.eNodeDao();
                ENode eNode = new ENode();
                Node parent = brother.getParent();
                eNode.parentId = parent.getNodeId();
                eNode.styleId  = parent.getStyleId();
                eNode.mapId    = mapId;

                int position;
                ArrayList<ChildNode> children;
                if(parent == centralNode){
                    children = centralNode.getRightChildren();
                    if(children.contains(brother)){
                    }
                    else {
                        children = centralNode.getLeftChildren();
                    }
                }
                else{
                    ChildNode parentChildNode =(ChildNode)parent;
                    children = parentChildNode.getChildren();
                }
                position = children.indexOf(brother);
                eNode.position = position;
                eNode.mainText = " ";
                eNode.attachedText = "";
                long id = eNodeDao.insert(eNode);

                ChildNode addedNode = new ChildNode(id,parent.getStyleId()," ","");
                children.add(position,addedNode);
                addedNode.setParent(parent);

                mapNodes.add(addedNode);

                updatePosition(children);

                getLastOperation().postValue(new Pair<Operation, Node>(Operation.NodeAdded,addedNode));

            }
        });
    }

    public void addDownBrother(final ChildNode brother){
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                ENodeDao eNodeDao = db.eNodeDao();
                ENode eNode = new ENode();
                Node parent = brother.getParent();
                eNode.parentId = parent.getNodeId();
                eNode.styleId  = parent.getStyleId();
                eNode.mapId    = mapId;

                int position;
                ArrayList<ChildNode> children;
                if(parent == centralNode){
                    children = centralNode.getRightChildren();
                    if(children.contains(brother)){
                    }
                    else {
                        children = centralNode.getLeftChildren();
                    }
                }
                else{
                    ChildNode parentChildNode =(ChildNode)parent;
                    children = parentChildNode.getChildren();
                }
                position = children.indexOf(brother);
                eNode.position = position+1;
                eNode.mainText = " ";
                eNode.attachedText = "";
                long id = eNodeDao.insert(eNode);

                ChildNode addedNode = new ChildNode(id,parent.getStyleId()," ","");
                children.add(position+1,addedNode);
                addedNode.setParent(parent);

                mapNodes.add(addedNode);

                updatePosition(children);

                getLastOperation().postValue(new Pair<Operation, Node>(Operation.NodeAdded,addedNode));

            }
        });
    }



    public void delNode(final ChildNode node){
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                ENodeDao eNodeDao = db.eNodeDao();
                eNodeDao.deleteENode(node.getNodeId());

                node.getParent().delSon(node);

                BranchIterator iterator = new BranchIterator(node);
                while (!iterator.atEnd()){
                    mapNodes.remove(iterator.next());
                }

                lastOperation.postValue(new Pair<Operation, Node>(Operation.NodeDeled,node));

            }
        });
    }
//    public void delNode(final ChildNode node){
//        node.getParent().delSon(node);
//
//        BranchIterator iterator = new BranchIterator(node);
//        while (!iterator.atEnd()){
//            mapNodes.remove(iterator.next());
//        }
//
//        lastOperation.setValue(new Pair<Operation, Node>(Operation.NodeDeled,node));
//    }

    public void cutNode(ChildNode node){
        node.getParent().delSon(node);

        BranchIterator iterator = new BranchIterator(node);
        while (!iterator.atEnd()){
            mapNodes.remove(iterator.next());
        }

        copiedNode = node;

        lastOperation.setValue(new Pair<Operation, Node>(Operation.NodeDeled,node));
    }

    public void copyNode(ChildNode node){
//        HashMap<Integer,ChildNode> newParents = new HashMap<>();
//        BranchIterator iterator = new BranchIterator(node);
//
//        ++id;
//        ChildNode nodeCopy = new ChildNode(id,node.getStyleId(),node.getMainText(),node.getAttachedText());
//        newParents.put(node.getNodeId(),nodeCopy);
//        iterator.next();
//        while (!iterator.atEnd()){
//            ChildNode childNode = iterator.next();
//            ++id;
//            ChildNode copy = new ChildNode(id,childNode.getStyleId(),childNode.getMainText(),childNode.getAttachedText());
//            newParents.get(childNode.getParent().getNodeId()).addSon(copy);
//            newParents.put(childNode.getNodeId(),copy);
//        }
//
//        copiedNode = nodeCopy;
    }

    public void startPastingNode(Node node){
        if(copiedNode == null) return;
        ChildNode temp = copiedNode;
        copyNode(copiedNode);

        if(node == centralNode) centralNode.addRightSon(temp);
        else ((ChildNode)node).addSon(temp);

        BranchIterator iterator = new BranchIterator(temp);
        while (!iterator.atEnd()){
            mapNodes.add(iterator.next());
        }

        getLastOperation().setValue(new Pair<Operation, Node>(Operation.PastingNode,temp));
    }

    private void updatePosition(ArrayList<ChildNode> children){
        ENodeDao eNodeDao = db.eNodeDao();
        for (int i = 0; i < children.size(); i++) {
            eNodeDao.updatePosition(children.get(i).getNodeId(),i);
        }
    }
}
