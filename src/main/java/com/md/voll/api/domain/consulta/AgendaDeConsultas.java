package com.md.voll.api.domain.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.md.voll.api.domain.ValidacaoException;
import com.md.voll.api.domain.consulta.validacoes.agendamento.ValidadorAgendamentoConsulta;
import com.md.voll.api.domain.consulta.validacoes.agendamento.cancelamento.ValidadorCancelamentoDeConsulta;
import com.md.voll.api.domain.medico.Medico;
import com.md.voll.api.domain.medico.MedicoRepository;
import com.md.voll.api.domain.paciente.PacienteRepository;

@Service
public class AgendaDeConsultas {
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private MedicoRepository medicoRepository;
    @Autowired
    private List<ValidadorAgendamentoConsulta> validadores;
    @Autowired
    private List<ValidadorCancelamentoDeConsulta> validadoresCancelamento;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        if (!pacienteRepository.existsById(dados.idPaciente()))
            throw new ValidacaoException("Id do paciente não existe!");

        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico()))
            throw new ValidacaoException("Id do medico não existe!");

        validadores.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());

        var medico = escolherMedico(dados);
        if (medico == null)
            throw new ValidacaoException("Não existe médico disponível nessa data!");

        var consulta = new Consulta(null, medico, paciente, dados.data(), null);

        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {
        if (dados.idMedico() != null)
            return medicoRepository.getReferenceById(dados.idMedico());
        
        if (dados.especialidade() == null)
            throw new ValidacaoException("Especilidade obrigatorio quando medico nao escolhido!");
        
        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
            throw new ValidacaoException("Id da consulta informado não existe!");
        }

        validadoresCancelamento.forEach(v -> v.cancelar(dados));
    
        var consulta = consultaRepository.getReferenceById(dados.idConsulta());
        consulta.cancelar(dados.motivo());
    }
}
