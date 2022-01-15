package com.leonardobufalo.tour.repository

import com.leonardobufalo.tour.model.Promocao
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface PromocaoRepository: PagingAndSortingRepository<Promocao, Long> {

    fun findByLocal(local: String, pageable: Pageable): Page<Promocao?>

    @Query(value = "select * from promocao p where p.preco <= 5000.00", nativeQuery = true)
    fun findByPreco(@Param(value = "preco") preco: Double): List<Promocao?>

    @Query(value = "update promocao p set p.preco = :preco where p.local = :local", nativeQuery = true)
    @Transactional @Modifying
    fun updateByLocal(@Param(value = "local") local: String, @Param(value = "preco") preco: Double): Int
}