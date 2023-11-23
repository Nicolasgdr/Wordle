package Business;


import Db.DBManager;
import Dto.GameDto;
import Dto.UserDto;
import Exception.BusinessException;
import Exception.DbException;
import Specification.UserSpecification;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Run the program for the administrator.
 */
public class AdminModel implements AdminFacade {

    @Override
    public List<UserDto> getUsers() throws BusinessException {
        try {
            DBManager.startTransaction();
            List<UserDto> col = UserBusinessLogic.findAll();
            DBManager.validateTransaction();
            return col;
        } catch (DbException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (DbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Liste des Users inaccessible! \n" + msg);
            }
        }
    }

    @Override
    public UserDto getUser(int userId) throws BusinessException {
        try {
            DBManager.startTransaction();
            UserDto user = UserBusinessLogic.findById(userId);
            DBManager.validateTransaction();
            return user;
        } catch (DbException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (DbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Liste des Users inaccessible! \n" + msg);
            }
        }
    }

    @Override
    public UserDto getUser(String userName) throws BusinessException {
        try {
            DBManager.startTransaction();
            UserDto user = UserBusinessLogic.findByName(userName);
            DBManager.validateTransaction();
            return user;
        } catch (DbException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (DbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Liste des Users inaccessible! \n" + msg);
            }
        }
    }

    public static Collection<UserDto> getSelectedUsers(UserSpecification sel) throws BusinessException {
        try {
            DBManager.startTransaction();
            Collection<UserDto> col = UserBusinessLogic.findBySel(sel);
            DBManager.validateTransaction();
            return col;
        } catch (DbException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (DbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Liste des Users inaccessible! \n" + msg);
            }
        }
    }

    @Override
    public int addUser(String userName) throws BusinessException {
        try {
            DBManager.startTransaction();
            UserDto user = new UserDto( userName);
            int id = UserBusinessLogic.add(user);
            DBManager.validateTransaction();
            return id;
        } catch (DbException ex) {
            try {
                DBManager.cancelTransaction();
                throw new BusinessException(ex.getMessage());
            } catch (DbException ex1) {
                throw new BusinessException(ex1.getMessage());
            }
        }
    }
    public int addGame(String userName,int score,String state,String word,String mod) throws BusinessException {
        try {
            DBManager.startTransaction();
            GameDto game = new GameDto( userName,score,state,word,mod);
            int id = GameBusiness.add(game);
            DBManager.validateTransaction();
            return id;
        } catch (DbException ex) {
            try {
                DBManager.cancelTransaction();
                throw new BusinessException(ex.getMessage());
            } catch (DbException ex1) {
                throw new BusinessException(ex1.getMessage());
            }
        }
    }
    
    public boolean exist(String name) throws DbException, SQLException{
        DBManager.startTransaction();
        if(UserBusinessLogic.checkExist(name)){
            DBManager.validateTransaction();
            return true;
        }
        return false;
        
    }


    @Override
    public void removeUser(UserDto user) throws BusinessException {
 
    }

    @Override
    public void updateUser(UserDto user) throws BusinessException {
        try {
            DBManager.startTransaction();
            UserBusinessLogic.update(user);
            DBManager.validateTransaction();
        } catch (DbException eDB) {
            String msg = eDB.getMessage();
            try {
                DBManager.cancelTransaction();
            } catch (DbException ex) {
                msg = ex.getMessage() + "\n" + msg;
            } finally {
                throw new BusinessException("Mise à jour de User impossible! \n" + msg);
            }
        }
    }

    @Override
    public UserDto getRandomUser() throws BusinessException {
        List<UserDto> users = getUsers();
        if (users.isEmpty()) {
            throw new BusinessException("Aucun utilisateur présent");
        }
        Collections.shuffle(users);
        return users.get(0);
    }
    
    public int getId(String name) throws DbException, SQLException{
        return UserBusinessLogic.checkExistID(name);
        
    }

    //54317
    public int getNbGameHard(String name, String mod) throws DbException, SQLException{
        return GameBusiness.checkNbHard(name, mod);
    }
    //54317
    public int getNbGameEasy(String name, String mod) throws DbException, SQLException{
        return GameBusiness.checkNbHard(name, mod);
    }
}
