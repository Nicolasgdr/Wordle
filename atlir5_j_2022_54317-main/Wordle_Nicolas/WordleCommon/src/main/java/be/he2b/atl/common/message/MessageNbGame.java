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
//54317
public class MessageNbGame implements Message{
     private final User player;
     private final int nbGameEasy;
     private final int nbGameHard;

    @Override
    public Type getType() {
          return Type.NBGAME;
    }

    @Override
    public User getAuthor() {
        return this.player;
    }

    @Override
    public User getRecipient() {
       return User.ADMIN;
    }

    @Override
    public Object getContent() {
        return nbGameHard;
    }
    //54317
    public Object getContent2(){
        return nbGameEasy;
    }
//54317
    public MessageNbGame(User player,int nbGameEasy,int nbGameHard) {
        this.player = player;
        this.nbGameEasy = nbGameEasy;
         this.nbGameHard = nbGameHard;
    }
    
}
