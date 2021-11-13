package br.unipar.pos.meuead.domain.disciplinas.model

import java.time.LocalDateTime
import java.util.*

class InformacoesCancelamento(
    val usuario: UUID,
    val data: LocalDateTime,
    val motivo: String
)
