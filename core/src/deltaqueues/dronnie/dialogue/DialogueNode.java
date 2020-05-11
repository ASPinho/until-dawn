package deltaqueues.dronnie.dialogue;

import java.util.ArrayList;

public class DialogueNode {

    private ArrayList<Integer> pointers = new ArrayList<>();
    private ArrayList<String> labels = new ArrayList<>();

    private String text;
    private int id;

    private NodeType type;

    public enum NodeType {
        MULTIPLE_CHOICE,
        LINEAR,
        END,
        ;
    }

    public DialogueNode(String text, int id) {
       this.text = text;
       this.id = id;
       type = NodeType.END;
    }

    public void addChoice(String option, int nodeId) {
        if(type == NodeType.LINEAR) {
            pointers.clear();
        }
        labels.add(option);
        pointers.add(nodeId);
        type = NodeType.MULTIPLE_CHOICE;
    }

    public void makeLinear(int nodeId) {
        pointers.clear();
        labels.clear();
        pointers.add(nodeId);
        type = NodeType.LINEAR;
    }

    public NodeType getType() {
        return type;

    }
    public ArrayList<Integer> getPointers() {
        return pointers;

    }
    public ArrayList<String> getLabels() {
        return labels;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
