package com.example.mindmaper;

public class Node {
    protected int nodeId;
    protected int styleId;
    protected String mainText;
    protected String attachedText;
    protected Object graphicModule;


    public Node(int nodeId, int styleId, String mainText, String attachedText) {
        this.nodeId = nodeId;
        this.styleId = styleId;
        this.mainText = mainText;
        this.attachedText = attachedText;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }

    public String getAttachedText() {
        return attachedText;
    }

    public void setAttachedText(String attachedText) {
        this.attachedText = attachedText;
    }

    public Object getGraphicModule() {
        return graphicModule;
    }

    public void setGraphicModule(Object graphicModule) {
        this.graphicModule = graphicModule;
    }
    public void delSon(ChildNode node){

    }

}
