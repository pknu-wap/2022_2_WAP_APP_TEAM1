package com.example.witt.presentation.listener

interface ItemTouchHelperListener {
    fun onItemMove(from: Int, to: Int) : Boolean
}