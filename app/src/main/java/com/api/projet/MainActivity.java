package com.api.projet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.api.projet.backend.ConnexionAPI;
import com.api.projet.network.NetworkState;
import com.api.projet.network.client.ClientSocket;
import com.api.projet.ui.connexion.ConnexionViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.api.projet.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private static final int PICK_IMAGE = 100;
    ImageView ppImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if(!NetworkState.isConnected(this)){
            Toast.makeText(this, "Aucune connexion Internet. Certaines fonctionnalités peuvent ne pas être disponibles.", Toast.LENGTH_LONG).show();
        }

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ConnexionViewModel conn = new ViewModelProvider(this).get(ConnexionViewModel.class);
        Log.i("TESTSQTSTQ", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        conn.control(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        initProfile();
        loadImage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void initProfile() {
        View headerView = binding.navView.getHeaderView(0);

        TextView pseudoTextView = headerView.findViewById(R.id.pseudoTextView);
        ppImageView = headerView.findViewById(R.id.profileImageView);

        String displayName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        if (displayName != null) {
            pseudoTextView.setText(displayName);
            initListener(headerView);
        }else{
            Log.e("PSEUDODOODODODO", "CAOKAOKZOF MARCHE PASP DAPO");
        }
    }

    private void initListener(View headerView){
        ImageView logoutImageView = headerView.findViewById(R.id.logoutImageView);
        logoutImageView.setOnClickListener(v->{
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginMain.class);
            startActivity(intent);
        });


        ppImageView.setOnClickListener(v->{
            openGallery();
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imageUri = data.getData();
            ppImageView.setImageURI(imageUri);

            sendSocket(imageUri);
        }
    }

    private void sendSocket(Uri imageUri) {

        if (imageUri != null) {
            Picasso.get().load(imageUri).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String imageBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    JSONObject data = new JSONObject();
                    try {
                        data.put("imageBase64", imageBase64);
                        data.put("name", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        ClientSocket.emit("uploadImage", data);
                    } catch (JSONException e) {
                        Log.e("Erreur JSON", e.getMessage());
                    }
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Log.e("Picasso Error", "Failed to load image: " + e.getMessage());
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            });
        }
    }

    private void loadImage(){
        if(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl() != null){
            try{
                JSONObject data = new JSONObject();
                data.put("name", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                ClientSocket.emit("downloadImage", data);
            }catch (JSONException e){
                Log.e("ERREUR JSON", e.getMessage());
            }
        }

    }
    @Override
    public void onStart(){
        super.onStart();
        ClientSocket.on("imageUploadResponse", args->{
            try {
                JSONObject response = (JSONObject) args[0];
                String filePath = response.getString("filePath");
                Uri photoUri = Uri.parse(filePath);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setPhotoUri(photoUri).build();
                user.updateProfile(userProfileChangeRequest);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


        });
        ClientSocket.on("imageDownloadResponse", arqs->{
            try{
                JSONObject data = (JSONObject) arqs[0];
                Log.i("MESSAGEAGA ", data.getString("message"));
                String imageBase64 = data.getString("imageBase64");
                byte[] decodedString = Base64.decode(imageBase64, Base64.DEFAULT);

                Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ppImageView.setImageBitmap(decodedBitmap);
                    }
                });
            }catch (JSONException e){
                Log.e("ERREUR JSON", e.getMessage());
            }
        });
    }

}