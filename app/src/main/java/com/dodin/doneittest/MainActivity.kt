package com.dodin.doneittest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dodin.doneittest.common.Lce
import com.dodin.doneittest.databinding.ActivityMainBinding
import com.dodin.doneittest.di.ViewModelFactory
import com.dodin.doneittest.ui.MainViewModel
import com.dodin.doneittest.ui.adapters.MoviesAdapter
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var model: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        (application as App).appComponent.inject(this)
        model = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        initList()
        initObservers()
    }

    private fun initObservers() {
        model.movies.observe(this, Observer {
            binding.progress.isVisible = it is Lce.Loading
            when(it) {
                is Lce.Error -> {
                    Snackbar.make(binding.root, it.message, Snackbar.LENGTH_SHORT).show()
                }
                is Lce.Success -> {
                    (binding.list.adapter as MoviesAdapter).submitList(it.data)
                }
            }
        })
    }

    private fun initList() {
        with(binding.list) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MoviesAdapter()
        }
    }
}
