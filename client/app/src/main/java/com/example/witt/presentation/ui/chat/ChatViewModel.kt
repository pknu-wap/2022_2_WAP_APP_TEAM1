package com.example.witt.presentation.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.witt.data.model.socket.chat.ChatResponse
import com.example.witt.domain.repository.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import io.socket.client.Socket
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val socket: Socket
) : ViewModel() {

    private val chatList = mutableListOf<ChatResponse>()
    private val gson by lazy { Gson() }

    private val _chatData: MutableLiveData<List<ChatResponse>> = MutableLiveData(emptyList())
    val chatData: LiveData<List<ChatResponse>> get() = _chatData

    fun connectServer(tripId: Int) {
        viewModelScope.launch {
            userRepository.getUserInfo().mapCatching { response ->
                socket.on("evtJoin") { args ->
                    viewModelScope.launch {
                        val data = GsonBuilder().create().fromJson(
                            args[0].toString(),
                            Array<ChatResponse>::class.java
                        ).toList()
                        data.forEach {
                            chatList.add(it)
                        }
                        _chatData.value = chatList
                    }
                }

                socket.on("evtMessage") { args ->
                    viewModelScope.launch {
                        chatList.add(gson.fromJson(args[0].toString(), ChatResponse::class.java))
                        _chatData.value = chatList
                    }
                }
                socket.connect()

                val joinJson = JSONObject()
                joinJson.put("TripId", tripId)
                joinJson.put("UserId", response.user.userId)
                socket.emit("join", joinJson)
            }.onFailure { e ->
                e.printStackTrace()
            }
        }
    }

    fun sendChat(chat: String) {
        val chatJson = JSONObject()
        chatJson.put("Content", chat)
        socket.emit("addMessage", chatJson)
    }

    fun leaveChat() {
        socket.emit("leave")
    }
}
