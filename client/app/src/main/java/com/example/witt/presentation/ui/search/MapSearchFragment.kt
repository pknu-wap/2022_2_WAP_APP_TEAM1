package com.example.witt.presentation.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.witt.BuildConfig
import com.example.witt.R
import com.example.witt.data.api.DetailPlanService
import com.example.witt.data.model.search.PlaceModel
import com.example.witt.databinding.FragmentMapSearchBinding
import com.example.witt.presentation.base.BaseFragment
import com.example.witt.data.model.search.ResultSearchKeyword
import com.example.witt.presentation.ui.search.adapter.MapSearchAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MapSearchFragment: BaseFragment<FragmentMapSearchBinding>(R.layout.fragment_map_search){

    companion object{
        const val BASE_URL = "https://dapi.kakao.com"
        const val API_KEY = BuildConfig.KAKAO_REST_API_KEY
    }

    private val args by navArgs<MapSearchFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchKeywordInit()
    }

    private fun searchKeywordInit(){
        binding.editText.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                searchKeyword(binding.editText.text.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun initAdapter(data: List<PlaceModel>){
        val adapter = MapSearchAdapter(
            placeItemOnClick = {
                initNavigation(it)
            }
        )
        adapter.listData.clear()
        adapter.listData.addAll(data)
        binding.searchMapRecyclerView.adapter = adapter
        binding.searchMapRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initNavigation(place: PlaceModel){
        if( findNavController().currentDestination?.id == R.id.mapSearchFragment) {
            val direction = MapSearchFragmentDirections
                .actionMapSearchFragmentToAddPlaceFragment(day = args.dayId, place = place)
            findNavController().navigate(direction)
        }
    }

    private fun searchKeyword(keyword: String){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(DetailPlanService::class.java)
        val call = api.getSearchKeyword(API_KEY,keyword)
        CoroutineScope(Dispatchers.IO).launch{
            call.enqueue(object : Callback<ResultSearchKeyword> {
                override fun onResponse(
                    call: Call<ResultSearchKeyword>,
                    response: Response<ResultSearchKeyword>
                ) {
                    response.body()?.let{
                        initAdapter(it.documents)
                    }
                }
                override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                    Log.w("mapSearch", "통신 실패 : ${t.message}")
                }
            })
        }
    }
}
