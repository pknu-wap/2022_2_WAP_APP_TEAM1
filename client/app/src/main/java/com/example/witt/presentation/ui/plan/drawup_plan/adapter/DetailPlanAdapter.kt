package com.example.witt.presentation.ui.plan.drawup_plan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.witt.databinding.ItemDetailPlanBinding
import com.example.witt.domain.model.plan.get_plan.DetailPlanModel

class DetailPlanAdapter(
    val memoClick : (DetailPlanModel) -> Unit
) : RecyclerView.Adapter<DetailPlanAdapter.PlanDateViewHolder>(){

    val planContentData = mutableListOf<DetailPlanModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanDateViewHolder {
        return PlanDateViewHolder(ItemDetailPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PlanDateViewHolder, position: Int) {
        holder.bind(planContentData[position])
    }

    override fun getItemCount(): Int {
        return planContentData.size
    }

    inner class PlanDateViewHolder(private val binding: ItemDetailPlanBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(item: DetailPlanModel){
            binding.item = item.Memo
            binding.timePlanCardView.setOnClickListener{
                memoClick(item)
            }
        }
    }
}