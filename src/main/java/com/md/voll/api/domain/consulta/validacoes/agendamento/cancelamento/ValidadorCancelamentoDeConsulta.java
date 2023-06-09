package com.md.voll.api.domain.consulta.validacoes.agendamento.cancelamento;

import com.md.voll.api.domain.consulta.DadosCancelamentoConsulta;

public interface ValidadorCancelamentoDeConsulta {
    void cancelar(DadosCancelamentoConsulta dados);
}
