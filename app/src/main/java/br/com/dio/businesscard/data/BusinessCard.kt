package br.com.dio.businesscard.data

import androidx.room.Entity
import androidx.room.PrimaryKey

//Classe da Entidade do banco de dados que se pretende persistir
@Entity
data class BusinessCard(

    //criando primary key (requisito obrigatório da entity)
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val empresa: String,
    val telefone: String,
    val email: String,
    val fundoPersonalizado: String
)