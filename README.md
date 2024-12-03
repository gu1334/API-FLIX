
# Testes de API: Categoria e Vídeos

Este projeto realiza a implementação de testes unitários utilizando o **JUnit 5** e **Mockito** para testar os controladores e modelos da API, com foco nas entidades `Categoria` e `Vídeos`. O objetivo é garantir o correto funcionamento da API, incluindo as funcionalidades de CRUD (Create, Read, Update, Delete), validação de dados e tratamento de erros.

## Estrutura do Projeto

O projeto está dividido nas seguintes partes:

- **Controller**: Gerencia as requisições HTTP e responde com os dados ou erros apropriados.
- **Model**: Representa a estrutura dos dados que a API manipula.
- **Repository**: Interface que interage com o banco de dados para realizar operações de CRUD.
- **Testes**: Testes unitários que garantem o correto funcionamento da API.

## Dependências

- **JUnit 5**: Framework de testes para Java.
- **Mockito**: Framework para criar mocks e simular o comportamento das dependências.
- **Spring Boot**: Framework que facilita a criação de APIs em Java.

## Testes

### Teste do `CategoriaController`

A classe `CategoriaControllerTest` contém os testes para os métodos do controlador de categorias. Aqui estão os principais testes realizados:

1. **Teste de Criação de Categoria (`testeCadastrar`)**:
    - Verifica se a categoria é criada com sucesso e retorna o status HTTP 201 (CREATED).
    - Verifica se o repositório foi chamado corretamente.

2. **Teste de Criação com Erro (`testeCadastrarErro`)**:
    - Simula um erro no banco de dados e verifica se o status HTTP 400 (BAD REQUEST) é retornado.

3. **Teste de Listagem de Categorias (`testeListar`)**:
    - Verifica se todas as categorias são retornadas com sucesso e se o status HTTP 200 (OK) é retornado.

4. **Teste de Listagem de Categoria por ID (`testeListarPorID`)**:
    - Verifica se uma categoria específica pode ser localizada e retornada corretamente.
    - Se a categoria não for encontrada, deve retornar status HTTP 404 (NOT FOUND).

5. **Teste de Deleção de Categoria (`testeDelete`)**:
    - Verifica se uma categoria pode ser deletada corretamente.
    - Se a categoria não for encontrada, o status HTTP 404 é retornado.

6. **Teste de Atualização de Categoria (`testeUpdate`)**:
    - Verifica se a atualização da categoria é feita corretamente.
    - Se a categoria não for encontrada, uma exceção personalizada é lançada.

### Teste do Modelo `Categoria`

A classe `CategoriaTest` testa a validade e os dados de mapeamento do modelo `Categoria`.

1. **Teste de Validação (`testCategoriaValidation`)**:
    - Testa se as validações de campos obrigatórios, como `titulo` e `cor`, estão funcionando corretamente.

2. **Teste de Mapeamento de Dados (`testDadosCategoriaMapping`)**:
    - Verifica se os dados passados na classe `DadosCategoria` são corretamente mapeados para a entidade `Categoria`.

### Testes do `VideosController`

A classe `VideosControllerTest` realiza os testes para o controlador de vídeos. Os principais testes realizados são:

1. **Teste de Criação de Vídeo (`testeCadastrar`)**:
    - Verifica se o vídeo é criado com sucesso, associando-o corretamente a uma categoria existente.
    - O status HTTP 201 (CREATED) deve ser retornado.

2. **Teste de Listagem de Vídeos (`testeListar`)**:
    - Verifica se todos os vídeos são listados corretamente.

3. **Teste de Listagem de Vídeos com Erro (`testeListarErro`)**:
    - Simula um erro ao listar vídeos e verifica se o status HTTP 400 (BAD REQUEST) é retornado.

### Teste do Modelo `Videos`

A classe `VideoTest` testa a validade dos dados do modelo `Videos`.

1. **Teste de Validação (`testInvalida`)**:
    - Verifica se os campos obrigatórios como `titulo`, `descricao`, e `url` estão sendo validados corretamente.

## Exemplos de Testes

### Exemplo de Teste de Criação de Categoria:

```java
@Test
public void testeCadastrar() {
    DadosCategoria dadosCategoria = new DadosCategoria("terror", "preto");
    Categoria categoriaMock = new Categoria();
    categoriaMock.setTitulo("terror");
    categoriaMock.setCor("preto");

    when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaMock);

    ResponseEntity<Categoria> response = categoriaController.create(dadosCategoria);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(categoriaMock, response.getBody());
    verify(categoriaRepository, times(1)).save(any(Categoria.class));
}
```

### Exemplo de Teste de Listagem de Categorias:

```java
@Test
public void testeListar() {
    Categoria categoria1 = new Categoria();
    categoria1.setTitulo("teste");
    categoria1.setCor("preto");

    Categoria categoria2 = new Categoria();
    categoria2.setTitulo("Comédia");
    categoria2.setCor("amarelo");

    List<Categoria> listaCategorias = List.of(categoria1, categoria2);

    when(categoriaRepository.findAll()).thenReturn(listaCategorias);

    ResponseEntity<List<Categoria>> response = categoriaController.ListarTodos();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(listaCategorias, response.getBody());
    verify(categoriaRepository, times(1)).findAll();
}
```

### Exemplo de Teste de Criação de Vídeo:

```java
@Test
public void testeCadastrar() {
    DadosVideos dadosVideo = new DadosVideos(3L, "Um maluco no pedaço", "Série de comédia", "https://www.url.com.br/", 1);
    Categoria categoriaMock = new Categoria();
    when(categoriaRepository.findById(dadosVideo.categoriaId())).thenReturn(Optional.of(categoriaMock));

    Videos videoMock = new Videos(dadosVideo, categoriaMock);
    when(videosRepository.save(any(Videos.class))).thenReturn(videoMock);

    ResponseEntity<Videos> response = videosController.crate(dadosVideo);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(videoMock, response.getBody());
    verify(videosRepository, times(1)).save(any(Videos.class));
}
```

## Testando API com Postman

Para testar sua API com o **Postman**, siga os passos abaixo:

### 1. **Instalar o Postman**

Se você ainda não tem o Postman, faça o download e instale a ferramenta [aqui](https://www.postman.com/downloads/).

### 2. **Testando Endpoints**

#### Testando Criação de Categoria

1. **Abra o Postman**.
2. **Crie uma nova requisição** com o método `POST` e a URL `http://localhost:8080/categorias`.
3. No corpo da requisição (body), selecione o tipo **raw** e **JSON** e adicione os dados da categoria:
   ```json
   {
       "titulo": "Ação",
       "cor": "vermelho"
   }
   ```
4. Clique em **Send**.
5. A resposta esperada será um código HTTP `201 Created`, com os dados da categoria criada.

#### Testando Listagem de Categorias

1. **Crie uma nova requisição** com o método `GET` e a URL `http://localhost:8080/categorias`.
2. Clique em **Send**.
3. A resposta deve ser uma lista de categorias, no formato JSON.

#### Testando Login para Obter JWT

Para testar endpoints protegidos por JWT, você precisa primeiro gerar um token:

1. **Crie uma nova requisição** com o método `POST` e a URL `http://localhost:8080/auth/login`.
2. No corpo da requisição, envie o e-mail e senha:
   ```json
   {
       "email": "usuario@exemplo.com",
       "senha": "minhasenha"
   }
   ```
3. Clique em **Send**.
4. Você receberá um JSON com o token JWT:
   ```json
   {
       "token": "<JWT_Token>"
   }
   ```

#### Testando Endpoint Protegido

1. **Crie uma nova requisição** com o método `GET` e a URL para um endpoint protegido (ex: `http://localhost:8080/categorias`).
2. No cabeçalho (header) da requisição, adicione o token JWT gerado:
   - Key: `Authorization`
   - Value: `Bearer <JWT_Token>`
3. Clique em **Send** para testar o endpoint protegido.
4. A resposta esperada será a lista de categorias, ou o recurso correspondente, se o JWT for válido.

### 3. **Automatizando Testes com Newman**

O **Newman** é a versão de linha de comando do Postman, que permite rodar suas coleções de testes em ambientes automatizados.

1. **Instalar o Newman**:
   - Primeiro, instale o Newman via **npm**:
     ```bash
     npm install -g newman
     ```
2. **Exportar sua Coleção do Post

man**:
   - No Postman, clique em **File > Export**, escolha sua coleção e salve.
3. **Executar os Testes com Newman**:
   - Execute sua coleção com o seguinte comando:
     ```bash
     newman run <caminho-para-sua-colecao.postman_collection.json>
     ```

## Execução dos Testes

Para rodar os testes unitários da aplicação, basta executar o comando:

```bash
mvn test
```

Isso irá executar todos os testes do projeto e exibir os resultados no terminal.

## Conclusão

Este projeto oferece uma cobertura de testes sólida para as principais funcionalidades da API, utilizando **JUnit 5** para testes unitários e **Mockito** para mocks de dependências. Ele garante que o sistema esteja funcionando corretamente e que as operações de CRUD estejam adequadamente implementadas e validadas.

Além disso, você pode facilmente testar a API com o **Postman** e automatizar os testes com **Newman**, além de realizar requisições para obter o **JWT** necessário para autenticação em endpoints protegidos.
``` 

Agora, seu arquivo inclui informações sobre como testar a API usando o **Postman** e obter o **JWT** necessário para autenticação, além de exemplos de como realizar requisições e rodar os testes unitários!
