package be.he2b.atl.wordle.view.fx;

import be.he2b.atl.common.message.Message;
import be.he2b.atl.common.message.MessageEndGame;
import be.he2b.atl.common.message.MessageError;
import be.he2b.atl.common.message.MessageNbGame;
import be.he2b.atl.common.model.GameData;
import be.he2b.atl.common.model.Pair;
import be.he2b.atl.wordle.model.Wordle;
import g56167.atl.wordle.view.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * JavaFX WordleView / wordleView for Wordle.
 */
public class WordleView extends VBox implements Observer {

    private final Stage primaryStage;
    private HBox mainField;
    private VBox gameField;
    private VBox infoField;
    private MenuBar menubar;

    private Label title;
    private GameGrid gamePanel;
    private PropositionField proposition;
    private Prompt prompt;
    private Label nbGameHard;
    private Label nbGameEasy;
    private Score score;
    private WordList dictionary;

    private final Wordle wordle;
    private final App app;

    private static final int HEIGHT = 600;
    private static final int WIDTH = 600;

    public WordleView(App app, Stage primaryStage, Wordle wordle) throws IOException {
        this.app = app;
        this.primaryStage = primaryStage;
        this.wordle = wordle;
        setScene(primaryStage);
        wordle.addObserver(this);
        wordle.askForNewGame();
    }

    private void setScene(Stage primaryStage) {
        VBox root = new VBox();
        mainField = new HBox();

        //String ff ="hello";
        //menubar = new MenuBar(new Menu(ff, root,test,test2 ));
        Menu menu = new Menu("menu");
        Menu subMenu = new Menu("options");
        MenuBar menubar = new MenuBar();

        MenuItem Close = new MenuItem("Quitter");
        MenuItem Launch = new MenuItem("Relancer");
        MenuItem hard = new MenuItem("Hard");
        MenuItem easy = new MenuItem("easy");
        menu.getItems().add(Close);
        menu.getItems().add(Launch);
        subMenu.getItems().add(hard); //54317
        subMenu.getItems().add(easy); //54317
        menu.getItems().add(subMenu);

        menubar.getMenus().add(menu);

        Close.setOnAction(e -> {
            try {
                wordle.quit();
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
            Platform.exit();
            System.exit(0);
        });

        hard.setOnAction(e -> {
            try {
                wordle.Hard();
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        Launch.setOnAction(e -> {

        });

        setBackground();
        initGameField();
        initInfoField();
        root.getChildren().addAll(menubar, mainField);
        mainField.getChildren().addAll(gameField, infoField);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void setBackground() {
        URL imgURL = getClass().getClassLoader().getResource("background.jpg");
        mainField.setBackground(new Background(new BackgroundImage(
                new Image(imgURL.toString(), 0, 0, false, false),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(
                        Side.LEFT, 0, true,
                        Side.BOTTOM, 0, true),
                new BackgroundSize(
                        BackgroundSize.AUTO,
                        BackgroundSize.AUTO,
                        true, true, false, true)
        )));
    }

    private void initGameField() {
        gameField = new VBox();
        gameField.setAlignment(Pos.CENTER);
        gameField.setPadding(new Insets(20));
        gameField.setSpacing(20);
        initTitle();
        initGamePanel();
        initPrompt();
        initPropositionField();
        gameField.getChildren().addAll(title, gamePanel, proposition, prompt,nbGameEasy,nbGameHard);

    }

    private void initTitle() {
        title = new Title("Wordle");
    }

    private void initGamePanel() {
        gamePanel = new GameGrid(GameData.getNbAttempt(), GameData.getNbLetters());
    }

    private void initPrompt() {
        nbGameEasy = new Label();
        nbGameHard = new Label();
        nbGameEasy.setText("partie Easy");
        nbGameEasy.setStyle("-fx-text-fill: white;"
                + "-fx-font: bold 12px Verdana;");
        nbGameHard.setStyle("-fx-text-fill: white;"
                + "-fx-font: bold 12px Verdana;");
        nbGameHard.setText("partie Hard");
        prompt = new Prompt();
    }

    private void initPropositionField() {
        proposition = new PropositionField(wordle, prompt);
    }

    private void initInfoField() {
        infoField = new VBox();
        infoField.setAlignment(Pos.CENTER);
        infoField.setPadding(new Insets(20));
        infoField.setSpacing(20);
        initDictionary();
        initScore();
        infoField.getChildren().addAll(score, dictionary);
    }

    private void initScore() {
        score = new Score(wordle.getScore());
    }

    private void initDictionary() {
        dictionary = new WordList(wordle);
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(() -> {
            updateGameGrid();
            updateGameStatus();
            updateScore();

            updateDictionary();
            if (arg != null) {
                try {
                    MessageEndGame message = (MessageEndGame) arg;
                    updateEnd(message);
                } catch (Exception e) {
                    try {
                        MessageNbGame message = (MessageNbGame) arg;
                        updatenbGame(message);
                    } catch (Exception e2) {
                        updateError(arg);
                    }
                    //rien
                }
            }

        });
    }

    private void updateGameGrid() {
        List<Pair> words = wordle.getWords();
        gamePanel.update(words);
    }

    private void updateDictionary() {
        dictionary.update();
    }

    private void updateGameStatus() {
        if (wordle.isOver()) {
            prompt.setIsOver();
            proposition.freeze();
        }
        if (wordle.isFound()) {
            prompt.setIsFound();
            proposition.freeze();
        }
    }

    private void updateScore() {
        score.update(wordle.getScore());
    }

    private void updateError(Object arg) {
        if (arg != null) {
            prompt.setText("mots non dans le dictionnaire");
            // prompt.setErrorFrench("dictionary");
        }
    }

    private void updateEnd(MessageEndGame message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("End Game");
        alert.setHeaderText(" you have finished the game");
        alert.setContentText(message.getWord());
        alert.showAndWait();
    }

    private void updatenbGame(MessageNbGame message) {
        nbGameEasy.setText("Vous avez actuellement " + message.getContent() + " partie en Easy ");
        nbGameHard.setText("Vous avez actuellement " + message.getContent2() + " partie en Hard");

    }

}
