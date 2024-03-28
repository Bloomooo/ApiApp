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

import com.api.projet.entity.User;
import com.api.projet.network.NetworkState;
import com.api.projet.network.client.ClientSocket;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/**
 * Cette classe gère l'activité de connexion de l'application.
 * Les utilisateurs peuvent saisir leur email et leur mot de passe pour se connecter,
 * ainsi que passer à l'activité d'inscription s'ils n'ont pas de compte.
 * L'authentification est gérée via Firebase Authentication.
 */
public class LoginMain extends AppCompatActivity {

    // Instance de FirebaseAuth pour gérer l'authentification des utilisateurs
    private FirebaseAuth mAuth;

    // Écouteur d'état d'authentification pour suivre les changements d'état d'authentification
    private FirebaseAuth.AuthStateListener authStateListener;

    // EditText pour saisir l'email de l'utilisateur
    private EditText emailEditText;

    // EditText pour saisir le mot de passe de l'utilisateur
    private EditText passwordEditText;

    // Bouton pour se connecter à l'application
    private Button loginButton;

    // Bouton pour passer à l'activité d'inscription
    private Button signUpButton;

    /**
     * Méthode appelée lors de la création de l'activité.
     * Initialise les composants de l'interface utilisateur et configure les écouteurs.
     * Affiche un message si aucune connexion Internet n'est disponible.
     * @param bundle L'état de l'instance sauvegardée de cette activité, le cas échéant.
     */
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        // Vérifie la connectivité Internet
        if(!NetworkState.isConnected(this)){
            Toast.makeText(this, "Aucune connexion Internet. Certaines fonctionnalités peuvent ne pas être disponibles.", Toast.LENGTH_LONG).show();
        }else{
            setContentView(R.layout.login_activity);
            initComponent();
            initListener(loginButton, signUpButton);
        }
    }

    /**
     * Méthode appelée lorsque l'activité devient visible à l'utilisateur.
     * Ajoute l'écouteur d'état d'authentification pour suivre les changements d'état d'authentification.
     */
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    /**
     * Méthode appelée lorsque l'activité n'est plus visible à l'utilisateur.
     * Supprime l'écouteur d'état d'authentification.
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    /**
     * Initialise les composants de l'interface utilisateur.
     * Initialise également l'instance FirebaseAuth et configure l'écouteur d'état d'authentification.
     */
    private void initComponent(){
        this.mAuth = FirebaseAuth.getInstance();
        this.emailEditText = findViewById(R.id.emailLogin);
        this.passwordEditText = findViewById(R.id.passwordLogin);
        this.loginButton = findViewById(R.id.loginButton);
        this.signUpButton = findViewById(R.id.signupLoginButton);
        this.authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    ClientSocket.connectToServer(new User(user.getDisplayName()));
                    Intent intent = new Intent(LoginMain.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    /**
     * Initialise les écouteurs pour les boutons de connexion et d'inscription.
     * @param loginButton Bouton de connexion
     * @param signUpButton Bouton d'inscription
     */
    private void initListener(Button loginButton, Button signUpButton){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(isValidEmail(email)){
                    signIn(email, password);
                }else{
                    Toast.makeText(LoginMain.this, "Invalid format email !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirige vers l'activité d'inscription
                Intent signUpIntent = new Intent(LoginMain.this, SignUpMain.class);
                startActivity(signUpIntent);
            }
        });
    }

    /**
     * Tente de connecter l'utilisateur avec l'email et le mot de passe fournis.
     * Affiche un message Toast approprié en fonction du succès ou de l'échec de l'authentification.
     * @param email Email de l'utilisateur
     * @param password Mot de passe de l'utilisateur
     */
    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginMain.this, "Authentication successful.",
                                    Toast.LENGTH_SHORT).show();
                            Intent mainIntent = new Intent(LoginMain.this, MainActivity.class);
                            startActivity(mainIntent);
                        } else {
                            Toast.makeText(LoginMain.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Vérifie si une chaîne donnée est un email valide.
     * @param target Chaîne à vérifier
     * @return true si la chaîne est un email valide, sinon false
     */
    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}

