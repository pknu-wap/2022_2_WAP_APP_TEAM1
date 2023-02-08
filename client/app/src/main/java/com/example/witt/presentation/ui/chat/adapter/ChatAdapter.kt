package com.example.witt.presentation.ui.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.witt.domain.model.socket.chat.ChatModel
import com.example.witt.databinding.ItemChatReceiverBinding
import com.example.witt.databinding.ItemChatSenderBinding
import java.time.format.DateTimeFormatter

class ChatAdapter : ListAdapter<ChatModel, RecyclerView.ViewHolder>(diffutil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
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
        when (holder) {
            is ChatAdapter.SentMessageHolder -> {
                if(position > 0){
                    val currentDate = currentList[position].createdAt.toLocalDate()
                    val previousDate = currentList[position - 1].createdAt.toLocalDate()
                    if(currentDate != previousDate){
                        holder.bind(currentList[position], true)
                        return
                    }
                }
                holder.bind(currentList[position], false)
            }
            is ChatAdapter.ReceivedMessageHolder -> {
                if(position > 0){
                    val currentDate = currentList[position].createdAt.toLocalDate()
                    val previousDate = currentList[position - 1].createdAt.toLocalDate()
                    if(currentDate != previousDate){
                        holder.bind(currentList[position], true)
                        return
                    }
                }
                holder.bind(currentList[position], false)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].isWrittenByMe) {
            SEND
        } else {
            RECEIVE
        }
    }

    inner class ReceivedMessageHolder(private val binding: ItemChatReceiverBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatModel, isDateChange: Boolean) {
            with(binding) {
                val timeStamp = item.createdAt.format(DateTimeFormatter.ofPattern(TIME_PATTERN))
                recevierTimeStampTextView.text = timeStamp
                recevierMessageTextView.text = item.Content
                receiverNameTextView.text = item.Nickname
                if(isDateChange){
                    val date = item.createdAt.format(DateTimeFormatter.ofPattern(DATE_PATTERN))
                    receiverDateTextView.visibility = View.VISIBLE
                    receiverDateTextView.text = date
                }
            }
        }
    }

    inner class SentMessageHolder(private val binding: ItemChatSenderBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatModel, isDateChange: Boolean) {
            with(binding) {
                val timeStamp = item.createdAt.format(DateTimeFormatter.ofPattern(TIME_PATTERN))
                senderTimeStampTextView.text = timeStamp
                senderMessageTextView.text = item.Content
                if(isDateChange){
                    val date = item.createdAt.format(DateTimeFormatter.ofPattern(DATE_PATTERN))
                    senderDateTextView.visibility = View.VISIBLE
                    senderDateTextView.text = date
                }
            }
        }
    }

    companion object {
        val diffutil = object : DiffUtil.ItemCallback<ChatModel>() {
            override fun areItemsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ChatModel, newItem: ChatModel): Boolean {
                return oldItem == newItem
            }
        }
        const val RECEIVE = 0
        const val SEND = 1
        const val DATE_PATTERN = "yyyy년 MM월 dd일"
        const val TIME_PATTERN = "a HH:mm"
    }
}
