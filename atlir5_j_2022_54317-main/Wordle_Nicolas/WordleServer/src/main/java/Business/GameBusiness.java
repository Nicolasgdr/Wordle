package Business;

import Db.GameDB;
import Dto.GameDto;
import Exception.DbException;
import Specification.GameSpecification;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author le-ni
 */
public class GameBusiness {
    /**
     * Insert an user in the database. Returns the user's id.
     *
     * @param dataGame the data game
     * @return the user's id.
     * @throws DBException the db exception
     */
    static int add(GameDto dataGame) throws DbException {
        return GameDB.insertDb(dataGame);
    }

    /**
     * Returns the unique user with the given mail.
     *
     * @param idGame the id game
     * @return the unique user with the given mail.
     * @throws DBException the db exception
     */
    static Collection<GameDto> findByGameID(int id) throws DbException {
        GameSpecification sel = new GameSpecification(id);
        Collection<GameDto> col = GameDB.getCollection(sel);
        return col; 
    }

    /**
     * Find by ids data game dto.
     *
     * @param idGame the id game
     * @param idUser the id user
     * @return the data game dto
     * @throws DBException the db exception
     */
    static GameDto findByIDs(int id, String User) throws Exception {
        GameSpecification sel = new GameSpecification(id,User);
        Collection<GameDto> col = GameDB.getCollection(sel);
        if (col.size() == 1) {
            return col.iterator().next();
        } else {
            return null;
        }
    }
    //54317
     static GameDto findByName(String user) throws DbException {
       GameSpecification sel = new GameSpecification(user);
        Collection<GameDto> col = GameDB.getCollection(sel);
        if (col.size() == 1) {
            return col.iterator().next();
        } else {
            return null;
        }
    }


    /**
     * Returns a list of users with the given specifications.
     *
     * @param sel specifications (where clause)
     * @return a list of users with the given specifications.
     * @throws DBException the db exception
     */
    static List<GameDto> findBySel(GameSpecification sel) throws DbException {
        return GameDB.getCollection(sel);
    }

    /**
     * Returns a list of all users.
     *
     * @return a list of all users.
     * @throws DBException the db exception
     */
    static List<GameDto> findAll() throws DbException {
        GameSpecification sel = new GameSpecification(0);
        List<GameDto> col = GameDB.getCollection(sel);
        return col;
    }

    //54317
    static int checkNbHard(String name, String mod) throws DbException, SQLException {
        return GameDB.nbGameEasy(name, mod);
    }
    //54317
    static int checkNbEasy(String name, String mod) throws DbException, SQLException{
        return GameDB.nbGameEasy(name, mod);
    }
}
