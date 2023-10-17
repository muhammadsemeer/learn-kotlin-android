package com.example.sample

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class ThirdActivity : AppCompatActivity() {

    private var imageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnExit = findViewById<Button>(R.id.btnExit)
        val btnChoose = findViewById<Button>(R.id.btnChoose)
        imageView = findViewById<ImageView>(R.id.showImage)

        btnBack.setOnClickListener {
            finish();
        }

        btnExit.setOnClickListener {
            finishAffinity();
        }

        btnChoose.setOnClickListener {

            Intent(Intent.ACTION_PICK).also {
                it.type = "image/*"
                startActivityForResult(it, 0)
            }

        }

        requestPermissions()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode === Activity.RESULT_OK) {
            imageView?.setImageURI(data?.data)
        }
    }

    private fun hasReadContactsPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

    private fun hasWriteContactsPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions() {
        var permissionsToRequest = mutableListOf<String>()

        Log.d(
            "PERMISSION",
            "hasReadContactsPermission: ${hasReadContactsPermission()}"
        )

        Log.d(
            "PERMISSION",
            "hasWriteContactsPermission: ${hasWriteContactsPermission()}"
        )

        if (!hasReadContactsPermission()) {
            permissionsToRequest.add(Manifest.permission.READ_CONTACTS)
        }

        if (!hasWriteContactsPermission()) {
            permissionsToRequest.add(Manifest.permission.WRITE_CONTACTS)
        }

        if (permissionsToRequest.isNotEmpty()) {
            Log.d("PERMISSION", "Requesting permissions...")
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                0
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && grantResults.isNotEmpty()) {
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PERMISSION", "${permissions[i]} granted.")
                }
            }
        }

    }

}