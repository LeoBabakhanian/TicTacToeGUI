package view;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.CoinFlipLabel;
import model.CoinFlipSubScene;
import model.CreditsNamesLabel;
import model.InfoLabel;
import model.RulesLabel;
import model.ScoreLabel;
import model.THEMES;
import model.ThemePicker;
import model.TicTacToeButton;
import model.TicTacToeSubScene;

public class ViewManager {
	
	public int coinFlip, Player1Wins = 0, Player2Wins = 0, PlayerWins = 0, CPUWins = 0, counter = 0, counterCPU = 0;
	public boolean playerTurn;
	public String turn, turnCPU = "O";
	
	private static final int HEIGHT = 720;
	private static final int WIDTH = 1080;
	
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private static final int MENU_BUTTONS_START_X = 100;
	private static final int MENU_BUTTONS_START_Y = 245;
	
	private TicTacToeSubScene creditsSubScene;
	private TicTacToeSubScene rulesSubScene;
	private TicTacToeSubScene themeSubScene;
	private TicTacToeSubScene sceneToHide;
	
	List<TicTacToeButton> menuButtons;
	List<ThemePicker> themeList;
	private THEMES chosenTheme;
	
	public ViewManager() {
		
		menuButtons = new ArrayList<>();
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		mainStage.setResizable(false);
		mainStage.getIcons().add(new Image("/model/resources/gameIcon.png"));
		mainStage.setTitle("Two's Creamery - Tic Tac Toe");
		
		createSubScenes();
		createButtons();
		createBackground();
		createLogo();
	}

	private void showSubScene(TicTacToeSubScene subScene) {
		
		if(sceneToHide != null) {
			
			sceneToHide.moveSubScene();
		}
		
		subScene.moveSubScene();
		sceneToHide = subScene;
	}
	
	private void createSubScenes() {
		
		creditsSubScene = new TicTacToeSubScene();
		mainPane.getChildren().add(creditsSubScene);
		
		rulesSubScene = new TicTacToeSubScene();
		mainPane.getChildren().add(rulesSubScene);
		
		createThemeChooserSubScene();
		createCreditsSubScene();
		createRulesSubScene();
	}
	
	private void createRulesSubScene() {
		
		rulesSubScene = new TicTacToeSubScene();
		mainPane.getChildren().add(rulesSubScene);
		
		InfoLabel rulesLabel = new InfoLabel("RULES");
		rulesLabel.setLayoutX(110);
		rulesLabel.setLayoutY(25);
		rulesSubScene.getPane().getChildren().add(rulesLabel);
		
		RulesLabel rules = new RulesLabel(
				"1. The game is played by 2 players on a grid that is 3 squares by 3 squares.\n\n"
				+ "2. Each player takes turns placing their marks on the grid.\n\n"
				+ "3. The first player to get 3 of their marks in a row (up, down, across, or diagonally) is the winner.\n\n"
				+ "4. When all 9 squares are full and no player achieved 3 marks in a row, the game ends in a draw.");
		rules.setLayoutX(50);
		rules.setLayoutY(50);
		rulesSubScene.getPane().getChildren().add(rules);
	}
	
	private void createCreditsSubScene() {
		
		creditsSubScene = new TicTacToeSubScene();
		mainPane.getChildren().add(creditsSubScene);
		
		InfoLabel creditsLabel = new InfoLabel("CREDITS");
		creditsLabel.setLayoutX(110);
		creditsLabel.setLayoutY(50);
		creditsSubScene.getPane().getChildren().add(creditsLabel);
		
		CreditsNamesLabel credits = new CreditsNamesLabel(
				"LEO BABAKHANIAN          RUITAO WU\n\n"
				+ "RAYDON LAM                     VARDGES HARUTYUNYAN\n\n"
				+ "LAWRENCE ANTONIO     RYAN NIETO\n\n"
				+ "ADRIAN HERRERA            SADAF FAKHRIRAVARI");
		credits.setLayoutX(50);
		credits.setLayoutY(50);
		creditsSubScene.getPane().getChildren().add(credits);
	}
	
	private void createThemeChooserSubScene() {
		
		themeSubScene = new TicTacToeSubScene();
		mainPane.getChildren().add(themeSubScene);
		
		InfoLabel chooseThemeLabel = new InfoLabel("CHOOSE YOUR THEME");
		chooseThemeLabel.setLayoutX(110);
		chooseThemeLabel.setLayoutY(50);
		themeSubScene.getPane().getChildren().add(chooseThemeLabel);
		themeSubScene.getPane().getChildren().add(createThemesToChoose());
		themeSubScene.getPane().getChildren().add(createButtonToPlay());
		themeSubScene.getPane().getChildren().add(createButtonToPlayCPU());
	}
	
	private HBox createThemesToChoose() {
		HBox box = new HBox();
		box.setSpacing(20);
		themeList = new ArrayList<>();
		
		for(THEMES theme : THEMES.values()) {
			
			ThemePicker themeToPick = new ThemePicker(theme);
			themeList.add(themeToPick);
			box.getChildren().add(themeToPick);
			
			themeToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
				@Override
				public void handle(MouseEvent event) {
					
					for(ThemePicker theme : themeList) {
						
						theme.setIsCircleChosen(false);
					}
					
					clickSound();
					themeToPick.setIsCircleChosen(true);
					chosenTheme = themeToPick.getTheme();
				}
			});
		}
		box.setLayoutX(300 - (118*2));
		box.setLayoutY(140);
		return box;
	}

	
	private TicTacToeButton createButtonToPlay() {
		
		TicTacToeButton playButton = new TicTacToeButton("VS PLAYER");
		playButton.setLayoutX(338);
		playButton.setLayoutY(350);
		
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				clickSound();
				
				if (chosenTheme != null) {
					
					GameViewManager gameManager = new GameViewManager();
					
					if(counter > 0) {
						
						gameManager.createResetGame(mainStage, chosenTheme);
					}
					else {
						
						gameManager.createNewGame(mainStage, chosenTheme);
					}
				}
			}
		});
		
		return playButton;
	}
	
	private TicTacToeButton createButtonToPlayCPU() {
		
		TicTacToeButton playButtonCPU = new TicTacToeButton("VS COMPUTER");
		playButtonCPU.setLayoutX(75);
		playButtonCPU.setLayoutY(350);
		
		playButtonCPU.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				clickSound();
				
				if (chosenTheme != null) {
					
					GameViewManagerCPU gameManagerCPU = new GameViewManagerCPU();
					
					if(counterCPU % 2 == 0) {
						
						gameManagerCPU.createNewGame(mainStage, chosenTheme);
					}
					else {
						
						gameManagerCPU.createResetGame(mainStage, chosenTheme);
					}
				}
			}
		});
		
		return playButtonCPU;
	}
	
	public Stage getMainStage() {
		
		return mainStage;
	}
	
	private void addMenuButton(TicTacToeButton button) {
		
		button.setLayoutX(MENU_BUTTONS_START_X);
		button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
	}
	
	
	private void createButtons() {
		
		createStartButton();
		createRulesButton();
		createCreditsButton();
		createQuitButton();
	}
	
	MediaPlayer mediaPlayer;
	
	public void clickSound() {
		
		String s = "click.mp3";
		Media h = new Media(Paths.get(s).toUri().toString());
		mediaPlayer = new MediaPlayer(h);
		mediaPlayer.setVolume(0.1);
		mediaPlayer.play();
		//use this sound for menu buttons
	}
	
	public void confirmationSound() {
		
		String s = "confirmation.mp3";
		Media h = new Media(Paths.get(s).toUri().toString());
		mediaPlayer = new MediaPlayer(h);
		mediaPlayer.setVolume(0.1);
		mediaPlayer.play();
		//use this sound for coinflip alert
	}
	
	public void bongSound() {
		
		String s = "bong.mp3";
		Media h = new Media(Paths.get(s).toUri().toString());
		mediaPlayer = new MediaPlayer(h);
		mediaPlayer.setVolume(0.1);
		mediaPlayer.play();
		//Use this sound for X and O placements
	}
	
	MediaPlayer media;
	
	public void winSound() {
		
		String s = "Win_sound.mp3";
		Media h = new Media(Paths.get(s).toUri().toString());
		media = new MediaPlayer(h);
		media.setVolume(0.1);
		media.play();
		//Use this sound for a win at the end of a game
	}
	
	MediaPlayer mediaP;
	
	public void loseSound() {
		
		String s = "Loser_sound.mp3";
		Media h = new Media(Paths.get(s).toUri().toString());
		mediaP = new MediaPlayer(h);
		mediaP.setVolume(0.1);
		mediaP.play();
		//Use this sound for a loss for CPU mode only
	}
	
	MediaPlayer mediaPlay;
	
	public void drawSound() {
		
		String s = "Draw_sound.mp3";
		Media h = new Media(Paths.get(s).toUri().toString());
		mediaPlay = new MediaPlayer(h);
		mediaPlay.setVolume(0.1);
		mediaPlay.play();
		//Use this for a draw at the end of a game
	}
	
	private void createStartButton() {
		
		TicTacToeButton startButton = new TicTacToeButton("PLAY");
		addMenuButton(startButton);
	    
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				clickSound();
				showSubScene(themeSubScene);
			}
		});
	}
	
	private void createRulesButton() {
		
		TicTacToeButton rulesButton = new TicTacToeButton("RULES");
		addMenuButton(rulesButton);
		
		rulesButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				clickSound();
				showSubScene(rulesSubScene);
			}
		});
	}
	
	private void createQuitButton() {
		
		TicTacToeButton quitButton = new TicTacToeButton("EXIT");
		addMenuButton(quitButton);
		
		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				clickSound();
				mainStage.close();
			}
		});
	}
	
	private void createCreditsButton() {
		
		TicTacToeButton creditsButton = new TicTacToeButton("CREDITS");
		addMenuButton(creditsButton);
		
		creditsButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				clickSound();
				showSubScene(creditsSubScene);
			}
		});
	}
	
	private void createBackground() {
		
		Image backgroundImage = new Image("view/resources/bg.png", 1080, 720, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, 
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
		
	}
	
	private void createLogo() {
		
		ImageView logo = new ImageView("view/resources/title.png");
		logo.setFitHeight(150);
		logo.setFitWidth(600);
		logo.setLayoutX(375);
		logo.setLayoutY(20);
		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				
				logo.setEffect(new DropShadow());
			}
		});
		
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent event) {
				
				logo.setEffect(null);
			}
		});
		
		mainPane.getChildren().add(logo);
	}
	
	public class GameViewManager {
		
		private AnchorPane gamePane;
		private Scene vsPlayer;
		
		private CoinFlipSubScene coinFlipSubScene;
		private CoinFlipSubScene sceneToHide;
		
		private static final int GAME_WIDTH = 1080;
		private static final int GAME_HEIGHT = 720;
		
		private final static String ICECREAM_THEME = "view/resources/TC_BG.png";
		private final static String SPACE_THEME = "view/resources/Space_BG.png";
		private final static String BEACH_THEME = "view/resources/Beach_Background.png";
		private final static String NATURE_THEME = "view/resources/Nature_Background.png";
		
		private String[][] game = new String[3][3];
		boolean winner, draw;
		Button button1 = new Button();
		Button button2 = new Button();
		Button button3 = new Button();
		Button button4 = new Button();
		Button button5 = new Button();
		Button button6 = new Button();
		Button button7 = new Button();
		Button button8 = new Button();
		Button button9 = new Button();

		public GameViewManager(){
			
			gamePane = new AnchorPane();
			vsPlayer = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
			mainStage.setScene(vsPlayer);	
			vsPlayer.getStylesheets().add("application/application.css");
		}
		
		private void createNewGame(Stage menuStage, THEMES chosenTheme) {
			
			coinFlipper();
			createTheme(chosenTheme);
			drawBoard();
			createGameButtons();
			scoreboard();
			createGrid();
			playerMove();
			//Put everything before createSubScene function so it loads in before the coinflip dialog
			createSubScene();
			mainStage.show();
		}
		
		private void createResetGame(Stage mainStage, THEMES chosenTheme){
			
			switchPlayer();
			createTheme(chosenTheme);
			drawBoard();
			createGameButtons();
			scoreboard();
			createGrid();
			playerMove();
			mainStage.show();
		}

		private void playerMove() {
			
			String x, o;
			x = createXPiece(chosenTheme);
			o = createOPiece(chosenTheme);
			Image X = new Image(x, 120, 120, true, true);
			Image O = new Image(o, 120, 120, true, true);
			switchPlayer();
			
			button1.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {

						Image currentPlayer;
						
						if (turn == "X") {
							currentPlayer = X;
							switchPlayer();
							game[0][0] = "X";
							checkWinX();
						}
						else {
							currentPlayer = O;
							switchPlayer();
							game[0][0] = "O";
							checkWinO();
						}
						
						button1.setGraphic(new ImageView(currentPlayer));
						button1.setDisable(true);
						bongSound();
					}
				});
			

			button2.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					Image currentPlayer;
					
					if (turn == "X") {
						currentPlayer = X;
						switchPlayer();
						game[0][1] = "X";
						checkWinX();
					}
					else {
						currentPlayer = O;
						switchPlayer();
						game[0][1] = "O";
						checkWinO();
					}
					
					button2.setGraphic(new ImageView(currentPlayer));
					button2.setDisable(true);
					bongSound();
				}

			});

			button3.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					Image currentPlayer;
					
					if (turn == "X") {
						currentPlayer = X;
						switchPlayer();
						game[0][2] = "X";
						checkWinX();
					}
					else {
						currentPlayer = O;
						switchPlayer();
						game[0][2] = "O";
						checkWinO();
					}
					
					button3.setGraphic(new ImageView(currentPlayer));
					button3.setDisable(true);
					bongSound();
				}

			});

			button4.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;
					
					if (turn == "X") {
						currentPlayer = X;
						switchPlayer();
						game[1][0] = "X";
						checkWinX();
					}
					else {
						currentPlayer = O;
						switchPlayer();
						game[1][0] = "O";
						checkWinO();
					}
					
					button4.setGraphic(new ImageView(currentPlayer));
					button4.setDisable(true);
					bongSound();
				}

			});

			button5.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					Image currentPlayer;
					
					if (turn == "X") {
						currentPlayer = X;
						switchPlayer();
						game[1][1] = "X";
						checkWinX();
					}
					else {
						currentPlayer = O;
						switchPlayer();
						game[1][1] = "O";
						checkWinO();
					}
					
					button5.setGraphic(new ImageView(currentPlayer));
					button5.setDisable(true);
					bongSound();
				}

			});

			button6.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					Image currentPlayer;
					
					if (turn == "X") {
						currentPlayer = X;
						switchPlayer();
						game[1][2] = "X";
						checkWinX();
					}
					else {
						currentPlayer = O;
						switchPlayer();
						game[1][2] = "O";
						checkWinO();
					}
					
					button6.setGraphic(new ImageView(currentPlayer));
					button6.setDisable(true);
					bongSound();
				}

			});

			button7.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					Image currentPlayer;
					
					if (turn == "X") {
						currentPlayer = X;
						switchPlayer();
						game[2][0] = "X";
						checkWinX();
					}
					else {
						currentPlayer = O;
						switchPlayer();
						game[2][0] = "O";
						checkWinO();
					}
					
					button7.setGraphic(new ImageView(currentPlayer));
					button7.setDisable(true);
					bongSound();
				}

			});

			button8.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					Image currentPlayer;
					
					if (turn == "X") {
						currentPlayer = X;
						switchPlayer();
						game[2][1] = "X";
						checkWinX();
					}
					else {
						currentPlayer = O;
						switchPlayer();
						game[2][1] = "O";
						checkWinO();
					}
					
					button8.setGraphic(new ImageView(currentPlayer));
					button8.setDisable(true);
					bongSound();
				}

			});
			
			button9.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					Image currentPlayer;
					
					if (turn == "X") {
						currentPlayer = X;
						switchPlayer();
						game[2][2] = "X";
						checkWinX();
					}
					else {
						currentPlayer = O;
						switchPlayer();
						game[2][2] = "O";
						checkWinO();
					}
					
					button9.setGraphic(new ImageView(currentPlayer));
					button9.setDisable(true);
					bongSound();
				}
			});
			
			checkWinX();
			checkWinO();
		}
		
		private void switchPlayer() {
			
			if (counter >= 1) {
				
				if (turn == "X") {
					
					turn = "O";
				}
				else {
					
					turn = "X";
				}
			}
			else if (counter == 0) {
				
				counter++;
				
				if(coinFlip >= 0 && coinFlip <= 49) {
					
					turn = "X";
				}
				else if (coinFlip >= 50 && coinFlip <= 99) {
					
					turn = "O";
				}
			}
		}
		
		private void checkWinX() {
			
			if (game[0][0] == "X" && game[0][1] == "X" && game[0][2] == "X") {
				
				Player1Wins++;
				winner = true;
				winSound();
			}
			else if (game[1][0] == "X" && game[1][1] == "X" && game[1][2] == "X") {
				
				Player1Wins++;
				winner = true;
				winSound();
			}
			else if (game[2][0] == "X" && game[2][1] == "X" && game[2][2] == "X") {
				
				Player1Wins++;
				winner = true;
				winSound();
			}
			else if (game[0][0] == "X" && game[1][0] == "X" && game[2][0] == "X") {
				
				Player1Wins++;
				winner = true;
				winSound();
			}
			else if (game[0][1] == "X" && game[1][1] == "X" && game[2][1] == "X") {
				
				Player1Wins++;
				winner = true;
				winSound();
			}
			else if (game[0][2] == "X" && game[1][2] == "X" && game[2][2] == "X") {
				
				Player1Wins++;
				winner = true;
				winSound();
			}
			else if (game[0][0] == "X" && game[1][1] == "X" && game[2][2] == "X") {
				
				Player1Wins++;
				winner = true;
				winSound();
			}
			else if (game[0][2] == "X" && game[1][1] == "X" && game[2][0] == "X") {
				
				Player1Wins++;
				winner = true;
				winSound();
			}
			else {
				
				winner = false;
			}
			
			checkDraw(winner);
			resetBoard(winner);
		}
		
		private void checkWinO() {
			
			if (game[0][0] == "O" && game[0][1] == "O" && game[0][2] == "O") {
				
				Player2Wins++;
				winner = true;
				winSound();
			}
			else if (game[1][0] == "O" && game[1][1] == "O" && game[1][2] == "O") {
				
				Player2Wins++;
				winner = true;
				winSound();
			}
			else if (game[2][0] == "O" && game[2][1] == "O" && game[2][2] == "O") {
				
				Player2Wins++;
				winner = true;
				winSound();
			}
			else if (game[0][0] == "O" && game[1][0] == "O" && game[2][0] == "O") {
				
				Player2Wins++;
				winner = true;
				winSound();
			}
			else if (game[0][1] == "O" && game[1][1] == "O" && game[2][1] == "O") {
				
				Player2Wins++;
				winner = true;
				winSound();
			}
			else if (game[0][2] == "O" && game[1][2] == "O" && game[2][2] == "O") {
				
				Player2Wins++;
				winner = true;
				winSound();
			}
			else if (game[0][0] == "O" && game[1][1] == "O" && game[2][2] == "O") {
				
				Player2Wins++;
				winner = true;
				winSound();
			}
			else if (game[0][2] == "O" && game[1][1] == "O" && game[2][0] == "O") {
				
				Player2Wins++;
				winner = true;
				winSound();
			}
			else {
				
				winner = false;
			}
			
			checkDraw(winner);
			resetBoard(winner);
		}
		
		private void checkDraw(boolean winner) {
			
			if (winner != true && game[0][0] != null && game[0][1] != null && game[0][2] != null && game[1][0] != null
					&& game[1][1] != null && game[1][2] != null && game[2][0] != null && game[2][1] != null && game[2][2] != null) {
				
				draw = true;
				drawSound();
			}
			else {
				
				draw = false;
			}
			
			resetBoard(draw);
		}
		
		private void resetBoard(boolean condition) {
			
			if(condition == true) {

				GameViewManager gameManager = new GameViewManager();
				gameManager.createResetGame(mainStage, chosenTheme);
			}
			else {
				
				return;
			}
		}
		
		private void createGrid() {

			GridPane grid = new GridPane();

			grid.setHgap(25);
			grid.setVgap(25);

			button1.setStyle("-fx-background-color: transparent;");
			button2.setStyle("-fx-background-color: transparent;");
			button3.setStyle("-fx-background-color: transparent;");
			button4.setStyle("-fx-background-color: transparent;");
			button5.setStyle("-fx-background-color: transparent;");
			button6.setStyle("-fx-background-color: transparent;");
			button7.setStyle("-fx-background-color: transparent;");
			button8.setStyle("-fx-background-color: transparent;");
			button9.setStyle("-fx-background-color: transparent;");

			button1.setPrefSize(150, 150);
			button2.setPrefSize(150, 150);
			button3.setPrefSize(150, 150);
			button4.setPrefSize(150, 150);
			button5.setPrefSize(150, 150);
			button6.setPrefSize(150, 150);
			button7.setPrefSize(150, 150);
			button8.setPrefSize(150, 150);
			button9.setPrefSize(150, 150);

			// grid.setPadding(new Insets(5));

			grid.addRow(0, button1, button2, button3);
			grid.addRow(1, button4, button5, button6);
			grid.addRow(2, button7, button8, button9);
			grid.setLayoutX(290);
			grid.setLayoutY(110);
			gamePane.getChildren().add(grid);
		}

		private void createTheme(THEMES chosenTheme) {
			
			THEMES bg = chosenTheme;
			createBackground(bg);
		}
		
		private String createXPiece(THEMES bg) {
			
			String x = null;
			
			if (bg == THEMES.ICECREAM) {
				
				x = "view/resources/themes/boardPieces/2C_Icon_Bar_NEW.PNG";
			}
			else if (bg == THEMES.SPACE) {
				
				x = "view/resources/themes/boardPieces/Space_Icon_Moon_NEW.png";
			}
			else if (bg == THEMES.BEACH) {
				
				x = "view/resources/themes/boardPieces/Beach_Icon_Cocktail_NEW.PNG";
			}
			else if (bg == THEMES.NATURE) {
				
				x = "view/resources/themes/boardPieces/Nature_Icon_Clover_NEW.PNG";
			}
			
			return x;
		}
		
		private String createOPiece(THEMES bg) {
			
			String o = null;
			
			if (bg == THEMES.ICECREAM) {
				
				o = "view/resources/themes/boardPieces/2C_Icon_Cone_NEW.PNG";
			}
			else if (bg == THEMES.SPACE) {

				o = "view/resources/themes/boardPieces/Space_Icon_Sun_NEW.png";
			}
			else if (bg == THEMES.BEACH) {

				o = "view/resources/themes/boardPieces/Beach_Icon_Ball_NEW.PNG";
			}
			else if (bg == THEMES.NATURE) {

				o = "view/resources/themes/boardPieces/Nature_Icon_Flower_NEW.PNG";
			}
			
			return o;
		}
		
		private void createBackground(THEMES bg) {
			
			String chosenBackground = null;
			
			if (bg == THEMES.ICECREAM) {
				
				chosenBackground = ICECREAM_THEME;
			}
			else if (bg == THEMES.SPACE) {
				
				chosenBackground = SPACE_THEME;
			}
			else if (bg == THEMES.BEACH) {
				
				chosenBackground = BEACH_THEME;
			}
			else if (bg == THEMES.NATURE) {
				
				chosenBackground = NATURE_THEME;
			}
			
			Image backgroundImage = new Image(chosenBackground, 1080, 720, false, true);
			BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, 
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
			gamePane.setBackground(new Background(background));
		}
		
		private void hide(CoinFlipSubScene subScene) {
			
			subScene.moveSubScene();
			sceneToHide = subScene;
		}
		
		private void showCoinFlipSubScene(CoinFlipSubScene subScene) {
			
			if(sceneToHide != null) {
				
				sceneToHide.moveSubScene();
			}
			
			subScene.moveSubScene();
			sceneToHide = subScene;
		}
		
		private void createSubScene() {
			
			coinFlipSubScene = new CoinFlipSubScene();
			gamePane.getChildren().add(coinFlipSubScene);
			
			coinFlipSubScene.getPane().getChildren().add(createOKButton());
			
			String firstTurn = "";
			
			if(coinFlip >= 0 && coinFlip <= 49) {
				
				firstTurn = "PLAYER 1";
			}
			else if (coinFlip >= 50 && coinFlip <= 99) {
				
				firstTurn = "PLAYER 2";
			}
			
			CoinFlipLabel coinFlip = new CoinFlipLabel(firstTurn + " HAS FIRST TURN");
			coinFlip.setLayoutX(100);
			coinFlip.setLayoutY(375);
			coinFlipSubScene.getPane().getChildren().add(coinFlip);
			
			coinFlipSubScene();
		}
		
		private void coinFlipSubScene() {
			
			showCoinFlipSubScene(coinFlipSubScene);
		}
		
		private void setCoinFlip(int coinflip) {
			
			coinFlip = coinflip;
		}
		
		private void coinFlipper() {
			
			Random rand = new Random(System.currentTimeMillis());
			int coinflip = rand.nextInt(99);
			
			setCoinFlip(coinflip);
		}
		
		private void scoreboard() {
			
			ScoreLabel scoreboard = new ScoreLabel("PLAYER 1 WINS: " + Player1Wins);
			scoreboard.setLayoutX(20);
			scoreboard.setLayoutY(10);
			gamePane.getChildren().add(scoreboard);
			
			ScoreLabel scoreboard2 = new ScoreLabel("PLAYER 2 WINS: " + Player2Wins);
			scoreboard2.setLayoutX(840);
			scoreboard2.setLayoutY(10);
			gamePane.getChildren().add(scoreboard2);
		}
		
		private void addGameButton(TicTacToeButton gameButton) {
			
			gameButton.setLayoutX(860);
			gameButton.setLayoutY(650);
			menuButtons.add(gameButton);
			gamePane.getChildren().add(gameButton);
		}
		
		private void drawBoard() {
			
			ImageView gameBoard = new ImageView("view/resources/gameBoard.png");
			//650x450
			gameBoard.setFitHeight(550);
			gameBoard.setFitWidth(550);
			gameBoard.setLayoutX(265);
			gameBoard.setLayoutY(85);
			
			gamePane.getChildren().add(gameBoard);
		}
		
		private void createGameButtons() {
			
			createBackButton();
			createOKButton();
		}
		
		private TicTacToeButton createOKButton() {
			
			TicTacToeButton OK_CPU = new TicTacToeButton("OK");
			OK_CPU.setLayoutX(440);
			OK_CPU.setLayoutY(620);
			
			OK_CPU.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					
					clickSound();
					hide(coinFlipSubScene);
					confirmationSound();
				}
			});
			
			return OK_CPU;
		}
		
		private void createBackButton() {
			
			TicTacToeButton backButton = new TicTacToeButton("< BACK");
			addGameButton(backButton);
			
			backButton.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					
					clickSound();
					mainStage.setScene(mainScene);
				}
			});
		}
		
	}

	public class GameViewManagerCPU {
		
		private AnchorPane gamePaneCPU;
		private Scene vsCPU;
		
		private static final int GAME_WIDTH = 1080;
		private static final int GAME_HEIGHT = 720;
		
		private final static String ICECREAM_THEME = "view/resources/TC_BG.png";
		private final static String SPACE_THEME = "view/resources/Space_BG.png";
		private final static String BEACH_THEME = "view/resources/Beach_Background.png";
		private final static String NATURE_THEME = "view/resources/Nature_Background.png";
		
		private String[][] game = new String[3][3];
		private boolean winCPU, winner, draw;
		Button button1 = new Button();
		Button button2 = new Button();
		Button button3 = new Button();
		Button button4 = new Button();
		Button button5 = new Button();
		Button button6 = new Button();
		Button button7 = new Button();
		Button button8 = new Button();
		Button button9 = new Button();
		
		public GameViewManagerCPU(){
			
			gamePaneCPU = new AnchorPane();
			vsCPU = new Scene(gamePaneCPU, GAME_WIDTH, GAME_HEIGHT);
			mainStage.setScene(vsCPU);
			vsCPU.getStylesheets().add("application/application.css");
		}
		
		private void createNewGame(Stage menuStage, THEMES chosenTheme) {
			
			createTheme(chosenTheme);
			drawBoard();
			createGameButtons();
			scoreboardCPU();
			createGrid();
			playerMove();
			mainStage.show();
		}
		
		private void createResetGame(Stage menuStage, THEMES chosenTheme) {
			
			createTheme(chosenTheme);
			drawBoard();
			createGameButtons();
			scoreboardCPU();
			createGrid();
			CPUMovesFirst();
			mainStage.show();
		}
		
		private void playerMove() {
			
			String x;
			x = createXPiece(chosenTheme);
			Image X = new Image(x, 120, 120, true, true);
			
			button1.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					Image currentPlayer;
					boolean win;
					
					currentPlayer = X;
					game[0][0] = "X";
					win = checkWinX();
					
					button1.setGraphic(new ImageView(currentPlayer));
					button1.setDisable(true);
					bongSound();
					
					if (win != true) {
						
						CPUMove();
					}

					if (draw != true) {
						
						checkWinO();
					}
				}
			});
		
			button2.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;
					boolean win;
					
					currentPlayer = X;
					game[0][1] = "X";
					win = checkWinX();
					
					button2.setGraphic(new ImageView(currentPlayer));
					button2.setDisable(true);
					bongSound();
					
					if (win != true) {
						
						CPUMove();
					}

					if (draw != true) {
						
						checkWinO();
					}
				}
	
			});
	
			button3.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;
					boolean win;
					
					currentPlayer = X;
					game[0][2] = "X";
					win = checkWinX();
					
					button3.setGraphic(new ImageView(currentPlayer));
					button3.setDisable(true);
					bongSound();
					
					if (win != true) {
						
						CPUMove();
					}

					if (draw != true) {
						
						checkWinO();
					}
				}
	
			});
	
			button4.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;
					boolean win;
					
					currentPlayer = X;
					game[1][0] = "X";
					win = checkWinX();
					
					button4.setGraphic(new ImageView(currentPlayer));
					button4.setDisable(true);
					bongSound();

					if (win != true) {
						
						CPUMove();
					}

					if (draw != true) {
						
						checkWinO();
					}
				}
	
			});
	
			button5.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;
					boolean win;
					
					currentPlayer = X;
					game[1][1] = "X";
					win = checkWinX();
					
					button5.setGraphic(new ImageView(currentPlayer));
					button5.setDisable(true);
					bongSound();

					if (win != true) {
						
						CPUMove();
					}

					if (draw != true) {
						
						checkWinO();
					}
				}
	
			});
	
			button6.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;
					boolean win;
					
					currentPlayer = X;
					game[1][2] = "X";
					win = checkWinX();
					
					button6.setGraphic(new ImageView(currentPlayer));
					button6.setDisable(true);
					bongSound();

					if (win != true) {
						
						CPUMove();
					}

					if (draw != true) {
						
						checkWinO();
					}
				}
	
			});
	
			button7.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;
					boolean win;
					
					currentPlayer = X;
					game[2][0] = "X";
					win = checkWinX();
					
					button7.setGraphic(new ImageView(currentPlayer));
					button7.setDisable(true);
					bongSound();

					if (win != true) {
						
						CPUMove();
					}

					if (draw != true) {
						
						checkWinO();
					}
				}
	
			});
	
			button8.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;
					boolean win;
					
					currentPlayer = X;
					game[2][1] = "X";
					win = checkWinX();
					
					button8.setGraphic(new ImageView(currentPlayer));
					button8.setDisable(true);
					bongSound();

					if (win != true) {
						
						CPUMove();
					}

					if (draw != true) {
						
						checkWinO();
					}
				}
	
			});
			
			button9.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;
					boolean win;
					
					currentPlayer = X;
					game[2][2] = "X";
					win = checkWinX();
	
					button9.setGraphic(new ImageView(currentPlayer));
					button9.setDisable(true);
					bongSound();

					if (win != true) {
						
						CPUMove();
					}
					
					if (draw != true) {
						
						checkWinO();
					}
				}
			});
			
			checkWinX();
		}
		
		private void playerMovesSecond() {
			
			String x;
			x = createXPiece(chosenTheme);
			Image X = new Image(x, 120, 120, true, true);
			
			button1.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {

					Image currentPlayer;
					
					currentPlayer = X;
					game[0][0] = "X";
					
					button1.setGraphic(new ImageView(currentPlayer));
					button1.setDisable(true);
					bongSound();
					checkWinX();

					if (winCPU != true) {
						
						CPUMovesFirst();
					}

					if (draw != true) {
						
						checkWinO();
					}
				}
			});
		
			button2.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;
					
					currentPlayer = X;
					game[0][1] = "X";
					
					button2.setGraphic(new ImageView(currentPlayer));
					button2.setDisable(true);
					bongSound();
					checkWinX();

					if (winCPU != true) {
						
						CPUMovesFirst();
					}

					if (draw != true) {
						
						checkWinO();
					}
				}
	
			});
	
			button3.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;

					currentPlayer = X;
					game[0][2] = "X";
					
					button3.setGraphic(new ImageView(currentPlayer));
					button3.setDisable(true);
					bongSound();
					checkWinX();

					if (winCPU != true) {
						
						CPUMovesFirst();
					}
					
					if (draw != true) {
						
						checkWinO();
					}
				}
	
			});
	
			button4.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;
					
					currentPlayer = X;
					game[1][0] = "X";
					
					button4.setGraphic(new ImageView(currentPlayer));
					button4.setDisable(true);
					bongSound();
					checkWinX();

					if (winCPU != true) {
						
						CPUMovesFirst();
					}

					if (draw != true) {
						
						checkWinO();
					}
				}
	
			});
	
			button5.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;
					
					currentPlayer = X;
					game[1][1] = "X";
					
					button5.setGraphic(new ImageView(currentPlayer));
					button5.setDisable(true);
					bongSound();
					checkWinX();

					if (winCPU != true) {
						
						CPUMovesFirst();
					}

					if (draw != true) {
						
						checkWinO();
					}
				}
	
			});
	
			button6.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;
					
					currentPlayer = X;
					game[1][2] = "X";
					
					button6.setGraphic(new ImageView(currentPlayer));
					button6.setDisable(true);
					bongSound();
					checkWinX();

					if (winCPU != true) {
						
						CPUMovesFirst();
					}

					if (draw != true) {
						
						checkWinO();
					}
				}
	
			});
	
			button7.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;
					
					currentPlayer = X;
					game[2][0] = "X";
					
					button7.setGraphic(new ImageView(currentPlayer));
					button7.setDisable(true);
					bongSound();
					checkWinX();
					
					if (winCPU!= true) {
						
						CPUMovesFirst();
					}

					if (draw != true) {
						
						checkWinO();
					}
				}
	
			});
	
			button8.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;
					
					currentPlayer = X;
					game[2][1] = "X";
					
					button8.setGraphic(new ImageView(currentPlayer));
					button8.setDisable(true);
					bongSound();
					checkWinX();
					
					if (winCPU != true) {
						
						CPUMovesFirst();
					}

					if (draw != true) {
						
						checkWinO();
					}
				}
	
			});
			
			button9.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
	
					Image currentPlayer;

					currentPlayer = X;
					game[2][2] = "X";
	
					button9.setGraphic(new ImageView(currentPlayer));
					button9.setDisable(true);
					bongSound();
					checkWinX();
					
					if (winCPU != true) {
						
						CPUMovesFirst();
					}
					
					if (draw != true) {
						
						checkWinO();
					}
				}
			});
		}
		
		private void CPUMove() {
			
			boolean CPUHasWin = CPUTryForWin();

			if (CPUHasWin != true) {
			
				nextMove();
			}
		}
		
		private void CPUMovesFirst() {
			
			boolean CPUHasWin = CPUTryForWin();
			
			if (CPUHasWin != true) {
				
				nextMove();
			}
			
			if (winCPU != true) {
				
				playerMovesSecond();
			}
		}
		
		private void nextMove() {
			
			int row, col;
			String o;
			o = createOPiece(chosenTheme);
			Image O = new Image(o, 120, 120, true, true), currentPlayer;
			currentPlayer = O;
			Random rand = new Random(System.currentTimeMillis());
			
			row = rand.nextInt(2);
			col = rand.nextInt(2);			
			
			if(game[row][col] != null) {
				
				if (game[0][0] == null) {
					
					row = 0;
					col = 0;
				}
				else if (game[0][1] == null) {
				
					row = 0;
					col = 1;
				}
				else if (game[0][2] == null) {
					
					row = 0;
					col = 2;
				}
				else if (game[1][0] == null) {
					
					row = 1;
					col = 0;
				}
				else if (game[1][1] == null) {
					
					row = 1;
					col = 1;
				}
				else if (game[1][2] == null) {
					
					row = 1;
					col = 2;
				}
				else if (game[2][0] == null) {
					
					row = 2;
					col = 0;
				}
				else if (game[2][1] == null) {
					
					row = 2;
					col = 1;
				}
				else if (game[2][2] == null) {
					
					row = 2;
					col = 2;
				}
			}

			if (row == 0 && col == 0) {
				
				button1.setGraphic(new ImageView(currentPlayer));
				button1.setDisable(true);
				game[0][0] = "O";
				
			}
			else if(row == 0 && col == 1) {
				
				button2.setGraphic(new ImageView(currentPlayer));
				button2.setDisable(true);
				game[0][1] = "O";
				
			}
			else if(row == 0 && col == 2) {
				
				button3.setGraphic(new ImageView(currentPlayer));
				button3.setDisable(true);
				game[0][2] = "O";
				
			}
			else if(row == 1 && col == 0) {
				
				button4.setGraphic(new ImageView(currentPlayer));
				button4.setDisable(true);
				game[1][0] = "O";
				
			}
			else if(row == 1 && col == 1) {
				
				button5.setGraphic(new ImageView(currentPlayer));
				button5.setDisable(true);
				game[1][1] = "O";
				
			}
			else if(row == 1 && col == 2) {
				
				button6.setGraphic(new ImageView(currentPlayer));
				button6.setDisable(true);
				game[1][2] = "O";
				
			}
			else if(row == 2 && col == 0) {
				
				button7.setGraphic(new ImageView(currentPlayer));
				button7.setDisable(true);
				game[2][0] = "O";
				
			}
			else if(row == 2 && col == 1) {
				
				button8.setGraphic(new ImageView(currentPlayer));
				button8.setDisable(true);
				game[2][1] = "O";
				
			}
			else if(row == 2 && col == 2) {
				
				button9.setGraphic(new ImageView(currentPlayer));
				button9.setDisable(true);
				game[2][2] = "O";
				
			}
			else { 
				
				return;
			}
		}
		
		private boolean CPUTryForWin() {
			
			boolean hasWin;
			String o;
			o = createOPiece(chosenTheme);
			Image O = new Image(o, 120, 120, true, true);
			
			if (game[0][0] == "O" && game[0][1] == "O" && game[0][2] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[0][2] = "O";
				hasWin = true;
				
				button3.setGraphic(new ImageView(currentPlayer));
				button3.setDisable(true);
				
			}
			else if (game[1][0] == "O" && game[1][1] == "O" && game[1][2] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[1][2] = "O";
				hasWin = true;

				button6.setGraphic(new ImageView(currentPlayer));
				button6.setDisable(true);
				
			}
			else if (game[2][0] == "O" && game[2][1] == "O" && game[2][2] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[2][2] = "O";
				hasWin = true;

				button9.setGraphic(new ImageView(currentPlayer));
				button9.setDisable(true);
				
			}
			else if (game[0][0] == "O" && game[0][2] == "O" && game[0][1] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[0][1] = "O";
				hasWin = true;

				button2.setGraphic(new ImageView(currentPlayer));
				button2.setDisable(true);
				
			}
			else if (game[1][0] == "O" && game[1][2] == "O" && game[1][1] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[1][1] = "O";
				hasWin = true;

				button5.setGraphic(new ImageView(currentPlayer));
				button5.setDisable(true);
				
			}
			else if (game[2][0] == "O" && game[2][2] == "O" && game[2][1] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[2][1] = "O";
				hasWin = true;

				button8.setGraphic(new ImageView(currentPlayer));
				button8.setDisable(true);
				
			}
			else if (game[1][0] == "O" && game[1][1] == "O" && game[1][2] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[1][2] = "O";
				hasWin = true;
				

				button6.setGraphic(new ImageView(currentPlayer));
				button6.setDisable(true);
				
			}
			else if (game[0][1] == "O" && game[0][2] == "O" && game[0][0] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[0][0] = "O";
				hasWin = true;

				button1.setGraphic(new ImageView(currentPlayer));
				button1.setDisable(true);
				
			}
			else if (game[1][1] == "O" && game[1][2] == "O" && game[1][0] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[1][0] = "O";
				hasWin = true;

				button4.setGraphic(new ImageView(currentPlayer));
				button4.setDisable(true);
				
			}
			else if (game[2][1] == "O" && game[2][2] == "O" && game[2][0] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[2][0] = "O";
				hasWin = true;

				button7.setGraphic(new ImageView(currentPlayer));
				button7.setDisable(true);
				
			}
			else if (game[0][0] == "O" && game[1][1] == "O" && game[2][2] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[2][2] = "O";
				hasWin = true;

				button9.setGraphic(new ImageView(currentPlayer));
				button9.setDisable(true);
				
			}
			else if (game[0][0] == "O" && game[2][2] == "O" && game[1][1] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[1][1] = "O";
				hasWin = true;

				button5.setGraphic(new ImageView(currentPlayer));
				button5.setDisable(true);
				
			}	
			else if (game[1][1] == "O" && game[2][2] == "O" && game[0][0] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[0][0] = "O";
				hasWin = true;

				button1.setGraphic(new ImageView(currentPlayer));
				button1.setDisable(true);
				
			}
			else if (game[0][2] == "O" && game[1][1] == "O" && game[2][0] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[2][0] = "O";
				hasWin = true;

				button7.setGraphic(new ImageView(currentPlayer));
				button7.setDisable(true);
				
			}
			else if (game[2][0] == "O" && game[0][2] == "O" && game[1][1] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[1][1] = "O";
				hasWin = true;

				button5.setGraphic(new ImageView(currentPlayer));
				button5.setDisable(true);
				
			}
			else if (game[2][0] == "O" && game[1][1] == "O" && game[0][2] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[0][2] = "O";
				hasWin = true;

				button3.setGraphic(new ImageView(currentPlayer));
				button3.setDisable(true);
				
			}
			else if (game[0][0] == "X" && game[0][1] == "X" && game[0][2] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[0][2] = "O";
				hasWin = true;

				button3.setGraphic(new ImageView(currentPlayer));
				button3.setDisable(true);
				
			}
			else if (game[1][0] == "X" && game[1][1] == "X" && game[1][2] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[1][2] = "O";
				hasWin = true;

				button6.setGraphic(new ImageView(currentPlayer));
				button6.setDisable(true);
				
			}
			else if (game[2][0] == "X" && game[2][1] == "X" && game[2][2] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[2][2] = "O";
				hasWin = true;

				button9.setGraphic(new ImageView(currentPlayer));
				button9.setDisable(true);
				
			}
			else if (game[0][0] == "X" && game[0][2] == "X" && game[0][1] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[0][1] = "O";
				hasWin = true;

				button2.setGraphic(new ImageView(currentPlayer));
				button2.setDisable(true);
				
			}
			else if (game[1][0] == "X" && game[1][2] == "X" && game[1][1] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[1][1] = "O";
				hasWin = true;

				button5.setGraphic(new ImageView(currentPlayer));
				button5.setDisable(true);
				
			}
			else if (game[2][0] == "X" && game[2][2] == "X" && game[2][1] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[2][1] = "O";
				hasWin = true;

				button8.setGraphic(new ImageView(currentPlayer));
				button8.setDisable(true);
				
			}
			else if (game[1][0] == "X" && game[1][1] == "X" && game[1][2] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[1][2] = "O";
				hasWin = true;

				button6.setGraphic(new ImageView(currentPlayer));
				button6.setDisable(true);
				
			}
			else if (game[0][1] == "X" && game[0][2] == "X" && game[0][0] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[0][0] = "O";
				hasWin = true;

				button1.setGraphic(new ImageView(currentPlayer));
				button1.setDisable(true);
				
			}
			else if (game[1][1] == "X" && game[1][2] == "X" && game[1][0] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[1][0] = "O";
				hasWin = true;

				button4.setGraphic(new ImageView(currentPlayer));
				button4.setDisable(true);
				
			}
			else if (game[2][1] == "X" && game[2][2] == "X" && game[2][0] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[2][0] = "O";
				hasWin = true;

				button7.setGraphic(new ImageView(currentPlayer));
				button7.setDisable(true);
				
			}
			else if (game[0][0] == "X" && game[1][1] == "X" && game[2][2] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[2][2] = "O";
				hasWin = true;

				button9.setGraphic(new ImageView(currentPlayer));
				button9.setDisable(true);
				
			}
			else if (game[0][0] == "X" && game[2][2] == "X" && game[1][1] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[1][1] = "O";
				hasWin = true;

				button5.setGraphic(new ImageView(currentPlayer));
				button5.setDisable(true);
				
			}
			else if (game[1][1] == "X" && game[2][2] == "X" && game[0][0] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[0][0] = "O";
				hasWin = true;

				button1.setGraphic(new ImageView(currentPlayer));
				button1.setDisable(true);
				
			}
			else if (game[0][2] == "X" && game[1][1] == "X" && game[2][0] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[2][0] = "O";
				hasWin = true;

				button7.setGraphic(new ImageView(currentPlayer));
				button7.setDisable(true);
				
			}
			else if (game[2][0] == "X" && game[0][2] == "X" && game[1][1] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[1][1] = "O";
				hasWin = true;

				button5.setGraphic(new ImageView(currentPlayer));
				button5.setDisable(true);
				
			}
			else if (game[2][0] == "X" && game[1][1] == "X" && game[0][2] == null) {
				
				Image currentPlayer;
				
				currentPlayer = O;
				game[0][2] = "O";
				hasWin = true;

				button3.setGraphic(new ImageView(currentPlayer));
				button3.setDisable(true);
				
			}
			else {
				
				hasWin = false;
			}
			
			return hasWin;
		}
		
		private boolean checkWinX() {
			
			if (game[0][0] == "X" && game[0][1] == "X" && game[0][2] == "X") {
				
				PlayerWins++;
				winner = true;
				winCPU = true;
				winSound();
			}
			else if (game[1][0] == "X" && game[1][1] == "X" && game[1][2] == "X") {
				
				PlayerWins++;
				winner = true;
				winCPU = true;
				winSound();
			}
			else if (game[2][0] == "X" && game[2][1] == "X" && game[2][2] == "X") {
				
				PlayerWins++;
				winner = true;
				winCPU = true;
				winSound();
			}
			else if (game[0][0] == "X" && game[1][0] == "X" && game[2][0] == "X") {
				
				PlayerWins++;
				winner = true;
				winCPU = true;
				winSound();
			}
			else if (game[0][1] == "X" && game[1][1] == "X" && game[2][1] == "X") {
				
				PlayerWins++;
				winner = true;
				winCPU = true;
				winSound();
			}
			else if (game[0][2] == "X" && game[1][2] == "X" && game[2][2] == "X") {
				
				PlayerWins++;
				winner = true;
				winCPU = true;
				winSound();
			}
			else if (game[0][0] == "X" && game[1][1] == "X" && game[2][2] == "X") {
				
				PlayerWins++;
				winner = true;
				winCPU = true;
				winSound();
			}
			else if (game[0][2] == "X" && game[1][1] == "X" && game[2][0] == "X") {
				
				PlayerWins++;
				winner = true;
				winCPU = true;
				winSound();
			}
			else {
				
				winner = false;
				winCPU = false;
			}
			
			checkDraw(winner);
			resetBoard(winner);
			
			return winner;
		}
		
		private boolean checkWinO() {
			
			if (game[0][0] == "O" && game[0][1] == "O" && game[0][2] == "O") {
				
				CPUWins++;
				winner = true;
				winCPU = true;
				loseSound();
			}
			else if (game[1][0] == "O" && game[1][1] == "O" && game[1][2] == "O") {
				
				CPUWins++;
				winner = true;
				winCPU = true;
				loseSound();
			}
			else if (game[2][0] == "O" && game[2][1] == "O" && game[2][2] == "O") {
				
				CPUWins++;
				winner = true;
				winCPU = true;
				loseSound();
			}
			else if (game[0][0] == "O" && game[1][0] == "O" && game[2][0] == "O") {
				
				CPUWins++;
				winner = true;
				winCPU = true;
				loseSound();
			}
			else if (game[0][1] == "O" && game[1][1] == "O" && game[2][1] == "O") {
				
				CPUWins++;
				winner = true;
				winCPU = true;
				loseSound();
			}
			else if (game[0][2] == "O" && game[1][2] == "O" && game[2][2] == "O") {
				
				CPUWins++;
				winner = true;
				winCPU = true;
				loseSound();
			}
			else if (game[0][0] == "O" && game[1][1] == "O" && game[2][2] == "O") {
				
				CPUWins++;
				winner = true;
				winCPU = true;
				loseSound();
			}
			else if (game[0][2] == "O" && game[1][1] == "O" && game[2][0] == "O") {
				
				CPUWins++;
				winner = true;
				winCPU = true;
				loseSound();
			}
			else {
				
				winner = false;
				winCPU = false;
			}
			
			checkDraw(winner);
			resetBoard(winner);
			
			return winner;
		}
		
		private boolean checkDraw(boolean winner) {
			
			if (winner != true && game[0][0] != null && game[0][1] != null && game[0][2] != null && game[1][0] != null
					&& game[1][1] != null && game[1][2] != null && game[2][0] != null && game[2][1] != null && game[2][2] != null) {
				
				draw = true;
				drawSound();
			}
			else {
				
				draw = false;
			}
			
			resetBoard(draw);
			
			return draw;
		}
		
		private void resetBoard(boolean condition) {
			
			if (condition == true) {
				
				counterCPU++;
				GameViewManagerCPU gameManagerCPU = new GameViewManagerCPU();
				
				if(counterCPU % 2 == 0) {
				
					gameManagerCPU.createNewGame(mainStage, chosenTheme);
				}
				else {
					
					gameManagerCPU.createResetGame(mainStage, chosenTheme);
				}
			}
			else {
				
				return;
			}
		}
		
		private void createGrid() {

			GridPane grid = new GridPane();

			grid.setHgap(25);
			grid.setVgap(25);

			button1.setStyle("-fx-background-color: transparent;");
			button2.setStyle("-fx-background-color: transparent;");
			button3.setStyle("-fx-background-color: transparent;");
			button4.setStyle("-fx-background-color: transparent;");
			button5.setStyle("-fx-background-color: transparent;");
			button6.setStyle("-fx-background-color: transparent;");
			button7.setStyle("-fx-background-color: transparent;");
			button8.setStyle("-fx-background-color: transparent;");
			button9.setStyle("-fx-background-color: transparent;");

			button1.setPrefSize(150, 150);
			button2.setPrefSize(150, 150);
			button3.setPrefSize(150, 150);
			button4.setPrefSize(150, 150);
			button5.setPrefSize(150, 150);
			button6.setPrefSize(150, 150);
			button7.setPrefSize(150, 150);
			button8.setPrefSize(150, 150);
			button9.setPrefSize(150, 150);

			grid.addRow(0, button1, button2, button3);
			grid.addRow(1, button4, button5, button6);
			grid.addRow(2, button7, button8, button9);
			grid.setLayoutX(290);
			grid.setLayoutY(110);
			gamePaneCPU.getChildren().add(grid);
		}
		
		private String createXPiece(THEMES bg) {
			
			String x = null;
			
			if (bg == THEMES.ICECREAM) {
				
				x = "view/resources/themes/boardPieces/2C_Icon_Bar_NEW.PNG";
			}
			else if (bg == THEMES.SPACE) {
				
				x = "view/resources/themes/boardPieces/Space_Icon_Moon_NEW.png";
			}
			else if (bg == THEMES.BEACH) {
				
				x = "view/resources/themes/boardPieces/Beach_Icon_Cocktail_NEW.PNG";
			}
			else if (bg == THEMES.NATURE) {
				
				x = "view/resources/themes/boardPieces/Nature_Icon_Clover_NEW.PNG";
			}
			
			return x;
		}
		
		private String createOPiece(THEMES bg) {
			
			String o = null;
			
			if (bg == THEMES.ICECREAM) {
				
				o = "view/resources/themes/boardPieces/2C_Icon_Cone_NEW.PNG";
			}
			else if (bg == THEMES.SPACE) {

				o = "view/resources/themes/boardPieces/Space_Icon_Sun_NEW.png";
			}
			else if (bg == THEMES.BEACH) {

				o = "view/resources/themes/boardPieces/Beach_Icon_Ball_NEW.PNG";
			}
			else if (bg == THEMES.NATURE) {

				o = "view/resources/themes/boardPieces/Nature_Icon_Flower_NEW.PNG";
			}
			
			return o;
		}
		
		private void scoreboardCPU() {
			
			ScoreLabel scoreboard = new ScoreLabel("PLAYER WINS: " + PlayerWins);
			scoreboard.setLayoutX(20);
			scoreboard.setLayoutY(10);
			gamePaneCPU.getChildren().add(scoreboard);
			
			ScoreLabel scoreboard2 = new ScoreLabel("CPU WINS: " + CPUWins);
			scoreboard2.setLayoutX(840);
			scoreboard2.setLayoutY(10);
			gamePaneCPU.getChildren().add(scoreboard2);
		}
		
		private void createBackground(THEMES bg) {
			
			String chosenBackground = null;
			
			if (bg == THEMES.ICECREAM) {
				
				chosenBackground = ICECREAM_THEME;
			}
			else if (bg == THEMES.SPACE) {
				
				chosenBackground = SPACE_THEME;
			}
			else if (bg == THEMES.BEACH) {
				
				chosenBackground = BEACH_THEME;
			}
			else if (bg == THEMES.NATURE) {
				
				chosenBackground = NATURE_THEME;
			}
			
			Image backgroundImage = new Image(chosenBackground, 1080, 720, false, true);
			BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, 
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
			gamePaneCPU.setBackground(new Background(background));
		}
		
		private void drawBoard() {
			
			ImageView gameBoard = new ImageView("view/resources/gameBoard.png");
			//650x450
			gameBoard.setFitHeight(550);
			gameBoard.setFitWidth(550);
			gameBoard.setLayoutX(265);
			gameBoard.setLayoutY(85);
			
			gamePaneCPU.getChildren().add(gameBoard);
		}
		
		private void createTheme(THEMES chosenTheme) {
			
			THEMES bg = chosenTheme;
			createBackground(bg);
		}
			
		private void addGameButton(TicTacToeButton gameButton) {
			
			gameButton.setLayoutX(860);
			gameButton.setLayoutY(650);
			menuButtons.add(gameButton);
			gamePaneCPU.getChildren().add(gameButton);
		}
		
		private void createGameButtons() {
			
			createBackButton();
		}
		
		private void createBackButton() {
			
			TicTacToeButton backButton = new TicTacToeButton("< BACK");
			addGameButton(backButton);
			
			backButton.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					
					clickSound();
					mainStage.setScene(mainScene);
				}
			});
		}
	}
}
