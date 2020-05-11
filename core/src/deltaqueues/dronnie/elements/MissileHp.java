package deltaqueues.dronnie.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import deltaqueues.dronnie.Utilities;
import deltaqueues.dronnie.attacks.AbstractProjectiles;
import deltaqueues.dronnie.attacks.BossMissile;

public class MissileHp extends AbstractProjectiles {

        private BossMissile bossMissile;
        private Texture hpBar;
        private Texture damage;

        private Sprite lifeBar;

        public MissileHp(BossMissile bossMissile){

            //bodyPic = new Texture(Gdx.files.internal("barra6.png"));

            //this.lifeBar = new Sprite(bodyPic);

            this.bossMissile = bossMissile;

            body = new Rectangle();
            body.x = bossMissile.getBody().x;
            body.y = bossMissile.getBody().y;
            body.width = Utilities.PICTURE_SIZE;
            body.height = Utilities.PICTURE_SIZE;

            //lifeBar.setPosition(bossMissile.getX() + PICTURE_SIZE/2,bossMissile.getY() - lifeBar.getHeight() / 2 - 20);
            hpBar = new Texture(Gdx.files.internal("blank.png"));
            //damage = new Texture(Gdx.files.internal("blank.png"));
        }

        public Sprite getLifeBar(){
            return lifeBar;
        }

        public void drawHpBar(SpriteBatch batch) {

            if(bossMissile.getHp() <=0) {
                return;
            }


            //batch.setColor(Color.BLACK);
            //batch.draw(damage, lifeBar.getX()+29,lifeBar.getY() +96, lifeBar.getWidth()-55,lifeBar.getHeight()/10-6);

            if(bossMissile.getHp() >100) {
                batch.setColor(Color.YELLOW);
            } else if(bossMissile.getHp() > 50) {
                batch.setColor(Color.ORANGE);
            } else {
                batch.setColor(Color.RED);
            }

            batch.draw(hpBar, body.x,body.y, bossMissile.getHp(),body.getHeight()/5);
            batch.setColor(Color.WHITE);
        }
    }

