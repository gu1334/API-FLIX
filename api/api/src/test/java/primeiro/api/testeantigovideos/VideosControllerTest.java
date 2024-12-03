package primeiro.api.testeantigovideos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import primeiro.api.categoria.Categoria;
import primeiro.api.categoria.DadosCategoria;
import primeiro.api.categoria.controller.CategoriaController;
import primeiro.api.categoria.repository.CategoriaRepository;
import primeiro.api.videos.DadosVideos;
import primeiro.api.videos.Videos;
import primeiro.api.videos.controller.VideosController;
import primeiro.api.videos.repository.VideosRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VideosControllerTest {
//
//    @Mock
//    private VideosRepository videosRepository;
//
//    @Mock
//    private CategoriaRepository categoriaRepository;
//
//    @InjectMocks
//    private VideosController videosController;
//
//    @InjectMocks
//    private CategoriaController categoriaController;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testeCadastrar() {
//        // Criando dados do vídeo
//        DadosVideos dadosVideo = new DadosVideos(3L, "Um maluco no pedaço", "Série de comédia", "https://www.url.com.br/",1);
//
//        // Mock da categoria
//        Categoria categoriaMock = new Categoria();
//        when(categoriaRepository.findById(dadosVideo.categoriaId())).thenReturn(Optional.of(categoriaMock));
//
//        // Criando mock de vídeo
//        Videos videoMock = new Videos(dadosVideo, categoriaMock);
//        when(videosRepository.save(any(Videos.class))).thenReturn(videoMock);
//
//        // Teste de criação do vídeo
//        ResponseEntity<Videos> response = videosController.crate(dadosVideo);
//        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Deve retornar status 201 para criação com sucesso.");
//        assertEquals(videoMock, response.getBody(), "O vídeo retornado deve ser o mockado.");
//
//        // Verifica se o método save foi chamado
//        verify(videosRepository, times(1)).save(any(Videos.class));
//    }
//
//    @Test
//    public void testeListar() {
//        // Criando lista de vídeos mockados
//        Videos video1 = new Videos("Video 1", "Descrição do Video 1", "https://www.url.com.br/video1",1);
//        Videos video2 = new Videos("Video 2", "Descrição do Video 2", "https://www.url.com.br/video2",1);
//
//        List<Videos> listaVideos = List.of(video1, video2);
//
//        // Mock do retorno do repositório
//        when(videosRepository.findAll()).thenReturn(listaVideos);
//
//        // Teste de listagem de vídeos
//        ResponseEntity<List<Videos>> response = videosController.list();
//        assertEquals(HttpStatus.OK, response.getStatusCode(), "Deve retornar status 200 para listagem de vídeos.");
//        assertEquals(listaVideos, response.getBody(), "A lista de vídeos retornada deve ser a esperada.");
//
//        // Verifica se o método findAll foi chamado
//        verify(videosRepository, times(1)).findAll();
//    }
//
//    @Test
//    public void testeListarErro() {
//        // Simulando uma falha no repositório
//        when(videosRepository.findAll()).thenThrow(new RuntimeException("Erro ao listar vídeos"));
//
//        // Teste de erro na listagem de vídeos
//        ResponseEntity<List<Videos>> response = videosController.list();
//        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Deve retornar status 400 em caso de erro no repositório.");
//
//        // Verifica se o método findAll foi chamado
//        verify(videosRepository, times(1)).findAll();
//    }
}
