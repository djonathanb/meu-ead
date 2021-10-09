package br.unipar.pos.meuead.api.web.planejamento

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.time.LocalDateTime
import java.util.*

@RestController
@RequestMapping("/disciplinas")
class DisciplinaResource {

    companion object {
        private val disciplinas = mutableListOf<Disciplina>(
            Disciplina(UUID.randomUUID(), "BackEnd", UUID.randomUUID(), UUID.randomUUID(), 40),
            Disciplina(UUID.randomUUID(), "Banco de Dados", UUID.randomUUID(), UUID.randomUUID(), 40)
        )
    }

    @GetMapping
    fun listar(): ResponseEntity<MutableList<Disciplina>> {
        return ResponseEntity.ok(disciplinas)
    }

    @PostMapping
    fun criar(@RequestBody disciplinaDTO: DisciplinaDTO): ResponseEntity<URI> {
        val novoId = UUID.randomUUID()
        disciplinas.add(toModel(novoId, disciplinaDTO))
        return ResponseEntity.created(URI.create("http://localhost:8080/disciplinas/$novoId")).build<URI>()
    }

    @GetMapping("/{id}")
    fun obter(@PathVariable("id") id: UUID): ResponseEntity<Disciplina> {
        val disciplina = disciplinas.find { it.id == id }
        return if (disciplina != null) {
            ResponseEntity.ok(disciplina)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable("id") id: UUID): ResponseEntity<Void> {
        val disciplina = disciplinas.find { it.id == id }
        return if (disciplina != null) {
            disciplinas.remove(disciplina)
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    fun atualizar(@PathVariable("id") id: UUID, @RequestBody disciplinaDTO: DisciplinaDTO): ResponseEntity<Void> {
        val disciplinaIndex = disciplinas.indexOfFirst { it.id == id }
        return if (disciplinaIndex == -1) {
            ResponseEntity.notFound().build()
        } else {
            disciplinas[disciplinaIndex] = toModel(id, disciplinaDTO)
            ResponseEntity.noContent().build()
        }
    }

    @PatchMapping("/{id}/professor")
    fun trocaProfessor(@PathVariable("id") id: UUID, @RequestBody trocarProfessorDTO: TrocarProfessorDTO): ResponseEntity<Any> {
        val disciplinaIndex = disciplinas.indexOfFirst { it.id == id }
        return if (disciplinaIndex == -1) {
            ResponseEntity.notFound().build()
        } else {
            val disciplina = disciplinas[disciplinaIndex]
            disciplinas[disciplinaIndex] = Disciplina(
                id = disciplina.id,
                nome = disciplina.nome,
                professor = trocarProfessorDTO.novoProfessor,
                turma = disciplina.turma,
                cargaHoraria = disciplina.cargaHoraria
            )
            ResponseEntity.noContent().build()
        }
    }

    @PostMapping("/{id}/cancelamento")
    fun cancelar(@PathVariable("id") id: UUID, @RequestBody cancelamentoDTO: CancelamentoDTO): ResponseEntity<String> {
        val disciplinaIndex = disciplinas.indexOfFirst { it.id == id }
        return if (disciplinaIndex == -1) {
            ResponseEntity.notFound().build()
        } else {
            val disciplina = disciplinas[disciplinaIndex]

            if (disciplina.status == Status.CANCELADA) {
                return ResponseEntity.badRequest().body("Disciplina ja cancelada")
            }

            disciplinas[disciplinaIndex] = Disciplina(
                id = disciplina.id,
                nome = disciplina.nome,
                professor = disciplina.professor,
                turma = disciplina.turma,
                cargaHoraria = disciplina.cargaHoraria,
                status = Status.CANCELADA,
                cancelamento = InformacoesCancelamento(
                    usuario = UUID.randomUUID(),
                    data = LocalDateTime.now(),
                    motivo = cancelamentoDTO.motivo
                )
            )

            ResponseEntity.noContent().build()
        }
    }

    private fun toModel(
        id: UUID,
        disciplinaDTO: DisciplinaDTO
    ) = Disciplina(
        id = id,
        nome = disciplinaDTO.nome,
        professor = disciplinaDTO.professor,
        turma = disciplinaDTO.turma,
        cargaHoraria = disciplinaDTO.cargaHoraria
    )

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

class Disciplina(
    val id: UUID,
    val nome: String,
    val professor: UUID,
    val turma: UUID,
    val cargaHoraria: Int,
    val status: Status = Status.NOVA,
    val cancelamento: InformacoesCancelamento? = null
)

class InformacoesCancelamento(
    val usuario: UUID,
    val data: LocalDateTime,
    val motivo: String
)

enum class Status {
    NOVA, EM_ANDAMENTO, FINALIZADA, CANCELADA
}