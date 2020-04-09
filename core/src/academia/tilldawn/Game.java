package academia.tilldawn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;


public class Game extends ApplicationAdapter {

	private TextureRegion background;

	private Texture dronePic;
	private Texture evilDronePic;

	private Rectangle drone;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Array<EvilDrone> evilDrones;
	private long lastDropTime;
	
	@Override
	public void create () {

		// starts graphic representations
		background = new TextureRegion(new Texture("background.jpg"), 0, 0, 1920, 800);
		dronePic = new Texture(Gdx.files.internal("drone.png"));
		evilDronePic = new Texture(Gdx.files.internal("evil-drone.png"));

		camera = new OrthographicCamera();

		camera.setToOrtho(false, 1200, 800);

		batch = new SpriteBatch();

		// starts Player Drone logic position
		drone = new Rectangle();
		drone.x = 800/2 - 64/2;
		drone.y = 480/2;
		drone.width = 64;
        drone.height = 64;

		evilDrones = new Array<EvilDrone>();

	}

	private void spawnRaindrop() {

		EvilDrone evilDrone = new EvilDrone(drone);
		evilDrones.add(evilDrone);
		lastDropTime = TimeUtils.nanoTime();

	}


	@Override
	public void render () {

		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0,0 );
		batch.draw(dronePic, drone.x, drone.y);

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
		if (drone.x > 1920 - 64) drone.x = 1920 - 64;

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
		if (drone.y > 800 - 64) drone.y = 800 - 64;



		//move camera horizontally
		if (drone.x > 600 - 64/2 && drone.x < 1320 - 64/2) {
			camera.position.set(drone.getX() + 64/2, camera.position.y, 0);
		}

		//move camera vertically
		/*
		if (drone.y > 240 && drone.y < 560){
			camera.position.set(camera.position.x, drone.getY(),0);
		}
		*/

		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();

	}
	
	@Override
	public void dispose () {

		evilDronePic.dispose();
		dronePic.dispose();
		batch.dispose();

	}


}
