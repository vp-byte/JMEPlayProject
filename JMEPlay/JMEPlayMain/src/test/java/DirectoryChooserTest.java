import java.io.File;

import javafx.stage.*;

public class DirectoryChooserTest {
	public static void main(String[] args) {
		DirectoryChooser chooser = new DirectoryChooser();
		chooser.setTitle("JavaFX Projects");
		File defaultDirectory = new File(System.getProperty("user.home"));
		chooser.setInitialDirectory(defaultDirectory);
		File selectedDirectory = chooser.showDialog(null);
	}
}
