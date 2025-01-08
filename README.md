<h1 align="center">Sistema de Gerenciamento de Estoque de Supermercado</h1>

## Visão Geral:
Este é um projeto de Sistema de Gerenciamento de Estoque de Supermercado desenvolvido em Java para fins de aprendizado. Criado por mim, o projeto busca fornecer uma base sólida para o gerenciamento básico de estoque, com funcionalidades essenciais como CRUD para produtos e departamentos. O foco principal é praticar conceitos de desenvolvimento de software utilizando tecnologias modernas. Por ser um projeto simples e educacional, ele não inclui recursos avançados como rastreamento de produtos em tempo real ou atualizações dinâmicas.

## Funcionalidades:
- Operações CRUD: Criar, Ler, Atualizar e Deletar operações para Produtos e Departamentos.
- DTOs (Objetos de Transferência de Dados): Simplifica a transferência de dados e melhora a estrutura das respostas da API.
- HATEOAS: Implementa Hypermedia as the Engine of Application State para fornecer links navegáveis nas respostas da API.
- Migrações de Banco de Dados: Utiliza ferramentas de migração para evolução do esquema do banco de dados.
- Documentação da API: Swagger integrado para fácil exploração e teste da API.
- Testes Unitários: Testes unitários abrangentes garantem a confiabilidade e robustez do sistema.

## Tecnologias Utilizadas:

- Java: Linguagem de programação principal.
- Spring Boot: Framework para construção da aplicação.
- Hibernate: ORM para interações com o banco de dados.
- Banco de Dados H2: Utilizado para desenvolvimento e testes.
- MapStruct/DozerMapper: Utilizado para mapeamento entre entidades e DTOs.
- Swagger: Documentação e exploração da API.
- JUnit: Framework para testes unitários.
- Mockito: Criar objetos simulados, conhecidos como "mocks", que substituem componentes reais durante os testes unitários.

## Começando: 
### Pré-requisitos
- Kit de Desenvolvimento Java (JDK) 11 ou superior.
- Maven ou Gradle para gerenciamento de dependências.
- Um IDE (ex.: IntelliJ IDEA, Eclipse).

### Instalação
- Clone o repositório:
git clone https://github.com/seunomeusuario/gerenciamento-estoque-supermercado.git

- Navegue até o diretório do projeto:
cd gerenciamento-estoque-supermercado

Execute a aplicação usando Maven ou seu IDE.

### Executando a Aplicação
1. Inicie a aplicação:
mvn spring-boot:run

2. Acesse a documentação da API:
http://localhost:8080/swagger-ui.html

## Estrutura do Projeto
- /src/main/java: Contém o código principal da aplicação.
- /controller: Controladores REST para manipulação de requisições HTTP.
- /service: Lógica de negócio e camada de serviço.
- /repository: Camada de acesso a dados interagindo com o banco de dados.
- /dto: Objetos de Transferência de Dados para respostas da API.
- /mapper: Mappers para converter entidades em DTOs.
- /exception: Tratamento centralizado de exceções.
  
- /src/main/resources: Arquivos de configuração e recursos estáticos.
- /src/test/java: Testes unitários da aplicação.

## Endpoints da API
###  Produtos:
- GET api/products: Recupera uma lista de todos os produtos.
- GET api/products/{id}: Recupera um produto pelo ID.
- GET api/products/department/{departmentId}: Recupera todos os produtos que estão no departamento selecionado.
- POST api/products: Adiciona um novo produto.
- PUT api/products/{id}: Atualiza um produto existente.
- DELETE api/products/{id}: Deleta um produto.

### Departamentos: 
- GET api/departments: Recupera uma lista de todos os departamentos.
- GET api/departments/sector: Recupera uma lista de todos os departamentos sem os produtos.
- GET api/departments/{id}: Recupera um setor pelo ID.
- POST api/departments: Adiciona um novo departamento.
- PUT api/departments/{id}: Atualiza um departamento existente.
- DELETE api/departments/{id}: Deleta um departamento.

## Contribuindo
Contribuições são bem-vindas! Por favor, faça um fork do repositório e envie um pull request.

Para quaisquer perguntas ou suporte, entre em contato com g.genaro.contato@gmail.com
  
