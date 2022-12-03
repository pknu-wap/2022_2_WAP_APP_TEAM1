package com.example.witt.presentation.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.witt.databinding.ItemHomePlanBinding
import com.example.witt.domain.model.use_case.plan.PlanStateModel
import com.example.witt.domain.model.remote.plan.get_plan.GetPlanListResultModel
import com.example.witt.domain.model.remote.plan.get_plan.toPlanStateModel

class HomePlanAdapter(
    val onPlanCardClick : (PlanStateModel) -> Unit,
    val onRemoveButtonClick : (Int) -> Unit
) : ListAdapter<GetPlanListResultModel, HomePlanAdapter.HomePlanAdapter>(diffutil){

    companion object{
        val diffutil = object : DiffUtil.ItemCallback<GetPlanListResultModel>(){
            override fun areItemsTheSame(oldItem: GetPlanListResultModel, newItem: GetPlanListResultModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: GetPlanListResultModel, newItem: GetPlanListResultModel): Boolean {
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
        fun bind(item: GetPlanListResultModel){
            binding.item = item.toPlanStateModel()

            binding.homePlanCardView.setOnClickListener{
                onPlanCardClick(
                    item.toPlanStateModel()
                )
            }

            binding.removePlanButton.setOnClickListener{
                onRemoveButtonClick(
                    item.TripId
                )
            }
        }
    }
}