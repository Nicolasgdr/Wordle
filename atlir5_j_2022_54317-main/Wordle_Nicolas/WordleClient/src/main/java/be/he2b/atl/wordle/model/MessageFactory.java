package be.he2b.atl.wordle.model;

import be.he2b.atl.common.message.Message;
import be.he2b.atl.common.message.MessageEndGame;
import be.he2b.atl.common.message.MessageError;
import be.he2b.atl.common.message.MessageNbGame;
import be.he2b.atl.common.message.Type;
import be.he2b.atl.common.model.GameState;
import java.io.IOException;

class MessageFactory {

    static void build(Message message, Wordle wordleClient) throws IOException {
        Type type = message.getType();
        switch (type) {
            case PROFILE:
                wordleClient.setMySelf(message.getAuthor());
                break;
            case GAMESTATE:
                wordleClient.setGameState((GameState)message.getContent());
                break;
            case ERROR:
                wordleClient.Switchview((MessageError) message);
                break;
            case ENDGAME:
                wordleClient.showend((MessageEndGame) message);
                break;
            case NBGAME:
                System.out.println("Message reçu");
                wordleClient.upnbGame((MessageNbGame) message);
                break;
            default:
                System.out.println("default");
                throw new IllegalArgumentException("Message type unknown " + type);
        }
    }

}
