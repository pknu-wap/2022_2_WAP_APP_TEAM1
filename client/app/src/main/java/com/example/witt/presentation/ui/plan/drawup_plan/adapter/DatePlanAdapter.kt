package com.example.witt.presentation.ui.plan.drawup_plan.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.witt.databinding.ItemDatePlanBinding
import com.example.witt.domain.model.plan.DatePlanModel

//일차, 날짜

class DatePlanAdapter(
    val context: Context,
    val timePlanAdapter: TimePlanAdapter,
    val memoButtonClick: (DatePlanModel) -> Unit,
    val placeButtonClick: (DatePlanModel) -> Unit,
) : ListAdapter<DatePlanModel, DatePlanAdapter.PlanDateViewHolder>(diffutil){

    companion object{
        val diffutil = object : DiffUtil.ItemCallback<DatePlanModel>(){
            override fun areItemsTheSame(oldItem: DatePlanModel, newItem: DatePlanModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DatePlanModel, newItem: DatePlanModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanDateViewHolder {
        return PlanDateViewHolder(ItemDatePlanBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: PlanDateViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class PlanDateViewHolder(private val binding: ItemDatePlanBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(datePlanItem: DatePlanModel){
            binding.planDateAddMemoButton.setOnClickListener {
                memoButtonClick(datePlanItem)
            }
            binding.planDateAddPlaceButton.setOnClickListener{
                placeButtonClick(datePlanItem)
            }
            binding.planDateDateTextView.text = datePlanItem.day
            binding.planDateDayTextView.text = datePlanItem.date
            binding.planDateRecyclerVIew.layoutManager = LinearLayoutManager(context)
            binding.planDateRecyclerVIew.adapter = timePlanAdapter
        }
    }

}