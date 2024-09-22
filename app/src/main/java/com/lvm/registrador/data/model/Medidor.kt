package com.lvm.registrador.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "medidor")
data class Medidor(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tipoGasto: String,
    val valorMedidor: Double,
    val fecha: Date
)
