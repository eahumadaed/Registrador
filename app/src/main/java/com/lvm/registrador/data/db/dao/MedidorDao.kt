package com.lvm.registrador.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update
import com.lvm.registrador.data.model.Medidor
import kotlinx.coroutines.flow.Flow

@Dao
interface MedidorDao {

    // Insertar un nuevo registro de medidor
    @Insert
    suspend fun insertMedidor(medidor: Medidor)

    // Actualizar un registro existente
    @Update
    suspend fun updateMedidor(medidor: Medidor)

    // Eliminar un registro
    @Delete
    suspend fun deleteMedidor(medidor: Medidor)

    // Obtener todos los registros de medidores, ordenados por fecha
    @Query("SELECT * FROM medidor ORDER BY fecha DESC")
    fun getAllMedidores(): Flow<List<Medidor>>

    // Obtener un registro específico por su ID
    @Query("SELECT * FROM medidor WHERE id = :medidorId")
    suspend fun getMedidorById(medidorId: Int): Medidor?
}
