package com.md.voll.api.domain.medico;

import com.md.voll.api.domain.endereco.DadosEndereco;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(
    @NotNull
    Long id,
    String nome,
    String telefone, 
    @Valid DadosEndereco endereco) {
    
}
