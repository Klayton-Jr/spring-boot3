package com.md.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.md.voll.api.domain.ValidacaoException;
import com.md.voll.api.domain.consulta.DadosAgendamentoConsulta;
import com.md.voll.api.domain.medico.MedicoRepository;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsulta {

    @Autowired
    private MedicoRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() == null)
            return;
        
        var medicoEstaAtivo = repository.findAtivoById(dados.idMedico());
        if (!medicoEstaAtivo)
            throw new ValidacaoException("Consulta nao pode ser agendada com médico");
    }
}
