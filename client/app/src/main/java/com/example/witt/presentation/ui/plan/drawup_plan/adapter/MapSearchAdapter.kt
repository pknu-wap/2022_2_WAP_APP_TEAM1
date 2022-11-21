package com.example.witt.presentation.ui.plan.drawup_plan.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.witt.R
import com.example.witt.data.model.search.Place
import com.example.witt.databinding.ItemMapSearchRecyclerBinding


class MapSearchAdapter : RecyclerView.Adapter<MapSearchAdapter.Holder>(){

    var listData = mutableListOf<Place>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMapSearchRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }
    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val place = listData[position]
        holder.setPlace(place)
        val bundle = bundleOf("place" to place)
        holder.itemView.setOnClickListener{ view -> view.findNavController().navigate(R.id.action_mapSearchFragment_to_placeInsertFragment,bundle)
        }
    }


    inner class Holder(val binding : ItemMapSearchRecyclerBinding) : RecyclerView.ViewHolder(binding.root){

        fun setPlace(place: Place){
            binding.textPlace.text = place.place_name
            binding.textCategory.text = if (place.category_group_name.isNullOrBlank()) "기타" else place.category_group_name
            binding.textAddress.text = place.address_name
        }
    }
}