package br.unipar.pos.meuead.interfaces.web

import br.unipar.pos.meuead.domain.disciplinas.*
import br.unipar.pos.meuead.domain.disciplinas.model.IdDisciplina
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/disciplinas")
class DisciplinaResource(
    private val disciplinaService: DisciplinaService,
    private val disciplinaQueryService: DisciplinaQueryService
) {

    @GetMapping
    fun listar(): ResponseEntity<List<DisciplinaQueryDTO>> {
        val disciplinas = disciplinaQueryService.lista()
        return ResponseEntity.ok(disciplinas)
    }

    @PostMapping
    fun criar(@RequestBody disciplinaDTO: DisciplinaDTO): ResponseEntity<URI> {
        val novoId = disciplinaService.cria(disciplinaDTO).id
        return ResponseEntity.created(URI.create("http://localhost:8080/disciplinas/$novoId")).build()
    }

    @GetMapping("/{id}")
    fun obter(@PathVariable("id") id: IdDisciplina): ResponseEntity<DisciplinaQueryDTO> {
        return ResponseEntity.ok(disciplinaQueryService.buscaPorId(id))
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable("id") id: IdDisciplina): ResponseEntity<Void> {
        disciplinaService.deletaPorId(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun atualizar(@PathVariable("id") id: IdDisciplina, @RequestBody disciplinaDTO: DisciplinaDTO): ResponseEntity<Void> {
        disciplinaService.atualiza(id, disciplinaDTO)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/{id}/professor")
    fun trocaProfessor(@PathVariable("id") id: IdDisciplina, @RequestBody trocarProfessorDTO: TrocarProfessorDTO): ResponseEntity<Void> {
        disciplinaService.trocaProfessor(id, trocarProfessorDTO)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/cancelamento")
    fun cancelar(@PathVariable("id") id: IdDisciplina, @RequestBody cancelamentoDTO: CancelamentoDTO): ResponseEntity<Void> {
        disciplinaService.cancela(id, cancelamentoDTO)
        return ResponseEntity.noContent().build()
    }

}
