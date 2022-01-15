package com.leonardobufalo.tour.service

import com.leonardobufalo.tour.model.Promocao
import org.springframework.data.domain.Page


interface PromocaoService {

    fun create(promocao: Promocao): Promocao?

    fun getAll(local: String, start: Int, size: Int): Page<Promocao?>

    fun getById(id: Long): Promocao?

    fun delete(id: Long)

    fun update(promocao: Promocao): Promocao?

    fun getAllSortedByLocal(): List<Promocao?>

    fun getByPreco(preco: Double): List<Promocao?>

    fun updateByLocal(local: String, preco: Double): Int

}