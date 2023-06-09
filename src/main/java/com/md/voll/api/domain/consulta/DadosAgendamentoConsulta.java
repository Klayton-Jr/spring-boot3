package com.md.voll.api.domain.consulta;

import java.time.LocalDateTime;

import com.md.voll.api.domain.medico.EspecialidadeEnum;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record DadosAgendamentoConsulta(
        Long idMedico,
        @NotNull
        Long idPaciente,
        @NotNull
        @Future
        LocalDateTime data,
        EspecialidadeEnum especialidade) {
    
}
