package deltaqueues.dronnie.dialogue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import deltaqueues.dronnie.ui.DialogueBox;
import deltaqueues.dronnie.ui.OptionBox;

import javax.swing.text.html.Option;

public class DialogueController {

    private DialogueTraverser traverser;
    private DialogueBox dialogueBox;
    private OptionBox optionBox;

    public DialogueController(DialogueBox box, OptionBox optionBox) {
        this.dialogueBox = box;
        this.optionBox = optionBox;
    }


    public boolean keyDown() {
        if (dialogueBox.isVisible()) {
            return true;
        }
        return false;
    }


    public boolean keyUp() {
        if (optionBox.isVisible()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
                optionBox.moveUp();
                return true;
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
                optionBox.moveDown();
                return true;
            }
        }

        if (traverser != null && Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && dialogueBox.isFinished()) {
            if (traverser.getType() == DialogueNode.NodeType.END) {
                traverser = null;
                dialogueBox.setVisible(false);
            } else if (traverser.getType() == DialogueNode.NodeType.LINEAR) {
                progress(0);
            } else if (traverser.getType() == DialogueNode.NodeType.MULTIPLE_CHOICE) {
                progress(optionBox.getIndex());
            }
            return true;
        }
        if (dialogueBox.isVisible()) {
            return true;
        }
        return false;
    }

    public void update(float delta) {
        if (dialogueBox.isFinished() && traverser != null) {
            if (traverser.getType() == DialogueNode.NodeType.MULTIPLE_CHOICE) {
                optionBox.setVisible(true);
                return;
            }
        }

    }

    public void startDialogue(Dialogue dialogue) {
        traverser = new DialogueTraverser(dialogue);
        dialogueBox.setVisible(true);
        dialogueBox.animateText(traverser.getText());
        if (traverser.getType() == DialogueNode.NodeType.MULTIPLE_CHOICE) {
            optionBox.clear();
            for (String s : dialogue.getNode(dialogue.getStart()).getLabels()) {
                optionBox.addOption(s);
            }
        }
    }

    private void progress(int index) {
        optionBox.setVisible(false);
        DialogueNode nextNode = traverser.getNextNode(index);
        dialogueBox.animateText(nextNode.getText());
        if (nextNode.getType() == DialogueNode.NodeType.MULTIPLE_CHOICE) {
            optionBox.clearChoices();
            for (String s : nextNode.getLabels()) {
                optionBox.addOption(s);
            }
        }
    }

}
