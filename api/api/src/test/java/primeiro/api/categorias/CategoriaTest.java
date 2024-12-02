package primeiro.api.categorias;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import primeiro.api.categoria.Categoria;
import primeiro.api.categoria.DadosCategoria;
import primeiro.api.videos.Videos;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CategoriaTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValida() {
        Categoria categoria = new Categoria();
        Videos video = new Videos();

        categoria.setId(1L);
        categoria.setTitulo("Teste Categoria");
        categoria.setCor("Teset Cor");

        Assertions.assertEquals(1l, categoria.getId());
        Assertions.assertEquals("Teste Categoria", categoria.getTitulo());
        Assertions.assertEquals("Teset Cor", categoria.getCor());


    }

    @Test
    public void testCategoriaValidation() {
        Categoria categoria = new Categoria();

        categoria.setId(1L);
        categoria.setTitulo(null); // Título nulo para testar a validação
        categoria.setCor(null); // Cor nula para testar a validação

        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);
        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size()); // Verifique o número de violações
    }
    @Test
    public void testDadosCategoriaMapping() {
        DadosCategoria dadosCategoria = new DadosCategoria("Drama", "#00FF00");
        Categoria categoria = new Categoria(dadosCategoria);

        assertEquals("Drama", categoria.getTitulo());
        assertEquals("#00FF00", categoria.getCor());
    }

}
