package deltaqueues.dronnie.elements;

import deltaqueues.dronnie.Utilities;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import deltaqueues.dronnie.elements.enemies.FinalBoss;

public class Beacon extends AbstractElements {

    private FinalBoss boss;

    private Sprite arrow;

    public Beacon(Rectangle player, FinalBoss boss){

        bodyPic = new Texture(Gdx.files.internal("arrowRight.png"));

        this.arrow = new Sprite(bodyPic);
        this.player = player;
        this.boss = boss;

        body = new Rectangle();
        body.x = 20;
        body.y = 20;
        body.width = Utilities.PICTURE_SIZE;
        body.height = Utilities.PICTURE_SIZE;

    }

    public void rotate(SpriteBatch batch){

        arrow.setSize(20,20);
        arrow.setPosition(player.x, player.y - arrow.getHeight() / 2 - 25);

        float xInput = boss.getX();
        float yInput = boss.getY();

        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(yInput - player.y, xInput - player.x + 40);

        if (angle < 0) {
            angle += 360;
        }
        arrow.setRotation(angle);

        arrow.draw(batch);
    }
}
