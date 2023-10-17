package com.example.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


fun Toast.showCustomToast(message: String, activity: Activity) {
    val layout = activity.layoutInflater.inflate(
        R.layout.custom_toast,
        null
    )

    // set the text of the TextView of the message
    val textView = layout.findViewById<TextView>(R.id.toastMessage)
    textView.text = message

    // use the application extension function
    this.apply {
        setGravity(Gravity.BOTTOM, 0, 40)
        duration = Toast.LENGTH_LONG
        view = layout
        show()
    }
}


class MainActivity : AppCompatActivity() {

    private var currentToast: Toast? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val applyButton = findViewById<Button>(R.id.applyButton)
        val nameEditText = findViewById<EditText>(R.id.name)
        val ageEditText = findViewById<EditText>(R.id.age)
        val countryEditText = findViewById<EditText>(R.id.country)

        applyButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val age = ageEditText.text.toString().toIntOrNull() ?: 0
            val country = countryEditText.text.toString()

            currentToast?.cancel();
            if (name.isBlank() || age <= 0 || country.isBlank()) {
                currentToast = Toast.makeText(this, "Fill required values!!", Toast.LENGTH_SHORT)
                currentToast?.show()
            } else {
                Intent(this, SecondActivity::class.java).also {
                    it.putExtra("name", name)
                    it.putExtra("age", age.toInt())
                    it.putExtra("country", country)
                    startActivity(it)
                }
            }
        }

        val showOrHide = findViewById<CheckBox>(R.id.showOrHide)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)

        showOrHide.setOnCheckedChangeListener { _, isChecked: Boolean ->
            showOrHideImage(isChecked, imageView)
        }

        // this is for initial load
        changeImageSourceBasedOnRadio(radioGroup.checkedRadioButtonId, imageView)
        showOrHideImage(showOrHide.isChecked, imageView)

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            changeImageSourceBasedOnRadio(checkedId, imageView)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> Intent(Settings.ACTION_SETTINGS).also {
                startActivity(it)
            }

            R.id.exit -> finishAffinity()
        }
        return true
    }

    private fun changeImageSourceBasedOnRadio(checkedId: Int, imageView: ImageView) {
        if (checkedId == R.id.radioImage1) {
            imageView.setImageResource(R.drawable.cartoon)
        } else {
            imageView.setImageResource(R.drawable.cartoon1)
        }
    }


    private fun showOrHideImage(status: Boolean, imageView: ImageView) {
        currentToast?.cancel();
        var toastMessage: String? = null
        if (status) {
            imageView.visibility = View.VISIBLE
            toastMessage = "Image Showed"
        } else {
            imageView.visibility = View.INVISIBLE
            toastMessage = "Image Hidden"
        }

        currentToast = Toast(this)

        if (toastMessage.isNotBlank()) currentToast?.showCustomToast(toastMessage, this)
    }
}