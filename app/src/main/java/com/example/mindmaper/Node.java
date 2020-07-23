package com.example.mindmaper;

public class Node {
    protected long nodeId;
    protected long styleId;
    protected String mainText;
    protected String attachedText;
    protected Object graphicModule;


    public Node(long nodeId, long styleId, String mainText, String attachedText) {
        this.nodeId = nodeId;
        this.styleId = styleId;
        this.mainText = mainText;
        this.attachedText = attachedText;
    }

    public long getNodeId() {
        return nodeId;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }

    public long getStyleId() {
        return styleId;
    }

    public void setStyleId(long styleId) {
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
