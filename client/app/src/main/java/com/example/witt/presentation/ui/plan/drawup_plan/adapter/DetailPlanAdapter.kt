package com.example.witt.presentation.ui.plan.drawup_plan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.witt.databinding.ItemDetailPlanBinding
import com.example.witt.domain.model.plan.get_plan.DetailPlanModel
import com.example.witt.presentation.listener.ItemTouchHelperListener

class DetailPlanAdapter(
    val memoClick : (DetailPlanModel) -> Unit
) : RecyclerView.Adapter<DetailPlanAdapter.PlanDateViewHolder>(), ItemTouchHelperListener{

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

    //todo refactor code
    //ItemTouchHelperListener
    override fun onItemMove(from: Int, to: Int) : Boolean{
        if(planContentData.size <= from){
            return false
        }
        val item = planContentData[from]
        planContentData.removeAt(from)
        planContentData.add(to, item)
        notifyItemMoved(from, to)
        return true
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