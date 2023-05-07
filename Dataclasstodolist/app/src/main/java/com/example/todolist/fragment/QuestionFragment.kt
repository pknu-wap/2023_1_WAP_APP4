package com.example.todolist.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.todolist.R

class QuestionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val testDialogButton = view.findViewById<Button>(R.id.testDialog)

        testDialogButton.setOnClickListener {

            // Dialog 만들기
            val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_0, null)
            val dialogBuilder = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setTitle("Login Form")

            val alertDialog = dialogBuilder.show()

            val okButton = dialogView.findViewById<Button>(R.id.successButton)
            okButton.setOnClickListener {

                Toast.makeText(requireContext(), "토스트 메시지", Toast.LENGTH_SHORT).show()
            }

            val noButton = dialogView.findViewById<Button>(R.id.closeButton)
            noButton.setOnClickListener {
                alertDialog.dismiss()
            }
        }
    }
}
