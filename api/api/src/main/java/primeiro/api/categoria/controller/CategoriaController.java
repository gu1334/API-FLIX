package primeiro.api.categoria.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import primeiro.api.categoria.Categoria;
import primeiro.api.categoria.DadosCategoria;
import primeiro.api.categoria.repository.CategoriaRepository;
import primeiro.api.infra.exceptions.RecursoNaoEncontradoException;
import primeiro.api.videos.Videos;
import primeiro.api.videos.repository.VideosRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
@SecurityRequirement(name = "bearer-key")
public class CategoriaController {

    @Autowired
    CategoriaRepository categoriaRepository;
    @Autowired
    private VideosRepository videosRepository;

    @PostMapping
    public ResponseEntity<Categoria> create(@RequestBody @Valid DadosCategoria dados) {

        Categoria categoria = new Categoria(dados);

        categoriaRepository.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);

    }

    @GetMapping
    public ResponseEntity<List<Categoria>> ListarTodos() {
        List<Categoria> categorias = categoriaRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(categorias);
    }

    @GetMapping("/page")
    public  ResponseEntity<?> findByPage(@PageableDefault(size = 5) Pageable pageable){
        try {
            Page<Categoria> categorias = categoriaRepository.findAll(pageable);
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Categoria> encontrarPeloID(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(categoria.get());
        } else {
            throw new RecursoNaoEncontradoException("Categoria com ID " + id + " não foi encontrado.");
        }
    }

    // Mostrar todos os videos dentro da categoria

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Videos>> showVideosByCategory(@PathVariable Long categoriaId) {
        Optional<Categoria> categoria = categoriaRepository.findById(categoriaId);
        if (categoria.isPresent()) {
            List<Videos> videos = videosRepository.findByCategoria(categoria.get());
            return ResponseEntity.status(HttpStatus.OK).body(videos);
        }else {
            throw new RecursoNaoEncontradoException("Video com categoria de ID " + categoriaId + " não foi encontrado.");
        }
    }

    @GetMapping("/search")
    public ResponseEntity <List<Categoria>> showCategoryByTitulo(@RequestParam(required = false)String titulo) {
        List<Categoria> categorias = (titulo != null && !titulo.isEmpty()) ?
                categoriaRepository.findByTitulo(titulo):
                categoriaRepository.findAll();
        if (categorias.isEmpty()) {
            throw new RecursoNaoEncontradoException("Titulo: " + titulo + " não encontrado.");
        }
        return ResponseEntity.ok(categorias);
    }




    @DeleteMapping("{id}")
    public ResponseEntity<Categoria> delete(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            categoriaRepository.delete(categoria.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            throw new RecursoNaoEncontradoException("Categoria com ID " + id + " não foi encontrado.");

        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody @Valid DadosCategoria dados) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            Categoria categoriaAtual = categoria.get();

            categoriaAtual.setTitulo(dados.titulo());
            categoriaAtual.setCor(dados.cor());

            Categoria categoriaNovo = categoriaRepository.save(categoriaAtual);

            return ResponseEntity.status(HttpStatus.OK).body(categoriaNovo);

        } else {
            throw new RecursoNaoEncontradoException("Categoria com ID " + id + " não foi encontrado.");

        }
    }

}

