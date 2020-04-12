package deltaqueues.dronnie.desktop;
import deltaqueues.dronnie.Utilities;
import deltaqueues.dronnie.screens.TitleScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class DesktopLauncher extends Game {
	private Game game;
	private Screen screen;
	private Skin skin;

	public DesktopLauncher() {
		game = this;
	}

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Game";
		config.width = Utilities.VIEWPORT_WIDTH;
		config.height = Utilities.VIEWPORT_HEIGHT;
		//Important!!!
		config.resizable = false;
		new LwjglApplication(new DesktopLauncher(), config);

	}

	@Override
	public void create() {
		skin = new Skin(Gdx.files.internal("skins/sgx/skin/glassyui/glassy-ui.json"));

		screen = new TitleScreen(game, skin);
		this.setScreen(screen);

	}

	@Override
	public void render () {
		super.render();
	}
}
