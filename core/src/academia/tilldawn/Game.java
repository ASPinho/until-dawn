package academia.tilldawn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.media.sound.SF2InstrumentRegion;

public class Game extends ApplicationAdapter {

	private TextureRegion background;

	private Texture toiletPic;
	private Texture johnsonPic;
	private Texture dronePic;
	private Texture evilDronePic;

	private Rectangle drone;
	private Rectangle toilet;
	private Rectangle johnson;
	private Rectangle evilDrone;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Viewport viewport;

	private Vector3 touchPos;

	
	@Override
	public void create () {

		// starts graphic representations
		background = new TextureRegion(new Texture("background.jpg"), 0, 0, 1920, 800);
		dronePic = new Texture(Gdx.files.internal("drone.png"));
		evilDronePic = new Texture(Gdx.files.internal("evil-drone.png"));

		camera = new OrthographicCamera();
		touchPos = new Vector3();

		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();


		// starts Player Drone logic position
		drone = new Rectangle();
		drone.x = 800/2 - 64/2;
		drone.y = 480/2;
		drone.width = 64;
        drone.height = 64;

		// starts Enemy Drone logic position
		evilDrone = new Rectangle();
		evilDrone.x = 1200;
		evilDrone.y = 600;
		evilDrone.width = 64;
		evilDrone.height = 64;

	}

	@Override
	public void render () {

/*
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);*/
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0,0 );
		batch.draw(dronePic, drone.x, drone.y);
		batch.draw(evilDronePic, evilDrone.x, evilDrone.y);
		batch.end();

		/*
		if (Gdx.input.isTouched()){
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			drone.x = touchPos.x - 64/2;
		}*/

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

		moveTowardsPlayer();


		//move camera horizontally
		if (drone.x > 400 - 64/2 && drone.x < 1520 - 64/2) {
			camera.position.set(drone.getX() + 64/2, camera.position.y, 0);
		}

		//move camera vertically
		if (drone.y > 240 && drone.y < 560){
			camera.position.set(camera.position.x, drone.getY(),0);
		}



	}
	
	@Override
	public void dispose () {

		evilDronePic.dispose();
		dronePic.dispose();
		batch.dispose();

	}

	private void moveTowardsPlayer(){

		switch (checkHorizontalDirection()){
			case LEFT:
				evilDrone.x -= 250 * Gdx.graphics.getDeltaTime();
				break;

			case RIGHT:
				evilDrone.x += 250 * Gdx.graphics.getDeltaTime();
				break;
		}

		switch (checkVerticalDirection()){
			case DOWN:
				evilDrone.y -= 250 * Gdx.graphics.getDeltaTime();
				break;
			case UP:
				evilDrone.y += 250 * Gdx.graphics.getDeltaTime();
		}

	}

	private Directions checkHorizontalDirection(){

		float enemyX = evilDrone.x;
		float playerX = drone.x;

		int distance = (int) (enemyX - playerX);

		if (distance == 0){
			return Directions.STAY;
		}

		return distance > 0 ? Directions.LEFT : Directions.RIGHT;
	}

	private Directions checkVerticalDirection(){

		float enemyY = evilDrone.y;
		float playerY = drone.y;

		int distance = (int) (enemyY - playerY);

		if (distance == 0) {
			return Directions.STAY;
		}

		return distance > 0 ? Directions.DOWN : Directions.UP;
	}
}
