package primeiro.api.categorias;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import primeiro.api.categoria.Categoria;
import primeiro.api.categoria.DadosCategoria;
import primeiro.api.categoria.controller.CategoriaController;
import primeiro.api.categoria.repository.CategoriaRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaControllerTest {
    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaController categoriaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testeCadastrar(){

        DadosCategoria dadosCategoria = new DadosCategoria("terror","preto");
        Categoria categoriaMock = new Categoria();
        categoriaMock.setTitulo("terror");
        categoriaMock.setCor("preto");

        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaMock);

        ResponseEntity<Categoria> response = categoriaController.create(dadosCategoria);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoriaMock, response.getBody());
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
    public void testeListarPorID(){

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
    public void testeShowVideosByCategory(){

        Categoria categoria1 = new Categoria();
        categoria1.setId(20L);


    }





}
