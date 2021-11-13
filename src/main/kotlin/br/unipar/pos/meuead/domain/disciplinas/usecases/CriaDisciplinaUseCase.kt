package br.unipar.pos.meuead.domain.disciplinas.usecases

import br.unipar.pos.meuead.domain.disciplinas.model.Disciplina
import br.unipar.pos.meuead.domain.disciplinas.model.DisciplinaRepository
import org.springframework.stereotype.Service

@Service
class CriaDisciplinaUseCase(private val disciplinaRepository: DisciplinaRepository) {

    fun execute(disciplina: Disciplina) = disciplinaRepository.save(disciplina)

}
