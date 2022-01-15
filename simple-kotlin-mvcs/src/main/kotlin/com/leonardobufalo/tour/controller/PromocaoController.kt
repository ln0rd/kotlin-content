package com.leonardobufalo.tour.controller

import com.leonardobufalo.tour.exception.PromocaoNotFoundException
import com.leonardobufalo.tour.model.Promocao
import com.leonardobufalo.tour.service.PromocaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/promocoes"])
class PromocaoController {

    @Autowired
    lateinit var promocaoService: PromocaoService

    @GetMapping(value = ["/{id}"])
    fun getById(@PathVariable id: Long): ResponseEntity<Promocao?> {
        val promocao: Promocao? = promocaoService.getById(id)
        var status = if(promocao == null) HttpStatus.NOT_FOUND else HttpStatus.OK
        return ResponseEntity(promocao, status)
    }

    @GetMapping()
    fun getAll(
            @RequestParam(required = false, defaultValue = "") localFilter: String,
            @RequestParam(required = false, defaultValue = "0") page: Int,
            @RequestParam(required = false, defaultValue = "3") size: Int ): ResponseEntity<Page<Promocao?>> {
        val promocoes: Page<Promocao?> = promocaoService.getAll(localFilter, page, size)
        val status = if(promocoes.size == 0) HttpStatus.NOT_FOUND else HttpStatus.OK
        return ResponseEntity(promocoes, status)
    }

    @PostMapping()
    fun create(@RequestBody promocao: Promocao): ResponseEntity<Promocao?> {
        val promocao: Promocao? = promocaoService.create(promocao)
        val status = if(promocao == null) HttpStatus.NOT_FOUND else HttpStatus.CREATED
        return ResponseEntity(promocao, status)
    }

    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: Long): ResponseEntity<Map<String, String>> {
        promocaoService.delete(id)
        val status = HttpStatus.OK
        val ResponseMessage = mapOf<String, String>("message" to "Ok", "status" to "deleted")
        return ResponseEntity(ResponseMessage, status)
    }

    @PutMapping()
    fun update(@RequestBody promocao: Promocao): ResponseEntity<Promocao?> {

        val promocaoUpdated: Promocao?
        var status = HttpStatus.NOT_MODIFIED

        if (promocaoService.getById(promocao.id) != null) {
            promocaoUpdated = promocaoService.update(promocao)
            status = if(promocaoUpdated != null) HttpStatus.OK else HttpStatus.NOT_FOUND
            return ResponseEntity(promocaoUpdated, status)
        }

        return ResponseEntity(promocao, status)
    }

    @GetMapping(value = ["/sortedByLocal"])
    fun getAllSorted(): ResponseEntity<List<Promocao?>> {
        val AllPromotionSortedByLocal = promocaoService.getAllSortedByLocal()
        val status = if(AllPromotionSortedByLocal.size == 0) HttpStatus.NOT_FOUND else HttpStatus.OK
        return ResponseEntity(AllPromotionSortedByLocal, status)
    }

    @GetMapping(value = ["/findByPreco"])
    fun getByPreco(@RequestParam(required = true) preco: Double): ResponseEntity<List<Promocao?>>{
        val promotions : List<Promocao?> = promocaoService.getByPreco(preco)
        val status = if(promotions.size >= 0) HttpStatus.NOT_FOUND else HttpStatus.OK
        return ResponseEntity(promotions, status)
    }

    @PutMapping(value = ["/updateByLocal"])
    fun updateByLocal(@RequestParam local: String, @RequestParam preco: Double): ResponseEntity<Int> {
        val updatedPromocoes : Int =  promocaoService.updateByLocal(local, preco)
        val status: HttpStatus = if(updatedPromocoes > 0) HttpStatus.OK else HttpStatus.NOT_MODIFIED
        return ResponseEntity(updatedPromocoes, status)
    }

}