
# CONCEITOS SPRINGBOOT #

## Primeiras dependencias, Config Inicial do projeto ##

1- maven - parent
É uma biblioteca que vai configurar automaticamente toda a biblioteca.
Como se fosse um template

2- maven - dependencias
mvnrepository.com 

3- maven - starter parent SPRINGBOOT
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.4.RELEASE</version>
    </parent>

4- maven - como configurar

após adicionar um parent para gerenciar as libs, é necessário incluir tbm as dependencias e plugin dentro do build.
o groupId deve ter relação com o do parent.

5- starters

Os starters são dependencias, módulos que englobam classes de configuração.
Para o springboot funcionar, o minimo a ser adicionado é o parent (com a versao) e o starter,
a partir dai já é possivel utilizar todo poder do springboot

os starters possuem uma sintaxe padrão, é necessario invocar o mesmo groupId + starter.

ex: 

<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-****</artifactId>

***: web, data, etc

## Primeiro hello world ##

@RestController anota sua classe como um controlador

@GetMapping("/hello") habilita o método GET dentro da uri /hello
caminho padrao: localhost:8080/hello