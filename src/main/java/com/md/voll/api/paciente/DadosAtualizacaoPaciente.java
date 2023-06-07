package com.md.voll.api.paciente;

import com.md.voll.api.endereco.DadosEndereco;

import jakarta.validation.Valid;

public record DadosAtualizacaoPaciente(
    Long id,
    String nome,
    String telefone,
    @Valid DadosEndereco endereco
) {
}
