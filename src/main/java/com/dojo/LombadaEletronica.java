/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dojo;

import java.util.Calendar;
import javax.persistence.EntityManager;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 *
 * @author caren.moraes.nichele
 */
public class LombadaEletronica {

    private double valorMulta = 0;
    private EntityManager entityManager;
    private Log log;

    // Não precisa mais de wrapper getRegistraDAO()
    // a inicialização do objeto RegistraDAO foi retirado de dentro do método e
    // colocado como propriedade da classe, pois @InjectMocks funciona em:
    // - propriedades da classe
    // - parâmetros de metodos
    // - parâmetros de construtor da classe
    RegistraDAO registraDAO = new RegistraDAO();

    void setLog() {
        try {
            log = LogFactory.getLog("C:\teste.log");

        } catch (final Exception exe) {
            exe.printStackTrace();
        }
    }

    void LombadaEletronica() {
        this.setLog();
    }

    public double getValorMulta() {
        return valorMulta;
    }

    // Exemplo 1
    // Método de cálculo da regra do negócio (se velocidade > max)
    // Não precisa ser mockado, pois não usa recursos externos a esta classe
    protected boolean verificaSeInfracao(double velocidade, Calendar data) {
        boolean retorno = false;

        // 1 to 7 => Sunday to Saturday
        int iDiaSemana = data.get(Calendar.DAY_OF_WEEK);

        // Verifica se é dia de semana (40kh)
        if ((iDiaSemana > 1) && (iDiaSemana < 7)) {
            retorno = (velocidade > 40);
        } // Final de semana (60kh)
        else {
            if ((iDiaSemana == 7) || (iDiaSemana == 1)) {
                retorno = (velocidade > 60);
            }
        }

        return retorno;
    }

    // Exemplo 2 - Uma classe que usa outra classe que precisa ser mockada)
    // Método publico registraInfracao() deve ser chamado no teste
    // Método interno registraDAO.gravaNoDB() deve ser mockado no teste
    public boolean registraInfracao(double velocidade, Calendar data, String placa) throws InfracaoException {
        boolean retorno = false;

        if (data != null && placa != null) {
            if (verificaSeInfracao(velocidade, data)) {

                this.registraDAO.gravaNoDB(entityManager, velocidade, data, placa);
                retorno = true;
            }
        } else {
            InfracaoException ex = new InfracaoException(1, "EXCEPTION: DATA OU PLACA NULAS");
            // Não precisa mais de wrapper getLog()
            // a inicialização do objeto RegistraDAO foi retirado de dentro do método e
            // colocado como propriedade da classe, pois @InjectMocks funciona em:
            // - propriedades da classe
            // - parâmetros de metodos
            // - parâmetros de construtor da classe

            this.log.error(ex);
            throw ex;
        }

        return retorno;
    }

    // Exemplo VOID (A) - método com retorno void que precisa ser testado
    protected void metodoOneVoid(double valor) {
        metodoTwoVoid(valor * 3); // regra qualquer
    }

    // Exemplo VOID (A) - Método com retorno void que recebe valor do
    // metodoOneVoid
    protected void metodoTwoVoid(double valor) {
        System.out.printf("\n>>>>METODO VOID  <%d><<<\n", valor);
    }

    // Exemplo VOID (B) - Método com retorno void que precisa ser testado
    protected void metodoVoid(double valor) {
        this.valorMulta = valor * 5; // facilidade para o teste unitario:
        // armazena o valor a ser validado em um
        // atributo privado da classe
    }
}
