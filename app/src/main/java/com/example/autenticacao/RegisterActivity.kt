package com.example.autenticacao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val btnCadastrar:Button = findViewById(R.id.btnCadastrar)
        btnCadastrar.setOnClickListener {
            performSingUp()
        }

    }

    private fun performSingUp() {
        val email:EditText = findViewById(R.id.edtEmail)
        val senha:EditText = findViewById(R.id.edtSenha)

        if(email.text.isEmpty() || senha.text.isEmpty()){
            Toast.makeText(this, "Preencha os campos para concluir o registro!", Toast.LENGTH_SHORT).show()
            return
        }

        val inputEmail = email.text.toString()
        val inputSenha = senha.text.toString()

        auth.createUserWithEmailAndPassword(inputEmail,inputSenha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                    Toast.makeText(baseContext, "Autenticação Concluída com Sucesso!.",
                        Toast.LENGTH_SHORT).show()

                } else {
                    // If sign in fails, display a message to the user.

                    Toast.makeText(baseContext, "Autenticação Falhou!",
                        Toast.LENGTH_SHORT).show()

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error ocurred ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
            }
    }
}