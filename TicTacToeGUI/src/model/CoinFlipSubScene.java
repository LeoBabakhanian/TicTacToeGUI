package model;
import javafx.animation.TranslateTransition;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class CoinFlipSubScene extends SubScene {
	
	private final static String BACKGROUND_IMAGE = "model/resources/Coinflip.png";
	
	private boolean isHidden;
	
	public CoinFlipSubScene() {
		
		super(new AnchorPane(), 1080, 720);
		prefWidth(1080);
		prefHeight(720);
		
		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 1080, 720, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		
		AnchorPane root2 = (AnchorPane) this.getRoot();
		root2.setBackground(new Background(image));
		
		isHidden = true;
		setLayoutX(1080);
		setLayoutY(0);
	}
	
	public void moveSubScene() {
		
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(this);
		
		if (isHidden) {
			
			transition.setToX(-1080);
			isHidden = false;
		}
		else {
			
			transition.setToX(1000);
			isHidden = true;
		}
		
		transition.play();
	}
	
	public AnchorPane getPane() {
		
		return (AnchorPane) this.getRoot();
	}
}
