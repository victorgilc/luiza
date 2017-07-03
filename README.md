# Projeto "Luiza"
Projeto fictício que ordena, filtra e agrupa uma lista de produtos
<br/><br/>
Versão do Java: <b>8</b>
<br/>
IDE utilizada: <b>Eclipse Neon 3</b>
<br/>
Servidor WEB utilizado: <b>WildFly 10.x</b>
<br/>
Instale o Loombok para gerar Getters/Setters!
<br/>
Instale o Maven para resolução das dependências

<br/>
URL para acesso ao único endpoint: <b> http://localhost:8080/produtoEndpoint/trataItensSemelhantes </b>
<br/><br/>

<b>Atenção:</b>
<br/>
-Esse endpoint deve ser acessado através de uma requisição POST, é recomendado utilizar o "app" do Google Chrome <b>Postman</b>

<br/>

<b>Observações:</b>
<br/>
-Refente ao item: "Disponibilizar	um jar para	que	sua	aplicação	seja	executada	via	terminal":
O comando "mvn clean package" gera o arquivo ".war" dentro do diretório "target" do projeto.

-Para o agrupamento de Strings(exceto ID), foi usada a "definição de Levenshtein" que é um algoritmo que calcula a distância/semelhança entre 2 Strings e, baseado nisso, foi colocado uma margem de 20%  de tolerância da não semelhança(essa sensibilidade poderia estar em um .properties, fica como sugestão de melhoria).

-Os testes unitários foram feitos apenas para a camada pertinente a regra de negócio, a fim de evitar testes demasiados e tempo perdido em camadas desnecessárias, não focando assim na cobertura do projeto, mas sim, em testes minimamente inteligentes.

-Não me preocupei com o lançamento de exceptions, como sugestão de melhoria, implementar um logger, como o log4J, lançando exceptions em um arquivo de texto.
