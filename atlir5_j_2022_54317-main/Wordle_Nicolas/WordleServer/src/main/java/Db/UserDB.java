package Db;


import Dto.UserDto;
import Exception.DbException;
import Exception.DtoException;
import Specification.UserSpecification;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jlc
 */
public class UserDB {

    private static final String recordName = "USER";
    
    /**
     *
     * @return
     * @throws Exception.DtoException
     * @throws DbException
     */
    public static List<UserDto> getAllUsers() throws DtoException, DbException {
        List<UserDto> users = getCollection(new UserSpecification(0));
        return users;
    }

    /**
     *
     * @param sel
     * @return
     * @throws DbException
     */
    public static List<UserDto> getCollection(UserSpecification sel) throws DbException {
        List<UserDto> al = new ArrayList<>();
        try {
            String query = "Select id, name  FROM USER ";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            String where = "";
            if (sel.getId() != 0) {
                where = where + " id = ? ";
            }
          

            if (sel.getName() != null && !sel.getName().isEmpty()) {
                if (!where.isEmpty()) {
                    where = where + " AND ";
                }
                where = where + " name like ? ";
            }

            if (where.length() != 0) {
                where = " where " + where + " order by name";
                query = query + where;
                stmt = connexion.prepareStatement(query);
                int i = 1;
                if (sel.getId() != 0) {
                    stmt.setInt(i, sel.getId());
                    i++;

                }
             
                if (sel.getName() != null && !sel.getName().isEmpty()) {
                    stmt.setString(i, sel.getName() + "%");
                    i++;
                }
            } else {
                query = query + " Order by name";
                stmt = connexion.prepareStatement(query);
            }
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                al.add(new UserDto( rs.getString("name")));
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DbException("Instanciation de "+recordName+" impossible:\nSQLException: " + eSQL.getMessage());
        }
        return al;
    }

    /**
     *
     * @param id
     * @throws DbException
     */
    public static void deleteDb(int id) throws DbException {
        try {
            java.sql.Statement stmt = DBManager.getConnection().createStatement();
            stmt.execute("delete from Users where id=" + id);
        } catch (DbException | SQLException ex) {
            throw new DbException(recordName+": suppression impossible\n" + ex.getMessage());
        }
    }

    /**
     *
     * @param record
     * @throws DbException
     */
    public static void updateDb(UserDto record) throws DbException {
        try {
            java.sql.Connection connexion = DBManager.getConnection();

            java.sql.PreparedStatement update;
            String sql = "Update USER set "                                  
                    + "name=? "
                    + "where id=?";
            update = connexion.prepareStatement(sql);                     
            update.setString(1, record.getName());
            update.setInt(2, record.getId());
            update.executeUpdate();
        } catch (DbException | SQLException ex) {
            throw new DbException(recordName+", modification impossible:\n" + ex.getMessage());
        }
    }

    /**
     *
     * @param record
     * @return
     * @throws DbException
     */
    public static int insertDb(UserDto record) throws DbException {
        try {
            int num = SequenceDB.getNextNum(SequenceDB.USER);
            System.out.println(num);
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement insert;
            insert = connexion.prepareStatement(
                    "Insert into USER(id, name) "
                    + "values(?, ? )");
            insert.setInt(1, num);
            insert.setString(2, record.getName());
            insert.executeUpdate();
            return num;
        } catch (DbException | SQLException ex) {
            throw new DbException(recordName+": ajout impossible\n" + ex.getMessage());
        }
    }
    public static boolean exist(String name) throws DbException, SQLException{
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement insert;
            insert = connexion.prepareStatement(
                    "SELECT name FROM USER WHERE name = ?");
            insert.setString(1, name);           
            ResultSet result = insert.executeQuery();
            return result.next();
}

   public static int existId(String name) throws DbException, SQLException{
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement insert;
            insert = connexion.prepareStatement(
                    "SELECT id FROM USER WHERE name = ?");
            insert.setString(1, name);           
            ResultSet result = insert.executeQuery();
            return ((Number) result.getObject(1)).intValue();
}
   
}
