/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dojo;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author caren.moraes.nichele
 */
public class RegistraDAO {

    /**
     * Metodo que faria o registro da infracao em um banco de dados
     *
     * @param velocidade
     * @param placa
     * @param data
     * @return
     */
    public boolean gravaNoDB(final EntityManager entityManager, double velocidade, Calendar data, String placa) {
        boolean retorno = false;

        // Grava infracao <data, hora, placa, velocidade>
        System.out.printf(
                "\n>>>>\n>>>>EXECUTA SQL NO BANCO: <%s, %s, %f><<<<\n>>>>I Think you should mock this method, don't ya? <<<<<<\n\n",
                data.getTime(), placa, velocidade);

        StringBuilder sql = new StringBuilder();
        try {
            sql.append("SELECT * ");
            sql.append(" FROM CRED_OWNER.MVW_PARAMETRO_PRODUTO  MV, ");
            sql.append(" CRED_OWNER.MVW_BUSCA_PROD_CREDITO VW, ");
            sql.append(" WHERE MV.OID_PRODUTO = VW.OID_PRODUTO ");
            sql.append(" AND MV.COD_PRODUTO = VW.COD_PRODUTO ");
            sql.append(" AND TITU.FCCODPROD = MV.COD_PRODUTO ");
            sql.append(" AND PARV.FCCODTITU = TITU.FCCODTITU ");

            final Query query = entityManager.createNativeQuery(sql.toString());
            List<BigDecimal> valorAntecipado = (List<BigDecimal>) query.getResultList();
            retorno = true;
        } catch (final NoResultException exception) {
            PrintStream printf = System.out.printf("\n>>>>DAO: NoResultException >>>>\n\n");
            exception.printStackTrace();
            return false;
        } catch (final Exception exception) {
            System.out.printf("\n>>>>DAO: Exception >>>>\n\n");
            exception.printStackTrace();
            return false;
        }

        return retorno;
    }

}
