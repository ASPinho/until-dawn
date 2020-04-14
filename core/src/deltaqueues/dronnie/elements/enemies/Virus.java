package deltaqueues.dronnie.elements.enemies;

import deltaqueues.dronnie.Utilities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Virus extends AbstractEnemy {

    public Virus(Rectangle player) {

        this.player = player;
        this.bodyPic = new Texture(Gdx.files.internal("virus-32.png"));

        this.body = new Rectangle();
        body.x = MathUtils.random(Utilities.BACKGROUND_WIDTH/2);
        body.y = MathUtils.random( Utilities.BACKGROUND_HEIGHT);
        body.width = Utilities.PICTURE_SIZE;
        body.height = Utilities.PICTURE_SIZE;
    }


}
