package com.mugan86.qrscanner

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat



class MainActivity : AppCompatActivity() {

    private val ZXING_CAMERA_PERMISSION = 1
    private var mClss: Class<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openScanner.setOnClickListener({
            launchActivity(ScannerViewActivity::class.java)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        val barcode = data.extras.getString("BarCode")

        if (barcode == "") {
            Toast.makeText(this@MainActivity,"Bar code not found",Toast.LENGTH_LONG).show()
        } else {
            bar_code_id_txt?.text = barcode
        }
    }

    private fun launchActivity(clss: Class<*>) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            mClss = clss
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CAMERA), ZXING_CAMERA_PERMISSION)
        } else {
            val intent = Intent(this, clss)
            startActivityForResult(intent, 2)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            ZXING_CAMERA_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.
                    Toast.makeText(this, "Press again button to scan code", Toast.LENGTH_LONG).show()


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }
        }// other 'switch' lines to check for other
        // permissions this app might request
    }


}
