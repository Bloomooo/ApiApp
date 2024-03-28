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
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Activité gérant l'inscription des utilisateurs.
 */
public class SignUpMain extends AppCompatActivity {

    private FirebaseAuth mAuth; // Instance de FirebaseAuth pour gérer l'authentification
    private EditText emailEditText; // Champ de texte pour l'adresse e-mail
    private EditText passwordEditText; // Champ de texte pour le mot de passe
    private EditText usernameEditText; // Champ de texte pour le nom d'utilisateur
    private Button signUpButton; // Bouton pour l'inscription

    /**
     * Méthode onCreate, appelée lors de la création de l'activité.
     * Initialise les composants de l'interface utilisateur et les écouteurs d'événements.
     * @param bundle Un objet Bundle qui contient des données potentiellement fournies à l'activité lors de sa création.
     */
    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        if(!NetworkState.isConnected(this)){
            Toast.makeText(this, "Aucune connexion Internet. Certaines fonctionnalités peuvent ne pas être disponibles.", Toast.LENGTH_LONG).show();
        }else{
            setContentView(R.layout.signup_activity);
            initComponent();
            initListener(signUpButton);
        }
    }

    /**
     * Initialise les composants de l'interface utilisateur.
     */
    private void initComponent(){
        this.mAuth = FirebaseAuth.getInstance();
        this.emailEditText = findViewById(R.id.emailSignUp);
        this.passwordEditText = findViewById(R.id.passwordSignUp);
        this.usernameEditText = findViewById(R.id.usernameSignUp);
        this.signUpButton = findViewById(R.id.signupButton);
    }

    /**
     * Initialise les écouteurs d'événements pour les boutons.
     * @param signUpButton Le bouton d'inscription
     */
    private void initListener(Button signUpButton){
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                if(isValidEmail(email)){
                    createUser(email, password, username);
                }else{
                    Toast.makeText(SignUpMain.this, "Format d'email invalide !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Crée un nouvel utilisateur avec l'adresse email et le mot de passe fournis.
     * @param email L'adresse email de l'utilisateur
     * @param password Le mot de passe de l'utilisateur
     * @param username Le nom d'utilisateur de l'utilisateur
     */
    private void createUser(String email, String password, String username){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(username)
                                    .build();
                            user.updateProfile(profileUpdates);
                            Toast.makeText(SignUpMain.this, "Authentification réussie.",
                                    Toast.LENGTH_SHORT).show();
                            ClientSocket.connectToServer(new User(username));
                            Intent mainIntent = new Intent(SignUpMain.this, MainActivity.class);
                            startActivity(mainIntent);
                        } else {
                            Toast.makeText(SignUpMain.this, "Échec de l'authentification.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Vérifie si une adresse email est valide.
     * @param target L'adresse email à vérifier
     * @return true si l'adresse email est valide, sinon false
     */
    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
