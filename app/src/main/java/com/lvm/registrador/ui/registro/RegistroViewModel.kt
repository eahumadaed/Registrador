package com.lvm.registrador.ui.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lvm.registrador.data.model.Medidor
import com.lvm.registrador.data.repository.MedidorRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegistroViewModel(private val repository: MedidorRepository) : ViewModel() {

    fun insertMedidor(tipoGasto: String, valorMedidor: Double, fecha: String) {
        val fechaDate = stringToDate(fecha)
        if (fechaDate != null) {
            val nuevoMedidor = Medidor(tipoGasto = tipoGasto, valorMedidor = valorMedidor, fecha = fechaDate)
            viewModelScope.launch {
                repository.insert(nuevoMedidor)
            }
        } else {
            println("Error: Fecha no válida")
        }
    }

    fun stringToDate(fecha: String): Date? {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return try {
            format.parse(fecha)
        } catch (e: Exception) {
            null
        }
    }
}

