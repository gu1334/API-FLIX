package primeiro.api.categorias;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import primeiro.api.categoria.Categoria;
import primeiro.api.categoria.DadosCategoria;
import primeiro.api.categoria.controller.CategoriaController;
import primeiro.api.categoria.repository.CategoriaRepository;
import primeiro.api.infra.exceptions.RecursoNaoEncontradoException;
import primeiro.api.videos.Videos;
import primeiro.api.videos.repository.VideosRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaControllerTest {
    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private VideosRepository videosRepository;

    @InjectMocks
    private CategoriaController categoriaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

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

    //simular erro no banco de dados
    @Test
    public void testeCadastrarErro(){
        DadosCategoria dadosCategoria = new DadosCategoria("titulo", "preto");
        when(categoriaRepository.save(any(Categoria.class))).thenThrow(new RuntimeException("Erro ao salvar a categoria"));

        ResponseEntity<Categoria> response = categoriaController.create(dadosCategoria);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(categoriaRepository, times(1)).save(any(Categoria.class));

    }

    //Simular erro com dados errados
    @Test
    public void testeCadastrarErroValidacao(){
        DadosCategoria dadosCategoria = new DadosCategoria("", "preto");

        when(categoriaRepository.save(any(Categoria.class))).thenThrow(new IllegalArgumentException("Título inválido"));

        ResponseEntity<Categoria> response = categoriaController.create(dadosCategoria);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(categoriaRepository, times(1)).save(any(Categoria.class));

    }

    @Test
    public void testeListar() {
        // Mock da lista de categorias que o repositório deve retornar
        Categoria categoria1 = new Categoria();
        categoria1.setTitulo("teste");
        categoria1.setCor("preto");

        Categoria categoria2 = new Categoria();
        categoria2.setTitulo("Comédia");
        categoria2.setCor("amarelo");

        List<Categoria> listaCategorias = List.of(categoria1, categoria2);

        // Mock do repositório para retornar a lista de categorias
        when(categoriaRepository.findAll()).thenReturn(listaCategorias);

        // Executa o método listar
        ResponseEntity<List<Categoria>> response = categoriaController.ListarTodos();

        // Verifica se o status HTTP é OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica se a lista retornada é a esperada
        assertEquals(listaCategorias, response.getBody());

        // Verifica se o método findAll foi chamado uma vez
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    public void testeListarErro(){

        when(categoriaRepository.findAll()).thenThrow(new RuntimeException("Erro ao listar"));
        ResponseEntity<List<Categoria>> response = categoriaController.ListarTodos();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(categoriaRepository, times(1)).findAll();

    }

    @Test
    public void testeListarPorID() {

        Categoria categoria1 = new Categoria();
        categoria1.setId(20L);
        categoria1.setTitulo("Buscar por ID teste");
        categoria1.setCor("teste cor");

        //Defino o comportamento do repositorio
        when(categoriaRepository.findById(20L)).thenReturn(Optional.of(categoria1));

        ResponseEntity<Categoria> response = categoriaController.encontrarPeloID(categoria1.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(categoria1, response.getBody());

        verify(categoriaRepository, times(1)).findById(categoria1.getId());
    }

    @Test
    public void testeListarPorIDErro(){
        when(categoriaRepository.findById(20L)).thenReturn(Optional.empty());
        ResponseEntity<Categoria> response = categoriaController.encontrarPeloID(20l);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(categoriaRepository, times(1)).findById(20L);
    }

    @Test
    public void testeShowVideosByCategory() {

        Categoria categoria1 = new Categoria();
        categoria1.setId(20L);

        Videos videos1 = new Videos(1L, "teste", "teste", "https://www.google.com.br/videos/", categoria1);

        List<Videos> listaVideos1 = List.of(videos1);
        when(categoriaRepository.findById(categoria1.getId())).thenReturn(Optional.of(categoria1));
        when(videosRepository.findByCategoria(categoria1)).thenReturn(listaVideos1);

        ResponseEntity<List<Videos>> response = categoriaController.showVideosByCategory(categoria1.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listaVideos1, response.getBody());
        verify(videosRepository, times(1)).findByCategoria(categoria1);
    }

    @Test
    public void testeShowVideosByCategoryErro(){
        Categoria categoria1 = new Categoria();
        categoria1.setId(null);

        Videos videos1 = new Videos(1L, "teste", "teste", "https://www.google.com.br/videos/", categoria1);

        List<Videos> listaVideos1 = List.of(videos1);
        when(categoriaRepository.findById(20L)).thenReturn(Optional.empty());
        when(videosRepository.findByCategoria(categoria1)).thenReturn(listaVideos1);
        ResponseEntity<List<Videos>> response = categoriaController.showVideosByCategory(30L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(categoriaRepository, times(1)).findById(20L);





    }

    @Test
    public void testeShowCategoryByTitulo(){

        Categoria categoria1 = new Categoria();
        categoria1.setTitulo("Filme");
        categoria1.setCor("preto");

        List<Categoria> listaCategorias = List.of(categoria1);

        when(categoriaRepository.findByTitulo("Filme")).thenReturn(listaCategorias);
        ResponseEntity<List<Categoria>> response = categoriaController.showCategoryByTitulo("Filme");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listaCategorias, response.getBody());
        verify(categoriaRepository, times(1)).findByTitulo("Filme");

    }

    @Test
    public void testeShowCategoryByTituloErro(){
        Categoria categoria1 = new Categoria();
        categoria1.setTitulo("Filme");
        categoria1.setCor("preto");

        List<Categoria> listaCategorias = List.of(categoria1);


        when(categoriaRepository.findByTitulo("filme")).thenReturn(listaCategorias);
        ResponseEntity<List<Categoria>> response = categoriaController.showCategoryByTitulo("banana");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listaCategorias, response.getBody());
        verify(categoriaRepository, times(1)).findByTitulo("banana");

    }

    @Test
    public void testeDelete() {

        // Cria a categoria
        Categoria categoria1 = new Categoria();
        categoria1.setId(20L);
        categoria1.setTitulo("Deletar por ID teste");
        categoria1.setCor("teste cor");

        // Simula que a categoria existe no banco de dados
        when(categoriaRepository.findById(20L)).thenReturn(Optional.of(categoria1));

        // Chama o método delete do controlador
        ResponseEntity<Categoria> response = categoriaController.delete(categoria1.getId());

        // Verifica se o status HTTP é NO_CONTENT (204)
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verifica que o corpo da resposta está vazio (não há conteúdo)
        assertNull(response.getBody());

        // Verifica se o método findById foi chamado uma vez
        verify(categoriaRepository, times(1)).findById(categoria1.getId());

        // Verifica se o método delete foi chamado uma vez
        verify(categoriaRepository, times(1)).delete(categoria1);
    }


    @Test
    public void testeDeleteCategoriaNaoEncontrada() {

        // Simula que a categoria não existe no banco de dados
        when(categoriaRepository.findById(20L)).thenReturn(Optional.empty());

        // Chama o método delete do controlador
        ResponseEntity<Categoria> response = categoriaController.delete(20L);

        // Verifica se o status HTTP é NOT_FOUND (404)
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        // Verifica que o corpo da resposta está vazio (não é possível deletar categoria não encontrada)
        assertNull(response.getBody());

        // Verifica se o método findById foi chamado uma vez
        verify(categoriaRepository, times(1)).findById(20L);

        // Verifica que o método delete não foi chamado, pois a categoria não foi encontrada
        verify(categoriaRepository, times(0)).delete(any());
    }



    @Test
    public void testeUpdate(){

        // Cria a categoria
        Categoria categoria1 = new Categoria();
        categoria1.setTitulo("Deletar por ID teste");
        categoria1.setCor("teste cor");

        DadosCategoria dadosCategoria = new DadosCategoria("update","update");

        // Simula que a categoria existe no banco de dados
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria1));

        // Mock do repositório para salvar a categoria atualizada
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria1);

        // Chama o método delete do controlador
        ResponseEntity<Categoria> response = categoriaController.update(1L, dadosCategoria);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica se o método findById foi chamado uma vez
        verify(categoriaRepository, times(1)).save(any(Categoria.class));

    }

    @Test
    public void testeUpdateErro(){

        // Mock do repositório para não encontrar a categoria
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        // Dados da categoria
        DadosCategoria dadosCategoria = new DadosCategoria("Ação Atualizada", "#00FF00");

        // Verifica se a exceção é lançada
        assertThrows(RecursoNaoEncontradoException.class, () -> {
            categoriaController.update(1L, dadosCategoria);
        });
    }


}
