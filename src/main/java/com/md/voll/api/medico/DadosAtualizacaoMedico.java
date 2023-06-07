package com.md.voll.api.medico;

import com.md.voll.api.endereco.DadosEndereco;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMedico(
    @NotNull
    Long id,
    String nome,
    String telefone, 
    @Valid DadosEndereco endereco) {
    
}
