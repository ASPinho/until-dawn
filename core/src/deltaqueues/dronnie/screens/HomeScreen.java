package deltaqueues.dronnie.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import deltaqueues.dronnie.dialogue.Dialogue;
import deltaqueues.dronnie.dialogue.DialogueController;
import deltaqueues.dronnie.dialogue.DialogueNode;
import deltaqueues.dronnie.ui.DialogueBox;
import deltaqueues.dronnie.tileObjects.ExitHouses;
import deltaqueues.dronnie.tileObjects.Wall;
import deltaqueues.dronnie.WorldContactListener;
import deltaqueues.dronnie.streetGame.PlayerRpg;
import deltaqueues.dronnie.ui.OptionBox;
import deltaqueues.dronnie.ui.OptionBoxController;


import static deltaqueues.dronnie.Utilities.*;

public class HomeScreen implements Screen {

    private Game game;
    private SpriteBatch batch;
    private TmxMapLoader mapLoader;
    private final Screen firstLevel;


    private Stage uiStage;
    private Table root;
    private DialogueBox dialogueBox;
    private OptionBox optionBox;
    private OptionBoxController optionBoxController;
    private Dialogue dialogue;
    private DialogueController dialogueController;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private PlayerRpg player;

    private Array<Texture> bodyPic;

    private World world;
    private Box2DDebugRenderer b2dr;
    private Body body;
    private FixtureDef fdef;
    private PolygonShape shape;
    private BodyDef bdef;





    private Viewport gamePort;

    public TiledMap getMap() {
        return map;
    }

    public void setMap(TiledMap map) {
        this.map = map;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public HomeScreen(Game game, Screen firstLevel, final Skin skin) {

        bodyPic = new Array<>();

        bodyPic.add(new Texture(Gdx.files.internal("player/rpgStyle/up.png")));
        bodyPic.add(new Texture(Gdx.files.internal("player/rpgStyle/right.png")));
        bodyPic.add(new Texture(Gdx.files.internal("player/rpgStyle/left.png")));
        bodyPic.add(new Texture(Gdx.files.internal("player/rpgStyle/down.png")));

        this.game = game;
        this.firstLevel = firstLevel;
        //this.batch = batch;

        camera = new OrthographicCamera();

        gamePort = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, camera);

        world = new World(new Vector2(0, 0), true);
        b2dr = new Box2DDebugRenderer();

        bdef = new BodyDef();
        shape = new PolygonShape();
        fdef = new FixtureDef();

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("interiors/home3.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        //camera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2,0);
        camera.setToOrtho(false, VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2);
        player = new PlayerRpg(world, this);
        batch = new SpriteBatch();

        createFixtures(2);
        createFixtures(3);
        createFixtures(4);
        createFixtures(5);
        createFixtures(6);
        createFixtures(7);

        initUI(skin);

        world.setContactListener(new WorldContactListener());
        optionBoxController = new OptionBoxController(optionBox);

        dialogue = new Dialogue();
        dialogueController = new DialogueController(dialogueBox, optionBox);

        DialogueNode node1 = new DialogueNode("Hello", 0);
        DialogueNode node2 = new DialogueNode("Are you ok", 1);
        DialogueNode node3 = new DialogueNode("I hope you get better", 2);
        DialogueNode node4 = new DialogueNode("That's the spirit!!! keep on!", 3);

        node1.makeLinear(node2.getId());
        node2.addChoice("no", 2);
        node2.addChoice("yes", 3);

        dialogue.addNode(node1);
        dialogue.addNode(node2);
        dialogue.addNode(node3);
        dialogue.addNode(node4);

        dialogueController.startDialogue(dialogue);
    }

    @Override
    public void show() {

    }

    public void handleInput(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.W) && player.b2body.getLinearVelocity().y <= 2) {
            player.b2body.applyLinearImpulse(0f, 1000f, player.getX(), player.getY(), true);
            player.setRegion(bodyPic.get(0));
            return;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) /*&& player.b2body.getLinearVelocity().x <= 2*/) {
            player.b2body.applyLinearImpulse(1000f, 0f, player.getX(), player.getY(), true);
            player.setRegion(bodyPic.get(1));
            return;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(-1000f, 0f, player.getX(), player.getY(), true);
            player.setRegion(bodyPic.get(2));
            return;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)/* && player.b2body.getLinearVelocity().y >= 2*/) {
            player.b2body.applyLinearImpulse(0f, -1000f, player.getX(), player.getY(), true);
            player.setRegion(bodyPic.get(3));
            return;
        }
    }

    public void update(float delta) {
        handleInput(delta);

        world.step(1 / 60f, 6, 2);

        player.update(delta);



        camera.position.set(player.b2body.getPosition().x, player.b2body.getPosition().y, 0);


        camera.update();
        player.b2body.setLinearVelocity(0f, 0f);
        renderer.setView(camera);
    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        uiStage.act(delta);

        b2dr.render(world, camera.combined);

        dialogueController.keyDown();
        dialogueController.keyUp();
        dialogueController.update(delta);


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.draw(batch);
        batch.end();

        uiStage.draw();

        backToStreet();

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

    public void createFixtures(int i) {
        for (MapObject object : map.getLayers().get(i).getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rectangle = ((RectangleMapObject) object).getRectangle();


            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rectangle.getX() + rectangle.getWidth() / 2), (rectangle.getY() + rectangle.getHeight() / 2));
            body = world.createBody(bdef);
            shape.setAsBox((rectangle.getWidth() / 2), (rectangle.getHeight() / 2));
            fdef.shape = shape;

            if (i == 2) {
                new Wall(world, map, rectangle);
                continue;
            }
            if (i == 7) {
                new ExitHouses(world, map, rectangle);
                continue;
            }

            body.createFixture(fdef);
        }
    }

    public Array<Texture> getBodyPic() {
        return bodyPic;
    }

    public void initUI(Skin skin) {
        uiStage = new Stage(new ScreenViewport());
        //uiStage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        root = new Table();
        root.setFillParent(true);
        uiStage.addActor(root);


        dialogueBox = new DialogueBox(skin);
        optionBox = new OptionBox(skin);
        optionBox.setVisible(false);


        Table dialogueTable = new Table();
        dialogueTable.add(optionBox)
                .expand()
                .align(Align.right)
                .space(8f)
                .row();
        dialogueTable.add(dialogueBox)
                .expand()
                .align(Align.bottom)
                .space(8f)
                .row();


        root.add(dialogueTable)
                .expand()
                .align(Align.left)
                .pad(8f)
        ;
    }

    public void backToStreet() {
        for (int i = 0; i < world.getContactList().size; i++) {
            if (world.getContactList().get(i).getFixtureA().getFilterData().categoryBits == EXITED_BIT) {
                game.setScreen(firstLevel);
            }
            if (world.getContactList().get(i).getFixtureB().getFilterData().categoryBits == EXITED_BIT) {
                game.setScreen(firstLevel);
            }
        }
    }
}
