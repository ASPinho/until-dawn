package deltaqueues.dronnie.screens;

import deltaqueues.dronnie.attacks.SimpleShot;
import deltaqueues.dronnie.attacks.PlayerProjectile;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import deltaqueues.dronnie.elements.*;
import deltaqueues.dronnie.elements.enemies.Boss;
import deltaqueues.dronnie.elements.enemies.FinalBoss;
import deltaqueues.dronnie.elements.enemies.Virus;

import java.util.Iterator;

import static deltaqueues.dronnie.Utilities.*;
import static deltaqueues.dronnie.Utilities.PICTURE_SIZE;

public class GameScreen implements Screen {

    private Game game;
    private TextureRegion background;

    private Dronnie dronnie;
    private Beacon beacon;

    private Array<Virus> viruses;
    private Array<SimpleShot> evilProjectiles;
    private Array<Toillet> toillets;
    private Array<Boss> bosses;

    private PlayerProjectile wave;

    private FinalBoss target;

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
    private Music quarentine;


    public GameScreen(Game game) {
        this.game = game;

        background = new TextureRegion(new Texture("map-bkg-02.jpg"), 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);

        camera = new OrthographicCamera();

        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        batch = new SpriteBatch();

        toillets = new Array<>();

        viruses = new Array<>();

        evilProjectiles = new Array<>();

        bosses = new Array<>();

        target = new FinalBoss();

        dronnie = new Dronnie();

        beacon = new Beacon(dronnie.getBody(), target);

        wave = new PlayerProjectile(dronnie.getBody());


        yourScoreName = "SCORE: ";
        health = new BitmapFont();
        yourBitmapFontName = new BitmapFont();
        quarentine = Gdx.audio.newMusic(Gdx.files.internal("dronnie-music.mp3"));
        quarentine.setLooping(true);
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
        batch.draw(target.getBodyPic(), target.getX(), target.getY());
        batch.draw(dronnie.getBodyPic(), dronnie.getX(), dronnie.getY());


        beacon.rotate(batch);

        infection();


        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            wave.shoot();
            batch.draw(wave.getBodyPic(), dronnie.getX() - PICTURE_SIZE -16, dronnie.getY() - PICTURE_SIZE -16);
        }

        yourBitmapFontName.setColor(Color.GREEN);
        yourBitmapFontName.draw(batch, yourScoreName + score, camera.position.x - VIEWPORT_WIDTH / 2 + 20, camera.position.y + VIEWPORT_HEIGHT / 2 - 20);
        health.setColor(Color.GREEN);
        health.draw(batch, "HEALTH: " + hp, camera.position.x + VIEWPORT_WIDTH / 2 - 150, camera.position.y + VIEWPORT_HEIGHT / 2 - 20);


        for (Iterator<Virus> iter = viruses.iterator(); iter.hasNext();){
            Virus virus = iter.next();
            batch.draw(virus.getBodyPic(), virus.getX(), virus.getY());
            virus.moveTowardsPlayer();


            if (virus.getBody().overlaps(wave.getBody())){
                iter.remove();
                score += 10;
            }

            if(virus.getBody().overlaps(dronnie.getBody())){
                setIsInfectedTrue();
                //System.out.println(viruses.indexOf(virus, true));
                iter.remove();
            }
        }

            spawnToillet();
        for(Iterator<Toillet> iter = toillets.iterator(); iter.hasNext();) {
            Toillet toillet = iter.next();
            batch.draw(toillet.getBodyPic(), toillet.getX(), toillet.getY());
            if(dronnie.getBody().overlaps(toillet.getBody())) {
                setIsInfectedFalse();
                iter.remove();
            }
        }


        // draws Johnsons
        for (Iterator<Boss> iter = bosses.iterator(); iter.hasNext();){
            Boss boss = iter.next();
            batch.draw(boss.getBodyPic(), boss.getX(), boss.getY());
            boss.moveTowardsPlayer();
            spwanShootDrop(boss, dronnie.getBody());

            if (boss.getBody().overlaps(wave.getBody())){
                iter.remove();
                score +=20;
            }

        }

        for (Iterator<SimpleShot> iter = evilProjectiles.iterator(); iter.hasNext(); ) {
            SimpleShot evilProjectile = iter.next();
            batch.draw(evilProjectile.getBodyPic(), evilProjectile.getX(), evilProjectile.getY());
            evilProjectile.move();

            if(evilProjectile.getBody().overlaps(dronnie.getBody())) {
                hp -= 10;
                iter.remove();
                if(hp <= 0) {
                    health.draw(batch, "HEALTH: " + hp, camera.position.x + VIEWPORT_WIDTH / 2 - 150, camera.position.y + VIEWPORT_HEIGHT / 2 - 20);
                    quarentine.pause();
                    gameOver();
                }
            }
        }

        if (target.getBody().overlaps(wave.getBody())){
            target.takeDamage();
        }

        batch.end();

        dronnie.move();
        moveCamera();

        if (target.isDead()){
            gameWin();
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

        for (Boss boss : bosses){
            boss.dispose();
        }

        for(Virus virus : viruses) {
            virus.getBodyPic().dispose();
        }

        batch.dispose();
    }

    private void spawnRaindrop() {

        if (viruses.size <= 10) {
            Virus evilDrone = new Virus(dronnie.getBody());
            viruses.add(evilDrone);
        }

        if (bosses.size <= 10){
            Boss boss = new Boss(dronnie.getBody());
            bosses.add(boss);
        }

        lastDropTime = TimeUtils.nanoTime();
    }

    private void spwanShootDrop(Boss boss, Rectangle player) {

        if(TimeUtils.nanoTime() - boss.getLastShootTime() > 1000000000) {
            SimpleShot evilProjectile = new SimpleShot(boss, player);
            evilProjectiles.add(evilProjectile);
            boss.shoot();
        }
    }

    private void gameOver() {
        quarentine.dispose();
        game.setScreen(new GameOverScreen(game, SKIN));
    }

    private void gameWin(){
        quarentine.dispose();
        game.setScreen(new WinScreen(game, SKIN));
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



