package com.example.todolist.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.todolist.MainActivity
import com.example.todolist.R
import com.example.todolist.TamaCharacter

class MainFragment : Fragment() {

    private lateinit var feedButton: Button
    private lateinit var curChar: TamaCharacter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedButton = view.findViewById(R.id.feedButton)
        curChar = (activity as MainActivity).getChar()

        feedButton.setOnClickListener {
            curChar.feed()
            (activity as MainActivity).updateExperienceText()
        }
    }

}
