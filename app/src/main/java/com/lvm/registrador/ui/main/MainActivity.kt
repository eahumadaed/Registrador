package com.lvm.registrador.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.lvm.registrador.databinding.ActivityMainBinding
import com.lvm.registrador.ui.adapter.MainScreenAdapter
import com.lvm.registrador.ui.registro.RegistroMedidoresActivity
import com.lvm.registrador.data.repository.MedidorRepository
import com.lvm.registrador.data.db.AppDatabase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory(MedidorRepository(AppDatabase.getDatabase(this).medidorDao()))
    }

    private lateinit var adapter: MainScreenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        lifecycleScope.launch {
            mainViewModel.allMedidores.collect { medidores ->
                adapter.submitList(medidores)
            }
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this, RegistroMedidoresActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        adapter = MainScreenAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }
}
