package com.example.g2c

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.g2c.databinding.ActivityChecklistBinding
import com.example.g2c.model.CheckList


class CheckListActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChecklistBinding
    private lateinit var checkList : ArrayList<CheckList>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChecklistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabAdd.setOnClickListener {
            showAddDialog()
        }
    }

    private fun showAddDialog(){
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.edit_text_layout,null)
        val editText= dialogLayout.findViewById<EditText>(R.id.checkItemName)
        val button = dialogLayout.findViewById<Button>(R.id.AddCheckItem)

        button.setOnClickListener {
            val checkItemName = editText.text.toString()
            Toast.makeText(this,checkItemName.toString(),Toast.LENGTH_SHORT).show()
        }
        with(builder){
            setView(dialogLayout)
            show()
        }

    }
}

