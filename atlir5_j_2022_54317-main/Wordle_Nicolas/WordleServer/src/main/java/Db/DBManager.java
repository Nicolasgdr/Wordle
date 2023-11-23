/*
 * DBManager.java
 */
package Db;

import Exception.DbException;

import java.sql.*;

/**
 * Offre les outils de connexion et de gestion de transaction. 
 */
public class DBManager {

    private static Connection connection;

    /**
     * Retourne la connexion établie ou à défaut, l'établit
     * @return la connexion établie.
     * @throws Exception.DbException
     */
    public static Connection getConnection() throws DbException {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:data/sqlite/Projet.db");
                
            } catch (SQLException ex) {
                throw new DbException("Connexion impossible: " + ex.getMessage());
            }
        }
        return connection;
    }

    /**
     * débute une transaction
     * @throws Exception.DbException
     */
    public static void startTransaction() throws DbException {
        try {

            getConnection().setAutoCommit(false);
        } catch (SQLException ex) {
            throw new DbException("Impossible de démarrer une transaction: "+ex.getMessage());
        }
    }

    /**
     * débute une transaction en spécifiant son niveau d'isolation
     * Attention, ceci n'est pas implémenté sous Access!
     * (cette notion sera abordée ultérieurement dans le cours de bd)
     * @param isolationLevel
     * @throws Exception.DbException
     */
    public static void startTransaction(int isolationLevel) throws DbException {
        try {
            getConnection().setAutoCommit(false);

            int isol = 0;
            switch (isolationLevel) {
                case 0 -> isol = java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
                case 1 -> isol = java.sql.Connection.TRANSACTION_READ_COMMITTED;
                case 2 -> isol = java.sql.Connection.TRANSACTION_REPEATABLE_READ;
                case 3 -> isol = java.sql.Connection.TRANSACTION_SERIALIZABLE;
                default -> throw new DbException("Degré d'isolation inexistant!");
            }
            getConnection().setTransactionIsolation(isol);
        } catch (SQLException ex) {
            throw new DbException("Impossible de démarrer une transaction: "+ex.getMessage());
        }
    }

    /**
     * valide la transaction courante
     * @throws Exception.DbException
     */
    public static void validateTransaction() throws DbException {
        try {
            getConnection().commit();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new DbException("Impossible de valider la transaction: "+ex.getMessage());
        }
    }

    /**
     * annule la transaction courante
     * @throws Exception.DbException
     */
    public static void cancelTransaction() throws DbException {
        try {
            getConnection().rollback();
            getConnection().setAutoCommit(true);
        } catch (SQLException ex) {
            throw new DbException("Impossible d'annuler la transaction: "+ex.getMessage());
        }
    }
}
