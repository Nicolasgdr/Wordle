package Business;

import Db.UserDB;
import Dto.UserDto;
import Exception.DbException;
import Specification.UserSpecification;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

class UserBusinessLogic {

    /**
     * Insert an user in the database. Returns the user's id.
     *
     * @param login user's login.
     * @param name user's name.
     * @return the user's id.
     * @throws SQLException if the query failed.
     */
    static int add(UserDto user) throws DbException {
        return UserDB.insertDb(user);
    }

    /**
     * Removes the given user.
     *
     * @param user user to delete.
     * @throws SQLException if the query failed.
     */
    static void delete(int id) throws DbException {
        UserDB.deleteDb(id);
    }

    /**
     * Updates the given user.
     *
     * @param user user to update.
     * @throws SQLException if the query failed.
     */
    static void update(UserDto user) throws DbException {
        UserDB.updateDb(user);
    }

    static boolean checkExist(String name) throws DbException, SQLException {

        return UserDB.exist(name);

    }

    static int checkExistID(String name) throws DbException, SQLException {

        return UserDB.existId(name);

    }

    

    /**
     * Returns the unique user with the given name.
     *
     * @param name user's name.
     * @return the unique user with the given name.
     * @throws SQLException if the query failed.
     */
    static UserDto findByName(String name) throws DbException {
        UserSpecification sel = new UserSpecification(name);//pas sur 
        Collection<UserDto> col = UserDB.getCollection(sel);
        if (col.size() == 1) {
            return col.iterator().next();
        } else {
            return null;
        }
    }

    /**
     * Returns the unique user with the given id.
     *
     * @param id user's id.
     * @return the unique user with the given id.
     * @throws SQLException if the query failed.
     */
    static UserDto findById(int id) throws DbException {
        UserSpecification sel = new UserSpecification(id);
        Collection<UserDto> col = UserDB.getCollection(sel);
        if (col.size() == 1) {
            return col.iterator().next();
        } else {
            return null;
        }
    }

    /**
     * Returns a list of users whith the gibven specifications.
     *
     * @param sel specifications (where clause)
     * @return a list of users whith the gibven specifications.
     * @throws BusinessException if the query failed.
     */
    static List<UserDto> findBySel(UserSpecification sel) throws DbException {
        List<UserDto> col = UserDB.getCollection(sel);
        return col;
    }

    /**
     * Returns a list of all users.
     *
     * @return a list of all users.
     * @throws BusinessException if the query failed.
     */
    static List<UserDto> findAll() throws DbException {
        UserSpecification sel = new UserSpecification(0);
        List<UserDto> col = UserDB.getCollection(sel);
        return col;
    }

    //ajouter les methodes avec une logique métier
}
