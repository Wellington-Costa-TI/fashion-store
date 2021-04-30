package com.example.fashion_store.entity

import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class Pedido(
        val valorTotal: Double = 0.00,
        val dataFeitura: Date?,
        val dataFinalizacao: Date?,
        val finalizado: Boolean = false,
        val idPedido: String
){
    constructor() : this(0.00,null,null,false,"")
}
