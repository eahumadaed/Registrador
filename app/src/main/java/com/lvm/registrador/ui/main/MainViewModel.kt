package com.lvm.registrador.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lvm.registrador.data.model.Medidor
import com.lvm.registrador.data.repository.MedidorRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MedidorRepository) : ViewModel() {

    val allMedidores: StateFlow<List<Medidor>> = repository.allMedidores
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun deleteMedidor(medidor: Medidor) {
        viewModelScope.launch {
            repository.delete(medidor)
        }
    }
}
