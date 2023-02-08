package com.example.witt.presentation.ui.search.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.witt.data.model.remote.detail_plan.search.PlaceModel
import com.example.witt.databinding.ItemMapSearchRecyclerBinding

class MapSearchAdapter(
    val placeItemOnClick: (PlaceModel) -> Unit
) : RecyclerView.Adapter<MapSearchAdapter.Holder>() {

    val listData = mutableListOf<PlaceModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemMapSearchRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }
    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val placeItem = listData[position]
        holder.setPlace(placeItem)
    }

    inner class Holder(val binding: ItemMapSearchRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setPlace(place: PlaceModel) {
            binding.textPlace.text = place.place_name
            binding.textCategory.text = place.category_group_name.ifBlank { "기타" }
            binding.textAddress.text = place.address_name
            binding.timePlanCardView.setOnClickListener {
                placeItemOnClick(place)
            }
        }
    }
}
