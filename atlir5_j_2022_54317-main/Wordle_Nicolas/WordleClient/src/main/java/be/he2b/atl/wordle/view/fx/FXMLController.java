/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package be.he2b.atl.wordle.view.fx;

import be.he2b.atl.wordle.model.Wordle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author le-ni
 */
public class FXMLController implements Initializable {
@FXML 
private Button ButtonConnexion;

@FXML
private TextField pseudoPlayer;

  private App app;
  private Stage primaryStage;
  private Wordle wordle;
  private WordleView view;

    public FXMLController(App app, Stage primaryStage, Wordle wordle, WordleView view) {
        this.app = app;
        this.primaryStage = primaryStage;
        this.wordle = wordle;
        this.view = view;
    }
  
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         ButtonConnexion.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{try { 
              try {
            String host = "localhost";
            int port = 12_345;
            String name = pseudoPlayer.getText();
            String password = "";
            wordle = new Wordle(host, port, name, password);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
             view=new WordleView(app, primaryStage, wordle);
             } catch (IOException ex) {
                 Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
             }
        });
         try{
         primaryStage.setOnCloseRequest((WindowEvent t1) -> {
                try {
                    wordle.quit();
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
                Platform.exit();
                System.exit(0);
            });
    
         }catch (Exception ex) {
            System.out.println(ex.getMessage());
            try {
                wordle.quit();
            } catch (IOException ex1) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Platform.exit();
            System.exit(0);
        }
    }
  
}
