package com.example.resumenev2;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginMain extends AppCompatActivity {
    private FirebaseAuth miFirebaseAuth;
    private FirebaseAuth fba;
    private FirebaseUser user;
    private EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        miFirebaseAuth = FirebaseAuth.getInstance();
        email=findViewById(R.id.usr);
        password=findViewById(R.id.pass);



    }


    public void acceder(View view){
        miFirebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = miFirebaseAuth.getCurrentUser();
                            Toast.makeText(LoginMain.this, "Inicio de Sesion exitoso!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginMain.this, MainActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginMain.this, "Authentication failed. Registrese o pruebe otra vez",
                                    Toast.LENGTH_SHORT).show();
                        }}});




    }


    public void registrar(View view){
        miFirebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = miFirebaseAuth.getCurrentUser();
                            Toast.makeText(LoginMain.this, "Registro exitoso!", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginMain.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }}});
    }

    public void anonimo(View view){
        Intent intent = new Intent(LoginMain.this, MainActivity.class);
        Toast.makeText(LoginMain.this, "Bienvenido!", Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

}