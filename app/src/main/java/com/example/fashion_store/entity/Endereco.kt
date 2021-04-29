package com.example.fashion_store.entity

class Endereco (
        val destinatario: String,
        val cep: String,
        val endereco: String,
        val complemento: String,
        val numero: String,
        val cidade: String,
        val estado: String,
        val pontoReferencia: String
        ){
    constructor(): this("","","","","","","","")
}