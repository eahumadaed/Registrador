package com.lvm.registrador.ui.registro

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.lvm.registrador.R
import com.lvm.registrador.databinding.ActivityRegistroMedidoresBinding
import com.lvm.registrador.data.repository.MedidorRepository
import com.lvm.registrador.data.db.AppDatabase
import java.text.SimpleDateFormat
import java.util.*

class RegistroMedidoresActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroMedidoresBinding
    private val registroViewModel: RegistroViewModel by viewModels {
        RegistroViewModelFactory(MedidorRepository(AppDatabase.getDatabase(this).medidorDao()))
    }

    // Formato de fecha
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroMedidoresBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar la fecha con la fecha de hoy
        val today = Calendar.getInstance()
        binding.inputFecha.setText(dateFormat.format(today.time))

        binding.inputFecha.setOnClickListener {
            val year = today.get(Calendar.YEAR)
            val month = today.get(Calendar.MONTH)
            val day = today.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)
                    binding.inputFecha.setText(dateFormat.format(selectedDate.time))
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        binding.btnRegistrarMedicion.setOnClickListener {
            val tipoGasto = when {
                binding.radioAgua.isChecked -> "Agua"
                binding.radioLuz.isChecked -> "Luz"
                binding.radioGas.isChecked -> "Gas"
                else -> null
            }

            val valorMedidor = binding.inputValorMedidor.text.toString().toDoubleOrNull()
            val fecha = binding.inputFecha.text.toString()

            // Validación de campos
            if (tipoGasto == null || valorMedidor == null || valorMedidor == 0.0 || fecha.isEmpty()) {
                mostrarAlertaCamposObligatorios()
            } else {
                // Logs para verificar lo que se está guardando
                Log.d("RegistroMedidoresActivity", "Tipo de gasto: $tipoGasto")
                Log.d("RegistroMedidoresActivity", "Valor del medidor: $valorMedidor")
                Log.d("RegistroMedidoresActivity", "Fecha: $fecha")

                registroViewModel.insertMedidor(tipoGasto, valorMedidor, fecha)

                finish()
            }
        }
    }

    private fun mostrarAlertaCamposObligatorios() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.campo_obligatorio_titulo))
        builder.setMessage(getString(R.string.campo_obligatorio_mensaje))
        builder.setPositiveButton(getString(R.string.ok)) { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

}
