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

data class TodoItem(val title: String, val task: String, var priorityNum: Int)

class ResultFragment : Fragment() {

    var todoList = mutableListOf<TodoItem>()
    val priorityOptions = arrayOf("1", "2", "3", "4", "5")

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
        val adapter = MyListAdapter(requireContext(), todoList)
        listView.adapter = adapter

        // FloatingActionButton 찾기
        val NewTask = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        // 클릭 이벤트 리스너 설정
        NewTask.setOnClickListener {
            // Dialog 만들기
            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_0, null)
            val dialogBuilder = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setTitle("새로운 할 일 추가")

            val alertDialog = dialogBuilder.show()

            // Spinner 찾기
            val priorityNumSpinner = dialogView.findViewById<Spinner>(R.id.priorityNumSpinner)

            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorityOptions)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            priorityNumSpinner.adapter = adapter

            val okButton = dialogView.findViewById<Button>(R.id.successButton)
            okButton.setOnClickListener {
                // 입력한 내용 가져오기
                val titleEditText = dialogView.findViewById<EditText>(R.id.editTitle)
                val taskEditText = dialogView.findViewById<EditText>(R.id.editTask)

                val priorityNum = priorityNumSpinner.selectedItem.toString().toInt()
                val title = ("${titleEditText.text.toString()} - $priorityNum")
                //val title = titleEditText.text.toString()
                val task = taskEditText.text.toString()

                // TodoItem 객체 생성
                val todoItem = TodoItem(title, task, priorityNum)

                // todoList에 추가
                todoList.add(todoItem)

                // todoList를 우선순위에 따라 소팅
                todoList.sortBy { it.priorityNum }

                // 어댑터 갱신
                adapter.notifyDataSetChanged()

                // ListView에 정렬된 todoList를 반영하기 위해 ListAdapter를 재설정
                listView.adapter = MyListAdapter(requireContext(), todoList)

                // 팝업 닫기
                alertDialog.dismiss()

                Toast.makeText(requireContext(), "할 일 추가됨", Toast.LENGTH_SHORT).show()
            }

            val noButton = dialogView.findViewById<Button>(R.id.closeButton)
            noButton.setOnClickListener {
                alertDialog.dismiss()
            }
        }


        // View 반환
        return view
    }

    inner class MyListAdapter(context: Context, data: MutableList<TodoItem>) :
        ArrayAdapter<TodoItem>(context, 0, data) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            // convertView를 재사용하거나 새로 생성하여 View 객체를 반환합니다.
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false)

            // 현재 position에 해당하는 데이터를 가져옵니다.
            val item = todoList[position]

            // 뷰에 데이터를 표시합니다.
            val titleView = view.findViewById<TextView>(R.id.todoTitleView)
            titleView.text = item.title
            titleView.setTextColor(ContextCompat.getColor(context, R.color.black))

            val textView = view.findViewById<TextView>(R.id.todoTextView)
            textView.text = item.task
            textView.setTextColor(ContextCompat.getColor(context, R.color.black))

            titleView.setOnClickListener {

                // 클릭한 항목 삭제
                todoList.removeAt(position)

                // 어댑터 갱신
                notifyDataSetChanged()
            }
            return view
        }
    }
}
