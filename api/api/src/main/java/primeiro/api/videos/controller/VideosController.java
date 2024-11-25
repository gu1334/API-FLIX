package primeiro.api.videos.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import primeiro.api.categoria.Categoria;
import primeiro.api.categoria.repository.CategoriaRepository;
import primeiro.api.exceptions.RecursoNaoEncontradoException;
import primeiro.api.videos.DadosVideos;
import primeiro.api.videos.Videos;
import primeiro.api.videos.repository.VideosRepository;
import jakarta.validation.Valid;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videos")
public class VideosController {

    @Autowired
    VideosRepository videosRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @PostMapping
    public ResponseEntity<Videos> crate(@RequestBody @Valid DadosVideos dados) {
        if (dados.categoriaId() == null){
            throw new IllegalArgumentException("O ID da categoria não pode ser nulo.");
        }

        Categoria categoria = categoriaRepository.findById(dados.categoriaId())
                .orElseGet(() -> categoriaRepository.findById(1L)
                        .orElseThrow(() -> new EntityNotFoundException("Categoria padrão não encontrada com ID 1")));

        Videos videos = new Videos(dados, categoria);
        videosRepository.save(videos);
        return ResponseEntity.status(HttpStatus.CREATED).body(videos);
    }

    @GetMapping
    public ResponseEntity<List<Videos>> list() {
        List<Videos> videos = videosRepository.findAll();
        return ResponseEntity.ok(videos);

    }

    @GetMapping("{id}")
    public ResponseEntity<Videos> findByid(@PathVariable Long id) {
        Optional<Videos> video = videosRepository.findById(id);
        if (video.isPresent()) {
            return ResponseEntity.ok(video.get());
        } else {

            throw new RecursoNaoEncontradoException("Vídeo com ID " + id + " não foi encontrado.");

        }

    }

    //Achar videos por titulo
    @GetMapping("/search")
    public ResponseEntity<List<Videos>> showVideosByTitulo(@RequestParam(required = false) String titulo) {
        List<Videos> videos = (titulo != null && !titulo.isEmpty()) ?
                videosRepository.findByTitulo(titulo) :
                videosRepository.findAll();

        if (videos.isEmpty()) {
            throw new RecursoNaoEncontradoException("Titulo " + titulo + " não encontrado.");
        }

        return ResponseEntity.ok(videos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Videos> delete(@PathVariable Long id) {
        Optional<Videos> video = videosRepository.findById(id);
        if (video.isPresent()) {
            videosRepository.deleteById(id);
            return ResponseEntity.ok(video.get());
        } else {
            throw new RecursoNaoEncontradoException("Vídeo com ID " + id + " não foi encontrado.");
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Videos> change(@PathVariable Long id, @RequestBody @Valid DadosVideos dados) {
        Optional<Videos> video = videosRepository.findById(id);

        if (video.isPresent()) {

            Videos videoAtual = video.get();

            videoAtual.setTitulo(dados.titulo());
            videoAtual.setDescricao(dados.descricao());
            videoAtual.setUrl(dados.url());

            Videos videoAtualizado = videosRepository.save(videoAtual);
            return ResponseEntity.ok(videoAtualizado);


        }else {
            throw new RecursoNaoEncontradoException("Vídeo com ID " + id + " não foi encontrado.");
        }
    }


}
