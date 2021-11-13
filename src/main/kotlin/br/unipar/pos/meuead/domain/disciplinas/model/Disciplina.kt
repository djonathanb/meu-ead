package br.unipar.pos.meuead.domain.disciplinas.model

import br.unipar.pos.meuead.application.types.Identifier
import java.time.LocalDateTime
import java.util.*

class Disciplina(
    val id: IdDisciplina,
    val nome: String,
    val professor: UUID,
    val turma: UUID,
    val cargaHoraria: Int,
    val status: Status = Status.PLANEJADA,
    val cancelamento: InformacoesCancelamento? = null
) {

    fun cancela(usuario: UUID, motivo: String) = copy(
        status = Status.CANCELADA,
        cancelamento = InformacoesCancelamento(
            usuario = usuario,
            data = LocalDateTime.now(),
            motivo = motivo
        )
    )

    fun with(
        id: IdDisciplina = this.id,
        nome: String = this.nome,
        professor: UUID = this.professor,
        turma: UUID = this.turma,
        cargaHoraria: Int = this.cargaHoraria
    ) = copy(
        id = id,
        nome = nome,
        professor = professor,
        turma = turma,
        cargaHoraria = cargaHoraria,
        status = this.status,
        cancelamento = this.cancelamento
    )

    private fun copy(
        id: IdDisciplina = this.id,
        nome: String = this.nome,
        professor: UUID = this.professor,
        turma: UUID = this.turma,
        cargaHoraria: Int = this.cargaHoraria,
        status: Status = this.status,
        cancelamento: InformacoesCancelamento? = this.cancelamento
    ) = Disciplina(
        id = id,
        nome = nome,
        professor = professor,
        turma = turma,
        cargaHoraria = cargaHoraria,
        status = status,
        cancelamento = cancelamento
    )

}

class IdDisciplina(private val id: UUID = UUID.randomUUID()) : Identifier(id)
