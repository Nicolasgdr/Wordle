package be.he2b.atl.wordle.view.fx;

import be.he2b.atl.wordle.model.Wordle;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author jlc, aro
 */
public class App extends Application {

    private static final String TITLE = "Client Wordle";
    private static final int MIN_WIDHT = 600;
    private static final int MIN_HEIGHT = 600;

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    private Scene scene;
    private Wordle wordle;

    private WordleView wordleView;

    /**
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

       
      
            primaryStage.setMinWidth(MIN_WIDHT);
            primaryStage.setMinHeight(MIN_HEIGHT);
            primaryStage.setTitle(TITLE);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FXML.fxml"));
                loader.setController(new FXMLController(this,primaryStage,wordle,wordleView));
                Scene scene = new Scene(loader.load());
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException e) {
            }
            
           // wordleView = new WordleView(this, primaryStage, wordle);
            //primaryStage.setScene(new Scene(wordleView));
          
    }
}
