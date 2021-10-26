package com.example.android.mewchelin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {
    private var auth : FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        signup_button.setOnClickListener{
            signUp()
        }
    }

    private fun signUp() {
        auth?.createUserWithEmailAndPassword(email_editText_signup.text.toString(), password_editText_signup.text.toString())
            ?.addOnCompleteListener {
                    task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Sign up success!", Toast.LENGTH_LONG).show()
                    moveToLoginPage(task.result?.user)
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun moveToLoginPage(user: FirebaseUser?) {
        if(user != null) {
            startActivity(Intent(this, LoginActivity::class.java))

        }
    }
}