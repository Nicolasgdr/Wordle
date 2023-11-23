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
public class MessageError implements Message{
    private final User player; 

    public MessageError(User player) {
        this.player = player;
    }
    

    @Override
    public Type getType() {
       return Type.ERROR;
    }

    @Override
    public User getAuthor() {
       return player;
    }

    @Override
    public User getRecipient() {
       return User.ADMIN;
    }

    @Override
    public Object getContent() {
      return null;
      
    }
    
}

