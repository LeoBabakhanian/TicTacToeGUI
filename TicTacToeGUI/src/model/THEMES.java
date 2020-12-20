package model;

public enum THEMES {
	
	ICECREAM("view/resources/themes/2C_Icon_Bar.png"),
	SPACE("view/resources/themes/Space_Icon_Moon.png"),
	BEACH("view/resources/themes/Beach_Icon_Cocktail.png"),
	NATURE("view/resources/themes/Nature_Icon_Clover.png");
	
	private String theme;
	
	private THEMES(String theme) {
		
		this.theme = theme;
	}
	
	public String getTheme() {
		
		return this.theme;
	}
}
