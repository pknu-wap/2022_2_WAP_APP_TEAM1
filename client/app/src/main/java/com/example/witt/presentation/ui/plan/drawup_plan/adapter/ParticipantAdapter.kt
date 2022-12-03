package com.example.witt.presentation.ui.plan.drawup_plan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.witt.R
import com.example.witt.databinding.ItemParticipantBinding
import com.example.witt.domain.model.remote.plan.get_plan.PlanParticipantModel

class ParticipantAdapter
    : ListAdapter<PlanParticipantModel, ParticipantAdapter.PlanParticipantViewHolder>(diffutil) {

    companion object{
        val diffutil = object : DiffUtil.ItemCallback<PlanParticipantModel>(){
            override fun areItemsTheSame(oldItem: PlanParticipantModel, newItem: PlanParticipantModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PlanParticipantModel, newItem: PlanParticipantModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanParticipantViewHolder {
        return PlanParticipantViewHolder(ItemParticipantBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: PlanParticipantViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class PlanParticipantViewHolder(private val binding: ItemParticipantBinding)
    : RecyclerView.ViewHolder(binding.root){
        fun bind(item: PlanParticipantModel){
            binding.itemParticipantName.text = item.nickName
            Glide.with(binding.itemParticipantImage)
                .load(item.profileImage)
                .placeholder(R.drawable.penguin)
                .into(binding.itemParticipantImage)
        }
    }
}