package com.example.witt.data.model.socket.chat

import com.example.witt.domain.model.socket.chat.ChatModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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

fun ChatResponse.toChatModel() = ChatModel(
    TripId = TripId,
    ChatId = ChatId,
    UserId = UserId,
    Content = Content,
    createdAt = LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_DATE_TIME),
    updatedAt = LocalDateTime.parse(updatedAt, DateTimeFormatter.ISO_DATE_TIME),
    Nickname = Nickname,
    isWrittenByMe = isWrittenByMe,
    Type = Type
)
