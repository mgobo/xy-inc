# xy-inc

Tecnologias utilizadas:
EJB (PADRAO ENTERPRISE JAVA),
JAX-RS (API RESTFULL JAVA),
JPA-2 (API DE PERSISTENCIA),
CRITERIA-2 (FRAMEWORK AGREGADO AO JPA),
CDI (MANIPULA INJECAO DE DEPEDENCIAS),
JSF-2 (FRAMEWORK DE COMPONENTS VISUAIS JAVA - PARA CLIENT),
PRIMEFACES (FRAMEWORK DE COMPONENTS VISUAIS AGREGADO AO JSF-2 - PARA CLIENT),
HTML5,
CSS,
JAVASCRIPT,
BOOTSTRAP,
JACKSON,
Log4J,
ARQUILLIAN / JUNIT,
MAVEN.

#IDE
TODO O PROJETO FOI DESENVOLVIDO UTILIZANDO NETBEANS-8.1;

#ORGANIZACAO DA SOLUCAO
A solu��o foi desenvolvida utilizando EJB, invers�o de controle ATIVO (CDI) com MVC (MODEL-VIEW-CONTROLLER) e JAX-RS.
O EJB foi escolhido pois possui uma camada consistente e segura para o gerenciamento dos dados atrav�s de JTA (Java Transaction API). O JAX-RS � a api Java para Rest e possui in�meros facilitadores para constru��o de servi�os REST. 

#PROJETO
A id�ia principal neste ponto � delegar ao container a responsabilidade de gerenciar da melhor forma poss�vel as transa���es realizadas entre APLICACAO-BANCO. Al�m disto, as classes s�o organizadas de forma �nica e utilizam invers�o de controle com um mecanismo pr�prio de persist��ncia, gerenciado pelo container.
Criteria 2 garante que todas as consultas sejam realizadas utilizando 100% de objetos mapeados, minimizando em 100% falhas na escrita de comandos JPQL.
O jackson � utilizado para convers�o de objetos Java-Json, Json-Java.
O Layout � responsivo e utiliza Bootstrap, o JSF com primefaces � utilizado apenas para gerenciar alguns eventos do Front-End.
O banco utilizado � o derby, pois se trata de uma solu��o embarcada do Java.
Log4j entra para gerar "logs" de opera��es espec�ficas que auxiliam o desenvolvedor no entendimento e resolu���o de problemas.
O Arquillian e o JUNIT s�o os framework de teste respons�veis por validar a camanda de neg�cios;

#RODANDO O PROJETO
O projeto foi testado no GLASSFISH, release 4.1. A 4.1.1 possui um erro e n�o � poss�vel registrar o "Pool de conex�es JDBC". � poss�vel utilizar o GLASSFISH-5 beta ou qualquer outro servidor como WILDFLY.


--CRIANDO O BANCO
ACESSAR: %GLASSFISH_HOME%/javadb/bin
EM WINDOWS, EXECUTAR: ij.bat
EM UNIX, DAR PERMISSAO FULL NO DIRETORIO "javadb" e EXECUTAR: ./ij
CRIAR O BANCO ZUP no derby executando o comando como SUPER USUARIO: CONNECT 'jdbc:derby:zup;user=zup;password=zup;create=true';

--Registrar o JDBC Connection Pool no servidor de aplica��es:
Nome do pool: zup (Pool Name)
Nome da jndi: jdbc/__zup (Fonte de dados JTA).

IMPLANTAR A APLICACAO "zup-service";
IMPLANTAR A APLICACAO "zup-client";

Acessar zup-client no navegador;
Acessar zup-service no navegador (Teste dos verbos HTTP);


#TESTES UNITARIOS;
Acessar o projeto "zup-ejb";
Abrir o arquivo "pom.xml";
Alterar a linha de "<skipTests>true</skipTests>" para "<skipTests>false</skipTests>"
Limpar e construir o projeto. Durante o processo de "build" os testes unit�rios ser�o executados;
