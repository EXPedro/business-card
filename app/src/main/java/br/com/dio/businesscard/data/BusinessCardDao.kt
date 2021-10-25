package br.com.dio.businesscard.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//Interface para realizar a persistência no banco de dados
@Dao
interface BusinessCardDao {

    //Buscar dados no banco de dados
    @Query("SELECT * FROM BusinessCard")
    fun getAll(): LiveData<List<BusinessCard>>

    //Criar inserção para o banco de dados
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(businessCard: BusinessCard)
}