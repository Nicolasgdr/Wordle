package Dto;

import java.util.List;

/**
 *
 * @author jlc
 */
public class GameDto extends EntityDto<Integer> {

    private String user;
    private int score;
    private String state;
    private String word;
    private String Mod;


    public GameDto( String user, int score, String state, String word,String mod) {
       
        this.user = user;
        this.score = score;
        this.state = state;
        this.word = word;
        this.Mod = mod;
    }

    public String getUser() {
        return user;
    }

    public int getScore() {
        return score;
    }

    public String getState() {
        return state;
    }

    public String getWord() {
        return word;
    }
    


}
