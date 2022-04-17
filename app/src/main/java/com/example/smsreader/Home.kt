package com.example.smsreader


import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
class Home : MessageListener,AppCompatActivity()
{
    private val permission: String = Manifest.permission.READ_SMS
    private val requestCode: Int = 1

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home2)


        recs.bindListener(this);
        if (ContextCompat.checkSelfPermission(this, permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {
            checkAndRequestPermissions();
            readSms()
        }



    }
    override fun messageReceived(message: String?) {
        Toast.makeText(this, "New Message Received: $message", Toast.LENGTH_SHORT).show()
    }
//    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context, intent: Intent) {
//            if (intent.action.equals("otp", ignoreCase = true)) {
//                val message = intent.getStringExtra("message")
//                val sender = intent.getStringExtra("Sender")
//                print(message!!.replace("\\D+".toRegex(), ""))
//                print("$sender : $message")
//                Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
//                Log.e("OTP MESSSGE", message)
//                print("JJJJJJJJJJJJJJJJJJJJJJJJJ")
//            }else {
//                print("oooooooooooooooooooooooo")
//            }}
//
//    }

    private fun checkAndRequestPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            val receiveSMS =
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
            val readSMS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
            val listPermissionsNeeded: MutableList<String> = ArrayList()
            if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS)
            }
            if (readSMS != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_SMS)
            }
            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(
                    this,
                    listPermissionsNeeded.toTypedArray(), 1
                )
                return false
            }
            return true
        }
        return true
    }

//    override fun onResume() {
//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, IntentFilter("otp"))
//        super.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
//    }
    private fun readSms()
    {
        val numberCol = Telephony.TextBasedSmsColumns.ADDRESS
        val textCol = Telephony.TextBasedSmsColumns.BODY
        val typeCol = Telephony.TextBasedSmsColumns.TYPE // 1 - Inbox, 2 - Sent

        val projection = arrayOf(numberCol, textCol, typeCol)
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)
        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view


        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview

        val cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            projection, null, null, null
        )

        val numberColIdx = cursor!!.getColumnIndex(numberCol)
        val textColIdx = cursor.getColumnIndex(textCol)
        val typeColIdx = cursor.getColumnIndex(typeCol)

        while (cursor.moveToNext()) {
            val number = cursor.getString(numberColIdx)
            val text = cursor.getString(textColIdx)
            val type = cursor.getString(typeColIdx)
            data.add(ItemViewModel(R.drawable.ic_baseline_person_pin_24, "" + number , text ))
            Log.d("MY_APP", "$number $text $type")
        }
        recyclerview.adapter = adapter
        cursor.close()
    }
}