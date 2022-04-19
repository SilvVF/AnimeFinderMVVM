package com.example.animemvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.animemvvm.R
import com.example.animemvvm.databinding.AcitvityAnimeBinding
import com.example.animemvvm.db.AnimeDatabase
import com.example.animemvvm.repository.AnimeRepository


class AnimeActivity : AppCompatActivity() {

    private lateinit var binding: AcitvityAnimeBinding
    private lateinit var navController: NavController
    val viewModel :AnimeViewModel by viewModels { AnimeViewModelFactory(
        application,
    AnimeRepository(AnimeDatabase(this))
    )}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AcitvityAnimeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val repository = AnimeRepository(AnimeDatabase(applicationContext))



        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.animeNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)


    }



}