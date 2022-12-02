package com.example.witt.presentation.ui.chat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.witt.R
import com.example.witt.databinding.FragmentChatBinding
import com.example.witt.presentation.base.BaseFragment
import com.example.witt.presentation.ui.chat.adapter.ChatAdapter
import com.example.witt.presentation.ui.plan.PlanViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : BaseFragment<FragmentChatBinding>(R.layout.fragment_chat) {

    private val viewModel : ChatViewModel by viewModels()
    private val planViewModel: PlanViewModel by activityViewModels()

    private lateinit var chatAdapter : ChatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observeData()
        initViews()
    }

    private fun observeData(){
        planViewModel.planState.observe(viewLifecycleOwner){
            viewModel.connectServer(it.TripId)
        }

        viewModel.chatData.observe(viewLifecycleOwner){
            chatAdapter.submitList(it)
        }
    }

    private fun initViews(){
        binding.addChatButton.setOnClickListener{
            viewModel.sendChat(binding.addChatsEditText.text.toString())
            binding.addChatsEditText.setText("")
        }
        binding.chatToDrawUpButton.setOnClickListener{
            val direction = ChatFragmentDirections.actionChatFragmentToDrawUpPlanFragment()
            findNavController().navigate(direction)
        }
        chatAdapter = ChatAdapter()
        binding.chatRecyclerView.adapter = chatAdapter
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.leaveChat()
    }

}