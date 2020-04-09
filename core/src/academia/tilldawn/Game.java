package academia.tilldawn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import static academia.tilldawn.Utilities.*;

import java.util.Iterator;


public class Game extends ApplicationAdapter {

	private TextureRegion background;

	private Texture dronePic;
	private Texture evilDronePic;
	private Texture beaconPic;

	private Rectangle drone;
	private Array<EvilDrone> evilDrones;
	private Beacon beacon;

	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Sprite arrow;

	private long lastDropTime;
	private String yourScoreName;
	private BitmapFont yourBitmapFontName;
	private BitmapFont hp;
	
	@Override
	public void create () {

		// starts graphic representations
		background = new TextureRegion(new Texture("map-bkg-medium.jpg"), 0, 0, BACKGROUND_WIDTH, BACKGROUND_HEIGHT);
		dronePic = new Texture(Gdx.files.internal("bonnie-drone-32.png"));
		evilDronePic = new Texture(Gdx.files.internal("virus-32.png"));
		beaconPic = new Texture(Gdx.files.internal("arrowRight.png"));


		camera = new OrthographicCamera();

		camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

		batch = new SpriteBatch();

		// starts Player Drone logic position
		drone = new Rectangle();
		drone.x = PICTURE_SIZE*2;
		drone.y = BACKGROUND_HEIGHT/2 - PICTURE_SIZE/2;
		drone.width = PICTURE_SIZE;
		drone.height = PICTURE_SIZE;

		evilDrones = new Array<EvilDrone>();
		beacon = new Beacon(camera);

		arrow = new Sprite(beaconPic, (int)beacon.getX(), (int)beacon.getY());

		yourScoreName = "SCORE: 0";
		hp = new BitmapFont();
		yourBitmapFontName = new BitmapFont();
	}


	@Override
	public void render () {

		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0,0 );
		batch.draw(dronePic, drone.x, drone.y);

		arrow.rotate(-45);
		arrow.draw(batch);

		//batch.draw(beaconPic, beacon.getX(), beacon.getY());

		yourBitmapFontName.setColor(Color.GREEN);
		yourBitmapFontName.draw(batch, yourScoreName, camera.position.x - VIEWPORT_WIDTH/2 + 20, camera.position.y + VIEWPORT_HEIGHT/2 - 20);
		hp.setColor(Color.GREEN);
		hp.draw(batch,"HEALTH: 100", camera.position.x + VIEWPORT_WIDTH/2 - 150, camera.position.y + VIEWPORT_HEIGHT/2 - 20);

		// draws EvilDrones in position and moves them towards PlayerDrone;
		for(EvilDrone raindrop: evilDrones) {
			batch.draw(evilDronePic, raindrop.getRectangle().x, raindrop.getRectangle().y);
			raindrop.moveTowardsPlayer();
		}
		batch.end();

		// Player move left
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			drone.x -= 400 * Gdx.graphics.getDeltaTime();
		}

		// Player move right
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			drone.x += 400 * Gdx.graphics.getDeltaTime();
		}

		// Horizontal bounds
		if (drone.x < 0) drone.x = 0;
		if (drone.x > BACKGROUND_WIDTH - PICTURE_SIZE) drone.x = BACKGROUND_WIDTH - PICTURE_SIZE;

		// Player move up
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			drone.y += 400 * Gdx.graphics.getDeltaTime();
		}

		// Player move down
		if (Gdx.input.isKeyPressed(Input.Keys.S)){
			drone.y -= 400 * Gdx.graphics.getDeltaTime();
		}

		// Vertical bounds
		if (drone.y < 0) drone.y = 0;
		if (drone.y > BACKGROUND_HEIGHT - 64) drone.y = BACKGROUND_HEIGHT - 64;

		//move camera horizontally
		if (drone.x > VIEWPORT_WIDTH/2 - PICTURE_SIZE/2 && drone.x < (BACKGROUND_WIDTH - VIEWPORT_WIDTH/2) - PICTURE_SIZE/2) {
			camera.position.set(drone.getX() + PICTURE_SIZE/2, camera.position.y, 0);
		}

		//move camera vertically
		if (drone.y > VIEWPORT_HEIGHT/2 && drone.y < BACKGROUND_HEIGHT - VIEWPORT_HEIGHT/2){
			camera.position.set(camera.position.x, drone.getY(),0);
		}

		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
	}

	@Override
	public void dispose () {

		evilDronePic.dispose();
		dronePic.dispose();
		batch.dispose();
	}

	private void drawScore(){

	}

	private void spawnRaindrop() {

		if (evilDrones.size >= 5) {
			return;
		}

		EvilDrone evilDrone = new EvilDrone(drone);
		evilDrones.add(evilDrone);
		lastDropTime = TimeUtils.nanoTime();
	}
}
