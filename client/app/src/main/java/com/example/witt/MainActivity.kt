package com.example.witt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

    //androidExecutionParams으로 받은 값 확인
    private fun getQueryParameter(){
        val value = intent?.data
        if(value != null){
            intent.data?.getQueryParameter("key1")?.let {
                Log.d("tag", it)
            }
        }
    }
}