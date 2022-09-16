package com.example.feednews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.feednews.ArticleRepository
import com.example.feednews.NewsViewModelFactory
import com.example.feednews.R
import com.example.feednews.databinding.ActivityMainBinding
import com.example.feednews.ui.home.MainViewModel

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = ArticleRepository()
        val viewModelFactory = NewsViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)
    }
}