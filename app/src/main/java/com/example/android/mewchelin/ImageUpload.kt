package com.example.android.mewchelin

import android.app.Activity
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle;
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button;
import android.widget.ImageView
import android.widget.Toast;
import com.example.android.mewchelin.R.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.core.View
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_image_upload.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class ImageUpload : AppCompatActivity() {

    private var viewProfile: View? = null
    var pickImageFromAlbum = 0;
    var fbStorage: FirebaseStorage? = null
    var fbAuth:FirebaseAuth?=null
    var fbFireStore: FirebaseFirestore?=null
    var currentImageURL: Uri? = null


    val OPEN_GALLERY = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_image_upload)
        val button: Button = findViewById(id.submit_button)
        button.setOnClickListener {
            funImageUpload()
        }

        val button_cancle: Button = findViewById(id.cancel_button)
        button_cancle.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
        }

        val button_add: Button = findViewById(id.add_pic);
        button_add.setOnClickListener {
            openGallery()
        }

    }
    private fun funImageUpload(){
        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imgFileName="IMAGE_"+timeStamp+"_.png"
        var storageRef=fbStorage?.reference?.child("images")?.child(imgFileName)
        storageRef?.putFile(currentImageURL!!)?.addOnSuccessListener {
            Toast.makeText(this.applicationContext,"Image Uploaded",Toast.LENGTH_SHORT).show();
        }
    }

    private fun openGallery() {
        val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.setType("image/*")
        startActivityForResult(intent, OPEN_GALLERY)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            if(requestCode==OPEN_GALLERY) {
                currentImageURL = data?.data
                image_area.setImageURI(currentImageURL);
                fbStorage = FirebaseStorage.getInstance()
                    try {
                        val bitmap =
                            MediaStore.Images.Media.getBitmap(contentResolver, currentImageURL)
                        val image_area: ImageView = findViewById(id.image_area);
                        image_area.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
        }else{
            Log.d("ActivityResult","Something wrong");
        }
    }

}