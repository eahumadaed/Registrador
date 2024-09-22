package com.lvm.registrador.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lvm.registrador.R
import com.lvm.registrador.data.model.Medidor
import com.lvm.registrador.databinding.ItemMedidorBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class MainScreenAdapter : ListAdapter<Medidor, MainScreenAdapter.MedidorViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedidorViewHolder {
        val binding = ItemMedidorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedidorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedidorViewHolder, position: Int) {
        val currentMedidor = getItem(position)
        holder.bind(currentMedidor)
    }

    class MedidorViewHolder(private val binding: ItemMedidorBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(medidor: Medidor) {
            val valorFormateado = NumberFormat.getNumberInstance(Locale.getDefault()).format(medidor.valorMedidor)
            binding.tvValorMedidor.text = valorFormateado

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.tvFecha.text = dateFormat.format(medidor.fecha)

            val tipoGastoTraducido = when (medidor.tipoGasto) {
                "Agua" -> binding.root.context.getString(R.string.agua)
                "Luz" -> binding.root.context.getString(R.string.luz)
                "Gas" -> binding.root.context.getString(R.string.gas)
                else -> medidor.tipoGasto
            }

            binding.tvTipoGasto.text = tipoGastoTraducido
            when (medidor.tipoGasto) {
                "Agua" -> binding.imageTipoGasto.setImageResource(R.drawable.ic_water)
                "Luz" -> binding.imageTipoGasto.setImageResource(R.drawable.ic_light)
                "Gas" -> binding.imageTipoGasto.setImageResource(R.drawable.ic_gas)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Medidor>() {
        override fun areItemsTheSame(oldItem: Medidor, newItem: Medidor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Medidor, newItem: Medidor): Boolean {
            return oldItem == newItem
        }
    }
}
