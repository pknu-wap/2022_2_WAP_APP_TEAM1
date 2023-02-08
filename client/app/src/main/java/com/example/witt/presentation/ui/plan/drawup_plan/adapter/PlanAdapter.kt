package com.example.witt.presentation.ui.plan.drawup_plan.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.witt.databinding.ItemPlanDayBinding
import com.example.witt.domain.model.remote.plan.get_plan.DetailPlanModel
import com.example.witt.domain.model.use_case.plan.PlanModel
import com.example.witt.presentation.listener.ItemTouchCallback

class PlanAdapter(
    val context: Context,
    val memoClick: (DetailPlanModel) -> Unit,
    val placeClick: (DetailPlanModel) -> Unit,
    val memoButtonClick: (Int) -> Unit,
    val placeButtonClick: (Int) -> Unit,
) : ListAdapter<PlanModel, PlanAdapter.PlanDateViewHolder>(diffutil) {

    companion object {
        val diffutil = object : DiffUtil.ItemCallback<PlanModel>() {
            override fun areItemsTheSame(oldItem: PlanModel, newItem: PlanModel): Boolean {
                return oldItem.detailPlan == newItem.detailPlan
            }

            override fun areContentsTheSame(oldItem: PlanModel, newItem: PlanModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanDateViewHolder {
        return PlanDateViewHolder(
            ItemPlanDayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ),
            memoClick,
            placeClick
        )
    }

    override fun onBindViewHolder(holder: PlanDateViewHolder, position: Int) {
        holder.bind(currentList[position], holder.adapterPosition + 1)
    }

    inner class PlanDateViewHolder(
        private val binding: ItemPlanDayBinding,
        private val memoClick: (DetailPlanModel) -> Unit,
        private val placeClick: (DetailPlanModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dayPlanItem: PlanModel, pos: Int) {

            val adapter = DetailPlanAdapter(memoClick, placeClick)

            val itemTouchHelperCallback = ItemTouchCallback(adapter)
            val helper = ItemTouchHelper(itemTouchHelperCallback)

            adapter.planContentData.clear()
            dayPlanItem.detailPlan?.let{
                adapter.planContentData.addAll(it)
            }

            with(binding) {
                item = dayPlanItem
                planDateAddMemoButton.setOnClickListener {
                    memoButtonClick(pos)
                }
                planDateAddPlaceButton.setOnClickListener {
                    placeButtonClick(pos)
                }
                with(timePlanRecyclerVIew) {
                    layoutManager = LinearLayoutManager(context)
                    this.adapter = adapter
                    helper.attachToRecyclerView(this) // ItemTouchHelper
                }
            }
        }
    }
}
