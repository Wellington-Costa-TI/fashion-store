package com.example.fashion_store.entity

data class Pedido(
    val codigo: String,
    val produtos : List<Produto>,
    val comprador : User,
){
    fun getValor(): Double {
        var valor : Double = 0.00
        this.produtos.forEach {
            valor += it.valor;
        }
        return valor
    }
}
