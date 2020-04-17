package deltaqueues.dronnie.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import deltaqueues.dronnie.Utilities;
import deltaqueues.dronnie.elements.enemies.FinalBoss;
import deltaqueues.dronnie.elements.enemies.FinalBoss2;

import static deltaqueues.dronnie.Utilities.PICTURE_SIZE;

public class FinalBossBeacon extends AbstractElements{

    private FinalBoss2 boss;
    private Texture hpBar;
    private Texture damage;

    private Sprite lifeBar;

    public FinalBossBeacon(Rectangle player, FinalBoss2 boss){

        bodyPic = new Texture(Gdx.files.internal("barra6.png"));

        this.lifeBar = new Sprite(bodyPic);
        this.player = player;
        this.boss = boss;

        body = new Rectangle();
        body.x = boss.getBody().x;
        body.y = boss.getBody().y;
        body.width = Utilities.PICTURE_SIZE;
        body.height = Utilities.PICTURE_SIZE;

        lifeBar.setPosition(boss.getX() + PICTURE_SIZE/2,boss.getY() - lifeBar.getHeight() / 2 - 20);
        hpBar = new Texture(Gdx.files.internal("blank.png"));
        damage = new Texture(Gdx.files.internal("blank.png"));
    }

    public Sprite getLifeBar(){
        return lifeBar;
    }

    public void drawHpBar(SpriteBatch batch) {

        if(boss.getHp() <=0) {
            return;
        }


        batch.setColor(Color.BLACK);
        batch.draw(damage, lifeBar.getX()+29,lifeBar.getY() +96, lifeBar.getWidth()-55,lifeBar.getHeight()/10-6);

        if(boss.getHp() >100) {
            batch.setColor(Color.YELLOW);
        } else if(boss.getHp() > 50) {
            batch.setColor(Color.ORANGE);
        } else {
            batch.setColor(Color.RED);
        }

        batch.draw(hpBar, lifeBar.getX()+29,lifeBar.getY()+96, boss.getHp(),lifeBar.getHeight()/10-6);
        batch.setColor(Color.WHITE);
    }
}
