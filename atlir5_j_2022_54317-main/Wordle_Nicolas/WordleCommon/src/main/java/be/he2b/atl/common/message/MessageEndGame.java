/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package be.he2b.atl.common.message;

import be.he2b.atl.common.model.User;

/**
 *
 * @author le-ni
 */
public class MessageEndGame implements Message{
  private final User player; 
  String word;

    public MessageEndGame(User player,String word) {
        this.player = player;
        this.word=word;
        
    }
     
    @Override
    public Type getType() {
        return Type.ENDGAME;
    }

    public User getPlayer() {
        return player;
    }

    public String getWord() {
        return word;
    }
    

    @Override
    public User getAuthor() {
    return User.ADMIN; 
        }

    @Override
    public User getRecipient() {
        return User.ADMIN;
    }

    @Override
    public Object getContent() {
     return word;
    }



}
