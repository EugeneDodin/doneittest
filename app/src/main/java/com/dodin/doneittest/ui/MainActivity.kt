package com.dodin.doneittest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dodin.doneittest.App
import com.dodin.doneittest.R
import com.dodin.doneittest.common.PaginationListener
import com.dodin.doneittest.databinding.ActivityMainBinding
import com.dodin.doneittest.di.ViewModelFactory
import com.dodin.doneittest.ui.adapters.MoviesAdapter
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory
    private lateinit var model: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private var networkErrorMessage: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        (application as App).appComponent.inject(this)
        model = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        initList()
        initObservers()
    }

    private fun initObservers() {
        model.movies.observe(this, Observer {
            (binding.list.adapter as MoviesAdapter).submitList(it)
        })

        model.networkState.observe(this, Observer { isAvailable ->
            if (isAvailable) {
                networkErrorMessage?.dismiss()
            } else {
                networkErrorMessage = Snackbar.make(binding.root, getString(R.string.networkNotAvailable), Snackbar.LENGTH_INDEFINITE)
                networkErrorMessage?.show()
            }
        })
    }

    private fun initList() {
        with(binding.list) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MoviesAdapter()
            addOnScrollListener(object : PaginationListener(layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() {
                    model.loadNext()
                }
            })
        }
    }
}
