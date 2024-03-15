package com.api.projet;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpMain extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText;

    private EditText passwordEditText;

    private Button signUpButton;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.signup_activity);
        initComponent();
        initListener(signUpButton);
    }

    private void initComponent(){
        this.mAuth = FirebaseAuth.getInstance();
        this.emailEditText = findViewById(R.id.emailSignUp);
        this.passwordEditText = findViewById(R.id.passwordSignUp);
        this.signUpButton = findViewById(R.id.signupButton);
    }

    private void initListener(Button signUpButton){
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(isValidEmail(email)){
                    createUser(email, password);
                    Intent mainIntent = new Intent(SignUpMain.this, MainActivity.class);
                    startActivity(mainIntent);
                }else{
                    Toast.makeText(SignUpMain.this, "Invalid format email !", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(SignUpMain.this, "Authentication successful.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpMain.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
