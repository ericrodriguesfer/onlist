package com.ufc.mobile.onlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register_user.*

class RegisterUserActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)

        registerUserAction.setOnClickListener({
            val nome = inputNameFormRegisterUser.text.toString();
            val email = inputEmailFormRegisterUser.text.toString();
            val senha = inputPasswordFormRegisterUser.text.toString();
            val confirmarSenha = inputConfirmPasswordFormRegisterUser.text.toString();
            val telefone = inputTelephoneFormRegisterUser.text.toString();
            val iniciais = inputInitialsFormRegisterUser.text.toString();

            if(nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty() || telefone.isEmpty() || iniciais.isEmpty()){
                Toast.makeText(this, "Por favor insira todos os dados", Toast.LENGTH_SHORT).show();
            }else if(!senha.equals(confirmarSenha)){
                Toast.makeText(this, "Por favor insira as duas senhas iguais", Toast.LENGTH_SHORT).show();
            }

            saveFirestore(nome, email, senha, telefone, iniciais);
        })
    }

    fun clickRegister (view: View) {
        val intentLogin = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)
    }

    fun clickLinkLoginUser (view: View) {
        val intentLogin = Intent(this, LoginActivity::class.java)
        startActivity(intentLogin)
    }

    fun saveFirestore(nome: String, email: String, senha: String, telefone: String, iniciais: String){
        val db = FirebaseFirestore.getInstance();
        val user: MutableMap<String, Any> = HashMap();
        user["nome"] = nome;
        user["email"] = email;
        user["senha"] = senha;
        user["telefone"] = telefone;
        user["iniciais"] = iniciais;

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            }
            .addOnFailureListener {
                Toast.makeText(this, "Erro ao cadastrar o usuário", Toast.LENGTH_SHORT).show();
            }
    }
}