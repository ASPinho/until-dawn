package academia.tilldawn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game extends ApplicationAdapter {

	private TextureRegion background;

	private Texture toiletPic;
	private Texture johnsonPic;
	private Texture dronePic;

	private Rectangle drone;
	private Rectangle toilet;
	private Rectangle johnson;

	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Viewport viewport;

	private Vector3 vitor;

	
	@Override
	public void create () {

		background = new TextureRegion(new Texture("background.jpg"), 0, 0, 1920, 800);
	//	toiletPic = new Texture(Gdx.files.internal("toillete.png"));
	//	johnsonPic = new Texture(Gdx.files.internal("johnson.png"));
		dronePic = new Texture(Gdx.files.internal("drone.png"));

		camera = new OrthographicCamera();
		vitor = new Vector3();

		camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		drone = new Rectangle();
		drone.x = 800/2 - 64/2;
		drone.y = 480/2;
		drone.width = 64;
		drone.height = 64;

		

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
		batch.end();

		if (Gdx.input.isTouched()){
			vitor.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(vitor);
			drone.x = vitor.x - 64/2;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			drone.x -= 400 * Gdx.graphics.getDeltaTime();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			drone.x += 400 * Gdx.graphics.getDeltaTime();
		}

		if (drone.x < 0) drone.x = 0;
		if (drone.x > 1920 - 64) drone.x = 1920 - 64;

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			drone.y += 400 * Gdx.graphics.getDeltaTime();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.S)){
			drone.y -= 400 * Gdx.graphics.getDeltaTime();
		}

		if (drone.y < 0) drone.y = 0;
		if (drone.y > 800 - 64) drone.y = 800 - 64;


		//move camera horizontallesly
		if (drone.x > 400 - 64/2 && drone.x < 1520 - 64/2) {

			camera.position.set(drone.getX() + 64/2, camera.position.y, 0);

		}


		//move camera verticallyzation

		if (drone.y > 240 && drone.y < 560){
			camera.position.set(camera.position.x, drone.getY(),0);

		}


		//camera.update();

	}
	
	@Override
	public void dispose () {

		dronePic.dispose();
		batch.dispose();

	}
}
