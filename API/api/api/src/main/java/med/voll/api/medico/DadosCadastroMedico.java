package med.voll.api.medico;

import med.voll.api.endereco.Dadosendereco;

public record DadosCadastroMedico(String nome, String email, String crm, Especialidade especialidade, Dadosendereco endereco ) {
}
