package be.he2b.atl.wordle.server.model;

import be.he2b.atl.common.model.User;
import be.he2b.atl.common.message.Message;
import be.he2b.atl.common.message.MessageEndGame;
import be.he2b.atl.common.message.MessageError;
import be.he2b.atl.common.message.MessageGameState;
import be.he2b.atl.common.message.MessageProfile;
import be.he2b.atl.common.model.GameState;
import be.he2b.atl.common.model.Word;
import be.he2b.atl.server.AbstractServer;
import be.he2b.atl.server.ConnectionToClient;
import g56167.atl.wordle.model.Game;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import Business.AdminModel;
import Db.GameDB;
import Exception.BusinessException;
import Exception.DbException;
import be.he2b.atl.common.message.MessageNbGame;
import java.sql.SQLException;

public class WordleServer extends AbstractServer {

    private AdminModel dbacces;

    public AdminModel getDbacces() {
        return dbacces;
    }

    public void setDbacces(AdminModel dbacces) {
        this.dbacces = dbacces;
    }

    private static final int PORT = 12_345;
    static final String ID_MAPINFO = "ID";
    static final String GAME_MAPINFO = "GAME";

    private static InetAddress getLocalAddress() {
        try {
            Enumeration<NetworkInterface> b = NetworkInterface.getNetworkInterfaces();
            while (b.hasMoreElements()) {
                for (InterfaceAddress f : b.nextElement().getInterfaceAddresses()) {
                    if (f.getAddress().isSiteLocalAddress()) {
                        return f.getAddress();
                    }
                }
            }
        } catch (SocketException e) {
            Logger.getLogger(WordleServer.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    private int clientId;

    private final Users users;

    public WordleServer() throws IOException {
        super(PORT);
        users = new Users();
        clientId = 0;
        this.listen();
    }

    final synchronized int getNextId() {
        clientId++;
        return clientId;
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        Message message = (Message) msg;
        System.out.println(msg.toString());
        try {
            MessageFactory.build(message, client, this);
        } catch (BusinessException | DbException | SQLException ex) {
            Logger.getLogger(WordleServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        notifyChange(message);
    }

    public void quit() throws IOException {
        this.stopListening();
        this.close();
    }

    @Override
    protected synchronized void clientDisconnected(ConnectionToClient client) {
        super.clientDisconnected(client);        
        int userId = (int) client.getInfo(ID_MAPINFO);
        users.remove(userId);
        notifyChange();
    }

    @Override
    protected synchronized void clientConnected(ConnectionToClient client) {
        super.clientConnected(client);
        //    int userId = users.add(getNextId(), client.getName(), client.getInetAddress());
        //  client.setInfo(ID_MAPINFO, userId);
        // notifyChange();
    }

    @Override
    protected synchronized void clientException(ConnectionToClient client, Throwable exception) {
        super.clientException(client, exception);
        try {
            if (client.isConnected()) {
                client.sendToClient(new IllegalArgumentException("Message illisible " + exception.getMessage()));
            }
        } catch (IOException ex) {
            Logger.getLogger(WordleServer.class.getName()).log(Level.SEVERE, "Impossible d envoyer erreur au client", ex);
        }
    }

    void notifyChange() {
        setChanged();
        notifyObservers();
    }

    void notifyChange(Message message) {
        setChanged();
        notifyObservers(message);
    }

    public Users getUsers() {
        return users;
    }

    public String getIP() {
        if (getLocalAddress() == null) {
            return "Unknown";
        }
        return getLocalAddress().getHostAddress();
    }

    public int getNbConnected() {
        return getNumberOfClients();
    }

    void sendToClient(Message message, User recipient) {
        sendToClient(message, recipient.getId());
    }
    //54317
    void sendNewGameToClient(ConnectionToClient client) throws DbException, SQLException, BusinessException {
        Game game = new Game();
        client.setInfo(GAME_MAPINFO, game);

        GameState gameState = game.getGameState();
        User player = users.getUser((int) client.getInfo(ID_MAPINFO));
        MessageGameState message = new MessageGameState(player, gameState);
        sendToClient(message, client);
        dbacces.addGame(client.getName(), game.getScore(),
                        game.getGameState().toString(), game.getSecretWord().toString(),null);
       
        int nbGameEasy = dbacces.getNbGameEasy(player.getName(),player.getMod());
        int nbGameHard = dbacces.getNbGameHard(player.getName(), player.getMod());
         MessageNbGame messageGame = new MessageNbGame(player,nbGameEasy, nbGameHard);
        sendToClient(messageGame, client);

    }
//54317
    void sendGameToClient(Message msg, ConnectionToClient client) throws BusinessException {
        Game game = (Game) client.getInfo(GAME_MAPINFO);
        //si je suis true
        try {
            game.attempt((Word) msg.getContent());
            GameState gameState = game.getGameState();
            User player = users.getUser((int) client.getInfo(ID_MAPINFO));
            if(player.getMod()=="true"){
                // pas envoyer le dictionnaire
            }
            MessageGameState message = new MessageGameState(player, gameState);
            sendToClient(message, client);
            if (game.isFound()) {
                MessageEndGame messageEnd = new MessageEndGame(player, "victoire");
                sendToClient(messageEnd, clientId);
               
            }
            if (game.getAttempts().size() == 6 && !game.isFound()) {
                MessageEndGame messageEnd = new MessageEndGame(player, "loose");
                sendToClient(messageEnd, clientId);
            }
        } catch (IllegalArgumentException e) {
            User player2 = users.getUser((int) client.getInfo(ID_MAPINFO));
            MessageError error = new MessageError(player2);
            sendToClient(error, client);

        }

    }

    void sendToClient(Message message, ConnectionToClient client) {
        if (client != null) {
            try {
                client.sendToClient(message);
            } catch (IOException ex) {
            }
        }
    }

    void sendToClient(Message message, int clientId) {
        ConnectionToClient client = getConnectionToClient(clientId);
        sendToClient(message, client);
    }

    ConnectionToClient getConnectionToClient(int clientId) {
        ConnectionToClient client = null;
        List<Thread> clientThreadList = getClientConnections();
        int index = 0;
        boolean find = false;
        while (index < clientThreadList.size() && !find) {
            client = (ConnectionToClient) clientThreadList.get(index);
            int id = (int) client.getInfo(ID_MAPINFO);
            if (id == clientId) {
                find = true;
            }
            index++;
        }
        return client;
    }

    void changeName(User author, int userId) {
        users.changeName(author.getName(), userId);
        MessageProfile messageProfile = new MessageProfile(userId, ID_MAPINFO);
        sendToClient(messageProfile, getConnectionToClient(clientId));

    }

    void SetupUser(User user, ConnectionToClient client) throws BusinessException, DbException, SQLException {
        int id = 0;
        if (!dbacces.exist(user.getName())) {
            dbacces.addUser(user.getName());
        }
        id = dbacces.getId(user.getName());
        users.add(id, client.getName(), client.getInetAddress());
        client.setInfo(ID_MAPINFO, id);
        client.setName(user.getName());
        changeName(user, id);
        notifyChange();
    }
    //54317
   
void Hard(User user,ConnectionToClient client) {
         Game game = (Game) client.getInfo(GAME_MAPINFO);
        //mettre a jour dans la map pas reussi
       // client.setInfo(ID_MAPINFO, this);      
            user.setMod("true");
        GameState gameState = game.getHardGameState();
        User player = users.getUser((int) client.getInfo(ID_MAPINFO));
        MessageGameState message = new MessageGameState(user, gameState);
        sendToClient(message, client);
        
    }

}

