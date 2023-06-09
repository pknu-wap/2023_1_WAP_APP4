package com.example.todolist.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R

class ShopFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var ShopItemList: ArrayList<ShopItemData>
    private lateinit var ShopItemAdapter: ShopItemAdapter

    private lateinit var alertDialog: AlertDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            GridLayoutManager(requireContext(), 3)

        ShopItemList = ArrayList()
        addDataToList()

        ShopItemAdapter = ShopItemAdapter(ShopItemList)
        recyclerView.adapter = ShopItemAdapter

        ShopItemAdapter.onItemClick = {
/*            Toast.makeText(view?.context, "abcd", Toast.LENGTH_SHORT).show()*///토스트 실험
            alertDialog.show()
        }


        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gotoshop = view.findViewById<Button>(R.id.gotoShop)

        alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("구매 확인")
            .setMessage("정말 구매하시겠습니까?")
            .setPositiveButton("OK") { dialog, which ->
                Toast.makeText(requireContext(), "구매 완료", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                Toast.makeText(requireContext(), "구매 취소", Toast.LENGTH_SHORT).show()
            }
            .create()

        gotoshop.setOnClickListener {
            
        }
    }

    private fun addDataToList() {
        ShopItemList.add(ShopItemData(R.drawable.itm_chair, "chair", 0, 100))
        ShopItemList.add(ShopItemData(R.drawable.itm_armchair, "armchair", 0, 100))
        ShopItemList.add(ShopItemData(R.drawable.itm_comfort_chair, "comfort chair", 0, 100))
        ShopItemList.add(ShopItemData(R.drawable.itm_disabled_chair, "disabled chair", 0, 200))
        ShopItemList.add(ShopItemData(R.drawable.itm_yellow_chair, "yellow chair", 0, 200))


    }
}

   /*

   private fun showFilteredItems() {
        val filteredList = ArrayList<ShopItemData>()

        // 특정 조건에 맞는 데이터 필터링
        for (item in ShopItemList) {
            if (item.contains("Item 3") || item.contains("Item 4")) {
                filteredList.add(item)
            }
        }

        // 어댑터에 필터링된 데이터 설정
        ShopItemAdapter.clear()
        ShopItemAdapter.addAll(filteredList)
        ShopItemAdapter.notifyDataSetChanged()
    }

    class MyAdapter(context: Context, objects: List<String>) : ArrayAdapter<ShopItemData>(context, android.R.layout.simple_list_item_1, objects) {
        // clear() 메소드 추가
        override fun clear() {
            super.clear()
        }

        // addAll() 메소드 추가
        override fun addAll(collection: Collection<ShopItemData>) {
            super.addAll(collection)
        }
    }
    
    
*/