package com.example.witt.data.model.socket.chat

data class ChatResponse(
    val TripId: Int,
    val ChatId: Int,
    val UserId: String,
    val Content: String,
    val createdAt: String,
    val updatedAt: String,
    val Nickname: String,
    val isWrittenByMe: Boolean,
    val Type: Int
)
