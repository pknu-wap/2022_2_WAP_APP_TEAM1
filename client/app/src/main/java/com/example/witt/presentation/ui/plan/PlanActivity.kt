package com.example.witt.presentation.ui.plan

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.witt.R
import com.example.witt.databinding.ActivityPlanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private val binding by lazy{ ActivityPlanBinding.inflate(layoutInflater)}

    private val viewModel : PlanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.plan_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}