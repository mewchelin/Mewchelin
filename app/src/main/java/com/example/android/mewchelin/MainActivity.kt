package com.example.android.mewchelin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    var fbAuth :FirebaseAuth?=null
    var fbFireStore:FirebaseFirestore?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fbAuth= FirebaseAuth.getInstance();
        fbFireStore= FirebaseFirestore.getInstance()

        if(true)
        {
            var userInfo=UserData()
            userInfo.uid = fbAuth?.uid
            userInfo.userId=fbAuth?.currentUser?.email

            fbFireStore?.collection("users")?.document(fbAuth?.uid.toString())?.set(userInfo)
        }

        val button : Button = findViewById(R.id.hello)
        button.setOnClickListener{
            val intent = Intent(this,ImageUpload::class.java)
            startActivity(intent);
        }

        val button_check : Button =findViewById(R.id.uploaded)
        button_check.setOnClickListener{
            val intent = Intent(this,checkImage::class.java)
            startActivity(intent);
        }

    }
}