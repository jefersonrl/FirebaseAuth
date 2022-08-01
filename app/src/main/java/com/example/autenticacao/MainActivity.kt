package com.example.autenticacao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth

        val registerText: TextView = findViewById(R.id.txtMessage)

        registerText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }

        val btnLogar:Button = findViewById(R.id.btnLogin)
        btnLogar.setOnClickListener {
            performLogin()
        }
    }

    private fun performLogin() {
        val email:TextView = findViewById(R.id.edtEmail)
        val senha:TextView = findViewById(R.id.edtSenha)

        if(email.text.isEmpty() || senha.text.isEmpty()){
            Toast.makeText(this, "Por favor preencher os campos para efetuar o Login!", Toast.LENGTH_SHORT).show()
            return
        }

        val emailInput = email.text.toString()
        val senhaInput = senha.text.toString()

        auth.signInWithEmailAndPassword(emailInput, senhaInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this, PrincipalActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(baseContext, "Autenticação realizada com sucesso!",
                        Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Autenticação falhou!",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(baseContext, "Autenticação falhou! ${it.localizedMessage}",
                    Toast.LENGTH_SHORT).show()
            }
    }
}