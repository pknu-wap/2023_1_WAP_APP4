package com.example.todolist.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R

class ShopFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ShopItemList : ArrayList<ShopItemData>
    private lateinit var ShopItemAdapter : ShopItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(requireContext() , 3) // requireContext() == this

        ShopItemList = ArrayList()
        addDataToList()

        ShopItemAdapter = ShopItemAdapter(ShopItemList) //오류 있으면 같은 이름 다른 ShopItemList 로 바꿔보기
        recyclerView.adapter = ShopItemAdapter //여기도 마찬가지.

        ShopItemAdapter.onItemClick = {
            Toast.makeText(view?.context, "abcd", Toast.LENGTH_SHORT).show() // toast 실험
        }


        return view
        
        
    }
    private fun addDataToList(){
        ShopItemList.add(ShopItemData(R.drawable.itm_chair, "chair", 0,100))
        ShopItemList.add(ShopItemData(R.drawable.itm_armchair, "armchair",0,100))
        ShopItemList.add(ShopItemData(R.drawable.itm_comfort_chair, "comfort chair",0,100))
        ShopItemList.add(ShopItemData(R.drawable.itm_disabled_chair, "disabled chair",0,200))
        ShopItemList.add(ShopItemData(R.drawable.itm_yellow_chair, "yellow chair",0,200))


    }
    
    
    
}