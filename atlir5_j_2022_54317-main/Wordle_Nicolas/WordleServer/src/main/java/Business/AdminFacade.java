package Business;


import Dto.UserDto;
import Exception.BusinessException;
import java.util.List;

/**
 *
 * @author le-ni
 */
   
public interface AdminFacade {

    /**
     * Returns a list of all users.
     *
     * @return a list of all users.
     * @throws BusinessException if the query failed.
     */
    List<UserDto> getUsers() throws BusinessException;

    /**
     * Returns the unique user with the given id.
     *
     * @param id user's id.
     * @return the unique user with the given id.
     * @throws Exception.BusinessException
  
     */
    UserDto getUser(int id) throws BusinessException;

    /**
     * Returns the last user with the given name.
     *
     * @param name user's name.
     * @return the last user with the given name.
     * @throws Exception.BusinessException
 
     */
    UserDto getUser(String name) throws BusinessException;

    /**
     * Creates a user and insert it in the database.Returns the user's id.
     *
     * @param name user's name.
     * @return the user's id.
     * @throws Exception.BusinessException
     */
    int addUser(String name) throws BusinessException;

    /**
     * Removes the given user.
     *
     * @param user user to delete.
     * @throws Exception.BusinessException
     */
    void removeUser(UserDto user) throws BusinessException;

    /**
     * Updates the given user.
     *
     * @param current
     * @throws Exception.BusinessException
     * 
     */
    void updateUser(UserDto current) throws BusinessException;

    /**
     * Returns a random user from the database.
     *
     * @return a random user from the database.
     * @throws Exception.BusinessException
     */
    UserDto getRandomUser() throws BusinessException;

}

