package com.example.witt.presentation.ui.home.adapter

import android.icu.util.LocaleData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.witt.databinding.ItemHomePlanBinding
import com.example.witt.domain.model.plan.GetPlanModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomePlanAdapter(
    val onPlanCardClick : (GetPlanModel) -> Unit
) : ListAdapter<GetPlanModel, HomePlanAdapter.HomePlanAdapter>(diffutil){

    companion object{
        val diffutil = object : DiffUtil.ItemCallback<GetPlanModel>(){
            override fun areItemsTheSame(oldItem: GetPlanModel, newItem: GetPlanModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: GetPlanModel, newItem: GetPlanModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePlanAdapter {
        return HomePlanAdapter(ItemHomePlanBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HomePlanAdapter, position: Int) {
        holder.bind(currentList[position])
    }

    inner class HomePlanAdapter(private val binding: ItemHomePlanBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item: GetPlanModel){
            binding.homePlanDestinationTextView.text = item.Region
            binding.homePlanNameTextView.text = item.Name
            binding.homePlanDateTextView.text = localDateToString(item.StartDate, item.EndDate)
            binding.homePlanCardView.setOnClickListener{
                onPlanCardClick(item)
            }
        }
    }

    //LocalDateTime -> String
    private fun localDateToString(startDate: LocalDateTime, endDate: LocalDateTime): String{
        val startString = startDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        val endString = endDate.format(DateTimeFormatter.ofPattern("MM.dd"))

        return "$startString - $endString"
    }
}