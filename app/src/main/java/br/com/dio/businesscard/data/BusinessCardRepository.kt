package br.com.dio.businesscard.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//Classe repositório do DAO
class BusinessCardRepository(private val dao: BusinessCardDao) {

    //Inserir no banco de dados
    fun insert(businessCard: BusinessCard) = runBlocking {
        launch(Dispatchers.IO) {
            dao.insert(businessCard)
        }
    }

    //Listar registros do banco de dados
    fun getAll() = dao.getAll()
}