package br.unipar.pos.meuead.domain.disciplinas.model

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DisciplinaRepository : JpaRepository<Disciplina, IdDisciplina>
