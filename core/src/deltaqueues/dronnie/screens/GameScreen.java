package deltaqueues.dronnie.screens;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import deltaqueues.dronnie.Explosion;
import deltaqueues.dronnie.Explosion2;
import deltaqueues.dronnie.attacks.FireType;
import deltaqueues.dronnie.attacks.SimpleShot;
import deltaqueues.dronnie.attacks.PlayerProjectile;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import deltaqueues.dronnie.elements.*;
import deltaqueues.dronnie.elements.enemies.Boss;
import deltaqueues.dronnie.elements.enemies.FinalBoss;
import deltaqueues.dronnie.elements.enemies.Virus;
import deltaqueues.dronnie.indicators.Health;
import deltaqueues.dronnie.indicators.InfectedMessage;
import deltaqueues.dronnie.indicators.Score;

import java.util.Iterator;

import static deltaqueues.dronnie.Utilities.*;
import static deltaqueues.dronnie.Utilities.PICTURE_SIZE;

public class GameScreen implements Screen {

    private Game game;
    private TextureRegion background;

    private BitmapFont textTest;


    private float energy = 10;

    private Texture energyBar;
    private Texture lifeBar;
    private Texture menuSeperator;
    private Texture menuSeperator2;
    private Texture menuBar;
    private Texture lifeBorder;
    private Texture energyBorder;

    private Texture wave1;
    private Texture wave2;
    private Texture wave3;
    private Texture wave4;


    private Vector3 mousePos;

    private Dronnie dronnie;
    private Beacon beacon;

    private Array<Virus> viruses;
    private Array<SimpleShot> evilProjectiles;
    private Array<Toillet> toillets;
    private Array<Battery> batteries;
    private Array<Boss> bosses;
    private Array<Explosion> explosions;
    private Array<Explosion2> explosions2;

    private PlayerProjectile wave;

    private FinalBoss target;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Score score;
    private Health health;
    private InfectedMessage infectedMessage;

    private long lastDropTime;
    private Music quarentine;


    public GameScreen(Game game) {
        this.game = game;

        background = new TextureRegion(new Texture("map-bkg-02.jpg"), 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);

        textTest = new BitmapFont();

        camera = new OrthographicCamera();

        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        batch = new SpriteBatch();

        toillets = new Array<>();

        batteries = new Array<>();

        viruses = new Array<>();

        evilProjectiles = new Array<>();

        bosses = new Array<>();

        explosions = new Array<Explosion>();

        explosions2 = new Array<Explosion2>();

        energyBar = new Texture("blank.png");
        lifeBar = new Texture("blank.png");
        menuBar = new Texture("blank.png");
        menuSeperator = new Texture("blank.png");
        menuSeperator2 = new Texture("blank.png");
        lifeBorder = new Texture(Gdx.files.internal("barra6.png"));
        energyBorder = new Texture(Gdx.files.internal("barra6.png"));

        wave1 = new Texture(Gdx.files.internal("shockwave1.png"));
        wave2 = new Texture(Gdx.files.internal("shockwave2.png"));
        wave3 = new Texture(Gdx.files.internal("shockwave3.png"));
        wave4 = new Texture(Gdx.files.internal("shockwave4.png"));

        dronnie = new Dronnie();

        target = new FinalBoss(dronnie.getBody());

        beacon = new Beacon(dronnie.getBody(), target);

        wave = new PlayerProjectile(dronnie.getBody());

        score = new Score(dronnie);

        health = new Health(dronnie);

        batch = new SpriteBatch();

        quarentine = Gdx.audio.newMusic(Gdx.files.internal("dronnie-music.mp3"));
        quarentine.setLooping(true);
        // quarentine.play();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(background, 0, 0);

        batch.draw(target.getBodyPic(), target.getX(), target.getY());
        batch.draw(dronnie.getBodyPic(), dronnie.getX(), dronnie.getY());
        beacon.rotate(batch);

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            camera.unproject(mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            SimpleShot evilProjectile1 = new SimpleShot(dronnie.getBody(), mousePos.x, mousePos.y);
            evilProjectiles.add(evilProjectile1);
        }


        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(energy >= 0) {

                batch.draw(wave1, dronnie.getX()- PICTURE_SIZE + PICTURE_SIZE+4, dronnie.getY() + PICTURE_SIZE/2-2);
                batch.draw(wave2, dronnie.getX()+ PICTURE_SIZE/2 - 16, dronnie.getY() + PICTURE_SIZE/2 -14);
                batch.draw(wave3, dronnie.getX()- PICTURE_SIZE/2 , dronnie.getY()- PICTURE_SIZE/2);
                batch.draw(wave4, dronnie.getX()- PICTURE_SIZE - 16, dronnie.getY()-PICTURE_SIZE-16);

            //explosions2.add(new Explosion2(dronnie.getX(), dronnie.getY()));
                if(dronnie.getHp() < 100) {
                    dronnie.addHealth(1);
                    if(dronnie.getHp() > 100){
                        dronnie.setHp(100);
                    }
                }
                wave.shoot();
                //batch.draw(wave.getBodyPic(), dronnie.getX() - PICTURE_SIZE - 16, dronnie.getY() - PICTURE_SIZE - 16);
                energy -=1;
            }
        }


        for (Iterator<Virus> iter = viruses.iterator(); iter.hasNext(); ) {
            Virus virus = iter.next();
            batch.draw(virus.getBodyPic(), virus.getX(), virus.getY());
            if(!virus.isDistroyed()) {
                virus.moveTowardsPlayer();
            }


            if (virus.getBody().overlaps(wave.getBody())) {
                virus.getBodyPic().dispose();
                virus.getBody().setPosition(-1,-1);
                score.covidKilled(1);
                virus.setDistroyed(true);
                dronnie.addScore(10);
            }

            if (virus.getBody().overlaps(dronnie.getBody())) {
                if(!virus.isDistroyed()) {
                    dronnie.setInfected(true);
                    iter.remove();
                }
            }
            for(Iterator<SimpleShot> iter2 = evilProjectiles.iterator(); iter2.hasNext();) {
                SimpleShot simpleShot = iter2.next();
                if(virus.getBody().overlaps(simpleShot.getBody()) && (simpleShot.getFireType() == FireType.PLAYER_FIRE)){
                    explosions2.add(new Explosion2(virus.getX(), virus.getY()));
                    score.covidKilled(1);
                    dronnie.addScore(10);
                    virus.getBodyPic().dispose();
                    virus.getBody().setPosition(-1,-1);
                    virus.setDistroyed(true);
                }
            }
        }

        spawnSoapAndBatteries();
        for (Iterator<Toillet> iter = toillets.iterator(); iter.hasNext(); ) {
            Toillet toillet = iter.next();
            batch.draw(toillet.getBodyPic(), toillet.getX(), toillet.getY());
            if (dronnie.getBody().overlaps(toillet.getBody())) {
                if(dronnie.isInfected()) {
                    dronnie.setInfected(false);
                    infectedMessage.getWriteIndicator().dispose();
                }
                iter.remove();
            }
        }

        for (Iterator<Battery> iter = batteries.iterator(); iter.hasNext(); ) {
             Battery battery = iter.next();
            batch.draw(battery.getBodyPic(), battery.getX(), battery.getY());
            if (dronnie.getBody().overlaps(battery.getBody())) {
                energy +=10;
                if(energy >= 130) {
                    energy = 130;
                }
                iter.remove();
            }
        }



        // draws Johnsons
        for (Iterator<Boss> iter = bosses.iterator(); iter.hasNext(); ) {
            Boss boss = iter.next();
            batch.draw(boss.getBodyPic(), boss.getX(), boss.getY());
            if(!boss.isDistroyed()){
                boss.moveTowardsPlayer();
                spwanShootDrop(boss, dronnie.getBody());
            }

            if (boss.getBody().overlaps(wave.getBody())) {
                boss.setDistroyed(true);
                boss.getBody().setPosition(-1,-1);
                boss.getBodyPic().dispose();
                score.killedDrones(1);
                dronnie.addScore(20);
            }

            for(Iterator<SimpleShot> iter2 = evilProjectiles.iterator(); iter2.hasNext();) {
                SimpleShot simpleShot = iter2.next();
                if(boss.getBody().overlaps(simpleShot.getBody()) && (simpleShot.getFireType() == FireType.PLAYER_FIRE)){
                    explosions.add(new Explosion(boss.getX(), boss.getY()));
                    score.killedDrones(1);
                    dronnie.addScore(10);
                    boss.getBodyPic().dispose();
                    boss.getBody().setPosition(-1,-1);
                    boss.setDistroyed(true);
                }
            }

        }

        for (Iterator<SimpleShot> iter = evilProjectiles.iterator(); iter.hasNext(); ) {
            SimpleShot evilProjectile = iter.next();
            batch.draw(evilProjectile.getBodyPic(), evilProjectile.getX(), evilProjectile.getY());
            if (evilProjectile.isLosted()) {
                iter.remove();
            }
            evilProjectile.move();

            if(evilProjectile.getBody().overlaps(wave.getBody()) && evilProjectile.getFireType() == FireType.ENEMY_FIRE) {
                iter.remove();
            }


            if (evilProjectile.getBody().overlaps(dronnie.getBody()) && evilProjectile.getFireType() == FireType.ENEMY_FIRE) {
                evilProjectile.setIsLosted(true);
                dronnie.takeDamage(10);
                evilProjectile.getBodyPic().dispose();
                evilProjectile.getBody().setPosition(-1,-1);

                if (dronnie.getHp() <= 0) {
                    dronnie.setHp(0);
                    health.drawHealth(batch, camera);
                    quarentine.pause();
                    gameOver();
                }
            }
        }

        Array<Explosion> explosionsToRemove = new Array<Explosion>();
        for (Explosion explosion : explosions) {

            explosion.update(delta*2);
            if (explosion.remove) {
                explosionsToRemove.add(explosion);
            }
        }

        Array<Explosion2> explosions2ToRemove = new Array<Explosion2>();
        for (Explosion2 explosion : explosions2) {

            explosion.update(delta);
            if (explosion.remove) {
                explosions2ToRemove.add(explosion);
            }
        }

        explosions.removeAll(explosionsToRemove, true);
        explosions2.removeAll(explosions2ToRemove, true);

        if (target.getBody().overlaps(wave.getBody())) {
            target.takeDamage();
        }

        //Explosions!

        for (Explosion explosion : explosions) {
            explosion.render(batch);
        }

        for (Explosion2 explosion : explosions2) {
            explosion.render(batch);
        }

        // Menu, energy bar draw & colors!

        batch.setColor(Color.LIGHT_GRAY);
        batch.draw(menuSeperator,camera.position.x-VIEWPORT_WIDTH, camera.position.y+265,VIEWPORT_WIDTH *2 , camera.position.y);
        batch.setColor(Color.WHITE);
        batch.setColor(Color.DARK_GRAY);
        batch.draw(menuSeperator2,camera.position.x-VIEWPORT_WIDTH, camera.position.y+271,VIEWPORT_WIDTH *2 , camera.position.y);
        batch.setColor(Color.WHITE);
        batch.setColor(Color.BLACK);
        batch.draw(menuBar, camera.position.x-VIEWPORT_WIDTH, camera.position.y+275,VIEWPORT_WIDTH *2  , camera.position.y);

        batch.setColor(Color.WHITE);
        checkInfection(batch, camera);
        score.drawScore(batch, camera);
        health.drawHealth(batch, camera);


        if(energy >= 90) {
            batch.setColor(new Color(0, 1,1,1));
        } else if(energy >= 60) {
            batch.setColor(Color.ROYAL);
        } else {
            batch.setColor(Color.BLUE);
        }
        batch.draw(energyBar, (camera.position.x + 250), camera.position.y+290 , energy,VIEWPORT_HEIGHT/2-315);

        batch.setColor(Color.FIREBRICK);
        batch.draw(energyBorder, camera.position.x+225, camera.position.y+205);



        textTest.setColor(Color.WHITE);


        if(dronnie.getHp() >= 90) {
            batch.setColor(Color.GREEN);
        } else if(dronnie.getHp() >= 60 ) {
            batch.setColor(Color.ORANGE);
        } else {
            batch.setColor(Color.RED);
        }
        batch.draw(lifeBar,camera.position.x + 441,camera.position.y+290 , dronnie.getHp(), VIEWPORT_HEIGHT/2-315);

        batch.setColor(Color.ROYAL);
        batch.draw(lifeBorder, camera.position.x+415, camera.position.y+205);


        batch.setColor(Color.WHITE);

        batch.end();


        dronnie.move();
        moveCamera();

        if (target.isDead()) {
            gameWin();
        }

        if(dronnie.getScore() > 500) {
            target.moveTowardsPlayer();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        dronnie.getBodyPic().dispose();
        beacon.getBodyPic().dispose();
        quarentine.dispose();

        for (Boss boss : bosses) {
            boss.dispose();
        }

        for (Virus virus : viruses) {
            virus.getBodyPic().dispose();
        }

        batch.dispose();
    }

    private void spawnRaindrop() {

        if (viruses.size <= 10) {
            Virus evilDrone = new Virus(dronnie.getBody());
            viruses.add(evilDrone);
        }

        for (Virus virus : viruses ) {
            if(virus.isDistroyed()) {
                viruses.removeIndex(viruses.indexOf(virus, true));
            }
        }

        if (bosses.size <= 10) {
            Boss boss = new Boss(dronnie.getBody());
            bosses.add(boss);
        }

        for (Boss boss : bosses ) {
            if(boss.isDistroyed()) {
                bosses.removeIndex(bosses.indexOf(boss, true));
            }
        }

        lastDropTime = TimeUtils.nanoTime();
    }

    private void spwanShootDrop(Boss boss, Rectangle player) {

        if (TimeUtils.nanoTime() - boss.getLastShootTime() > 1000000000) {
            SimpleShot evilProjectile = new SimpleShot(boss, player);
            evilProjectiles.add(evilProjectile);
            boss.shoot();
        }

    }

    private void gameOver() {
        quarentine.dispose();
        game.setScreen(new GameOverScreen(game, SKIN));
    }

    private void gameWin() {
        quarentine.dispose();
        game.setScreen(new WinScreen(game, SKIN));
    }

    public void spawnSoapAndBatteries() {
        if (toillets.size <= 20) {
            Toillet toillet = new Toillet();
            toillets.add(toillet);
        }

        if(batteries.size <= 20) {
            Battery battery = new Battery();
            batteries.add(battery);
        }
    }

    public void checkInfection(SpriteBatch batch, OrthographicCamera camera) {
        if (dronnie.isInfected()) {
            dronnie.getSick();
            infectedMessage = new InfectedMessage();
            infectedMessage.drawMessage(batch, camera);
        }


        if (dronnie.getHp() <= 0) {
            health.drawHealth(batch, camera);
            gameOver();
        }
    }

    public void moveCamera() {
        //move camera horizontally
        if (dronnie.getX() > VIEWPORT_WIDTH / 2 - PICTURE_SIZE / 2 && dronnie.getX() < (BACKGROUND_WIDTH - VIEWPORT_WIDTH / 2) - PICTURE_SIZE / 2) {
            camera.position.set(dronnie.getX() + PICTURE_SIZE / 2, camera.position.y, 0);
        }

        //move camera vertically
        if (dronnie.getY() > VIEWPORT_HEIGHT / 2 && dronnie.getY() < BACKGROUND_HEIGHT - VIEWPORT_HEIGHT / 2) {
            camera.position.set(camera.position.x, dronnie.getY(), 0);
        }
    }



}



