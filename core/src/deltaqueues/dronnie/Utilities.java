package deltaqueues.dronnie;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Utilities {


    public static final int DRONE_BACKGROUND_WIDTH = 8100;
    public static final int DRONE_BACKGROUND_HEIGHT = 5400;

    public static final int PRE_GAME_BACKGROUND_WIDTH = 1200;
    public static final int PRE_GAME_BACKGROUND_HEIGHT = 150;

    public static final int VIEWPORT_WIDTH = 1200;
    public static final int VIEWPORT_HEIGHT = 660;

    public static final float PPM = 100;

    public static final short DEFAULT_BIT = 1;
    public static final short PLAYER_BIT = 2;
    public static final short ON_LADDER_BIT = 4;
    public static final short LADDER_BIT = 5;
    public static final short WALL_BIT = 4;
    public static final short EXIT_BIT = 8;
    public static final short EXITED_BIT = 16;
    public static final short ENEMY_BIT = 32;
    public static final short ENEMY_FIRE_BIT = 33;
    public static final short PLAYER_FIRE_BIT = 64;
    public static final short DELETE_COLLISION_BIT = -1;

    public static final int PICTURE_SIZE = 32;

    public static final int PLAYER_SPEED = 200;
    public static final int ENEMY_SPEED = 100;

    public static final int PROJECTILE_SPEED = 200;

    public static final Skin SKIN = new Skin(Gdx.files.internal("skins/sgx/skin/glassyui/glassy-ui.json"));

}
