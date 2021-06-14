
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

### _@Configuration_ ###
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

> Ainda utilizado para field injection;
> 
> Pode ser necessario dependendo do cenário;
> 
> A melhor prática é utilizar um construtor para passar as dependencias, porque nao tem como checar
> o status do objeto usando Autowired.


### _application.properties_ ###
Arquivo default para propriedades da aplicacao. 
Tambem pode ser o _application.yml_.
Suporta centenas de configs.

As configs mais comuns foram adicionadas ao arquivo na pasta _resources_.
Tambem podem ser encontradas em
https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.core

### _@Profile_ ###
Serve para definir qual o perfil ativo por config

```
@Configuration
@Profile("nome_do_perfil")
public MinhaConfiguration(){
  //esta configuracao so vai rodar no perfil determinado
}
```

### _@Development_ ###

Annotation customizada para carregar valores de configuracao. 
Segue exemplo abaixo:

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Configuration
@Profile("development")
public @interface Development {
}
```

> _@Target_: Alvo da injecao de dependencias, no caso de classes de config, tem que ser sempre como TYPE
> 
> _@Retention_: Até quando a classe vai ficar retida, neste caso é somente até o Runtime
> 
> _@Profile_: self explanable


### _@Cachorro_ e _@Gato_ ###
Annotations customizadas que carregam as dependencias e policies referentes ao Bean previamente definido

```
    @Bean(name = "cachorro")
    public Animal cachorro(){
        return new Animal() {
            @Override
            public void fazerBarulho() {
                System.out.println("BAW WAW BAW");
            }
        };
    }
```

pode ser refeito com lambda, ficando simplesmente:

```
    @Bean(name = "cachorro")
    public Animal cachorro(){
        return () -> System.out.println("BAW WAW BAW");
    }
```

> A interface _Animal_ apenas define o metodo que todo animal deve implementar.
> 
> _AnimalConfiguration_ carrega os beans que são injetados


### Dependencias de Conexao com Banco de Dados ###

As dependencias e propriedades sao auto explicativas

```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
        </dependency>
```

### data.sql ###
Nomenclatura padrao para o script, o script sera executado todas as vezes que a aplicacao subir.

Como o H2 foi configurado pra rodar em memoria, os dados sao perdidos sempre que a aplicacao parar.

### Anotacoes referentes ao JPA ###
Alguns exemplos de anotacoes utilizadas com JPA
```
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 100)
    private String nome;
  }
```
>_@Entity_: anota como entidade para ser injetada pelo Spring, usando o JPA.
> Será registrada como uma tabela no banco de dados
> 
> _@Table_: indica a tabela a qual a classe se referencia.
> É desnecessaria se a classe campo tiver o mesmo nome que a tabela
> Pois sera mapeado automaticamente
> 
> _@Id_: indica que é a chave primaria
> 
> _@GeneratedValue_: modo de gerar os campos, no caso de AUTO sera incrementado automaticamente
> 
> _@Column_: auto explicativo.
> É desnecessaria se o campo tiver o mesmo nome que a coluna,
> Sendo mapeada automaticamente no mesmo caso da _@Table_
>
> _@EntityManager_: interface usada pelo JPA para realizar as operaçoes
> 
> _@Transactional_: necessario para o Spring saber que realizar transacao em banco de dados.
> pode (e deve) ser utilizado em conjunto com readonly = true, para otimizar as buscas
> 
> o arquivo data.sql foi renomeado porque se tornou desnecessario.
> o proprio Spring vai iniciar as tabelas, entidades e tudo mais, atraves
> das anotacoes.

### Operações comuns com o _EntityManager_ ###

>
>.persist()
: funciona como o Insert do SQL

>.merge()
: sincroniza os dados. Ao utilizar JPA, os dados passam a ser como _entidades_
e nao objetos em si

>.createQuery()
: pode ser escrita uma query em formato JPQL, uma mistura de JPA
com SQL

>.find()
: procura um determinado registro

>.setParameter()
: customiza ainda mais a sua query, adicionando parametros em uma
query já pronta


### JPARepository ###

Interface que ja contem metodos mais utilizados no JPA,
com boas praticas e performance excelente.

> _save_: faz o insert ou update de uma entidade na tabela
> 
> _findAll_: trás todos os resultados referentes á entidade buscada
> 
> _findById_: busca apenas por id
> 
> _findByNomeLike_: busca por nome que contem uma string, é método customizado
> 
> _delete_: deleta a entidade enviada
> 

Um exemplo da utilizacao, explicando as anotacoes:

```
public interface Clientes extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNomeLike(String nome);
}
```

> <Cliente, Integer>
> 
> Cliente é o objeto de resultado da query
> 
> Integer é o tipo do campo anotado com o _@Id_


### _@QueryMethods_ ###

O JPA Tambem permite que querys customizadas sejam adicionadas.
Os exemplos abaixo, sao para querys mais simples, porem tambem é
possivel adicionar outras mais complexas

```
    List<Cliente> findByNomeLike(String nome);
    List<Cliente> findByNomeOrId(String nome, Integer id);
    Cliente findOneByNome(String nome);
    boolean existsByNome(String nome);
```

> _findByNomeLike_
> 
> busca por uma string que se pareça com a entrada fornecida, a sintaxe do comando funciona da seguinte forma:
> 
> - find
> - - by
> - - - nome
> - - - - like
> 
> a query é construida nesta ordem, utilizando as palavras-chave informadas.
> 
> mesma coisa para _findNomeOrid_ onde como o proprio metodo diz, a busca sera realizada pelo nome
> ou id.

### _@Query_ customizada ###

```
    @Query("SELECT c FROM Cliente WHERE c.nome LIKE  :nome")
    List<Cliente> buscarPorNomeHQL(@Param("nome")String nome);
```

> _@Query_ passa a query a ser executada
> 
> _:nome_ é o parametro a ser buscado
> 
> _@Param_ indica o parametro que esta anotado, para a busca
> 
>  _@ManyToOne_ muitos para um, se refere ao relacionamento da classe atual
> com o item referenciado. exemplo:

```
  public class Pedido{
    
    @ManyToOne
    private Cliente cliente;
    //muitos pedidos para um cliente
  }
```

> _OneToMany_ relacao contraria do manyToOne. Apenas um para muitos.
> O _mappedBy_ indica por quem está sendo feito este mapeamento
> 
> _@JoinColumn_ informa a coluna que deseja ter relacao com a entidade
> 
> _Set_ é como uma lista, com o diferencial de que NAO ACEITA ITENS REPETIDOS!
> 
> _FetchType.LAZY_ é o modo padrao de trazer as informacoes. com Lazy, a consulta é otimizada
> porem não trás junto os pedidos do cliente ou informacao correlata.
> 
> _FetchType.EAGER_ tras todas as informacoes, porem a consulta é mais pesada.
> sempre que for buscado um registro, vem junto todas as informacoes relacionadas a ele


### _@Controller_ ###

Responsavel por obter as requisicoes, camada que fica exposta para web, servicos e afins

_@GetMapping_: Equivalente ao _RequestMapping_, voltado para o GET;

_@PostMapping_: Idem _GetMapping_, voltado para POST;

_@RequestBody_: Identifica o objeto que vem no body da requisicao;

_@PathVariable_: Autoexplicativo (variavel que vem no path)

_Optional_: é mandatório fazer essa verificacao ao trabalhar com dados que vem de base.
Evitando nullpointer.

_@JsonIgnore_: Propriedade será ignorada ao fazer o parse de json para objeto e vice versa

### _@RestController_ ###

É a melhor aplicação, substituindo o _@Controller_. Já vem com o _@ResponseBody_ embutido, sem necessidade de utilizar
em cada um dos métodos.

Se aplicado junto com _@RequestMapping_, facilita ainda mais o uso, deixando o código mais limpo.

> _ExampleMatcher_ serve para criar filtros de queries dinâmicas, é utilizado em conjunto com o Matcher.
> _matching_ é o metodo de comparacao, ou seja, funciona como um like;
> 
> _withIgnoreCase_ ignora a capitulacao das palavras, tanto faz se está em caixa alta ou baixa;
> 
> _Example_ aplica o filtro criado no _matcher_ junto com o objeto a ser filtrado, a ordem é _objeto_, _filtro(matcher)_

```
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(filtro, matcher);
```

> _@ResponseStatus_ modifica o codigo de status padrao que o método retorna, caso utilize o _@RestController_ o _@ResponseBody_
> sempre trás o status code 200.