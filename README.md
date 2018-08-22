# DOJO Teste Unitário - Implementação dos Exerciícios
Repositório contendo fontes de uma aplicação Java utilizada em DOJO para ensinar sobre como implementar Testes Unitários utilizando Junit e Mockito.

Os fontes contém a implementação dos exercícios propostos, e as refatorações necessárias.

## Dicas
Para implementar um teste unitário tenha em mente os seguintes passos:
1. Identifique os objetos necessários no escopo do teste:
   + Unidade a ser testada (ex: a classe a ser testada)
   + Os demais objetos que farão parte do teste (ex: outra(s) classe(s) necessárias externas a unidade a ser testada)    
2. Defina e configure os valores de entrada e as simulações de retorno no caso dos objetos externos a unidade a ser testada  
3. Chame o método a ser testado da unidade
4. Verifique o resultado com o “Assert” do Junit. Ex:
  + AssertTrue()
  + AssertFalse()
  + AssertEquals()

## Exercicios

### Exercício 1 (JUnit puro): teste para o método de regra de negócio
Testes unitários para método protected verificaSeInfracao() utilizando JUnit puro.

Implemente os seguintes cenários:
  + Velocidade <= 40 e Dia de semana: Resultado esperado “False”
  + Velocidade > 40 e Dia de semana:   Resultado esperado “True”
  + Velocidade <= 60 e Final de semana: Resultado esperado “False”
  + Velocidade > 60 e Final de semana:   Resultado esperado “True”


### Exercício 2 (JUnit + Mockito): simulação do objeto externo DAO
Teste unitário para o método público registraInfracao() utilizando Junit + Mockito + refatoração da instanciação do DAO.

#### Parte I (JUnit + Mockito)
Para mockar a classe DAO (externa a unidade a ser testada):
+ Na classe LombadaEletronica implemente método de getRegistraDAO() { return new RegistraDAO()}
+ Use getRegistraDAO() ao invéz de instanciá-lo dentro do método registraInfracao.
+ Na classe LombadaEletronicaTeste simule o retorno do getRegistraDAO()

Passos:
1. Instanciar classes (LombadaEletronica, RegistraDAO)
```Java
RegistraDAO registraDAOMock = mock(RegistraDAO.class);
LombadaEletronica lombadaEletronica = new LombadaEletronica ();
LombadaEletronica lombadaEletronicaSpy  = spy(lombadaEletronica);
```
2. Setar os valores de entrada do teste (e simular a resposta dos mocks)
```Java
doReturn(true).when(registraDAOMock).gravaNoDB(any(Double.class), any(Calendar.class), any(String.class));
doReturn(registraDAOMock).when(lombadaEletronicaSpy).getRegistraDAO();
```
3. Chamar o método para testar e verificar o resultado esperado com Assert
```Java
assertTrue(lombadaEletronicaSpy.registraInfracao(75, cal, “ISB7374”));
```


#### Parte II (JUnit + Mockito + Annotations @Spy @Mock @Before)
Modificar o teste unitário do método registraInfracao() para usar @Mock + @Spy + @BeforeTest

Passos:
1. Instanciar classes (LombadaEletronica, RegistraDAO) com @Mock e @Spy
```Java
@Mock
RegistraDAO registraBDmock;
@Spy
LombadaEletronica lombadaEletronicaSpy;
```
2. Setar valores de entrada do teste (e simular a resposta dos mocks no @Before)
 + Colocar as chamadas doReturn… dentro do método @Before doSetup()
 ```Java
 @Before
    public void setUp() {
	MockitoAnnotations.initMocks(this);

	doReturn(registraDAOMock).when(lombadaEletronicaSpy).getRegistraDAO();
	doReturn(true).when(registraDAOMock).gravaNoDB(any(EntityManager.class), any(Double.class), any(Calendar.class), any(String.class));
	doReturn(logMock).when(lombadaEletronicaSpy).getLog();
    }
```  
3. Chamar o método para testar e verificar o resultado esperado com Assert
```Java
assertTrue(lombadaEletronicaSpy.registraInfracao(75, cal, “ISB7374”));
```

### Exercício 3 (JUnit + Mockito) simulando exceptions
Testes unitários para validar exceptions do método público registraInfracao().

Pode ser feito de duas maneiras de forçar a exception:
1. Passar valores que forcem o retorno da Exception pelo método
2. Usar o doThrow(new <Exception>, when(<class>))

Duas maneiras de fazer a validação via JUnit:
1. Incluir uma tag ao lado da tag @Test (utilizado quando se deseja validar somente o tipo da exception retornada):
 ```Java
 @Test (expected = <ExceptionClass>.class)
  ```
2. Usar Try/Catch e Assert (utilizado quando se deseja validar atributos mais detalhados da exception, ex: código, descrição da exception)


#### Forçando Exceptions com os parâmetros de entrada
Teste unitário forçando exception via valores de input + Refatorar o “Static Log”.
+ Na classe LombadaEletronica implemente método de getLog() { ...} , e use getLog().
+ Na classe LombadaEletronicaTeste simule o retorno do getLog()

Passos:
1. Instanciar classes (LombadaEletronica)
 + já instanciada na classe de teste
2. Setar os valores de entrada do teste
 + Usar valores que forçem o método registraInfração() retornar a InfracaoException
 ```Java
 cal = null, placa = null
 ```
3. Chamar o método a ser testado
4. Verifique o resultado e ao invés de usar Assert deve colocar a tag <b>"expected = InfracaoException.class"</b> ao lado do @Test

```Java
@Test(expected = InfracaoException.class)
public void test_Ex2_registraInfracao_FinalDeSemana_Velocidade_NOK_ComAnnotations() throws Exception {

Calendar cal = Calendar.getInstance();
cal.set(2017, 5, 10, 10, 30, 00);
String sPlaca = "ISB3389";

lombadaEletronicaSpy.registraInfracao(null, cal, sPlaca); 
}
```

#### Forçando Exceptions com doThrow
Teste unitário forçando uma exception via doThrow() do JUnit.

```Java
@Test(expected = InfracaoException.class)
public void test_Ex2_registraInfracao_FinalDeSemana_Velocidade_NOK_ComAnnotations() throws Exception {

  Calendar cal = Calendar.getInstance();
  cal.set(2017, 5, 10, 10, 30, 00);
  String sPlaca = "ISB3389";

  doThrow(new InfracaoException(1, "EXCEPTION: DATA OU PLACA NULAS")).when(lombadaEletronicaSpy)
                .registraInfracao(any(Double.class), any(Calendar.class),any(String.class));

  lombadaEletronicaSpy.registraInfracao(76, cal, "ISB3389");
}
```
#### Validando Exceptions com Try/Catch
Teste unitário forçando uma exception via parâmetros de entrada e validando os parametros da exception via Try/Catch + Assert.

```Java
@Test
public void test_Ex2_registraInfracao_FinalDeSemana_Velocidade_NOK_ComAnnotations() throws Exception {

  try {
          lombadaEletronicaSpy.registraInfracao(70, null, null);
          } catch (InfracaoException ex) {
              assertEquals(1, ex.getCodigo());
              assertEquals("EXCEPTION: DATA OU PLACA NULAS", ex.getMessage());
          }
}
```

### Exercício 4 (JUnit + Mockito com @InjectMocks)
### Exercício 5 (JUnit + Mockito) para métodos com retorno Void
