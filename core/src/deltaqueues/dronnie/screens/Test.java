package deltaqueues.dronnie.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import deltaqueues.dronnie.*;
import deltaqueues.dronnie.backgrounds.RoofTop;
import deltaqueues.dronnie.streetGame.PlataformPlayer;
import deltaqueues.dronnie.tileObjects.*;

import static deltaqueues.dronnie.Utilities.*;

public class Test implements Screen {


    private Game game;
    private TextureAtlas atlas;
    private TextureAtlas atlas2;
    private TextureAtlas atlas3;
    private SpriteBatch batch;
    private SpriteBatch batch2;
    private PlataformPlayer player;

    private OrthogonalTiledMapRenderer renderer;


    private OrthographicCamera camera;
    private OrthographicCamera camera2;

    private Viewport gamePort;
    private Viewport gamePort2;

    private World world;
    private Box2DDebugRenderer b2dr;
    private Body body;
    private FixtureDef fdef;
    private PolygonShape shape;
    private BodyDef bdef;

    private Array<Turret> turrets;
    private Array<Drone> drones;
    private Array<Car> cars;

    private TiledMap map;
    private TmxMapLoader mapLoader;

    private RoofTop parallaxBackground;

    private WorldContactListener worldContactListener;

    private long generateCar;

    public Test(Game game) {

        atlas = new TextureAtlas(Gdx.files.internal("enemies/enemies.atlas"));
        atlas2 = new TextureAtlas(Gdx.files.internal("player/playermove.atlas"));
        atlas3 = new TextureAtlas(Gdx.files.internal("player/gunShots/shots.atlas"));

        this.game = game;
        batch2 = new SpriteBatch();

        batch = new SpriteBatch();
        turrets = new Array<>();
        drones = new Array<>();
        cars = new Array<>();



        camera2 = new OrthographicCamera();
        gamePort2 = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        camera2.position.set(gamePort2.getWorldWidth() / 2, gamePort2.getWorldHeight() / 2, 0);
        camera2.update();
        camera2.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);


        camera = new OrthographicCamera();
        gamePort = new FitViewport(VIEWPORT_WIDTH / PPM, VIEWPORT_HEIGHT / PPM, camera);
        camera.position.set((gamePort.getWorldWidth() / 2), (gamePort.getWorldHeight() / 2), 0);
        camera.update();

        Array<Texture> textures = new Array<Texture>();
        for (int i = 1; i <= 3; i++) {
            textures.add(new Texture(Gdx.files.internal("parallax/roofTop/top_0" + i + ".png")));
            textures.get(textures.size - 1).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }

        parallaxBackground = new RoofTop(textures);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/roofTop/test1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);
        camera.setToOrtho(false, VIEWPORT_WIDTH / 2 / PPM, VIEWPORT_HEIGHT / 2 / PPM - 0.5f);


        world = new World(new Vector2(0, -10f), false);

        b2dr = new Box2DDebugRenderer();

        bdef = new BodyDef();
        shape = new PolygonShape();
        fdef = new FixtureDef();

        player = new PlataformPlayer(world, this);

        createFixtures(3);
        createFixtures(4);
        createFixtures(5);
        createFixtures(6);
        createFixtures(7);

        worldContactListener = new WorldContactListener();
        world.setContactListener(worldContactListener);
    }


    @Override
    public void show() {


    }



    public void update(float delta) {

        player.checkGroundCollision();


        world.step(1 / 60f, 6, 2);
        moveCamera();

        for(Turret enemy : turrets) {
            enemy.update(delta);
        }

        for(Drone enemy : drones) {
            enemy.update(delta);
        }

        for(Car car : cars) {
            if(car.getX() > gamePort.getWorldWidth()) {
                cars.removeValue(car, true);
            }
            car.update(delta);
            if(cars.size >= 1) {
                System.out.println(cars.get(0).getX() + "    " + cars.get(0).getY());
            }
        }


        player.move();
        player.update(delta);
        camera2.update();
        camera.update();

    }


    @Override
    public void render(float delta) {


        update(delta);

        if(TimeUtils.timeSinceMillis(generateCar) > 1000000000) {
            cars.add(new Car(camera2));
        } else {
            generateCar = TimeUtils.millis();
        }


        batch2.begin();
        parallaxBackground.draw(batch2, camera2);


        for(Car car : cars) {
            if(cars.size < 1) {
                return;
            }else {
                car.draw(batch2);
                //System.out.println(cars.get(0).getX() + "    " + cars.get(0).getY());
            }
        }
        batch2.end();



        renderer.render();
       b2dr.render(world, camera.combined);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();


       // System.out.println(player.getBody().getPosition().y);
        player.fire();



        player.draw(batch);



        for(Turret enemy : turrets){



            enemy.draw(batch,player);
        }

        for(Drone enemy : drones) {

            enemy.draw(batch,player);
           }



        renderer.setView(camera);
        batch.end();

        player.setX(player.getBody().getPosition().x);
        player.setY(player.getBody().getPosition().y);




        if(player.isOnLadder()) {
            if(!player.isHanging()) {
                player.getBody().setLinearVelocity(0,0);
            }
            player.setHanging(true);
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

    }


    public void moveCamera() {



        MapProperties mapProperties = map.getProperties();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {

                parallaxBackground.setSpeed(0);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {

                parallaxBackground.setSpeed(1);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {

                parallaxBackground.setSpeed(0);

        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {

                parallaxBackground.setSpeed(-1);
            }

        if(player.getState() == PlataformPlayer.State.IDLE) {
            parallaxBackground.setSpeed(0);
        }


        camera.position.x = player.getBody().getPosition().x;
        camera.position.y = player.getBody().getPosition().y;

        if(camera.position.x < (gamePort.getWorldWidth() / 2) /2) {
            camera.position.x = (gamePort.getWorldWidth() / 2) /2;
        }

        if(camera.position.y < (gamePort.getWorldHeight() / 2) /2) {
            camera.position.y = (gamePort.getWorldHeight() / 2)/2;
        }



    }


    public void createFixtures(int i) {
        for (MapObject object : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
            MapObject mapObject = object;
            mapObject.getProperties().clear();

            if(i == 6) {

                drones.add(new Drone(this, (rectangle.getX() + rectangle.getWidth() / 2)/PPM, (rectangle.getY() + rectangle.getHeight() /2)/ PPM, map, rectangle, mapObject));
                continue;
            }

            if(i == 7) {
                turrets.add(new Turret(this, (rectangle.getX() + rectangle.getWidth() / 2)/PPM, (rectangle.getY() + rectangle.getHeight() /2)/ PPM, map, rectangle));
                continue;
            }


            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2) / PPM, (rectangle.getY() + rectangle.getHeight() / 2) / PPM);
            body = world.createBody(bdef);
            shape.setAsBox((rectangle.getWidth() / 2) / PPM, (rectangle.getHeight() / 2) / PPM);
            fdef.shape = shape;


            if (i == 4) {
                new Ladder(world, map, rectangle,player);

                continue;
            }

            body.createFixture(fdef);
        }
    }




    public World getWorld() {
        return world;
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public TextureAtlas getAtlas2() {
        return atlas2;
    }

    public TextureAtlas getAtlas3() {
        return atlas3;
    }

    public Array<Turret> getTurrets() {
        return turrets;
    }

    public FixtureDef getFdef() {
        return fdef;
    }
}
