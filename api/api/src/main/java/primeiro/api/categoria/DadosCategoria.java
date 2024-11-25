package primeiro.api.categoria;

import jakarta.validation.constraints.NotBlank;

public record DadosCategoria(
        @NotBlank(message = " O Título é obrigatorio.")
        String titulo,
        @NotBlank(message = "Cor não pode ser vazia")
        String cor
) {}
