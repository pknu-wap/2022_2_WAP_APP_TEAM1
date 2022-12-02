package com.example.witt.presentation.ui.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.witt.data.model.chat.ChatResponse
import com.example.witt.databinding.ItemChatReceiverBinding
import com.example.witt.databinding.ItemChatSenderBinding
import com.example.witt.utils.convertIsoToTime

class ChatAdapter : ListAdapter<ChatResponse, RecyclerView.ViewHolder>(diffutil){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType){
        SEND -> {
            val binding = ItemChatSenderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            SentMessageHolder(binding)
        }
        else -> {
            val binding = ItemChatReceiverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ReceivedMessageHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ChatAdapter.SentMessageHolder -> {
                holder.bind(currentList[position])
            }
            is ChatAdapter.ReceivedMessageHolder -> {
                holder.bind(currentList[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(currentList[position].isWrittenByMe){
            SEND
        }else{
            RECEIVE
        }
    }

    inner class ReceivedMessageHolder(private val binding: ItemChatReceiverBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ChatResponse){
            with(binding){
                recevierTimeStampTextView.text = item.createdAt.convertIsoToTime()
                recevierMessageTextView.text = item.Content
                receiverNameTextView.text = item.Nickname
            }
        }
    }

    inner class SentMessageHolder(private val binding: ItemChatSenderBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ChatResponse){
            with(binding){
                senderTimeStampTextView.text = item.createdAt.convertIsoToTime()
                senderMessageTextView.text = item.Content
            }
        }
    }

    companion object{
        const val RECEIVE = 0
        const val SEND = 1
        val diffutil = object : DiffUtil.ItemCallback<ChatResponse>(){
            override fun areItemsTheSame(oldItem: ChatResponse, newItem: ChatResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ChatResponse, newItem: ChatResponse): Boolean {
                return oldItem == newItem
            }
        }
    }
}