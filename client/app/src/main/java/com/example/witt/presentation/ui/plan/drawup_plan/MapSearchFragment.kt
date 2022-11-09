package com.example.witt.presentation.ui.plan.drawup_plan

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.witt.R
import com.example.witt.data.api.KakaoAPI
import com.example.witt.databinding.FragmentMapSearchBinding
import com.example.witt.presentation.base.BaseFragment
import com.example.witt.presentation.ui.plan.drawup_plan.adapter.ResultSearchKeyword
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MapSearchFragment: BaseFragment<FragmentMapSearchBinding>(R.layout.fragment_map_search){
    companion object{
        const val BASE_URL = "https://dapi.kakao.com"
        const val API_KEY = "KakaoAK f31eb4b51b8e5890b0a4915324a9b19e"
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchKeywordInit()

    }

    private fun searchKeywordInit(){
        binding.editText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                searchKeyword(binding.editText.text.toString())
            }
        })
    }

    private fun searchKeyword(keyword: String){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(KakaoAPI::class.java)
        val call = api.getSearchKeyword(API_KEY,keyword)

        call.enqueue(object : Callback<ResultSearchKeyword>{
            override fun onResponse(
                call: Call<ResultSearchKeyword>,
                response: Response<ResultSearchKeyword>
            ) {
                Log.d("Test","Raw:${response.raw()}")
                Log.d("Test","body:${response.body()}")
            }

            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                Log.w("mapSearch","통신 실패 : ${t.message}")
            }
        })
    }
}

