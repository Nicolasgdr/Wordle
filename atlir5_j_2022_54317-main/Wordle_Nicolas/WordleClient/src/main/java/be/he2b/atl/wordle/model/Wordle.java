package be.he2b.atl.wordle.model;

import be.he2b.atl.common.model.User;
import be.he2b.atl.client.AbstractClient;
import be.he2b.atl.common.message.Message;
import be.he2b.atl.common.message.MessageEndGame;
import be.he2b.atl.common.message.MessageError;
import be.he2b.atl.common.message.MessageHard;
import be.he2b.atl.common.message.MessageNbGame;
import be.he2b.atl.common.message.MessageNewGame;
import be.he2b.atl.common.message.MessageProfile;
import be.he2b.atl.common.message.MessagePropose;
import be.he2b.atl.common.model.GameState;
import be.he2b.atl.common.model.Pair;
import be.he2b.atl.common.model.Word;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Wordle extends AbstractClient {

    private User mySelf;
    private GameState gameState;

    public Wordle(String host, int port, String name, String password) throws IOException {
        super(host, port);
        openConnection();
        updateName(name);
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        Message message = (Message) msg;
        try {
            MessageFactory.build(message, this);
        } catch (IOException ex) {
            Logger.getLogger(Wordle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void quit() throws IOException {
        closeConnection();
    }

    public void sendAttempt(String text) throws IOException {
        
       throw new IllegalArgumentException(text);
    }

    void showMessage(Message message) {
        notifyChange(message);
    }

    private void updateName(String name) throws IOException {
        sendToServer(new MessageProfile(0, name));
    }

    public void askForNewGame() throws IOException {
        sendToServer(new MessageNewGame(getMySelf()));
    }

    private void notifyChange() {
        setChanged();
        notifyObservers();
    }

    private void notifyChange(Message message) {
        setChanged();
        notifyObservers(message);
    }

    @Override
    protected void connectionEstablished() {
        System.out.println("Client connecté");
    }

    public User getMySelf() {
        return mySelf;
    }

    void setMySelf(User user) {
        this.mySelf = user;
    }

    public GameState getGameState() {
        return gameState;
    }
   
    void setGameState(GameState gameState) {
        this.gameState = gameState;
        notifyChange();
    }

    public List<Pair> getWords() {
        List<Pair> words;
        if (gameState != null) {
            words = gameState.getWords();
        } else {
            words = new ArrayList<>();
        }
        return words;
    }

    public List<Word> getDictionary() {
        List<Word> words;
        if (gameState != null) {
            words = gameState.getDictionary();
        } else {
            words = new ArrayList<>();
        }
        return words;
    }

    public int getScore() {
        return gameState != null ? gameState.getScore() : 0;
    }

    public boolean isOver() {
        return gameState != null ? gameState.isOver() : false;
    }

    public boolean isFound() {
        return gameState != null ? gameState.isFound() : false;
    }

    public void attempt(Word word)  throws IOException {
        if(word.getLength()==0){
            sendAttempt("default");
        }else if(word.getLength()>5){
             sendAttempt("length");    
        }else if (word.getLength()<5) {
              sendAttempt("length");
        }
          MessagePropose message = new MessagePropose(getMySelf(),word);
        sendToServer(message);
    }
    public void Switchview(MessageError message) throws IOException{
        notifyChange(message);
    }
    public void showend(MessageEndGame message){
        notifyChange(message);
        
    }
    public void upnbGame(MessageNbGame message){
        notifyChange(message);
        
    }
    public void Hard() throws IOException{
        MessageHard message = new MessageHard(mySelf);
        sendToServer(message);
    }
}
