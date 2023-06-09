package com.md.voll.api.domain.medico;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.md.voll.api.domain.consulta.Consulta;
import com.md.voll.api.domain.endereco.DadosEndereco;
import com.md.voll.api.domain.paciente.DadosCadastroPaciente;
import com.md.voll.api.domain.paciente.Paciente;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deveria devolver null quando unico medico cadastrado nao esta disponivel na data")
    void testEscolherMedicoAleatorioLivreNaDataCenario1() {
        //given or arrange
        var proximaSegundaAsDez = LocalDate.now()
            .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
            .atTime(10, 0);
        
        var medico = cadastrarMedico("medico", "medico@voll.med", "123456", EspecialidadeEnum.CARDIOLOGIA);
        var paciente = cadastrarPaciente("paciente", "paciente@email.com", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAsDez);
        
        //when or act
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(EspecialidadeEnum.CARDIOLOGIA, proximaSegundaAsDez);
        
        //then or assert
        assertThat(medicoLivre).isNull();
    }

    @Test
    @DisplayName("Deveria devolver medico ele estiver disponivel na data")
    void testEscolherMedicoAleatorioLivreNaDataCenario2() {
        var proximaSegundaAsDez = LocalDate.now()
            .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
            .atTime(10, 0);
        var test = medicoRepository.findAll();
        
        var medico = cadastrarMedico("medico", "medico@voll.med", "123456", EspecialidadeEnum.CARDIOLOGIA);
        
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(EspecialidadeEnum.CARDIOLOGIA, proximaSegundaAsDez);

        assertThat(medicoLivre).isEqualTo(medico);
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data, null));
    }
    
    private Medico cadastrarMedico(String nome, String email, String crm, EspecialidadeEnum especialidade) {
        var medico = new Medico(dadosMedico(nome, email, crm, especialidade));
        em.persist(medico);
        return medico;
    }
    
    private Paciente cadastrarPaciente(String nome, String email, String cpf) {
        var paciente = new Paciente(dadosPaciente(nome, email, cpf));
        em.persist(paciente);
        return paciente;
    }
    
    private DadosCadastroMedico dadosMedico(String nome, String email, String crm, EspecialidadeEnum especialidade) {
        return new DadosCadastroMedico(
                nome,
                email,
                "61999999999",
                crm,
                especialidade,
                dadosEndereco()
        );
    }
    
    private DadosCadastroPaciente dadosPaciente(String nome, String email, String cpf) {
        return new DadosCadastroPaciente(
                nome,
                email,
                "61999999999",
                cpf,
                dadosEndereco()
        );
    }
    
    private DadosEndereco dadosEndereco() {
        return new DadosEndereco(
                "rua xpto",
                "bairro",
                "00000000",
                "Brasilia",
                "DF",
                null,
                null
        );
    }

}
