package com.example.witt.presentation.ui.plan.drawup_plan.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.witt.databinding.ItemPlanFlightBinding
import com.example.witt.databinding.ItemPlanMemoBinding
import com.example.witt.databinding.ItemPlanPlaceBinding
import com.example.witt.domain.model.remote.plan.get_plan.DetailPlanModel
import com.example.witt.presentation.listener.ItemTouchHelperListener
import com.example.witt.utils.convertIsoToTime

class DetailPlanAdapter(
    val memoClick: (DetailPlanModel) -> Unit,
    val placeClick: (DetailPlanModel) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperListener {

    val planContentData = mutableListOf<DetailPlanModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        TYPE_MEMO -> {
            val binding = ItemPlanMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            PlanMemoViewHolder(binding)
        }
        TYPE_PLACE -> {
            val binding = ItemPlanPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            PlanPlaceViewHolder(binding)
        }
        else -> {
            val binding = ItemPlanFlightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            PlanFlightViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int =
        planContentData[position].type

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlanMemoViewHolder -> {
                holder.bind(planContentData[position])
            }
            is PlanPlaceViewHolder -> {
                holder.bind(planContentData[position])
            }
            is PlanFlightViewHolder -> {
                holder.bind(planContentData[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return planContentData.size
    }

    // todo refactor code
    // ItemTouchHelperListener
    override fun onItemMove(from: Int, to: Int): Boolean {
        if (planContentData.size <= from) {
            return false
        }
        val item = planContentData[from]
        planContentData.removeAt(from)
        planContentData.add(to, item)
        notifyItemMoved(from, to)
        return true
    }

    inner class PlanMemoViewHolder(private val binding: ItemPlanMemoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetailPlanModel) {
            binding.item = item.memo
            binding.timePlanCardView.setOnClickListener {
                memoClick(item)
            }
        }
    }

    inner class PlanPlaceViewHolder(private val binding: ItemPlanPlaceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetailPlanModel) {
            binding.item = item.place
            binding.placeNumberTextView.text = item.orderIndex.toString()
            binding.planPlaceCardView.setOnClickListener{
                placeClick(item)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    inner class PlanFlightViewHolder(private val binding: ItemPlanFlightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DetailPlanModel) {
            item.flight?.let {
                binding.flightTimeTextView.text =
                    it.departureAirport + " " + it.departureTime.convertIsoToTime() + " - " + it.arrivalAirport + " " +
                    it.arrivalTime.convertIsoToTime()
                binding.flightAirlineTextView.text =
                    it.airlineCode + it.flightNum
            }
        }
    }
    companion object {
        const val TYPE_PLACE = 0
        const val TYPE_MEMO = 1
    }
}
