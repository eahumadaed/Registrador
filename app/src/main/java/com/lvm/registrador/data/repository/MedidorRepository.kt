package com.lvm.registrador.data.repository

import com.lvm.registrador.data.db.dao.MedidorDao
import com.lvm.registrador.data.model.Medidor
import kotlinx.coroutines.flow.Flow

class MedidorRepository(private val medidorDao: MedidorDao) {

    // Obtener todas las mediciones desde la base de datos
    val allMedidores: Flow<List<Medidor>> = medidorDao.getAllMedidores()

    // Insertar un nuevo medidor
    suspend fun insert(medidor: Medidor) {
        medidorDao.insertMedidor(medidor)
    }

    // Actualizar un medidor existente
    suspend fun update(medidor: Medidor) {
        medidorDao.updateMedidor(medidor)
    }

    // Eliminar un medidor
    suspend fun delete(medidor: Medidor) {
        medidorDao.deleteMedidor(medidor)
    }

    // Obtener un medidor por su ID
    suspend fun getMedidorById(id: Int): Medidor? {
        return medidorDao.getMedidorById(id)
    }
}
