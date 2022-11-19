package com.example.witt.presentation.ui.plan.drawup_plan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.witt.databinding.ItemPlanBinding
import com.example.witt.domain.model.plan.adapter.PlanModel

class PlanAdapter(
    val context: Context,
    val detailPlanAdapter: DetailPlanAdapter,
    val memoButtonClick: (Int) -> Unit,
    val placeButtonClick: (Int) -> Unit,
) : ListAdapter<PlanModel, PlanAdapter.PlanDateViewHolder>(diffutil){

    companion object{
        val diffutil = object : DiffUtil.ItemCallback<PlanModel>(){
            override fun areItemsTheSame(oldItem: PlanModel, newItem: PlanModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PlanModel, newItem: PlanModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanDateViewHolder {
        return PlanDateViewHolder(ItemPlanBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: PlanDateViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class PlanDateViewHolder(private val binding: ItemPlanBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(dayPlanItem: PlanModel){

            detailPlanAdapter.planContentData.clear()
            detailPlanAdapter.planContentData.addAll(dayPlanItem.detailPlan)

            with(binding){
                item = dayPlanItem
                timePlanRecyclerVIew.layoutManager = LinearLayoutManager(context)
                timePlanRecyclerVIew.adapter = detailPlanAdapter
                planDateAddMemoButton.setOnClickListener {
                    memoButtonClick(adapterPosition)
                }
                planDateAddPlaceButton.setOnClickListener{
                    placeButtonClick(adapterPosition)
                }
            }
        }
    }
}