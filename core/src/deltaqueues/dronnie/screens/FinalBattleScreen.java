package deltaqueues.dronnie.screens;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector3;
import deltaqueues.dronnie.Directions;
import deltaqueues.dronnie.animations.explosions.Explosion2;
import deltaqueues.dronnie.animations.explosions.Explosion3;
import deltaqueues.dronnie.animations.explosions.ExplosionTest;
import deltaqueues.dronnie.animations.attack.Portal;
import deltaqueues.dronnie.attacks.BossMissile;
import deltaqueues.dronnie.attacks.activeAttacks.*;
import deltaqueues.dronnie.attacks.passiveAttacks.ProgramedShot;
import deltaqueues.dronnie.attacks.FireType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import deltaqueues.dronnie.attacks.passiveAttacks.Satellite;
import deltaqueues.dronnie.elements.*;
import deltaqueues.dronnie.elements.allys.AllyDrone;
import deltaqueues.dronnie.elements.allys.Dronnie;
import deltaqueues.dronnie.elements.enemies.Boss;
import deltaqueues.dronnie.elements.enemies.FinalBoss2;
import deltaqueues.dronnie.elements.enemies.Virus;
import deltaqueues.dronnie.indicators.Health;
import deltaqueues.dronnie.indicators.InfectedMessage;
import deltaqueues.dronnie.indicators.Score;

import java.util.Iterator;

import static deltaqueues.dronnie.Utilities.*;
import static deltaqueues.dronnie.Utilities.PICTURE_SIZE;

public class FinalBattleScreen implements Screen {

    private Game game;
    private TextureRegion background;

    private BitmapFont textTest;

    private int previousEnergy;
    private int currentEnergy;

    private int previousEnergy2;
    private int currentEnergy2;

    private int previousEnergy3;
    private int currentEnergy3;

    private float firstPosX;
    private float firstPosY;
    private float energy = 10;
    private float count = 0;
    private int weaponNumbers = 4;
    private int weaponChoice = 1;
    private long currentTime;
    private long currentTime2;
    private long timeToCloseGame;


    private SpeedBoost speedBoost;

    private Texture electricBurst;
    private Texture energyBar;
    private Texture lifeBar;
    private Texture menuSeperator;
    private Texture menuSeperator2;
    private Texture menuBar;
    private Texture lifeBorder;
    private Texture energyBorder;
    private Texture energyWave;

    //private Texture speedBoost;


    private FinalBossBeacon finalBossBeacon;


    private Vector3 mousePos;


    private Dronnie dronnie;
    private Satellite firstSatellite;
    private Beacon beacon;

    private Array<Virus> viruses;
    private Array<SimpleShot> evilProjectiles;
    private Array<Toillet> toillets;
    private Array<Battery> batteries;
    private Array<Boss> bosses;
    private Array<ExplosionTest> explosions;
    private Array<Explosion2> explosions2;
    private Array<Explosion3> explosions3;
    private Array<ProgramedShot> programedShots;
    private Array<Satellite> satellites;
    private Array<BossMissile> bossMissiles;
    private Array<Burst> bursts;
    private Array<AutomaticShot> automaticShots;
    private Array<Mine> mines;
    private Array<Portal> portals;
    private Array<LaserPortal> lasers;
    private Array<AllyDrone> allys;


    private EnergyBall wave;

    private FinalBoss2 finalBoss;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private Sprite energyWaveSprite;

    private Score score;
    private Health health;
    private InfectedMessage infectedMessage;

    private long lastDropTime;
    private Music quarentine;


    public FinalBattleScreen(Game game) {
        this.game = game;

        background = new TextureRegion(new Texture("map-bkg-02.jpg"), 0, 0, DRONE_BACKGROUND_WIDTH, DRONE_BACKGROUND_HEIGHT);

        textTest = new BitmapFont();

        camera = new OrthographicCamera();

        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

        batch = new SpriteBatch();

        toillets = new Array<>();

        batteries = new Array<>();

        viruses = new Array<>();

        evilProjectiles = new Array<>();

        satellites = new Array<>();

        bosses = new Array<>();

        explosions = new Array<ExplosionTest>();

        explosions2 = new Array<Explosion2>();

        explosions3 = new Array<Explosion3>();

        programedShots = new Array<>();

        bossMissiles = new Array<>();

        bursts = new Array<>();

        automaticShots = new Array<>();

        portals = new Array<>();

        mines = new Array<>();

        lasers = new Array<>();

        allys = new Array<>();

        energyBar = new Texture("blank.png");
        lifeBar = new Texture("blank.png");
        menuBar = new Texture("blank.png");
        menuSeperator = new Texture("blank.png");
        menuSeperator2 = new Texture("blank.png");
        lifeBorder = new Texture(Gdx.files.internal("barra6.png"));
        energyBorder = new Texture(Gdx.files.internal("barra6.png"));
        electricBurst = new Texture(Gdx.files.internal("burst.png"));


        energyWave = new Texture(Gdx.files.internal("supernova2.png"));
        //speedBoost = new Texture(Gdx.files.internal("boostSpeed3.png"));

        dronnie = new Dronnie();

        //allyDrone = new AllyDrone(dronnie);

        // satellites.add(new Satellite(dronnie));

        finalBoss = new FinalBoss2(dronnie.getBody(), dronnie);

        beacon = new Beacon(dronnie.getBody(), finalBoss);

        finalBossBeacon = new FinalBossBeacon(dronnie.getBody(), finalBoss);

        wave = new EnergyBall(dronnie.getBody());

        score = new Score(dronnie);

        health = new Health(dronnie);

        batch = new SpriteBatch();

        speedBoost = new SpeedBoost(dronnie.getBody());

        energyWaveSprite = new Sprite(energyWave);


        // explosion3 = new Explosion3(finalBoss.getX(), finalBoss.getY());

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

        //SET INVISIBLE
        previousEnergy = dronnie.getEnergy();
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            if (dronnie.getEnergy() > 0) {
                dronnie.wasteEnergy(1);
                dronnie.setInvisibleMode(true);
                batch.setColor(Color.BLUE);
            }
        }
        currentEnergy = dronnie.getEnergy();

        if (previousEnergy == currentEnergy) {
            dronnie.setInvisibleMode(false);
        }

        //SET BOOST
        previousEnergy2 = dronnie.getEnergy();
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            if (dronnie.isMoving()) {
                if (dronnie.getEnergy() >= 0 && dronnie.getScore() >= 0) {
                    if (dronnie.getDirection() != Directions.STAY) {
                        speedBoost.rotate(batch, dronnie);
                        dronnie.boost();
                        dronnie.wasteEnergy(1);
                    }
                }
            }
        }
        currentEnergy2 = dronnie.getEnergy();

        if (currentEnergy2 == previousEnergy2) {
            dronnie.normalSpeed();
        }

        //ENERGY BALL
        previousEnergy3 = dronnie.getEnergy();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {

            if (dronnie.getEnergy() > 0) {
                wave.getBody().setSize(PICTURE_SIZE * 4, PICTURE_SIZE * 4);
                batch.setColor(new Color(0, 1, 1, 1));
                batch.draw(energyWaveSprite, dronnie.getX() - 48, dronnie.getY() - 47, energyWaveSprite.getRegionWidth() / 2.0f, energyWaveSprite.getRegionHeight() / 2.0f, energyWaveSprite.getRegionWidth(), energyWaveSprite.getRegionHeight(), 1f, 1f, count, false);
                if (count < 0.0f) {
                    count = 360.0f;
                } else {
                    count -= 75;
                }
                batch.setColor(Color.WHITE);

                if (dronnie.getHp() < 100) {
                    dronnie.addHealth(1);
                    if (dronnie.getHp() > 100) {
                        dronnie.setHp(100);
                    }
                }
                wave.shoot();

                dronnie.wasteEnergy(1);
                batch.setColor(new Color(0, 1, 1, 1));

            }
        }
        currentEnergy3 = dronnie.getEnergy();
        System.out.println(currentEnergy3 == previousEnergy3);
        if (currentEnergy3 == previousEnergy3) {
            wave.getBody().setSize(0);
        }

        batch.draw(dronnie.getBodyPic(), dronnie.getX(), dronnie.getY());
        //batch.draw(allyDrone.getBodyPic(), allyDrone.getX(), allyDrone.getY());
        batch.setColor(Color.WHITE);

        //FINAL BOSS COMES TO LIFE
        if (dronnie.getScore() > 0) {
            //finalBoss.setAlive(true);
            beacon.rotate(batch);

            finalBoss.getSprite().draw(batch);
            //final boss moves
            if (!dronnie.getInvisibleMode()) {
                finalBoss.speed();
            } else {
                finalBoss.moveRandomly(dronnie);
                finalBoss.getSprite().setPosition(finalBoss.getX(), finalBoss.getY());
            }

            //FINAL BOSS DIES
            if (finalBoss.getHp() <= 0) {
                float x = finalBoss.getX();
                float y = finalBoss.getY();

                finalBoss.getBody().setPosition(-1000, -1000);
                finalBoss.getBodyPic().dispose();
                finalBossBeacon.getBody().setPosition(-1000, -1000);
                finalBossBeacon.getBodyPic().dispose();
                finalBoss.getSprite().setPosition(-1000, -1000);

                finalBossBeacon.getBodyPic().dispose();


                explosions3.add(new Explosion3(x, y));

                gameWin();
            }


            finalBossBeacon.getLifeBar().setSize(200, 200);
            finalBossBeacon.getLifeBar().setPosition(finalBoss.getSprite().getX() + 20, finalBoss.getSprite().getY() - finalBoss.getSprite().getHeight() / 2 - 20);
            finalBossBeacon.getLifeBar().setColor(Color.ROYAL);
            finalBossBeacon.getLifeBar().draw(batch);
            finalBossBeacon.drawHpBar(batch);

            if (finalBoss.isMissileDestroyed()) {
                if (finalBoss.checkTime()) {
                    evilProjectiles.add(new SimpleShot(finalBoss, dronnie.getBody()));
                }
            } else if (!finalBoss.isMissileLaunched()) {
                if (!finalBoss.checkTime()) {
                    bossMissiles.add(new BossMissile(finalBoss, dronnie.getBody()));
                    finalBoss.setMissileDestroyed(false);
                    finalBoss.setMissileLaunched(true);
                }
            }


            if (finalBoss.getBody().overlaps(dronnie.getBody())) {
                dronnie.takeDamage(1);
            }
        }

        // Shoot!!!
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

            //dronnie.setInvisibleMode(false); <- paara trabalhar.
            //bossMissiles.add(new BossMissile(finalBoss, dronnie.getBody()));
            //finalBoss.setMissileDestroyed(false);
            //smoke.add(new Smoke(dronnie.getX(), dronnie.getY()));
            camera.unproject(mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (allys.size > 0) {
                for (int i = 0; i < allys.size; i++) {
                    evilProjectiles.add(new SimpleShot(allys.get(i).getBody(), mousePos.x, mousePos.y));
                }
            }

            if (weaponChoice == 1) {
                SimpleShot evilProjectile1 = new SimpleShot(dronnie.getBody(), mousePos.x, mousePos.y);
                // SimpleShot evilProjectile2 = new SimpleShot(allyDrone.getBody(), mousePos.x + 50, mousePos.y + 50);
                evilProjectiles.add(evilProjectile1);
                //evilProjectiles.add(evilProjectile2);
            }
            if (weaponChoice == 3) {
                if (mines.size < 5) {
                    mines.add(new Mine(dronnie.getX(), dronnie.getY()));
                }
            }
            if (weaponChoice == 4) {
                if (portals.size < 2) {
                    LaserPortal laser = new LaserPortal(dronnie.getBody(), mousePos.x, mousePos.y);
                    lasers.add(laser);
                    portals.add(new Portal(mousePos.x, mousePos.y, laser));
                }
            }
        }


        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            //dronnie.setInvisibleMode(false); <- paara trabalhar.
            // System.out.println(TimeUtils.timeSinceMillis(currentTime));
            if (TimeUtils.timeSinceMillis(currentTime) > 100) {
                if (weaponChoice == 2) {
                    currentTime = TimeUtils.millis();
                    //System.out.println(currentTime);
                    camera.unproject(mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                    AutomaticShot evilProjectile1 = new AutomaticShot(dronnie.getBody(), mousePos.x, mousePos.y);
                    automaticShots.add(evilProjectile1);
                }
            }
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            //dronnie.setInvisibleMode(false); <- paara trabalhar.
            //System.out.println(TimeUtils.timeSinceMillis(currentTime));
            if (TimeUtils.timeSinceMillis(currentTime) > 20000) {
                if (weaponChoice == 5) {
                    currentTime = TimeUtils.millis();
                    //System.out.println(currentTime);
                    camera.unproject(mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
                    SimpleShot evilProjectile1 = new SimpleShot(dronnie.getBody(), mousePos.x, mousePos.y);
                    evilProjectiles.add(evilProjectile1);
                }
            }
        }


        if (Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
            //satellites.add(new Satellite(dronnie));
            //satellites.add(new Satellite(dronnie,satellites.get(0)));
            if (weaponNumbers == 1) {
                return;
            }

            weaponChoice++;

            if (weaponChoice > weaponNumbers) {
                weaponChoice = 1;
            }
            System.out.println(weaponChoice + " <-choice  weapons -> " + weaponNumbers);
        }


        createSatellite();


        for (Iterator<Virus> iter = viruses.iterator(); iter.hasNext(); ) {
            Virus virus = iter.next();
            batch.draw(virus.getBodyPic(), virus.getX(), virus.getY());
            if (!virus.isDestroyed()) {
                if (!dronnie.getInvisibleMode()) {
                    virus.moveTowardsPlayer();
                } else {
                    virus.moveRandomly(dronnie);
                }
            }

            if (virus.getBody().overlaps(wave.getBody())) {
                bursts.add(new Burst(virus.getX(), virus.getY()));
                virus.getBodyPic().dispose();
                virus.getBody().setPosition(-1, -1);
                score.covidKilled(1);
                virus.setDestroyed(true);
                dronnie.addScore(10);
            }

            for (int i = 0; i < satellites.size; i++) {
                if (virus.getBody().overlaps(satellites.get(i).getBody())) {
                    explosions2.add(new Explosion2(virus.getX(), virus.getY()));
                    score.covidKilled(1);
                    dronnie.addScore(10);
                    virus.getBodyPic().dispose();
                    virus.getBody().setPosition(-1, -1);
                    virus.setDestroyed(true);
                }
            }


            if (virus.getBody().overlaps(dronnie.getBody())) {
                if (!virus.isDestroyed()) {
                    dronnie.setInfected(true);
                    iter.remove();
                }

            }
            for (Iterator<SimpleShot> iter2 = evilProjectiles.iterator(); iter2.hasNext(); ) {
                SimpleShot simpleShot = iter2.next();
                if (virus.getBody().overlaps(simpleShot.getBody()) && (simpleShot.getFireType() == FireType.PLAYER_FIRE)) {
                    explosions2.add(new Explosion2(virus.getX(), virus.getY()));
                    score.covidKilled(1);
                    dronnie.addScore(10);
                    virus.getBodyPic().dispose();
                    virus.getBody().setPosition(-1, -1);
                    virus.setDestroyed(true);
                }
            }
            for (int i = 0; i < programedShots.size; i++) {

                if (virus.getBody().overlaps(programedShots.get(i).getBody())) {
                    explosions.add(new ExplosionTest(virus.getX(), virus.getY()));
                    dronnie.addScore(10);
                    virus.getBodyPic().dispose();
                    score.covidKilled(1);
                    virus.getBody().setPosition(-1, -1);
                    virus.setDestroyed(true);
                }
            }

            for (int i = 0; i < mines.size; i++) {
                Mine mine = mines.get(i);
                if (virus.getBody().overlaps(mine.getBody())) {
                    mine.setIntact(false);
                    explosions.add(new ExplosionTest(virus.getX(), virus.getY()));
                    score.covidKilled(1);
                    dronnie.addScore(10);
                    virus.getBodyPic().dispose();
                    virus.getBody().setPosition(-1, -1);
                    virus.setDestroyed(true);
                }
            }

            for (Iterator<AutomaticShot> iter3 = automaticShots.iterator(); iter3.hasNext(); ) {
                AutomaticShot automaticShot = iter3.next();
                if (virus.getBody().overlaps(automaticShot.getBody())) {
                    automaticShot.setIsLosted(true);
                    explosions.add(new ExplosionTest(virus.getX(), virus.getY()));
                    score.covidKilled(1);
                    dronnie.addScore(10);
                    virus.getBodyPic().dispose();
                    virus.getBody().setPosition(-1, -1);
                    virus.setDestroyed(true);
                }
            }

        }

        spawnSoapAndBatteries();
        for (Iterator<Toillet> iter = toillets.iterator(); iter.hasNext(); ) {
            Toillet toillet = iter.next();
            batch.draw(toillet.getBodyPic(), toillet.getX(), toillet.getY());
            if (dronnie.getBody().overlaps(toillet.getBody())) {
                if (dronnie.isInfected()) {
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
                dronnie.addEnergy(10);
                if (dronnie.getEnergy() >= 130) {
                    dronnie.setEnergy(130);
                }
                iter.remove();
            }
        }


        // draws Johnsons
        for (Iterator<Boss> iter = bosses.iterator(); iter.hasNext(); ) {
            Boss boss = iter.next();
            batch.draw(boss.getBodyPic(), boss.getX(), boss.getY());
            if (!boss.isDestroyed()) {
                if (!dronnie.getInvisibleMode()) {
                    boss.moveTowardsPlayer();
                    spwanShootDrop(boss, dronnie.getBody());
                } else {
                    boss.moveRandomly(dronnie);
                }
            }

            if (boss.getBody().overlaps(wave.getBody())) {
                bursts.add(new Burst(boss.getX(), boss.getY()));
                boss.setDestroyed(true);
                boss.getBody().setPosition(-1, -1);
                boss.getBodyPic().dispose();
                score.killedDrones(1);
                dronnie.addScore(20);
            }

            for (int i = 0; i < satellites.size; i++) {
                if (boss.getBody().overlaps(satellites.get(i).getBody())) {
                    explosions.add(new ExplosionTest(boss.getX(), boss.getY()));
                    score.killedDrones(1);
                    dronnie.addScore(10);
                    boss.getBodyPic().dispose();
                    boss.getBody().setPosition(-1, -1);
                    boss.setDestroyed(true);
                }
            }

            for (Iterator<SimpleShot> iter3 = evilProjectiles.iterator(); iter3.hasNext(); ) {
                SimpleShot simpleShot = iter3.next();
                if (boss.getBody().overlaps(simpleShot.getBody()) && (simpleShot.getFireType() == FireType.PLAYER_FIRE)) {
                    explosions.add(new ExplosionTest(boss.getX(), boss.getY()));
                    score.killedDrones(1);
                    dronnie.addScore(10);
                    boss.getBodyPic().dispose();
                    boss.getBody().setPosition(-1, -1);
                    boss.setDestroyed(true);
                }
            }

            for (int i = 0; i < programedShots.size; i++) {

                if (boss.getBody().overlaps(programedShots.get(i).getBody())) {
                    explosions.add(new ExplosionTest(boss.getX(), boss.getY()));
                    dronnie.addScore(10);
                    boss.getBodyPic().dispose();
                    score.killedDrones(1);
                    boss.getBody().setPosition(-1, -1);
                    boss.setDestroyed(true);
                }
            }

            for (int i = 0; i < mines.size; i++) {
                Mine mine = mines.get(i);
                if (boss.getBody().overlaps(mine.getBody())) {
                    mine.setIntact(false);
                    explosions.add(new ExplosionTest(boss.getX(), boss.getY()));
                    score.killedDrones(1);
                    dronnie.addScore(10);
                    boss.getBodyPic().dispose();
                    boss.getBody().setPosition(-1, -1);
                    boss.setDestroyed(true);
                }
            }

            for (Iterator<AutomaticShot> iter3 = automaticShots.iterator(); iter3.hasNext(); ) {
                AutomaticShot automaticShot = iter3.next();
                if (boss.getBody().overlaps(automaticShot.getBody())) {
                    automaticShot.setIsLosted(true);
                    explosions.add(new ExplosionTest(boss.getX(), boss.getY()));
                    score.killedDrones(1);
                    dronnie.addScore(10);
                    boss.getBodyPic().dispose();
                    boss.getBody().setPosition(-1, -1);
                    boss.setDestroyed(true);
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

            if (evilProjectile.getBody().overlaps(wave.getBody()) && evilProjectile.getFireType() == FireType.ENEMY_FIRE) {
                dronnie.addHealth(10);
                evilProjectile.setIsLosted(true);
                evilProjectile.getBodyPic().dispose();
                evilProjectile.getBody().setPosition(-1, -1);
            }

            if (!dronnie.getInvisibleMode()) {
                if (evilProjectile.getBody().overlaps(dronnie.getBody()) && evilProjectile.getFireType() == FireType.ENEMY_FIRE) {
                    evilProjectile.setIsLosted(true);
                    dronnie.takeDamage(10);
                    evilProjectile.getBodyPic().dispose();
                    evilProjectile.getBody().setPosition(-1, -1);

                    if (dronnie.getHp() <= 0) {
                        dronnie.setHp(0);
                        health.drawHealth(batch, camera);
                        quarentine.pause();
                        if (dronnie.isAlive()) {
                            explosions.add(new ExplosionTest(dronnie.getX(), dronnie.getY()));
                        }
                        dronnie.getBodyPic().dispose();
                        dronnie.getBody().setPosition(-1, -1);
                        gameOver();
                    }
                }
            }

            if (finalBoss.isAlive()) {
                if (evilProjectile.getBody().overlaps(finalBoss.getBody()) && evilProjectile.getFireType() == FireType.PLAYER_FIRE) {
                    evilProjectile.setIsLosted(true);
                    finalBoss.takeDamage(1);
                }
            }
        }

        for (Iterator<BossMissile> iter = bossMissiles.iterator(); iter.hasNext(); ) {
            BossMissile bossMissile = iter.next();
            bossMissile.chase(batch);
            MissileHp missileHp = new MissileHp(bossMissile);
            missileHp.drawHpBar(batch);
            if (bossMissile.isDestroyed()) {
                explosions.add(new ExplosionTest(bossMissile.getX(), bossMissile.getY()));
                bossMissile.setDestroyed(true);
                bossMissile.getBodyPic().dispose();
                bossMissile.getBody().setPosition(-1, -1);
                iter.remove();
                finalBoss.setMissileDestroyed(true);
                finalBoss.setMissileLaunched(false);
            }
            if (bossMissile.getBody().overlaps(dronnie.getBody())) {
                dronnie.takeDamage(50);
                explosions.add(new ExplosionTest(dronnie.getX(), dronnie.getY()));
                bossMissile.setDestroyed(true);
                bossMissile.getBodyPic().dispose();
                bossMissile.getBody().setPosition(-1, -1);

                if (dronnie.getHp() <= 0) {
                    dronnie.setHp(0);
                    health.drawHealth(batch, camera);
                    quarentine.pause();
                    if (dronnie.isAlive()) {
                        explosions.add(new ExplosionTest(dronnie.getX(), dronnie.getY()));
                    }
                    dronnie.getBodyPic().dispose();
                    dronnie.getBody().setPosition(-1, -1);
                    gameOver();
                }
            }

            for (Iterator<SimpleShot> iterator = evilProjectiles.iterator(); iterator.hasNext(); ) {
                SimpleShot simpleShot = iterator.next();
                if (simpleShot.getBody().overlaps(bossMissile.getBody()) && simpleShot.getFireType() == FireType.PLAYER_FIRE) {
                    bossMissile.takeDamage(1);
                    simpleShot.setIsLosted(true);

                }
            }

            for (Iterator<AutomaticShot> iter3 = automaticShots.iterator(); iter3.hasNext(); ) {
                AutomaticShot automaticShot = iter3.next();
                if (bossMissile.getBody().overlaps(automaticShot.getBody())) {
                    automaticShot.setIsLosted(true);
                    bossMissile.takeDamage(1);
                }
            }

            for (int i = 0; i < mines.size; i++) {
                Mine mine = mines.get(i);
                if (bossMissile.getBody().overlaps(mine.getBody())) {
                    mine.setIntact(false);
                    explosions.add(new ExplosionTest(bossMissile.getX(), bossMissile.getY()));
                    bossMissile.takeDamage(20);
                }
            }

        }

        for (Iterator<Burst> iter = bursts.iterator(); iter.hasNext(); ) {
            Burst burst = iter.next();
            batch.draw(burst.getBodyPic(), burst.getX(), burst.getY());
            if (TimeUtils.timeSinceMillis(burst.getTime()) > 100) {
                burst.getBodyPic().dispose();
                iter.remove();
            }
        }

        for (Iterator<AutomaticShot> iter = automaticShots.iterator(); iter.hasNext(); ) {
            AutomaticShot automaticShot = iter.next();
            if (automaticShot.isLosted()) {
                iter.remove();
            }
            if (automaticShot.getBody().overlaps(finalBoss.getBody())) {
                automaticShot.setIsLosted(true);
                finalBoss.takeDamage(1);
                dronnie.addScore(10);
            }
            automaticShot.fire(batch);
        }

        for (Iterator<Mine> iter = mines.iterator(); iter.hasNext(); ) {
            Mine mine = iter.next();
            if (!mine.isIntact()) {
                iter.remove();
            }
            batch.draw(mine.getBodyPic(), mine.getX(), mine.getY());
        }

        for (Iterator<LaserPortal> iter = lasers.iterator(); iter.hasNext(); ) {
            LaserPortal laser = iter.next();
            if (laser.isLosted()) {
                iter.remove();
            }
            laser.fire(batch);
        }


        Array<ExplosionTest> explosionsToRemove = new Array<ExplosionTest>();
        for (ExplosionTest explosion : explosions) {

            explosion.update(delta);
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

        Array<Explosion3> explosions3ToRemove = new Array<Explosion3>();
        for (Explosion3 explosion : explosions3) {

            explosion.update(delta);
            if (explosion.remove) {
                explosions3ToRemove.add(explosion);
            }
        }

        Array<Portal> portalsToRemove = new Array<Portal>();
        for (Portal portal : portals) {
            if (portals.size >= 2) {
                if (dronnie.getBody().overlaps(portals.get(1).getBody())) {
                    Portal originPortal = portals.get(0);
                    dronnie.setPosition(originPortal.getBody().getX(), originPortal.getBody().getY());
                    portals.get(0).setUsed(true);
                    portals.get(1).setUsed(true);
                }
            }
            portal.update(delta);
            if (portal.remove) {
                portalsToRemove.add(portal);
            }
        }

        explosions.removeAll(explosionsToRemove, true);
        explosions2.removeAll(explosions2ToRemove, true);
        explosions3.removeAll(explosions3ToRemove, true);
        portals.removeAll(portalsToRemove, true);

        //Explosions!

        for (ExplosionTest explosion : explosions) {
            explosion.render(batch);
        }

        for (Explosion2 explosion : explosions2) {
            explosion.render(batch);
        }

        for (Explosion3 explosion : explosions3) {
            explosion.render(batch);
        }

        for (Portal smoke : portals) {
            smoke.render(batch);
        }

        // Menu, energy bar draw & colors!

        batch.setColor(Color.LIGHT_GRAY);
        batch.draw(menuSeperator, camera.position.x - VIEWPORT_WIDTH, camera.position.y + 265, VIEWPORT_WIDTH * 2, camera.position.y);
        batch.setColor(Color.WHITE);
        batch.setColor(Color.DARK_GRAY);
        batch.draw(menuSeperator2, camera.position.x - VIEWPORT_WIDTH, camera.position.y + 271, VIEWPORT_WIDTH * 2, camera.position.y);
        batch.setColor(Color.WHITE);
        batch.setColor(Color.BLACK);
        batch.draw(menuBar, camera.position.x - VIEWPORT_WIDTH, camera.position.y + 275, VIEWPORT_WIDTH * 2, camera.position.y);

        batch.setColor(Color.WHITE);
        checkInfection(batch, camera);
        score.drawScore(batch, camera);
        health.drawHealth(batch, camera);


        if (dronnie.getEnergy() >= 90) {
            batch.setColor(new Color(0, 1, 1, 1));
        } else if (dronnie.getEnergy() >= 60) {
            batch.setColor(Color.ROYAL);
        } else {
            batch.setColor(Color.BLUE);
        }
        batch.draw(energyBar, (camera.position.x + 250), camera.position.y + 290, dronnie.getEnergy(), VIEWPORT_HEIGHT / 2 - 315);


        batch.setColor(Color.FIREBRICK);
        batch.draw(energyBorder, camera.position.x + 225, camera.position.y + 205);


        textTest.setColor(Color.WHITE);


        if (dronnie.getHp() >= 90) {
            batch.setColor(Color.GREEN);
        } else if (dronnie.getHp() >= 60) {
            batch.setColor(Color.ORANGE);
        } else {
            batch.setColor(Color.RED);
        }
        batch.draw(lifeBar, camera.position.x + 441, camera.position.y + 290, dronnie.getHp(), VIEWPORT_HEIGHT / 2 - 315);

        batch.setColor(Color.ROYAL);
        batch.draw(lifeBorder, camera.position.x + 415, camera.position.y + 205);


        batch.setColor(Color.WHITE);

        if (TimeUtils.timeSinceMillis(currentTime2) > 5000) {
            currentTime2 = TimeUtils.millis();
            programedShots.add(new ProgramedShot(dronnie.getBody()));
            programedShots.add(new ProgramedShot(dronnie.getBody()));
            programedShots.add(new ProgramedShot(dronnie.getBody()));
            programedShots.add(new ProgramedShot(dronnie.getBody()));


        }


        dronnie.setPreviousX(dronnie.getX());
        dronnie.setPreviousY(dronnie.getY());
        dronnie.move(DRONE_BACKGROUND_WIDTH, DRONE_BACKGROUND_HEIGHT);

        for (Iterator<ProgramedShot> iter = programedShots.iterator(); iter.hasNext(); ) {
            ProgramedShot automaticShot = iter.next();
            automaticShot.fire(batch);

        }

        for (Iterator<Satellite> iter = satellites.iterator(); iter.hasNext(); ) {
            Satellite satellite = iter.next();
            satellite.move(batch);
        }


        //Ally drone!!!

        createAlly();


        for (Iterator<AllyDrone> iter = allys.iterator(); iter.hasNext(); ) {
            AllyDrone ally = iter.next();
            ally.move(batch);
        }

        batch.end();

        moveCamera();


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
            Virus evilDrone = new Virus(dronnie.getBody(), dronnie);
            viruses.add(evilDrone);
        }

        for (Virus virus : viruses) {
            if (virus.isDestroyed()) {
                viruses.removeIndex(viruses.indexOf(virus, true));
            }
        }

        if (bosses.size <= 10) {
            Boss boss = new Boss(dronnie.getBody(), dronnie);
            bosses.add(boss);
        }

        for (Boss boss : bosses) {
            if (boss.isDestroyed()) {
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
        startTime();

        if (TimeUtils.timeSinceMillis(timeToCloseGame) > 2000) {
            quarentine.dispose();
            game.setScreen(new GameOverScreen(game, SKIN));
        }
        dronnie.setAlive(false);
    }

    private void gameWin() {
        startTime();
        System.out.println(TimeUtils.timeSinceMillis(timeToCloseGame));
        if (TimeUtils.timeSinceMillis(timeToCloseGame) > 2000) {
            quarentine.dispose();
            game.setScreen(new WinScreen(game, SKIN));
        }
        finalBoss.setAlive(false);
    }

    public void startTime() {
        if (!finalBoss.isAlive() || !dronnie.isAlive()) {
            return;
        }
        timeToCloseGame = TimeUtils.millis();
    }

    public void spawnSoapAndBatteries() {
        if (toillets.size <= 20) {
            Toillet toillet = new Toillet();
            toillets.add(toillet);
        }

        if (batteries.size <= 20) {
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
            dronnie.setHp(0);
            health.drawHealth(batch, camera);
            quarentine.pause();
            if (dronnie.isAlive()) {
                explosions.add(new ExplosionTest(dronnie.getX(), dronnie.getY()));
            }
            dronnie.getBodyPic().dispose();
            dronnie.getBody().setPosition(-1, -1);
            gameOver();
        }
    }

    public void moveCamera() {

        if (!dronnie.isAlive()) {
            return;
        }
        //move camera horizontally
        if (dronnie.getX() > VIEWPORT_WIDTH / 2 - PICTURE_SIZE / 2 && dronnie.getX() < (DRONE_BACKGROUND_WIDTH - VIEWPORT_WIDTH / 2) - PICTURE_SIZE / 2) {
            camera.position.set(dronnie.getX() + PICTURE_SIZE / 2, camera.position.y, 0);
        }

        //move camera vertically
        if (dronnie.getY() > VIEWPORT_HEIGHT / 2 && dronnie.getY() < DRONE_BACKGROUND_HEIGHT - VIEWPORT_HEIGHT / 2) {
            camera.position.set(camera.position.x, dronnie.getY(), 0);
        }
    }

    public void createSatellite() {
        if (satellites.size == 4) {
            return;
        }


        if (dronnie.getScore() >= 80) {
            if (satellites.size == 2) {
                if (Math.round(firstSatellite.getDistance().angle()) == 0f) {
                    satellites.add(new Satellite(dronnie, -40, 70));
                    return;
                }
            }
        }
        if (dronnie.getScore() >= 40) {
            if (satellites.size == 1) {
                if (Math.round(firstSatellite.getDistance().angle()) == 0f) {
                    satellites.add(new Satellite(dronnie, -65, -40));
                }
            }
        }

        if (dronnie.getScore() >= 10) {
            if (satellites.size == 0) {
                satellites.add(new Satellite(dronnie, 65, 40));
                firstSatellite = satellites.get(0);
                firstPosX = firstSatellite.getX() - dronnie.getX();
                firstPosY = firstSatellite.getY() - dronnie.getY();
            }
        }
    }

    public void createAlly() {

        if (allys.size >= 2) {

            return;
        }

        if (allys.size == 1 && dronnie.getScore() > 50) {


            if (Math.round(firstSatellite.getDistance().angle()) == 0f) {

                allys.add(new AllyDrone(dronnie, -150, -125));


            }
            return;
        }

        if (dronnie.getScore() > 10 && allys.size == 0) {
            allys.add(new AllyDrone(dronnie, 150, 125));
        }
    }
}



