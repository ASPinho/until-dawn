package deltaqueues.dronnie.ui;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

public class OptionBox extends Table {

    private int selectedIndex = 0;

    private Skin skin;

    private ArrayList<Image> arrows = new ArrayList<Image>();
    private ArrayList<Label> options = new ArrayList<Label>();

    private Table uiContainer;


    public OptionBox(Skin skin) {
        super(skin);
        this.setBackground("textfield");
        uiContainer = new Table();
        this.add(uiContainer).pad(5f);
        this.skin = skin;
    }

    public void addOption(String option) {
        Label optionLabel = new Label(option, skin);
        options.add(optionLabel);
        Image arrow = new Image(skin, "plus");
        arrow.setVisible(false);
        arrows.add(arrow);

        uiContainer.add(arrow).expand().align(Align.left);
        uiContainer.add(optionLabel).expand().align(Align.left);
        uiContainer.row();

        calcArrowVisibility();
    }

    public void moveUp() {
        selectedIndex --;
        if(selectedIndex < 0) {
            selectedIndex = 0;
        }
        calcArrowVisibility();
    }

    public void moveDown() {
        selectedIndex ++;
        if(selectedIndex >= options.size()) {
            selectedIndex = options.size() -1;
        }
        calcArrowVisibility();
    }

    public int getIndex() {
        return selectedIndex;
    }

    public void clearChoices() {
        uiContainer.clearChildren();
        arrows.clear();
        options.clear();
        selectedIndex = 0;
    }

    public void calcArrowVisibility() {
        for(int i = 0; i < arrows.size(); i++) {
            if(i == selectedIndex) {
                arrows.get(i).setVisible(true);
            } else {
                arrows.get(i).setVisible(false);
            }
        }
    }
}
