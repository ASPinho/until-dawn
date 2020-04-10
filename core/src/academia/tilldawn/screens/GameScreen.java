package academia.tilldawn.screens;

import academia.tilldawn.Beacon;

import academia.tilldawn.Boss;

import academia.tilldawn.EvilDrone;
import academia.tilldawn.Toillet;
import academia.tilldawn.projectiles.EvilProjectile;
import academia.tilldawn.projectiles.PlayerProjectile;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import static academia.tilldawn.Utilities.*;
import static academia.tilldawn.Utilities.PICTURE_SIZE;

public class GameScreen implements Screen {

    private Game game;
    private TextureRegion background;

    private Texture dronePic;
    private Texture evilDronePic;
    private Texture beaconPic;
    private Texture targetPic;


    private Rectangle drone;
    private Rectangle target;

    private Array<EvilDrone> evilDrones;

    private Array<EvilProjectile> evilProjectiles;

    private Array<Toillet> toillets;

    private Array<Boss> bosses;
    private PlayerProjectile wave;

    private Beacon beacon;
    private Sprite arrow;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private String yourScoreName;
    private BitmapFont yourBitmapFontName;
    private BitmapFont health;

    private int hp = 100;
    private int score = 0;

    private boolean isInfected = false;
    private long lastDropTime;
    private long infectionTime;
    private long lastShootTime;
    private Music quarentine;


    public GameScreen(Game game) {
        this.game = game;

        // starts graphic representations
        background = new TextureRegion(new Texture("map-bkg-02.jpg"), 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
        dronePic = new Texture(Gdx.files.internal("bonnie-drone-32.png"));
        evilDronePic = new Texture(Gdx.files.internal("virus-32.png"));
        beaconPic = new Texture(Gdx.files.internal("arrowRight.png"));
        targetPic = new Texture(Gdx.files.internal("unnamed.png"));


        camera = new OrthographicCamera();

        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        batch = new SpriteBatch();

        // starts Player Drone logic position
        drone = new Rectangle();
        drone.x = PICTURE_SIZE * 2;
        drone.y = BACKGROUND_HEIGHT / 2 - PICTURE_SIZE / 2;
        drone.width = PICTURE_SIZE;
        drone.height = PICTURE_SIZE;

        target = new Rectangle();
        target.x = BACKGROUND_WIDTH - 800;
        target.y = BACKGROUND_HEIGHT - 800;
        target.width = 200;
        target.height = 200;



        toillets = new Array<Toillet>();

        evilDrones = new Array<EvilDrone>();

        evilProjectiles = new Array<EvilProjectile>();

        bosses = new Array<Boss>();
        wave = new PlayerProjectile(drone);

        beacon = new Beacon(camera);

        arrow = new Sprite(beaconPic);

        yourScoreName = "SCORE: 0";
        health = new BitmapFont();
        yourBitmapFontName = new BitmapFont();
       // quarentine = Gdx.audio.newMusic(Gdx.files.internal("quarentine.mp3"));
       // quarentine.setLooping(true);
       // quarentine.play();
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();

        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(dronePic, drone.x, drone.y);
        batch.draw(beaconPic, beacon.getX(), beacon.getY());

        batch.draw(targetPic, target.x, target.y);



        arrow.setSize(20, 20);
        arrow.setPosition(drone.x, drone.y - arrow.getHeight() / 2 - 25);

        float xInput = target.x;
        float yInput = target.y;

        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(yInput - drone.y, xInput - drone.x + 40);

        if (angle < 0) {
            angle += 360;
        }
        arrow.setRotation(angle);

        arrow.draw(batch);


        infection();



        // Player attack
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            batch.draw(wave.getShockwavePic(), drone.x - PICTURE_SIZE/2, drone.y - PICTURE_SIZE/2);

        }

       



        yourBitmapFontName.setColor(Color.GREEN);
        yourBitmapFontName.draw(batch, yourScoreName, camera.position.x - VIEWPORT_WIDTH / 2 + 20, camera.position.y + VIEWPORT_HEIGHT / 2 - 20);
        health.setColor(Color.GREEN);
        health.draw(batch, "HEALTH: " + hp, camera.position.x + VIEWPORT_WIDTH / 2 - 150, camera.position.y + VIEWPORT_HEIGHT / 2 - 20);





        for (Iterator<EvilDrone> iter = evilDrones.iterator(); iter.hasNext();){
            EvilDrone evilDrone = iter.next();
            batch.draw(evilDronePic, evilDrone.getRectangle().x, evilDrone.getRectangle().y);
            evilDrone.moveTowardsPlayer();
            if(evilDrone.getRectangle().overlaps(drone)){
                setIsInfectedTrue();
                iter.remove();
            }
        }

            spawnToillet();
        for(Iterator<Toillet> iter = toillets.iterator(); iter.hasNext();) {
            Toillet toillet = iter.next();
            batch.draw(toillet.getToilletPic(), toillet.getX(), toillet.getY());
            if(drone.overlaps(toillet.getPapper())) {
                setIsInfectedFalse();
                iter.remove();
            }
        }


        // draws Johnsons
        for (Boss boss : bosses){
            batch.draw(boss.getJohnson(), boss.getX(), boss.getY());
            boss.moveTowardsPlayer();
            spwanShootDrop(boss, drone);
        }


        for (Iterator<EvilProjectile> iter = evilProjectiles.iterator(); iter.hasNext(); ) {
            EvilProjectile evilProjectile = iter.next();
            batch.draw(evilProjectile.getTexture(), evilProjectile.getX(), evilProjectile.getY());
            evilProjectile.move();

            if(evilProjectile.getRectangle().overlaps(drone)) {
                hp -= 10;
                iter.remove();
                if(hp <= 0) {
                    health.draw(batch, "HEALTH: " + hp, camera.position.x + VIEWPORT_WIDTH / 2 - 150, camera.position.y + VIEWPORT_HEIGHT / 2 - 20);
                    gameOver();
                }
            }

            if (TimeUtils.nanoTime() - evilProjectile.getLastShootTime() > 2000000000) {
                iter.remove();
                //evilProjectile.dispose();
            }
        }

        batch.end();

        // Player move left
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            drone.x -= PLAYER_SPEED * Gdx.graphics.getDeltaTime();
        }

        // Player move right
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            drone.x += PLAYER_SPEED * Gdx.graphics.getDeltaTime();
        }

        // Horizontal bounds
        if (drone.x < 0) drone.x = 0;
        if (drone.x > BACKGROUND_WIDTH - PICTURE_SIZE) drone.x = BACKGROUND_WIDTH - PICTURE_SIZE;

        // Player move up
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            drone.y += PLAYER_SPEED * Gdx.graphics.getDeltaTime();
        }

        // Player move down
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            drone.y -= PLAYER_SPEED * Gdx.graphics.getDeltaTime();
        }

        // Vertical bounds
        if (drone.y < 0) drone.y = 0;
        if (drone.y > BACKGROUND_HEIGHT - 64) drone.y = BACKGROUND_HEIGHT - 64;


        //move camera horizontally
        if (drone.x > VIEWPORT_WIDTH / 2 - PICTURE_SIZE / 2 && drone.x < (BACKGROUND_WIDTH - VIEWPORT_WIDTH / 2) - PICTURE_SIZE / 2) {
            camera.position.set(drone.getX() + PICTURE_SIZE / 2, camera.position.y, 0);
        }

        //move camera vertically
        if (drone.y > VIEWPORT_HEIGHT / 2 && drone.y < BACKGROUND_HEIGHT - VIEWPORT_HEIGHT / 2) {
            camera.position.set(camera.position.x, drone.getY(), 0);
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
        beaconPic.dispose();
        evilDronePic.dispose();
        dronePic.dispose();
        quarentine.dispose();

        for (Boss boss : bosses){
            boss.dispose();
        }

        batch.dispose();
    }

    private void spawnRaindrop() {

        if (evilDrones.size <= 5) {
            EvilDrone evilDrone = new EvilDrone(drone);
            evilDrones.add(evilDrone);
        }

        if (bosses.size <= 5){
            Boss boss = new Boss(drone);
            bosses.add(boss);
        }

        lastDropTime = TimeUtils.nanoTime();
    }

    private void spwanShootDrop(Boss boss, Rectangle player) {

        if(TimeUtils.nanoTime() - boss.getLastShootTime() > 1000000000) {
            EvilProjectile evilProjectile = new EvilProjectile(boss, player);
            evilProjectiles.add(evilProjectile);
            lastShootTime = TimeUtils.nanoTime();
            boss.shoot();
        }
    }

    private void gameOver() {
        //quarentine.dispose();
        game.setScreen(new GameOverScreen(game, SKIN));
    }

    public void setIsInfectedTrue(){
        isInfected = true;
    }

    public void setIsInfectedFalse(){

            isInfected = false;
    }

    public void spawnToillet(){
            if(toillets.size <= 20) {
                Toillet toillet = new Toillet();
                toillets.add(toillet);
            }
    }

    public void infection(){

        if((isInfected) && (TimeUtils.nanoTime() - infectionTime > 1000000000)) {
            hp -= 1;
            infectionTime = TimeUtils.nanoTime();
        }
            if(hp <= 0) {
                health.draw(batch, "HEALTH: " + hp, camera.position.x + VIEWPORT_WIDTH / 2 - 150, camera.position.y + VIEWPORT_HEIGHT / 2 - 20);
                gameOver();
            }
        }
    }



