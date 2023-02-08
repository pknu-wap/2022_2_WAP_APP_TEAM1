package com.example.witt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        getQueryParameter()
    }

    // androidExecutionParams으로 받은 값 확인
    // todo 초대에서 나를 확인하는 방법
    private fun getQueryParameter() {
        // todo refactor
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val tripId = intent.data?.getQueryParameter("tripId") ?: return
        val tripName = intent.data?.getQueryParameter("tripName") ?: return
        val tripDate = intent.data?.getQueryParameter("tripDate") ?: return

        prefs.edit()
            .putString("tripId", tripId)
            .putString("tripName", tripName)
            .putString("tripDate", tripDate)
            .apply()
    }
}
