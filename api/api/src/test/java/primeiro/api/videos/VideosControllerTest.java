package primeiro.api.videos;

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
import primeiro.api.videos.controller.VideosController;
import primeiro.api.videos.repository.VideosRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class VideosControllerTest {


    @Mock
    private VideosRepository videosRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private VideosController videosController;

    @InjectMocks
    private CategoriaController categoriaController;

    @Test
    public void testeCadastrar() {
        DadosVideos dadosVideo = new DadosVideos(3L, "Um maluco no pedaço", "Série de comédia", "https://www.url.com.br/");
        Categoria categoriaMock = new Categoria();
        when(categoriaRepository.findById(dadosVideo.categoriaId())).thenReturn(Optional.of(categoriaMock));
        Videos videoMock = new Videos(dadosVideo, categoriaMock);
        when(videosRepository.save(any(Videos.class))).thenReturn(videoMock);

        ResponseEntity<Videos> response = videosController.crate(dadosVideo);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(videoMock, response.getBody());
        verify(videosRepository, times(1)).save(any(Videos.class));
    }

    @Test
    public void testeCadastrarErro(){
        DadosVideos dadosVideos = new DadosVideos(1L,"Erro", "teste", "https://www.url.com.br/");
        when(videosRepository.save(any(Videos.class))).thenThrow(new RuntimeException("Erro ao salvar a categoria"));

        ResponseEntity<Videos> response = videosController.crate(dadosVideos);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(videosRepository, times(1)).save(any(Videos.class));
    }
    @Test
    public void testeCadastrarErroValidacao(){
        DadosVideos dadosVideos = new DadosVideos(null,"", "teste", "https://www.url.com.br/");
        when(videosRepository.save(any(Videos.class))).thenThrow(new RuntimeException("Erro ao salvar a videos"));

        ResponseEntity<Videos> response = videosController.crate(dadosVideos);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(videosRepository, times(1)).save(any(Videos.class));
    }

    @Test
    public void testeListar(){

        Videos video1 = new Videos();
        video1.setTitulo("Video 1");
        video1.setDescricao("Video 1");
        video1.setUrl("https://www.google.com.br/videos/");

        Videos video2 = new Videos();
        video2.setTitulo("Video 2");
        video2.setDescricao("Video 2");
        video2.setUrl("https://www.google.com.br/videos/");

        List<Videos> listeVideos = List.of(video1, video2);

        when(videosRepository.findAll()).thenReturn(listeVideos);
        ResponseEntity<List<Videos>> response = videosController.list();
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica se a lista retornada é a esperada
        assertEquals(listeVideos, response.getBody());

        // Verifica se o método findAll foi chamado uma vez
        verify(videosRepository, times(1)).findAll();

    }

    @Test
    public void testeListarErro(){

        when(videosRepository.findAll()).thenThrow(new RuntimeException("Erro ao listar"));
        ResponseEntity<List<Videos>> response = videosController.list();
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(videosRepository, times(1)).findAll();

    }

    @Test
    public void testeListarPorID(){

         Videos video1 = new Videos();
         video1.setId(20L);
         video1.setTitulo("Video 1");
         video1.setDescricao("Video 1");
         video1.setUrl("https://www.google.com.br/videos/");

         when(videosRepository.findById(video1.getId())).thenReturn(Optional.of(video1));

        ResponseEntity<Videos> response = videosController.findByid(video1.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(video1, response.getBody());
        verify(videosRepository, times(1)).findById(video1.getId());

    }

    @Test
    public void testeListarPorIDErro(){
        when(videosRepository.findById(20L)).thenReturn(Optional.empty());
        ResponseEntity<Videos> response = videosController.findByid(20L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(videosRepository, times(1)).findById(20L);
    }

    @Test
    public void testeShowCategoryByTitulo(){

        Categoria categoriaMock = new Categoria();
        categoriaMock.setId(20L);

        Videos video1 = new Videos(1L, "Título do vídeo 1", "Descrição do vídeo 1", "https://www.url.com.br/", categoriaMock);

        List<Videos> listeVideos = List.of(video1);
        when(videosRepository.findByTitulo("Título do vídeo 1")).thenReturn(listeVideos);
        ResponseEntity<List<Videos>> response = videosController.showVideosByTitulo("Título do vídeo 1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listeVideos, response.getBody());
        verify(videosRepository, times(1)).findByTitulo("Título do vídeo 1");


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




}
