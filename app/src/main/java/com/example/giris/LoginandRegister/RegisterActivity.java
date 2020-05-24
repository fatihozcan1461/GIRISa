package com.example.giris.LoginandRegister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.giris.DashboardActivity;
import com.example.giris.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    Button mKaydet;
    EditText mSifre,mEmail;
    TextView gecistext;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );

        mKaydet=findViewById( R.id.kaydol );
        mSifre=findViewById( R.id.editText_sifre );
        mEmail=findViewById( R.id.editText_email );
        gecistext= findViewById( R.id.rise_git_textview );

        mAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle( "Hesap Oluştur" );
        actionBar.setDisplayShowHomeEnabled( true );
        actionBar.setDisplayHomeAsUpEnabled( true );

        progressDialog = new ProgressDialog( this );
        progressDialog.setMessage( "Kişi Kaydediliyor" );
    }

    public void kaydetbutonu(View view)
    {
        String email = mEmail.getText().toString().trim();
        String sifre = mSifre.getText().toString().trim();

        if(!Patterns.EMAIL_ADDRESS.matcher( email ).matches())
        {
            mEmail.setError( "Geçersiz email" );
            mEmail.setFocusable( true );
        }
        else if(sifre.length()<6)
        {
            mSifre.setError( "Şifre en az 6 karakter olmalıdır." );
            mSifre.setFocusable( true );
        }
        else
        {
            registerUser(email,sifre);
        }
    }

    private void registerUser(String email, String sifre) {
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email, sifre)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Check if user is signed in (non-null) and update UI accordingly.
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Get user email anduid from auth
                           /* String email = user.getEmail();
                            String uid   = user.getUid();
                            // When user is registered store user info in firebase realtime database too
                            HashMap<Object,String> hashMap = new HashMap<>(  );
                            //put info in hasmap
                            hashMap.put( "email",email );
                            hashMap.put( "uid",uid );
                            hashMap.put( "name", "" ); // will add later (e.g. edit profile)
                            hashMap.put( "phone","" ); // will add later (e.g. edit profile)
                            hashMap.put( "image","" ); // will add later (e.g. edit profile)

                            //firebase database instance
                            FirebaseDatabase database =  FirebaseDatabase.getInstance();
                            //path to store user data named "Users"
                            DatabaseReference reference = database.getReference("users");
                            //put data within hashmap in database
                            reference.child( uid ).setValue( hashMap );*/

                            Toast.makeText( RegisterActivity.this, "Kaydedildi .\n" + user.getEmail(),
                                    Toast.LENGTH_SHORT ).show();
                            progressDialog.dismiss();
                            startActivity( new Intent( RegisterActivity.this, DashboardActivity.class ) );
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(RegisterActivity.this, "Kimlik Sorgulama Hatası .",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }

                        // ...
                    }
                }).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        } );
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//geri tuşuna basınca geri activiteye gitmesi için
        return super.onSupportNavigateUp();
    }

    public void girise_git_text(View view)
    {
        startActivity( new Intent( RegisterActivity.this, LoginActivity.class ) );
        finish();
    }

    public void kaydola_git(View view) {
    }
}
