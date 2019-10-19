package com.thiga.strathbot.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonObject;
import com.thiga.strathbot.MainActivity;
import com.thiga.strathbot.R;
import com.thiga.strathbot.api.ApiService;
import com.thiga.strathbot.api.ApiUrl;
import com.thiga.strathbot.models.Result;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login";
    private Context context = LoginActivity.this;

    private ImageView backgroundImageView;
    private ImageView logoImageView;
    private TextView titleTextView;
    private TextInputEditText usernameEditText;
    private TextInputEditText passwordEditText;
    private MaterialButton loginButton;

//    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        backgroundImageView = findViewById(R.id.background_image);
        logoImageView = findViewById(R.id.logo);
        titleTextView = findViewById(R.id.title);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        loadImages();

        // Initialize Firebase Auth
//        mAuth = FirebaseAuth.getInstance();
    }

    private void login(){
        String str_username = usernameEditText.getText().toString();
        int username = Integer.parseInt(str_username);
        String password = Objects.requireNonNull(passwordEditText.getText()).toString().trim();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService service = retrofit.create(ApiService.class);

        Log.d(TAG, service.toString());
        Call<ResponseBody> call = service.userLogin(jsonObject);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                assert response.body() != null;
                if(response.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                }
                else{
                    Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
//
//    private void updateUI(FirebaseUser currentUser) {
//        if (currentUser != null) {
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        } else {
//            titleTextView.setText("Invalid login");
//        }
//    }
//
//    private void signIn(String username, String password){
//        Log.d(TAG, "signIn:" + username);
//        if (!validateForm()) {
//            return;
//        }
//
////        showProgressDialog();
//
//        // [START sign_in_with_email]
//        mAuth.signInWithEmailAndPassword(username, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithEmail:success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // [START_EXCLUDE]
//                        if (!task.isSuccessful()) {
//                            titleTextView.setText("FAIL!");
//                        }
////                        hideProgressDialog();
//                        // [END_EXCLUDE]
//                    }
//                });
//    }
//
//    private boolean validateForm() {
//        boolean valid = true;
//
//        String email = usernameEditText.getText().toString();
//        if (TextUtils.isEmpty(email)) {
//            usernameEditText.setError("Required.");
//            valid = false;
//        } else {
//            usernameEditText.setError(null);
//        }
//
//        String password = passwordEditText.getText().toString();
//        if (TextUtils.isEmpty(password)) {
//            passwordEditText.setError("Required.");
//            valid = false;
//        } else {
//            passwordEditText.setError(null);
//        }
//
//        return valid;
//    }
//
//    private void signOut() {
//        mAuth.signOut();
//        updateUI(null);
//    }

    private void loadLogo(){
        Glide.with(context).load(R.drawable.logo).into(logoImageView);
    }

    private void loadBackgroundImage(){
        Glide.with(context).load(R.drawable.background_image).into(backgroundImageView);
    }

    private void loadImages(){
        loadBackgroundImage();
        loadLogo();
    }

    public void onClick(View view) {
        if(view == loginButton)
            login();
    }

}
