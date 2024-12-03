package primeiro.api.testeantigocategorias;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import primeiro.api.categoria.Categoria;
import primeiro.api.categoria.DadosCategoria;
import primeiro.api.videos.Videos;

import java.util.Set;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CategoriaTest {

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
//        Categoria categoria = new Categoria();
//        Videos video = new Videos();
//
//        categoria.setId(1L);
//        categoria.setTitulo("Teste Categoria");
//        categoria.setCor("Teste Cor");
//
//        // Verificação de atributos da categoria
//        Assertions.assertEquals(1L, categoria.getId());
//        Assertions.assertEquals("Teste Categoria", categoria.getTitulo());
//        Assertions.assertEquals("Teste Cor", categoria.getCor());
//    }
//
//    @Test
//    public void testCategoriaValidation() {
//        Categoria categoria = new Categoria();
//
//        categoria.setId(1L);
//        categoria.setTitulo(null); // Título nulo para testar a validação
//        categoria.setCor(null); // Cor nula para testar a validação
//
//        // Validação
//        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);
//        assertFalse(violations.isEmpty(), "Deveria haver falhas de validação.");
//        assertEquals(2, violations.size(), "Esperado 2 erros de validação (título e cor).");
//
//        // Verificando mensagens de erro
//        violations.forEach(violation -> {
//            assertTrue(violation.getMessage().contains("não pode estar vazio"), "Mensagem de erro inválida.");
//        });
//    }
//
//    @Test
//    public void testDadosCategoriaMapping() {
//        DadosCategoria dadosCategoria = new DadosCategoria("Drama", "#00FF00");
//        Categoria categoria = new Categoria(dadosCategoria);
//
//        // Verificando mapeamento correto dos dados para a categoria
//        assertEquals("Drama", categoria.getTitulo(), "O título da categoria deve ser 'Drama'");
//        assertEquals("#00FF00", categoria.getCor(), "A cor da categoria deve ser '#00FF00'");
//    }
}
