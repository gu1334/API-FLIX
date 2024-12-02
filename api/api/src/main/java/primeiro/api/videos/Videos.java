package primeiro.api.videos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import primeiro.api.categoria.Categoria;

@Table(name = "videos")
@Entity(name = "videos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Videos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String url;
    private int paymente;



    @ManyToOne
    @JoinColumn(name = "categoria")
    private Categoria categoria;

    public Videos(DadosVideos dados, Categoria categoria) {
        this.titulo = dados.titulo();
        this.descricao = dados.descricao();
        this.url = dados.url();
        this.paymente = dados.paymente();
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getPaymente() {
        return paymente;
    }

    public void setPaymente(int paymente) {
        this.paymente = paymente;
    }
}
