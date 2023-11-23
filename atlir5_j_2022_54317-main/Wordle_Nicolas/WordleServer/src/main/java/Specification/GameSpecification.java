/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Specification;

/**
 *
 * @author le-ni
 */
public class GameSpecification {
    private int id;
    private String User;
    private int score;
    private String state;
    private String word;


    /**
     * Instantiates a new Data game specification.
     *
     * @param id
     * @param User
     * @param state
     * @param word
     * @param score  the score
     */
    public GameSpecification(int id,String User, int score, String state , String word) {
      this.id = id;
      this.User = User;
      this.score = score;
      this.state = state;
      this.word = word;
    }


    /**
     * Instantiates a new Data game specification.
     *
     * @param id
     * @param User
     */
    public GameSpecification(int id, String User) {
        this.id = id;
        this.User = User;
    }
    public GameSpecification(int id) {
        this.id = id;
    }
    //54317
    public GameSpecification(String User) {
        this.User = User;
    }

    /**
     * Gets id game.
     *
     * @return the id game
     */
    public int getid() {
        return id;
    }

    /**
     * Gets id user.
     *
     * @return the id user
     */
    public String getUser() {
        return User;
    }

    /**
     * Gets score.
     *
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets id game.
     *
     * @param id
     */
    public void setid(int id) {
        this.id = id;
    }

    /**
     * Sets id user.
     *
     * @param User
     */
    public void setUser(String User) {
        this.User = User;
    }

    /**
     * Sets score.
     *
     * @param score the score
     */
    public void setScore(int score) {
        this.score = score;
    }

    public String getState() {
        return state;
    }

    public String getWord() {
        return word;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setWord(String word) {
        this.word = word;
    }
    
}
