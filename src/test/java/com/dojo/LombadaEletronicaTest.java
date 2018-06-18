/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dojo;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Calendar;
import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 *
 * @author caren.moraes.nichele
 */
/**
 * This is a sample class to contain a TestCase
 */
public class LombadaEletronicaTest {

    @Mock
    RegistraDAO registraDAOMock;

    @Spy
    LombadaEletronica lombadaEletronicaSpy;

    public LombadaEletronicaTest() {
    }

    @Before
    public void setUp() throws InfracaoException {
        MockitoAnnotations.initMocks(this);

        doReturn(true).when(registraDAOMock).gravaNoDB(any(EntityManager.class), any(Double.class), any(Calendar.class), any(String.class));
        doReturn(registraDAOMock).when(lombadaEletronicaSpy).getRegistraDAO();
    }

    @After
    public void tearDown() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    // ------------------------------------------------------------------
    // Exercício 1 - testes unitários para método público verificaSeInfracao()
    // (Junit puro)
    // Objetivo: testar o método interno lombadaeletronica.verificaSeInfracao()
    // ------------------------------------------------------------------
    @Test
    // Verifica infração dia de semana e velocidade <= 40
    public void test_1_verificaSeInfracao_DiaDeSemana_Velocidade_Ok() {
        // 1. Instanciar as classes necessárias (a ser testada)
        LombadaEletronica lombadaeletronica = new LombadaEletronica();
        Calendar cal = Calendar.getInstance();

        // 2. Setar os valores de entrada
        // cal.set(YYYY, M, D, HH, MM, SS)
        // M (0 to 11 => January to December)
        cal.set(2017, 5, 1, 10, 30, 00);

        // 3. Chamar o método e verificar o resultado com o “Assert” do Junit
        assertFalse(lombadaeletronica.verificaSeInfracao(30, cal));
    }

    @Test
    // Verifica infração dia de semana velocidade > 40
    public void test_1_verificaSeInfracao_DiaDeSemana_Velocidade_NOk() {
        // 1. Instanciar as classes necessárias (a ser testada)
        LombadaEletronica lombadaeletronica = new LombadaEletronica();
        Calendar cal = Calendar.getInstance();

        // 2. Setar os valores de entrada
        // cal.set(YYYY, M, D, HH, MM, SS)
        // M (0 to 11 => January to December)
        cal.set(2017, 5, 1, 10, 30, 00);

        // 3. Chamar o método e verificar o resultado com o “Assert” do Junit
        assertTrue(lombadaeletronica.verificaSeInfracao(41, cal));
    }

    @Test
    // Verifica infração final de semana velocidade <= 60
    public void test_1_verificaSeInfracao_FinalDeSemana_Velocidade_Ok() {
        // 1. Instanciar as classes necessárias (a ser testada)
        LombadaEletronica lombadaeletronica = new LombadaEletronica();
        Calendar cal = Calendar.getInstance();

        // 2. Setar os valores de entrada
        // cal.set(YYYY, M, D, HH, MM, SS)
        // M (0 to 11 => January to December)
        cal.set(2017, 5, 10, 10, 30, 00);

        // 3. Chamar o método e verificar o resultado com o “Assert” do Junit
        assertFalse(lombadaeletronica.verificaSeInfracao(60, cal));
    }

    @Test
    // Verifica infração final de semana velocidade > 60
    public void test_1_verificaSeInfracao_FinalDeSemana_Velocidade_NOk() {
        // 1. Instanciar as classes necessárias (a ser testada)
        LombadaEletronica lombadaeletronica = new LombadaEletronica();
        Calendar cal = Calendar.getInstance();

        // 2. Setar os valores de entrada
        // cal.set(YYYY, M, D, HH, MM, SS)
        // M (0 to 11 => January to December)
        cal.set(2017, 5, 10, 10, 30, 00);

        // 3. Chamar o método e verificar o resultado com o “Assert” do Junit
        assertTrue(lombadaeletronica.verificaSeInfracao(71, cal));
    }

    // ------------------------------------------------------------------
    // Exercício 2 - teste unitário (Junit + mockito)
    // Objetivo: testar o método LombadaEletronica.registraInfracao() mockando a
    // chamada da classe RegistraDAO.gravaNoDB() que acessa recursos externos
    // (BD)
    // Passos:
    // - registraDAO = Mockar e simular respostado metodo gravaNoDB()
    // - LombadaEletronica = Spy e recebe a classe mockada mockRegistraDAO
    // ------------------------------------------------------------------
    @Test
    // Verifica infração dia de semana velocidade > 60
    public void test_2_registraInfracao_FinalDeSemana_Velocidade_NOK() throws Exception {

        // 1. Instanciar as classes necessárias (a ser testada)
        RegistraDAO registraDAOMock = mock(RegistraDAO.class);
        LombadaEletronica lombadaEletronica = new LombadaEletronica();
        LombadaEletronica lombadaEletronicaSpy = spy(lombadaEletronica);
        // 2. Setar os valores de entrada
        // Simula a resposta do metodo gravaNoDB retornando "true"
        // Mocka o DAO utilizado pela classe a ser testada lombadaEletronicaSpy
        doReturn(true).when(registraDAOMock).gravaNoDB(any(EntityManager.class), any(Double.class), any(Calendar.class), any(String.class));
        doReturn(registraDAOMock).when(lombadaEletronicaSpy).getRegistraDAO();
        Calendar cal = Calendar.getInstance();
        // cal.set(YYYY, M, D, HH, MM, SS)
        // M (0 to 11 => January to December)
        cal.set(2017, 5, 10, 10, 30, 00);

        // 3. Chamar o método e verificar o resultado com o “Assert” do Junit
        assertTrue(lombadaEletronicaSpy.registraInfracao(76, cal, "ISB3389"));
    }

    // ------------------------------------------------------------------
    // Exercício 2 - teste unitário (Junit + mockito + @Mock @Spy + @BeforeTest)
    // Objetivo: utilizar as annotations @Mock @Spy e @BeforeTest
    // ------------------------------------------------------------------
    @Test
    // Verifica infração dia de semana velocidade > 60
    public void test_2_registraInfracao_FinalDeSemana_Velocidade_NOK_ComAnnotations() throws Exception {

        // 1. Instanciar as classes necessárias (a ser testada)
        // O registro das instancias das classes necesárias será por annotation
        // 2. Setar os valores de entrada
        // a simulação do retorno é feito no @BeforeTest
        Calendar cal = Calendar.getInstance();
        // cal.set(YYYY, M, D, HH, MM, SS)
        // M (0 to 11 => January to December)
        cal.set(2017, 5, 10, 10, 30, 00);

        // 3. Chamar o método e verificar o resultado com o “Assert” do Junit
        assertTrue(lombadaEletronicaSpy.registraInfracao(100, cal, "ISB3389"));
    }

    // ------------------------------------------------------------------
    // Exercício 3 - testes unitários para testar exceptions (Junit + mockito)
    // Objetivo: validar Exception recebida
    // ------------------------------------------------------------------
    @Test(expected = InfracaoException.class)
    public void test_3_RegistraInfracao_Exception() throws InfracaoException {
        // 1. Instanciar as classes necessárias (a ser testada)
        // O registro das instancias das classes necesárias será por annotation

        // 2. Setar os valores de entrada
        // Usar valores que vão retornar exception
        double iValorVAlocidade = 76;
        String sPlaca = null;
        Calendar cal = null;

        // 3. Chamar o método
        // No caso do teste de Exception não precisa usar Assert, use a tag
        // "expected" ao lado do @Test
        lombadaEletronicaSpy.registraInfracao(iValorVAlocidade, cal, sPlaca);

    }

    // ------------------------------------------------------------------
    // Exercício 3 - testes unitários para testar exceptions (Junit + mockito)
    // Objetivo: validar Exception recebida
    // ------------------------------------------------------------------
    @Test(expected = InfracaoException.class)
    public void test_3_RegistraInfracao_Exception_doThrow() throws InfracaoException {
        // 1. Instanciar as classes necessárias (a ser testada)
        // O registro das instancias das classes necesárias será por annotation

        // 2. Setar os valores de entrada
        // Simular a exception usando o doThrow()
        Calendar cal = Calendar.getInstance();
        // cal.set(YYYY, M, D, HH, MM, SS)
        // M (0 to 11 => January to December)
        cal.set(2017, 5, 10, 10, 30, 00);

        doThrow(new InfracaoException(1, "EXCEPTION: DATA OU PLACA NULAS")).when(lombadaEletronicaSpy)
                .registraInfracao(any(Double.class), any(Calendar.class), any(String.class));

        // 3. Chamar o método
        // No caso do teste de Exception não precisa usar Assert, use a tag
        // "expected" ao lado do @Test
        lombadaEletronicaSpy.registraInfracao(76, cal, "ISB3389");

    }

    // ------------------------------------------------------------------
    // Exercício 3 - testes unitários para testar exceptions (Junit + mockito)
    // Objetivo: forçar a Exeption com doThrow() e validar Exception recebida
    // ------------------------------------------------------------------
    @Test
    public void test_3_RegistraInfracao_Exception_TryCatch() throws InfracaoException {
        // 1. Instanciar as classes necessárias (a ser testada)
        // O registro das instancias das classes necesárias será por annotation

        // 2. Setar os valores de entrada
        // Usar valores que vão retornar exception
        double iValorVAlocidade = 76;
        String sPlaca = null;
        Calendar cal = null;

        // 3. Chamar o método
        // No caso de precisar validar detalhes da exception use try/catch e
        // valide com Assert os atributos da Exception recebida
        try {
            lombadaEletronicaSpy.registraInfracao(iValorVAlocidade, cal, sPlaca);
        } catch (InfracaoException ex) {
            assertEquals(1, ex.getCodigo());
            assertEquals("EXCEPTION: DATA OU PLACA NULAS", ex.getMessage());
        }

    }

    // ------------------------------------------------------------------
    // Exercício 4 - teste unitário para um método que retorna Void (Junit +
    // mockito)
    // Objetivo: validar a lógica de um metodo void quando este chama um 2o.
    // metodo passando por parametro o valor/objeto foco do teste
    // Passos:
    // - Usar doAnswer() para quando o metodoTwoVoid for chamado
    // - Incluir o Assert que valida valor de input recebido pelo metodoTwoVoid
    // ------------------------------------------------------------------
    @Test
    public void test_4_RegistraInfracao_MetodoVoid_1() {
        // 1. Instanciar as classes necessárias (a ser testada)

        LombadaEletronica lombadaEletronica = new LombadaEletronica();
        LombadaEletronica lombadaEletronicaSpy = spy(lombadaEletronica);

        // 2. Setar os valores de entrada
        // simula a resposta Void do metodoTwoVoid
        doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                assertEquals(9.0, args[0]); // Assert que valida o valor de
                // input recebido
                // pelo metodoTwoVoid
                return null;
            }
        }).when(lombadaEletronicaSpy).metodoTwoVoid(any(Integer.class));

        // 3. Chamar o método a testar
        lombadaEletronicaSpy.metodoOneVoid(3); // 3 * 3

    }

    // ------------------------------------------------------------------
    // Exercício 4 - testes unitários para um método que retorna Void (Junit +
    // mockito)
    // Objetivo: Teste que valida o valor computado pelo metodoVoid verificando
    // o atributo privado da classe que armazena este valor
    // ------------------------------------------------------------------
    @Test
    public void test_4_RegistraInfracao_MetodoVoid_2() {
        // 1. Instanciar as classes necessárias (a ser testada)

        LombadaEletronica lombadaEletronica = new LombadaEletronica();
        LombadaEletronica lombadaEletronicaSpy = spy(lombadaEletronica);

        // 2. Setar os valores de entrada
        double iValorInput = 3;
        double iValorOutput = 15.0;

        // 3. Chamar o método e verificar o resultado com o “Assert” do Junit
        lombadaEletronicaSpy.metodoVoid(iValorInput);
        assertEquals(iValorOutput, lombadaEletronicaSpy.getValorMulta(), 0.001); // 3

    }

}
