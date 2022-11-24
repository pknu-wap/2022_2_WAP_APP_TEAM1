package com.example.witt.presentation.ui.plan

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.witt.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val viewModel : PlanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.plan_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

    }

}