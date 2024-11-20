package primeiro.api.videos;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import primeiro.api.categoria.Categoria;

import java.util.Set;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class VideoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    public void testValida() {
        Videos video = new Videos();
        Categoria categoria = new Categoria();

        video.setTitulo("Filme de teste 1");
        video.setDescricao("este filme serve para testar");
        video.setUrl("https://www.google.com.br");

        categoria.setId(1L);
        video.setCategoria(categoria);

        Assertions.assertEquals(categoria, video.getCategoria());
        Assertions.assertEquals("Filme de teste 1", video.getTitulo());
        Assertions.assertEquals("https://www.google.com.br", video.getUrl());
        Assertions.assertEquals("este filme serve para testar", video.getDescricao());


    }


    @Test
    public void testInvalida() {
    Videos video = new Videos();
    Categoria categoria = new Categoria();
    categoria.setId(1L);

    video.setCategoria(categoria);
    video.setTitulo(null);
    video.setDescricao(null);
    video.setUrl(null);

    Set<ConstraintViolation<Videos>> violations = validator.validate(video);
    Assertions.assertFalse(violations.isEmpty());
    Assertions.assertEquals(3, violations.size());
    }

}
