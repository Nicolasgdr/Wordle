package be.he2b.atl.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * Class representing the game state.
 *
 * @author aro
 */
public class GameState implements Serializable {

    private  List<Word> dictionary;
    private final List<Pair> words;
    private final int score;
    private final boolean over;
    private final boolean found;
    //private final boolean hard;



    public GameState(List<Word> dictionary, List<Pair> words, int score, boolean over, boolean found ) {
        this.dictionary = dictionary;
        this.words = words;
        this.score = score;
        this.over = over;
        this.found = found;
       
    }
    //54317
    public GameState(List<Pair> words, int score, boolean over, boolean found) {
        this.words = words;
        this.score = score;
        this.over = over;
        this.found = found;
    }
    
    
 
    public List<Pair> getWords() {
        return words;
    }

    public List<Word> getDictionary() {
        return dictionary;
    }

    public int getScore() {
        return score;
    }

    public boolean isOver() {
        return over;
    }

    public boolean isFound() {
        return found;
    }

 

}
