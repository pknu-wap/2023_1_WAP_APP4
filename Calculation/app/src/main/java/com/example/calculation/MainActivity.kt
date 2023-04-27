package com.example.calculation

import android.content.Context
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.calculation.fragment.MainFragment
import com.example.calculation.fragment.QuestionFragment
import com.example.calculation.fragment.ResultFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var ExpProgressBar: ProgressBar
    private lateinit var experienceText: TextView
    private lateinit var failText: TextView

    private var curChar: TamaCharacter = Whale()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        // 1) ViewPager2 참조
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)

        // 2) FragmentStateAdapter 생성 : Fragment 여러개를 ViewPager2에 연결해주는 역할
        val viewpagerFragmentAdapter = ViewpagerFragmentAdapter(this)

        // 3) ViewPager2의 adapter에 설정
        viewPager.adapter = viewpagerFragmentAdapter

        val tamaCharacterView = findViewById<ImageView>(R.id.charView)
        val curChar = Whale()
        tamaCharacterView.setImageResource(curChar.getDrawable())
        curChar.runAnimation(tamaCharacterView)

        failText = findViewById<TextView>(R.id.failText)
        updateFailText()

        ExpProgressBar = findViewById<ProgressBar>(R.id.expBar)
        updateExpProgressBar()

        experienceText = findViewById<TextView>(R.id.expText)
        updateExperienceText()



    }
    fun updateExpProgressBar() {
        ExpProgressBar.progress = curChar.state[1]
    }

    fun updateExperienceText() {
        val exp = curChar.state[1]
        val text = "Experience: $exp/100"
        experienceText.text = text
    }

    fun updateFailText(){
        val fail = curChar.state[2]
        val text = "Fail : $fail"
        failText.text = text
    }

    fun getChar(): TamaCharacter {
        return curChar
    }
}

class ViewpagerFragmentAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private val fragmentList = listOf<Fragment>(MainFragment(), QuestionFragment(), ResultFragment())

    override fun getItemCount(): Int {
        return fragmentList.size
    }
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}

class TodoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃 파일 inflate
        val view = inflater.inflate(R.layout.fragment_layout, container, false)

        // ListView 찾기
        val listView = view.findViewById<ListView>(R.id.todoList)

        // 데이터 준비
        val title = mutableListOf("TestTitle 1", "TestTitle 2", "TestTitle 3")
        val task = mutableListOf("TestTask 1", "TestTask 2", "TestTask 3")
        val completedTask: MutableList<String>? = mutableListOf()


        // 어댑터 생성 및 ListView와 연결
        var adapter = MyListAdapter(requireContext(), title, task)
        listView.adapter = adapter

        // FloatingActionButton 찾기
        val NewTask = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        // 클릭 이벤트 리스너 설정
        NewTask.setOnClickListener {
            // 새로운 할 일 추가
            val newData = "TestTask ${task.size + 1}"
            val newTitle = "TestTitle ${title.size + 1}"
            task.add(newData)
            title.add(newTitle)
            adapter.notifyDataSetChanged()
        }



        // View 반환
        return view
    }

    class MyListAdapter(context: Context, title: MutableList<String>, data: MutableList<String>) : ArrayAdapter<String>(context, 0, data) {

        private val mTitle = title

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

            // 리스트뷰의 항목이 클릭되었을 때
            view.setOnClickListener {
                // 선택한 항목의 titleView에 취소선 추가
                titleView.paintFlags = titleView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }

            return view
        }
    }
}
