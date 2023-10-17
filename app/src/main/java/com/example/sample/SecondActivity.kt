package com.example.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second);

        val name = intent.getStringExtra("name")
        val age = intent.getIntExtra("age", 0)
        val country = intent.getStringExtra("country")

        val textView = findViewById<TextView>(R.id.textView)

        textView.text = "Name: $name\nAge: $age\nCountry: $country"

        val btnBack = findViewById<Button>(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }

        val btnNewActivity = findViewById<Button>(R.id.btnNewActivity)

        btnNewActivity.setOnClickListener {
            Intent(this, ThirdActivity::class.java).also {
                startActivity(it)
            }
        }


        val contactDialog =
            AlertDialog.Builder(this).setTitle("Add to Contacts")
                .setIcon(R.drawable.ic_add_contact)
                .setMessage("Do you sure you want ot add $name to your contact?")
                .setPositiveButton("Yes") { _, _ ->
                    Toast.makeText(this, "Added to Contact", Toast.LENGTH_SHORT).show()
                }.setNegativeButton("No") { _, _ ->
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
                }.create()

        val options = arrayOf("First", "Second", "Last")
        val singleChoiceItemsDialog =
            AlertDialog.Builder(this).setSingleChoiceItems(options, 0) { dialog, which ->
                Toast.makeText(this, "You selected ${options[which]}", Toast.LENGTH_SHORT).show()
            }.setPositiveButton("Ok") { _, _ ->
                Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show()
            }.setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
            }.create()

        val multipleChoiceItemDialog = AlertDialog.Builder(this).setMultiChoiceItems(
            options,
            booleanArrayOf(false, false, false)
        ) { dialog, which, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "You selected ${options[which]}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "You unselected ${options[which]}", Toast.LENGTH_SHORT)
                    .show()
            }
        }.setPositiveButton("Ok") { _, _ ->
            Toast.makeText(this, "Ok", Toast.LENGTH_SHORT).show()
        }.setNegativeButton("Cancel") { _, _ ->
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()
        }.create()

        val btnDialog1 = findViewById<Button>(R.id.dialog1)
        val btnDialog2 = findViewById<Button>(R.id.dialog2)
        val btnDialog3 = findViewById<Button>(R.id.dialog3)

        btnDialog1.setOnClickListener {
            contactDialog.show();
        }

        btnDialog2.setOnClickListener {
            singleChoiceItemsDialog.show();
        }

        btnDialog3.setOnClickListener {
            multipleChoiceItemDialog.show();
        }

        val months = arrayOf(
            "January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"
        )

        val adapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, months
        )

        val spMonths = findViewById<Spinner>(R.id.spMonths)
        spMonths.adapter = adapter
        spMonths.setSelection(-1)


        spMonths.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    this@SecondActivity,
                    "You selected ${adapterView?.getItemAtPosition(position)}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Toast.makeText(this@SecondActivity, "Nothing selected", Toast.LENGTH_SHORT).show()
            }
        }
    }
}