package com.example.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TodoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        var todos = mutableListOf(
            Todo(
                "Wash Clothes",
                false
            ),
            Todo(
                "Study Android",
                true
            ),
            Todo(
                "Buy Groceries",
                false
            ),
        )

        val adapter = TodoAdapter(todos)

        val rv = findViewById<RecyclerView>(R.id.todoRecyclerView)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this)

        val addTodoButton = findViewById<Button>(R.id.addTodoButton)

        addTodoButton.setOnClickListener {
            val todoEditText = findViewById<EditText>(R.id.newTodoText)
            val todoText = todoEditText.text.toString()
            if (todoText.isNotBlank()) {
                todos.add(Todo(
                    todoText,
                    false
                ))
                adapter.notifyItemInserted(todos.size - 1)
                todoEditText.text.clear()
            }
        }
    }
}