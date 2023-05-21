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

data class TodoItem(var title: String, var task: String, var priorityNum: Int)

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
            val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false)
            val item = todoList[position]

            val titleView = view.findViewById<TextView>(R.id.todoTitleView)
            titleView.text = item.title
            titleView.setTextColor(ContextCompat.getColor(context, R.color.black))

            val textView = view.findViewById<TextView>(R.id.todoTextView)
            textView.text = item.task
            textView.setTextColor(ContextCompat.getColor(context, R.color.black))

            val editButton = view.findViewById<ImageButton>(R.id.editButton)
            editButton.setOnClickListener {
                showEditDeleteDialog(position)
            }

            return view
        }

        private fun showEditDeleteDialog(position: Int) {
            val options = arrayOf("수정", "삭제")
            val dialogBuilder = AlertDialog.Builder(context)
            dialogBuilder.setTitle("옵션 선택")
                .setItems(options) { dialog, which ->
                    when (which) {
                        0 -> showEditDialog(position)
                        1 -> deleteItem(position)
                    }
                }
            val alertDialog = dialogBuilder.create()
            alertDialog.show()
        }

        private fun showEditDialog(position: Int) {
            val item = todoList[position]

            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_0, null)
            val titleEditText = dialogView.findViewById<EditText>(R.id.editTitle)
            val taskEditText = dialogView.findViewById<EditText>(R.id.editTask)
            val priorityNumSpinner = dialogView.findViewById<Spinner>(R.id.priorityNumSpinner)

            titleEditText.setText(item.title.substringBeforeLast("-").trim())
            taskEditText.setText(item.task)

            val adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                priorityOptions
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            priorityNumSpinner.adapter = adapter

            priorityNumSpinner.setSelection(item.priorityNum - 1)

            val dialogBuilder = AlertDialog.Builder(context)
                .setView(dialogView)
                .setTitle("할 일 수정")

            val okButton = dialogView.findViewById<Button>(R.id.successButton)
            val alertDialog = dialogBuilder.create()
            okButton.setOnClickListener {
                // 수정된 내용 적용
                val editedTitle = titleEditText.text.toString()
                val editedTask = taskEditText.text.toString()
                val editedPriorityNum = priorityNumSpinner.selectedItem.toString().toInt()

                item.title = ("${editedTitle} - ${editedPriorityNum}")
                item.task = editedTask
                item.priorityNum = editedPriorityNum

                // todoList를 우선순위에 따라 소팅
                todoList.sortBy { it.priorityNum }

                // 데이터 갱신
                notifyDataSetChanged()
                Toast.makeText(context, "할 일이 수정되었습니다.", Toast.LENGTH_SHORT).show()

                // 다이얼로그 닫기
                alertDialog.dismiss()
            }
            alertDialog.show()

            val noButton = dialogView.findViewById<Button>(R.id.closeButton)
            noButton.setOnClickListener {
                alertDialog.dismiss()
            }
        }

        private fun deleteItem(position: Int) {
            val item = todoList[position]

            // 삭제 확인 팝업 띄우기
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle("삭제 확인")
            alertDialogBuilder.setMessage("Task를 삭제하시겠습니까?")
            alertDialogBuilder.setPositiveButton("예") { dialog, which ->
                // 사용자가 확인을 선택한 경우
                todoList.removeAt(position)
                notifyDataSetChanged()
                Toast.makeText(context, "할 일이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
            alertDialogBuilder.setNegativeButton("아니오", null)
            alertDialogBuilder.show()
        }
    }
}