package be.he2b.atl.wordle.server.model;

import Exception.BusinessException;
import Exception.DbException;
import be.he2b.atl.common.message.Message;
import be.he2b.atl.common.message.Type;
import static be.he2b.atl.common.message.Type.PROFILE;
import static be.he2b.atl.wordle.server.model.WordleServer.ID_MAPINFO;
import be.he2b.atl.server.ConnectionToClient;
import java.sql.SQLException;


class MessageFactory {

    static void build(Message message, ConnectionToClient client, WordleServer wordleServer) throws BusinessException, DbException, SQLException {
        Type type = message.getType();
        switch (type) {
            case PROFILE:      
               // wordleServer.changeName(message.getAuthor(), userId);               
                wordleServer.SetupUser(message.getAuthor(), client);                 
                break;
            case NEWGAME:
                wordleServer.sendNewGameToClient(client);
                break;
            case PROPOSE:
                wordleServer.sendGameToClient(message, client);
                break;
            case HARD:
                wordleServer.Hard(message.getAuthor(),client);
                break;
            default:
                throw new IllegalArgumentException("Message type unknown " + type);
        }
    }
}