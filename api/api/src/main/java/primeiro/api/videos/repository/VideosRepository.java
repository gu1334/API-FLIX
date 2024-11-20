package primeiro.api.videos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import primeiro.api.categoria.Categoria;
import primeiro.api.videos.Videos;

import java.util.List;


public interface VideosRepository extends JpaRepository<Videos, Long> {

    List<Videos> findByCategoria(Categoria categoria);

    List<Videos> findByTitulo(String titulo);

}
