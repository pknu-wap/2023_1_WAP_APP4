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

        //여기부터 boolean시작

        val testDialogButton = view.findViewById<Button>(R.id.cardview)

        testDialogButton.setOnClickListener {

            // Dialog 만들기
            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_yn, null)
            val dialogBuilder = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setTitle("Login Form")

            val alertDialog = dialogBuilder.show()

            val okButton = dialogView.findViewById<Button>(R.id.successButton1)
            okButton.setOnClickListener {

                Toast.makeText(requireContext(), "토스트 메시지", Toast.LENGTH_SHORT).show()
            }

            val noButton = dialogView.findViewById<Button>(R.id.closeButton1)
            noButton.setOnClickListener {
                alertDialog.dismiss()
            }
        }
        
        //여기가 boolean끝


        return view
        
        
    }
    private fun addDataToList(){
        ShopItemList.add(ShopItemData(R.drawable.itm_chair, "chair"))
        ShopItemList.add(ShopItemData(R.drawable.itm_armchair, "armchair"))
        ShopItemList.add(ShopItemData(R.drawable.itm_comfort_chair, "comfort chair"))
        ShopItemList.add(ShopItemData(R.drawable.itm_disabled_chair, "disabled chair"))
        ShopItemList.add(ShopItemData(R.drawable.itm_yellow_chair, "yellow chair"))

        ShopItemList.add(ShopItemData(R.drawable.itm_chair, "chair"))
        ShopItemList.add(ShopItemData(R.drawable.itm_armchair, "armchair"))
        ShopItemList.add(ShopItemData(R.drawable.itm_comfort_chair, "comfort chair"))
        ShopItemList.add(ShopItemData(R.drawable.itm_disabled_chair, "disabled chair"))
        ShopItemList.add(ShopItemData(R.drawable.itm_yellow_chair, "yellow chair"))


    }
    
    
    
}