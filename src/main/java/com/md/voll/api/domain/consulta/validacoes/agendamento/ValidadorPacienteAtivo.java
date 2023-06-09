package com.md.voll.api.domain.consulta.validacoes.agendamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.md.voll.api.domain.ValidacaoException;
import com.md.voll.api.domain.consulta.DadosAgendamentoConsulta;
import com.md.voll.api.domain.paciente.PacienteRepository;

@Component
public class ValidadorPacienteAtivo implements ValidadorAgendamentoConsulta {

    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsulta dados) {
        
        var pacienteEstaAtivo = repository.findAtivoById(dados.idMedico());
        if (!pacienteEstaAtivo)
            throw new ValidacaoException("Consulta nao pode ser agendada com paciente excluído");
    }
}
