package com.md.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.md.voll.api.domain.ValidacaoException;
import com.md.voll.api.domain.consulta.ConsultaRepository;
import com.md.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorMedicoComOutraConsultaNoMesmoHorario implements ValidadorAgendamentoConsulta {
    
    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        var medidoPossuiOutraConsultaNoMesmoHorario = repository.existsByMedicoIdAndDataAndMotivoCancelamentoIsNull(dados.idMedico(), dados.data());
        if (medidoPossuiOutraConsultaNoMesmoHorario)
            throw new ValidacaoException("Médico já possui outra consulta agendada nesse mesmo horário");
    }
}
