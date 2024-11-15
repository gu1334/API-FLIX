package med.voll.api.paciente;

import med.voll.api.endereco.Dadosendereco;

public record DadosCadastroPaciente(String nome, String email, Plano plano, Dadosendereco endereco) {
}
