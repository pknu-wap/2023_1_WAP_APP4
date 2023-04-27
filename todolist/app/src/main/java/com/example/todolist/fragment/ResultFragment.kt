package com.example.todolist.fragment

import android.app.AlertDialog
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.todolist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ResultFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 파일 inflate
        val view = inflater.inflate(R.layout.fragment_result, container, false)

        // ListView 찾기
        val listView = view.findViewById<ListView>(R.id.todoList)

        // 데이터 준비
        val title = mutableListOf<String>()
        val task = mutableListOf<String>()


        // 어댑터 생성 및 ListView와 연결
        var adapter = MyListAdapter(requireContext(), title, task)
        listView.adapter = adapter

        // FloatingActionButton 찾기
        val NewTask = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        // 클릭 이벤트 리스너 설정
        NewTask.setOnClickListener {
            // 팝업 띄우기
            val builder = AlertDialog.Builder(NewTask.context)
            builder.setTitle("새로운 Task 추가")

            // 할 일 입력받을 EditText 추가
            val inputTask = EditText(NewTask.context)
            inputTask.hint = "내용을 입력하세요"
            builder.setView(inputTask)

            // 제목 입력받을 EditText 추가
            val inputTitle = EditText(NewTask.context)
            inputTitle.hint = "제목을 입력하세요"
            builder.setView(
                LinearLayout(NewTask.context).apply {
                    orientation = LinearLayout.VERTICAL
                    addView(inputTitle)
                    addView(inputTask)
                }
            )

            builder.setPositiveButton("추가") { _, _ ->
                // 입력한 내용을 task와 title 리스트에 추가
                val newData = inputTask.text.toString()
                val newTitle = inputTitle.text.toString()
                task.add(newData)
                title.add(newTitle)
                adapter.notifyDataSetChanged()
            }
            builder.setNegativeButton("취소") { dialog, _ -> dialog.cancel() }
            builder.show()
        }





        // View 반환
        return view
    }

    class MyListAdapter(context: Context, title: MutableList<String>, data: MutableList<String>) : ArrayAdapter<String>(context, 0, data) {

        private val mTitle = title
        private val listView = ListView(context)

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            // convertView를 재사용하거나 새로 생성하여 View 객체를 반환합니다.
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false)

            // 현재 위치(position)에 해당하는 데이터를 가져옵니다.
            val title = mTitle[position]
            val item = getItem(position)

            // 뷰에 데이터를 표시합니다.
            val titleView = view.findViewById<TextView>(R.id.todoTitleView)
            titleView.text = title
            titleView.setTextColor(ContextCompat.getColor(context, R.color.black)) // 기본 색상

            val textView = view.findViewById<TextView>(R.id.todoTextView)
            textView.text = item
            textView.setTextColor(ContextCompat.getColor(context, R.color.black)) // 기본 색상

            // 항목 클릭 이벤트 처리
            listView.onItemClickListener = AdapterView.OnItemClickListener {
                
            }

            return view
        }
    }



}
