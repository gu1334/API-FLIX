package primeiro.api.testeantigovideos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import primeiro.api.categoria.Categoria;
import primeiro.api.videos.Videos;

import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class VideoTest {

//    private Validator validator;
//
//    @BeforeEach
//    public void setUp() {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//    }
//
//    @Test
//    public void testValida() {
//        // Criando dados do vídeo e categoria
//        Videos video = new Videos();
//        Categoria categoria = new Categoria();
//
//        video.setTitulo("Filme de teste 1");
//        video.setDescricao("este filme serve para testar");
//        video.setUrl("https://www.google.com.br");
//
//        categoria.setId(1L);
//        video.setCategoria(categoria);
//
//        // Verificação de atributos do vídeo
//        Assertions.assertEquals(categoria, video.getCategoria());
//        Assertions.assertEquals("Filme de teste 1", video.getTitulo());
//        Assertions.assertEquals("https://www.google.com.br", video.getUrl());
//        Assertions.assertEquals("este filme serve para testar", video.getDescricao());
//    }
//
//    @Test
//    public void testInvalida() {
//        // Criando dados de vídeo inválido (campos nulos)
//        Videos video = new Videos();
//        Categoria categoria = new Categoria();
//        categoria.setId(1L);
//
//        video.setCategoria(categoria);
//        video.setTitulo(null);
//        video.setDescricao(null);
//        video.setUrl(null);
//
//        // Validação do vídeo
//        Set<ConstraintViolation<Videos>> violations = validator.validate(video);
//        assertFalse(violations.isEmpty(), "Deveria haver falhas de validação.");
//        assertEquals(3, violations.size(), "Esperado 3 erros de validação (título, descrição e URL).");
//
//        // Verificando as mensagens de erro
//        violations.forEach(violation -> {
//            assertTrue(violation.getMessage().contains("não pode estar vazio"), "Mensagem de erro inválida.");
//        });
//    }
}
