package edu.temple.namelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var names: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        names = mutableListOf("Kevin Shaply", "Stacey Lou", "Gerard Clear", "Michael Studdard", "Michelle Studdard")

        val spinner = findViewById<Spinner>(R.id.spinner)
        val nameTextView = findViewById<TextView>(R.id.textView)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val customAdapter = CustomAdapter(names, this)

        spinner.adapter = customAdapter
        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 in names.indices) {
                    nameTextView.text = names[p2]
                } else {
                    nameTextView.text = ""
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                nameTextView.text = ""
            }
        }

        deleteButton.setOnClickListener {
            val position = spinner.selectedItemPosition

            // Only delete when the selected position is valid.
            if (position in names.indices) {
                names.removeAt(position)
                customAdapter.notifyDataSetChanged()

                if (names.isEmpty()) {
                    nameTextView.text = ""
                    deleteButton.isEnabled = false
                    spinner.isEnabled = false
                } else {
                    // Select the next name, or the new last name.
                    val newPosition = minOf(position, names.lastIndex)
                    spinner.setSelection(newPosition)

                    // The position can stay the same even though the name changed.
                    nameTextView.text = names[newPosition]
                }
            }
        }
    }
}
