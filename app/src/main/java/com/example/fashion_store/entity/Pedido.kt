package com.example.fashion_store.entity

import java.time.LocalDate

data class Pedido(
        val valorTotal: Double,
        val dataFeitura: LocalDate?,
        val dataFinalizacao: LocalDate?,
        val finalizado: Boolean
){
    constructor() : this(0.00,null,null,false)
}
