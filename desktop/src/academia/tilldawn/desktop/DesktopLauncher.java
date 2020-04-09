package academia.tilldawn.desktop;

import academia.tilldawn.Utilities;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import academia.tilldawn.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Game";
		config.width = Utilities.VIEWPORT_WIDTH;
		config.height = Utilities.VIEWPORT_HEIGHT;
		new LwjglApplication(new Game(), config);

	}
}
