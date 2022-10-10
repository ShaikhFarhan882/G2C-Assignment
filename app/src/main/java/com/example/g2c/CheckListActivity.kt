package com.example.g2c

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.g2c.adapter.CheckListAdapter
import com.example.g2c.adapter.ItemClickListener
import com.example.g2c.databinding.ActivityChecklistBinding
import com.example.g2c.model.CheckList
import com.google.firebase.database.*


class CheckListActivity : AppCompatActivity(),ItemClickListener {
    private lateinit var binding: ActivityChecklistBinding

    private lateinit var checkList: ArrayList<CheckList>

    private var databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().getReference("CheckList")

    private lateinit var checkListAdapter: CheckListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChecklistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkList = arrayListOf()

        getCheckList()

        binding.fabAdd.setOnClickListener {
            showAddDialog()
        }

        binding.checkListToBookingDetails.setOnClickListener {
            val intent = Intent(this,BookingDetailActivity::class.java)
            startActivity(intent)
        }
    }



    private fun showAddDialog() {

   /*     val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.edit_text_layout, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.checkItemName)
        val button = dialogLayout.findViewById<Button>(R.id.AddCheckItem)

        button.setOnClickListener {
            val checkListItem = editText.text.toString()
            editText.text.clear()
            addData(checkListItem)
        }
        with(builder) {
            setView(dialogLayout)
            show()
        }*/

        val dialogBinding = layoutInflater.inflate(R.layout.edit_text_layout,null)
        val myDialog = Dialog(this)
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.show()

        val editText = dialogBinding.findViewById<EditText>(R.id.checkItemName)
        val button = dialogBinding.findViewById<Button>(R.id.AddCheckItem)

        button.setOnClickListener {
            val checkListItem = editText.text.toString()
            editText.text.clear()
            addData(checkListItem)
            myDialog.dismiss()
        }


    }

    private fun showDeleteDialog(position : String) {

  /*      val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.delete_dialog, null)
        val button = dialogLayout.findViewById<Button>(R.id.DeleteItem)

        button.setOnClickListener {
         //Clearing the arrayList
            checkList.clear()

           databaseReference.child(position).removeValue().addOnCompleteListener {
               if(it.isSuccessful){
                   Toast.makeText(this@CheckListActivity,"Item Deleted",Toast.LENGTH_SHORT).show()
               } else {
                   Toast.makeText(this@CheckListActivity,"Something went wrong",Toast.LENGTH_SHORT).show()
               }
           }
        }
        with(builder) {
            setView(dialogLayout)
            show()
        }*/

        val dialogBinding = layoutInflater.inflate(R.layout.delete_dialog,null)
        val myDialog = Dialog(this)
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.show()

        val button = dialogBinding.findViewById<Button>(R.id.DeleteItem)
        button.setOnClickListener {
            //Clearing the arrayList
            checkList.clear()

            databaseReference.child(position).removeValue().addOnCompleteListener {
                if(it.isSuccessful){
                    Toast.makeText(this@CheckListActivity,"Item Deleted",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@CheckListActivity,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }

            myDialog.dismiss()
        }

    }

    private fun addData(checkListItem: String) {
        when {
            checkListItem.isEmpty() -> {
                Toast.makeText(this@CheckListActivity, "Enter CheckList Item", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                checkList.clear()

                val uniqueKey = databaseReference.push().key!!
                val checkList = CheckList(checkListItem,uniqueKey)

                databaseReference.child(uniqueKey).setValue(checkList).addOnCompleteListener {

                }.addOnSuccessListener {
                    Toast.makeText(this@CheckListActivity,
                        " Added CheckList Item",
                        Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this@CheckListActivity,
                        "Failed to add CheckList Item",
                        Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    private fun getCheckList() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val checkListItem = userSnapshot.getValue(CheckList::class.java)
                        checkList.add(checkListItem!!)
                    }

                    checkListAdapter = CheckListAdapter(checkList,this@CheckListActivity)
                    setRecyclerView(checkListAdapter)

                }
                else{
                    Toast.makeText(this@CheckListActivity,"No data exists",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun setRecyclerView(myadapter: CheckListAdapter) {
        binding.RecView.apply {
            layoutManager = LinearLayoutManager(this@CheckListActivity)
            adapter = myadapter
        }
    }

    //Onclick
    override fun onClick(checkList: CheckList) {
      val currentPosition = checkList.uniqueKey.toString()
        showDeleteDialog(currentPosition)
    }
}

