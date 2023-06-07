package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.todolist.R
import com.example.todolist.fragment.MainFragment
import com.example.todolist.fragment.QuestionFragment
import com.example.todolist.fragment.ResultFragment
import com.example.todolist.fragment.ShopFragment

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

    private val fragmentList = listOf<Fragment>(MainFragment(), QuestionFragment(), ResultFragment(),ShopFragment())

    override fun getItemCount(): Int {
        return fragmentList.size
    }
    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}

