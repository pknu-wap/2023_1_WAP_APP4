package com.example.todolist.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R

class ShopItemAdapter(private val ShopItemList:ArrayList<ShopItemData>) :
    RecyclerView.Adapter<ShopItemAdapter.ShopItemViewHolder>(){

    class ShopItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val imageView : ImageView = itemView.findViewById(R.id.imageView)
        val textView : TextView = itemView.findViewById(R.id.textView3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item,parent,false)
        return ShopItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ShopItemList.size
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItemData = ShopItemList[position]
        holder.imageView.setImageResource(shopItemData.image)
        holder.textView.text = shopItemData.name
        //만약 작동 안하면 shopItemData 첫글자 대문자로 바꾸기. 뒤에 언급된 두개도.
    }
}