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
    void testEscolherMedicoAleatorioLivreNaData() {
        var proximaSegundaAsDez = LocalDate.now()
            .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
            .atTime(10, 0);
        var medicoLivre = medicoRepository.escolherMedicoAleatorioLivreNaData(EspecialidadeEnum.CARDIOLOGIA, proximaSegundaAsDez);

        var medico = cadatrarMedico("medico", "medico@voll.med", "123456", "61999999999", EspecialidadeEnum.CARDIOLOGIA);
        var paciente = cadastrarPaciente("paciente", "paciente@email.com", "61999999999", "00000000000");
        cadastrarConsulta(medico, paciente, proximaSegundaAsDez);

        assertThat(medicoLivre).isNull();
    }

    private Medico cadatrarMedico(String nome, String email, String crm, String telefone, EspecialidadeEnum especialidade) {
        return em.persist(new Medico(new DadosCadastroMedico(nome, email, telefone, crm, especialidade, dadosEndereco())));
    }

    private Paciente cadastrarPaciente(String nome, String email, String telefone, String cpf) {
        return em.persist(new Paciente(new DadosCadastroPaciente(nome, email, telefone, cpf, dadosEndereco())));
    }

    private void cadastrarConsulta(Medico medico, Paciente paciente, LocalDateTime data) {
        em.persist(new Consulta(null, medico, paciente, data, null));
    }

    private DadosEndereco dadosEndereco() {
        return new DadosEndereco("rua xpto", "bairro", "00000000", "cidade", "uf", null, null);
    }

}
