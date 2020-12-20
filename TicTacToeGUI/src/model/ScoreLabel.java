package model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class ScoreLabel extends Label{

	public final static String FONT_PATH = "src/fonts/SMW2-Yoshis-Island.ttf";
	private final static String FONT_PATH_2 = "src/fonts/MachineGunk-nyqg.ttf";
	public final static String BACKGROUND_IMAGE = "view/resources/blue_button_unfilled.png";
	
	public ScoreLabel (String text) {
		
		setPrefWidth(220);
		setPrefHeight(49);
		setText(text);
		setWrapText(true);
		setScoreFont();
		setAlignment(Pos.CENTER);
		
		BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, 220, 49, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		setBackground(new Background(backgroundImage));
	}
	
	private void setScoreFont() {
		
		try {
			setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH_2)), 25));
			
		} catch(FileNotFoundException e) {
			
			setFont(Font.font("Verdana", 20));
		}
	}
}
