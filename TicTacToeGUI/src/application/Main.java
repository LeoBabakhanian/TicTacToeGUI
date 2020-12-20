package application;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import view.ViewManager;

public class Main extends Application{
	
	MediaPlayer mediaPlayer;
	
	public void music() {
		
		String s = "Tropicala.mp3";
		Media h = new Media(Paths.get(s).toUri().toString());
		mediaPlayer = new MediaPlayer(h);
		mediaPlayer.setVolume(0.1);
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.play();
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		music();
		ViewManager manager = new ViewManager();
		primaryStage = manager.getMainStage();
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}