package deltaqueues.dronnie.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class OptionBoxController {

    private OptionBox box;

    public OptionBoxController(OptionBox box) {
        this.box = box;
    }

    public void keyUp() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.I)) {
            box.moveUp();
        } else if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            System.out.println("wat?????");
            box.moveDown();
        }
    }

}
