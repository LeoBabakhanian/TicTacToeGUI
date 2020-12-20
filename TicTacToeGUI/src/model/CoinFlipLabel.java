package model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class CoinFlipLabel extends Label{

	public final static String FONT_PATH = "src/fonts/MachineGunk-nyqg.ttf";
	public final static String BACKGROUND_IMAGE = "view/resources/blue_button_unfilled.png";
	
	public CoinFlipLabel(String text) {
		
		setPrefWidth(900);
		setPrefHeight(200);
		setText(text);
		setWrapText(true);
		setLabelFont();
		setAlignment(Pos.CENTER);
	}
	
	public int coinFlipper() {
		
		Random rand = new Random();
		int coinFlip = rand.nextInt(99);
		
		return coinFlip;
	}
	
	private void setLabelFont() {
		
		try {
			setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 80));
			
		} catch(FileNotFoundException e) {
			
			setFont(Font.font("Verdana", 70));
		}
	}
}
