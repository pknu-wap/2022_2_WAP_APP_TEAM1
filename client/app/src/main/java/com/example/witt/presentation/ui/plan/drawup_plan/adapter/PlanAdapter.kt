package com.example.witt.presentation.ui.plan.drawup_plan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.witt.databinding.ItemPlanBinding
import com.example.witt.domain.model.plan.adapter.PlanModel
import com.example.witt.domain.model.plan.get_plan.DetailPlanModel
import com.example.witt.presentation.listener.ItemTouchCallback

class PlanAdapter(
    val context: Context,
    val memoClick : (DetailPlanModel) -> Unit,
    val memoButtonClick: (Int) -> Unit,
    val placeButtonClick: (Int) -> Unit,
) : ListAdapter<PlanModel, PlanAdapter.PlanDateViewHolder>(diffutil){

    companion object{
        val diffutil = object : DiffUtil.ItemCallback<PlanModel>(){
            override fun areItemsTheSame(oldItem: PlanModel, newItem: PlanModel): Boolean {
                return oldItem.detailPlan == newItem.detailPlan
            }

            override fun areContentsTheSame(oldItem: PlanModel, newItem: PlanModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanDateViewHolder {
        return PlanDateViewHolder(ItemPlanBinding.inflate(LayoutInflater.from(parent.context),
            parent, false), memoClick)
    }

    override fun onBindViewHolder(holder: PlanDateViewHolder, position: Int) {
        holder.bind(currentList[position], holder.adapterPosition + 1)
    }

    inner class PlanDateViewHolder(
        private val binding: ItemPlanBinding,
        private val memoClick : (DetailPlanModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dayPlanItem: PlanModel, pos: Int){

            val adapter = DetailPlanAdapter(memoClick)

            val itemTouchHelperCallback = ItemTouchCallback(adapter)
            val helper = ItemTouchHelper(itemTouchHelperCallback)

            adapter.planContentData.clear()
            adapter.planContentData.addAll(dayPlanItem.detailPlan)

            with(binding){
                item = dayPlanItem
                planDateAddMemoButton.setOnClickListener {
                    memoButtonClick(pos)
                }
                planDateAddPlaceButton.setOnClickListener{
                    placeButtonClick(pos)
                }
                with(timePlanRecyclerVIew){
                    layoutManager = LinearLayoutManager(context)
                    this.adapter = adapter
                    helper.attachToRecyclerView(this) //ItemTouchHelper
                }
            }
        }
    }
}