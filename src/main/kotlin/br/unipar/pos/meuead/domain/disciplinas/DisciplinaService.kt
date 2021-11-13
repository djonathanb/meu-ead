package br.unipar.pos.meuead.domain.disciplinas

import br.unipar.pos.meuead.application.configs.DTOMapper
import br.unipar.pos.meuead.application.exceptions.InvalidStateException
import br.unipar.pos.meuead.application.exceptions.NotFoundException
import br.unipar.pos.meuead.domain.disciplinas.model.Disciplina
import br.unipar.pos.meuead.domain.disciplinas.model.DisciplinaRepository
import br.unipar.pos.meuead.domain.disciplinas.model.IdDisciplina
import br.unipar.pos.meuead.domain.disciplinas.model.Status
import br.unipar.pos.meuead.domain.disciplinas.usecases.CriaDisciplinaUseCase
import org.springframework.stereotype.Service
import java.util.*

@Service
class DisciplinaService(
    private val dtoMapper: DTOMapper,
    private val disciplinaRepository: DisciplinaRepository,
    private val criaDisciplinaUseCase: CriaDisciplinaUseCase,
) {

    fun cria(disciplinaDTO: DisciplinaDTO): Disciplina {
        val disciplina = dtoMapper.map(disciplinaDTO, Disciplina::class.java)
        return criaDisciplinaUseCase.execute(disciplina)
    }

    fun deletaPorId(idDisciplina: IdDisciplina) {
        val disciplina = buscaPorId(idDisciplina)
        disciplinaRepository.delete(disciplina)
    }

    fun atualiza(idDisciplina: IdDisciplina, disciplinaDTO: DisciplinaDTO) {
        val disciplina = buscaPorId(idDisciplina)

        disciplinaRepository.save(disciplina.with(
            nome = disciplinaDTO.nome,
            professor = disciplinaDTO.professor,
            turma = disciplinaDTO.turma,
            cargaHoraria = disciplinaDTO.cargaHoraria
        ))
    }

    fun trocaProfessor(idDisciplina: IdDisciplina, trocarProfessorDTO: TrocarProfessorDTO) {
        val disciplina = buscaPorId(idDisciplina)
        disciplinaRepository.save(disciplina.with(professor = trocarProfessorDTO.novoProfessor))
    }

    fun cancela(idDisciplina: IdDisciplina, cancelamentoDTO: CancelamentoDTO) {
        val disciplina = buscaPorId(idDisciplina)

        if (disciplina.status == Status.CANCELADA) {
            throw InvalidStateException("Disciplina ja cancelada")
        }

        disciplinaRepository.save(disciplina.cancela(
            usuario = UUID.randomUUID(),
            motivo = cancelamentoDTO.motivo
        ))
    }

    private fun buscaPorId(idDisciplina: IdDisciplina) = disciplinaRepository.findById(idDisciplina).orElseThrow {
        NotFoundException("Não foi possível encontrar uma disciplina com o código $idDisciplina")
    }

}

data class DisciplinaDTO(
    val nome: String,
    val professor: UUID,
    val turma: UUID,
    val cargaHoraria: Int
)

data class TrocarProfessorDTO(
    val novoProfessor: UUID
)

data class CancelamentoDTO(
    val motivo: String
)
