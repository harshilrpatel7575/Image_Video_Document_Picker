package com.example.demo2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demo2.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var GSO: GoogleSignInOptions
    private lateinit var GSC:GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GSO = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        GSC= GoogleSignIn.getClient(this,GSO)

        val account:GoogleSignInAccount?= GoogleSignIn
            .getLastSignedInAccount(this)

        if (account!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
         binding.GsignInButton.setOnClickListener{
             goToSignIn()
         }

    }

    private fun goToSignIn() {
        val signInIntent=GSC.signInIntent

        startActivityForResult(signInIntent,10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==10){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)

            try{
                task.getResult(ApiException::class.java)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            catch (e: java.lang.Exception){
                Toast.makeText(this,e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

//    private fun goToMain() {
//        val intent= Intent(this,MainActivity::class.java)
//        startActivity(intent)
//        finish()
//    }
}