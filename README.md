
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

## Configuração e Beans ##

_@Configuration_ anota sua classe como uma classe de config;

deve ser usado somente para configurações!

uma boa prática é definir no nome da classe a finalidade de sua configuração

- EmailConfiguration
- SecurityConfiguration
- DatabaseConfiguration


```
@Configuration
public MyConfiguration(){

    @Bean(name = "minhaConfig")
     String nomeDoMeuApp(){
            return "Meu sistema v1";
        }
}
```

As configuracoes podem ser buscadas dentro do contexto do Springboot através de Qualifiers

```

public MeuControlador(){

    @Autowired
    @Qualifier("minhaConfig")
    private String minhaConfiguracao;

    @GetMapping("/myConfig")
    String obterConfiguracaoApp(){
        return minhaConfiguracao;
    }
}

```

onde o _@Qualifier_ vai buscar a informacao injetada pelo springboot.

## Container IOC Spring ##

O _Container IOC_ (inversao de controle) faz as injecoes das dependencias
de forma automatica.

As classes injetadas funcionam no modo _singleton_, ou seja, a mesma instancia é
acessada por toda JVM, não importa a quantidade de usuários ou conexoes.

###_@Configuration_ ###
Serve para classes de configuracao

- _@Bean_: sao atribuicoes genericas para os metodos (nunca para as classes!)
  para que sejam injetados pelo Spring.

### _@Component_ ###
Anotacao generica, pode ser utilizado quando a sua classe nao se 
encaixa nem em _controller, service ou repository_ mas mesmo assim
tem necessidade de ser injetada pelo Spring.

- _@Controller_: injecao de controlador, referente ao MVC

- _@Service_: injecao de regra de negocios, calculos, transformacoes, etc

- _@Repository_: injecao da camada de acesso de dados, banco de dados,
requisicoes e afins

> Importante saber:
> 
> o _@Configuration_ faz a leitura de todos os pacotes a partir de onde ele foi instanciado,
> procurando por outras annotations.
> 
> ex: pacote .org.renatosantana contem a classe _AppConfiguration_ entao todos as
> classes com annotations a partir de .org.renatosantana serao injetadas automaticamente.
> 
> para injetar classes fora deste escopo, como por exemplo, coml lib de terceiros, é necessario
> adicionar uma annotation _@ComponentScan_ e referenciar os pacotes para serem lidos

```
@ComponentScan(
basePackages = [
"com.mylib1",
"com.myexternallib2"])
public MyConfig(){
    ...
}
``` 

### _@Autowired_ ###
Serve para buscar as instancias das classes injetadas pelo Spring.
lembrando sempre que as classes anotadas ja possuem instancias lidas,
sem necessidade de gerar novas instancias.

Em versoes mais novas do Spring, o Autowired nao é mais necessario. O spring ja busca automaticamente
pelas dependencias, em classes anotadas.