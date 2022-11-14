package com.example.witt.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.witt.databinding.ItemHomePlanBinding
import com.example.witt.domain.model.plan.PlanStateModel
import com.example.witt.domain.model.plan.get_plan.GetPlanListModel
import com.example.witt.domain.model.plan.get_plan.toPlanStateModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomePlanAdapter(
    val onPlanCardClick : (PlanStateModel) -> Unit
) : ListAdapter<GetPlanListModel, HomePlanAdapter.HomePlanAdapter>(diffutil){

    companion object{
        val diffutil = object : DiffUtil.ItemCallback<GetPlanListModel>(){
            override fun areItemsTheSame(oldItem: GetPlanListModel, newItem: GetPlanListModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: GetPlanListModel, newItem: GetPlanListModel): Boolean {
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
        fun bind(item: GetPlanListModel){
            binding.item = item.toPlanStateModel()
            binding.homePlanCardView.setOnClickListener{
                onPlanCardClick(item.toPlanStateModel())
            }
        }
    }
}