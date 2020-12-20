package model;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class ThemePicker extends VBox {

	private ImageView circleImage;
	private ImageView themeImage;
	
	private String circleNotChosen = "/view/resources/themes/grey_circle.png";
	private String circleChosen = "/view/resources/themes/blue_boxTick.png";
	
	private THEMES theme;
	private boolean isCircleChosen;
	
	public ThemePicker(THEMES theme) {
		
		circleImage = new ImageView(circleNotChosen);
		themeImage = new ImageView(theme.getTheme());
		this.theme = theme;
		isCircleChosen = false;
		this.setAlignment(Pos.CENTER);
		this.setSpacing(20);
		this.getChildren().add(circleImage);
		this.getChildren().add(themeImage);
	}
	
	public THEMES getTheme() {
		
		return theme;
	}
	
	public boolean getIsCircleChosen() {
		
		return isCircleChosen;
	}
	
	public void setIsCircleChosen(boolean isCircleChosen) {
		
		this.isCircleChosen = isCircleChosen;
		String imageToSet = this.isCircleChosen ? circleChosen : circleNotChosen;
		circleImage.setImage(new Image(imageToSet));
	}
}
