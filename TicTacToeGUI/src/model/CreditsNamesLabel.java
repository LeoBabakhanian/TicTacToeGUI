package model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class CreditsNamesLabel extends Label{

	public final static String FONT_PATH = "src/fonts/MachineGunk-nyqg.ttf";
	public final static String BACKGROUND_IMAGE = "view/resources/blue_button_unfilled.png";
	
	public CreditsNamesLabel(String text) {
		
		setPrefWidth(500);
		setPrefHeight(400);
		setText(text);
		setWrapText(true);
		setLabelFont();
		setAlignment(Pos.CENTER);
	}
	
	private void setLabelFont() {
		
		try {
			setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 65));
			
		} catch(FileNotFoundException e) {
			
			setFont(Font.font("Verdana", 65));
		}
	}
}
