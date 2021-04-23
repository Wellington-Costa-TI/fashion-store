package com.example.fashion_store.entity

import java.util.*

data class Produto(
    val nome: String,
    val descricao: String,
    val valor : Double,
    val estoque : Int
)
