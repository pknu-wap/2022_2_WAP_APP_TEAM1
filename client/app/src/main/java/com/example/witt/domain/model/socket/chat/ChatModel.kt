package com.example.witt.domain.model.socket.chat

import java.time.LocalDateTime

data class ChatModel(
    val TripId: Int,
    val ChatId: Int,
    val UserId: String,
    val Content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val Nickname: String,
    val isWrittenByMe: Boolean,
    val Type: Int
)
