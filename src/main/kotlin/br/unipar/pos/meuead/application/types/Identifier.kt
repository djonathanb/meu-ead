package br.unipar.pos.meuead.application.types

import java.util.*

open abstract class Identifier(private val id: UUID) {

    override fun equals(other: Any?): Boolean {
        if (other == null || other::class != this::class) {
            return false
        }

        with (other as Identifier) {
            return other.id == id
        }
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return id.toString()
    }

}
