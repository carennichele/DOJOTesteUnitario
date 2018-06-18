/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dojo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import org.hibernate.Session;

/**
 *
 * @author caren.moraes.nichele
 */
public class RegistraDAO {

    private Connection getConnection(final EntityManager entityManager) throws SQLException {

        final Session session = (Session) entityManager.getDelegate();
        final Connection con = session.connection();

        return con;
    }

    /**
     *
     * Metodo que faria o registro da infracao em um banco de dados
     *
     * @param entityManager
     * @param velocidade
     * @param data
     * @param placa
     * @return
     * @throws com.dojo.InfracaoException
     */
    public boolean gravaNoDB(EntityManager entityManager, double velocidade, Calendar data, String placa) {

        // Grava infracao <data, hora, placa, velocidade>
        System.out.printf("\n>>>>DAO\n  >>>>GRAVA NO BANCO: <%s, %s, %f>!\n  I Think you should mock this method, don't ya?<<<<\n",
                data.getTime(), placa, velocidade);
        return true;
    }

    private void close(final ResultSet resultSet, final Connection connection, final CallableStatement callableStatement) {

        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException se) {
            System.out.printf("\n>>>>%s", se.getMessage());
        }

        try {
            if (callableStatement != null) {
                callableStatement.close();
            }
        } catch (SQLException se) {
            System.out.printf("\n>>>>%s", se.getMessage());
        }

        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException se) {
            System.out.printf("\n>>>>%s", se.getMessage());
        }
    }
}
