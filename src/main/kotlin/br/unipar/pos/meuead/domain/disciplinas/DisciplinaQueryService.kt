package br.unipar.pos.meuead.domain.disciplinas

import br.unipar.pos.meuead.application.configs.DTOMapper
import br.unipar.pos.meuead.application.exceptions.NotFoundException
import br.unipar.pos.meuead.domain.disciplinas.model.Disciplina
import br.unipar.pos.meuead.domain.disciplinas.model.DisciplinaRepository
import br.unipar.pos.meuead.domain.disciplinas.model.IdDisciplina
import org.springframework.stereotype.Service
import java.util.*

@Service
class DisciplinaQueryService(
    private val dtoMapper: DTOMapper,
    private val disciplinaRepository: DisciplinaRepository
) {

    fun lista(): List<DisciplinaQueryDTO> = disciplinaRepository.findAll().map(this::toDto)

    fun buscaPorId(id: IdDisciplina): DisciplinaQueryDTO = disciplinaRepository.findById(id)
        .map(this::toDto)
        .orElseThrow {
            NotFoundException("Não foi possível encontrar uma disciplina com o código $id")
        }

    private fun toDto(disciplina: Disciplina) = dtoMapper.map(disciplina, DisciplinaQueryDTO::class.java)

}

data class DisciplinaQueryDTO(
    val id: IdDisciplina,
    val nome: String,
    val professor: UUID,
    val turma: UUID,
    val cargaHoraria: Int
)
