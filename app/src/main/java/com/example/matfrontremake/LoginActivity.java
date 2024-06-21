package com.example.matfrontremake;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText senhaEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        senhaEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String senha = senhaEditText.getText().toString().trim();

                if (isValidEmail(email)) {
                    // Sucesso no login, iniciar a próxima atividade
                    if(isValidSenha(senha)){
                        Intent intent = new Intent(LoginActivity.this, SubjectActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Senha inválida", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Por favor, insira um email válido", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private boolean isValidSenha(String senha){
        return !TextUtils.isEmpty(senha) && !senha.equals("false");
    }
    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
