package com.example.witt.presentation.ui.plan

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.witt.R
import com.example.witt.presentation.widget.JoinPlanDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    private val viewModel : PlanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.plan_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        checkJoinPlan()
    }

    private fun checkJoinPlan(){
        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        if(!prefs.getString("tripId", null).isNullOrBlank()){
            val tripName = prefs.getString("tripName", null) ?: return
            val tripDate = prefs.getString("tripDate", null) ?: return
            showJoinPlanDialog(tripName, tripDate)
        }
    }

    private fun showJoinPlanDialog(tripName: String, tripDate: String){
        JoinPlanDialog(
            this, tripName, tripDate,
            onClickCancel = {
                rejectPlan()
            },
            onClickJoin = {
                viewModel.joinPlan()
            }
        ).show()
    }

    //sharedPreference에 값 삭제
    private fun rejectPlan(){
        getSharedPreferences("prefs", MODE_PRIVATE).edit()
            .remove("tripId")
            .remove("tripName")
            .remove("tripDate")
            .apply()
    }

}