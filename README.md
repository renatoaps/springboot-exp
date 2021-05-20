
# CONCEITOS SPRINGBOOT #

## _Primeiras dependencias e Config Inicial do projeto_ ##

> Utilizando projeto com Maven

--
*** <parent> ***

Configura automaticamente toda biblioteca, funciona como um template.

--
*** dependencias ***

[Maven Repository](https://mvnrepository.com) 

--
*** starter parent Springboot ***
```
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.4.RELEASE</version>
    </parent>
```

--
*** como configurar ***

após adicionar um parent para gerenciar as libs, é necessário incluir tbm as dependencias e plugin dentro do build.

o groupId deve ter relação com o do parent.

--
*** starters ***

Os starters são dependencias, módulos que englobam classes de configuração.

Para o springboot funcionar, o minimo a ser adicionado é o parent (com a versao) e o starter,
a partir dai já é possivel utilizar todo poder do springboot.

Os starters possuem uma sintaxe padrão, é necessario invocar o mesmo groupId + starter.

ex: 

```
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-****</artifactId>
```

- web
- data
- tests
- etc...

## Primeiro hello world ##

@RestController anota sua classe como um controlador

@GetMapping("/hello") habilita o método GET dentro da uri /hello

caminho padrao: localhost:8080/hello

```
@RestController
public SeuController(){

    @GetMapping("/hello")
    String helloWorld(){
        return "hello world!";
    }
}
```