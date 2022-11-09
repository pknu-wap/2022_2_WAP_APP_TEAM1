package com.example.witt.presentation.ui.plan.drawup_plan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.witt.databinding.ItemTimePlanBinding
import com.example.witt.domain.model.plan.TimePlanModel

class TimePlanAdapter(
    val memoClick : (TimePlanModel) -> Unit
) : ListAdapter<TimePlanModel, TimePlanAdapter.PlanDateViewHolder>(diffutil){

    companion object{
        val diffutil = object : DiffUtil.ItemCallback<TimePlanModel>(){
            override fun areItemsTheSame(oldItem: TimePlanModel, newItem: TimePlanModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TimePlanModel, newItem: TimePlanModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanDateViewHolder {
        return PlanDateViewHolder(ItemTimePlanBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PlanDateViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class PlanDateViewHolder(private val binding: ItemTimePlanBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item: TimePlanModel){
            binding.timePlanMemoTextView.text = item.memo
            binding.timePlanCardView.setOnClickListener{
                memoClick(TimePlanModel(item.memo))
            }
        }
    }
}