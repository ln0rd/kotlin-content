package com.leonardobufalo.tour.service.impl

import com.leonardobufalo.tour.model.Promocao
import com.leonardobufalo.tour.repository.PromocaoRepository
import com.leonardobufalo.tour.service.PromocaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class PromocaoServiceImpl: PromocaoService {


    @Autowired
    lateinit var promocaoRepository: PromocaoRepository



    override fun create(promocao: Promocao): Promocao? {
        return promocaoRepository.save(promocao)
    }

    override fun getAll(local: String, start: Int, size: Int): Page<Promocao?> {
        val promocoesPages: Pageable = PageRequest.of(start , size, Sort.by("local").descending())

        if (local == "") return promocaoRepository.findAll(promocoesPages)

        return promocaoRepository.findByLocal(local, promocoesPages)
    }

    override fun getById(id: Long): Promocao? {
        return promocaoRepository.findById(id).orElseGet(null)
    }

    override fun delete(id: Long) {
        return promocaoRepository.deleteById(id)
    }

    override fun update(promocao: Promocao): Promocao? {
        delete(promocao.id)
        create(promocao)
        return getById(promocao.id)
    }

    override fun getAllSortedByLocal(): List<Promocao?> {
        return promocaoRepository.findAll(Sort.by("Local").descending()).toList()
    }

    override fun getByPreco(preco: Double): List<Promocao?> {
        return promocaoRepository.findByPreco(preco)
    }

    override fun updateByLocal(local: String, preco: Double): Int {
        return promocaoRepository.updateByLocal(local, preco)
    }

}