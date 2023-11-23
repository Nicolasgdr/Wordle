/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Db;

import Dto.GameDto;
import Exception.DbException;
import Exception.DtoException;
import Specification.GameSpecification;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author le-ni
 */
public class GameDB {
       private static final String recordName = "GAME";
     /**
     * Gets all game data.
     *
     * @return all game data
     * @throws DtoException the dto exception
     * @throws Exception.DbException
     */
    public static List<GameDto> getAllGameData() throws DtoException, DbException {
        List<GameDto> users = getCollection(new GameSpecification(0));
        return users;
    }

    /**
     * Gets collection.
     *
     * @param sel the sel
     * @return collection
     * @throws Exception.DbException
     */
    public static List<GameDto> getCollection(GameSpecification sel) throws DbException {
        List<GameDto> al = new ArrayList<>();
        try {
            String query = "Select id,user,score,state,word  FROM GAME ";
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement stmt;
            String where = "";
            if (sel.getid()!= 0) {
                where = where + " id = ? ";
            }
            if (sel.getUser()!= null) {
                if (!where.isEmpty()) {
                    where = where + " AND ";
                }
                where = where + " user = ? ";
            } else {
            }

            if (where.length() != 0) {
                where = " where " + where + " order by id";
                query = query + where;
                stmt = connexion.prepareStatement(query);
                int i = 1;
                if (sel.getid()!= 0) {
                    stmt.setInt(i, sel.getid());
                    i++;

                }
                if (sel.getUser()!= null) {
                    stmt.setString(i, sel.getUser() + "%");
                    i++;
                }
            } else {
                query = query + " Order by id";
                stmt = connexion.prepareStatement(query);
            }
            java.sql.ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                al.add(new GameDto(rs.getString("\"user\""),
                        rs.getInt("\"score\""),rs.getString("\"state\""),rs.getString("\"word\""),rs.getString("\"mod\"")));
            }
        } catch (java.sql.SQLException eSQL) {
            throw new DbException("Instanciation de "+recordName+" impossible:\nSQLException: " + eSQL.getMessage());
        }
        return al;
    }

    /**
     * Insert db int.
     *
     * @param record the record
     * @return int
     * @throws Exception.DbException
     */
    public static int insertDb(GameDto record) throws DbException {
        try {
             int num = SequenceDB.getNextNumGame(SequenceDB.GAME);
            System.out.println(num);
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement insert;
            insert = connexion.prepareStatement( "INSERT INTO GAME VALUES (?,?,?,?,?);");
            insert.setInt(1, num);            
            insert.setString(2, record.getUser());
            insert.setInt(3, record.getScore());
            insert.setString(4, record.getState());
            insert.setString(5, record.getWord());
            insert.executeUpdate();
            return num;
        } catch (DbException | SQLException ex) {
            throw new DbException(recordName+": ajout impossible\n" + ex.getMessage());
        }
    }
 
    //54317
    public static int nbGameHard(String name,String mod) throws DbException, SQLException{
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement insert;
            insert = connexion.prepareStatement(
                    "SELECT count(*) FROM GAME WHERE user = ? && mod = Hard");
            insert.setString(1, name);   
            insert.setString(2, mod);
            ResultSet result = insert.executeQuery();
            return ((Number) result.getObject(1)).intValue();
    }
    //54317
        public static int nbGameEasy(String name, String mod) throws DbException, SQLException{
            java.sql.Connection connexion = DBManager.getConnection();
            java.sql.PreparedStatement insert;
            insert = connexion.prepareStatement(
                    "SELECT count(*) FROM GAME WHERE user = ? && mod = Easy");
            insert.setString(1, name);         
            insert.setString(2, mod);
            ResultSet result = insert.executeQuery();
            return ((Number) result.getObject(1)).intValue();
    }
}

