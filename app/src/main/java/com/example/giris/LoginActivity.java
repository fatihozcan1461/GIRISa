package com.example.giris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN =100 ;
    GoogleSignInClient mGoogleSignInClient;


    EditText mSifre,mEmail;
    TextView kaydetegit,Sifreyenilemetexti;
    Button   girisyap;
    SignInButton mGoogleSignInButton;


    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        mSifre    = findViewById( R.id.editText_giris_sifre );
        mEmail    = findViewById( R.id.editText_email_giris );
        girisyap = findViewById( R.id.girisyap_butonu );
        Sifreyenilemetexti= findViewById( R.id.sifre_unutma );
        mGoogleSignInButton = findViewById( R.id.google_sign_butonu );

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle( "Giriş Yap " );
        actionBar.setDisplayShowHomeEnabled( true );
        actionBar.setDisplayHomeAsUpEnabled( true );

        mAuth = FirebaseAuth.getInstance();

        mGoogleSignInButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        } );

        girisyap.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
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
                    loginUser(email,sifre);
                }
            }
        } );
        progressDialog = new ProgressDialog( this );
        progressDialog.setMessage( "Giriş Yapılıyor" );
    }

    public void kaydola_git(View view)
    {
        startActivity( new Intent( LoginActivity.this,RegisterActivity.class ) );
        finish();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//geri tuşuna basınca geri activiteye gitmesi için
        return super.onSupportNavigateUp();
    }

    public void GirisYapButonu(View view)
    {
        String email = mEmail.getText().toString();
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
            loginUser(email,sifre);
        }


    }

    private void loginUser(String eemail, String sifre) {
        progressDialog = new ProgressDialog( this );
        progressDialog.setMessage( "Giriş Yapılıyor" );
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(eemail, sifre)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Giriş Başarılı"+user.getEmail() ,
                                    Toast.LENGTH_SHORT).show();
                            startActivity( new Intent(LoginActivity.this,ProfileActivity.class) );
                            finish();
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Giriş Hatası.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                }).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });



    }

    public void yeni_sifre_olusturma(View view) {
        yenisifreolusturmadilogpencersigöster();
    }

    private void yenisifreolusturmadilogpencersigöster() {
        //uyarma dialoğu oluşturma
        AlertDialog.Builder builder = new AlertDialog.Builder( this );
        builder.setTitle( "Şifrenizi mi Unuttunuz" );

        //linear layout ayarlama
        LinearLayout linearLayout =  new LinearLayout( this );

        //view set to in dialog
        final EditText emailedittext = new EditText( this );
        emailedittext.setHint("email");
        emailedittext.setInputType( InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS );

        emailedittext.setMinEms(16);

        linearLayout.addView( emailedittext );
        linearLayout.setPadding(10,10,10,10);
        builder.setView( linearLayout );

        //Buttons Recover
        builder.setPositiveButton( "Şifre ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String email = emailedittext.getText().toString().trim();
                baslangicRecovery(email);

            }
        } );

        //buton cancel
        builder.setNegativeButton( "Vazgeç", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        } );

        builder.create().show();

    }

    private void baslangicRecovery(String email) {

        progressDialog = new ProgressDialog( this );
        progressDialog.setMessage( "email gönderiliyor" );
        progressDialog.show();


        mAuth.sendPasswordResetEmail( email )
                .addOnCompleteListener( new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText( LoginActivity.this,"email'e şifre yenileme bağlantısı gönderildi."
                                    ,Toast.LENGTH_SHORT ).show();
                        }
                        else
                        {
                            Toast.makeText( LoginActivity.this,"İşlem Başarısız"
                                    ,Toast.LENGTH_SHORT ).show();
                        }
                    }
                }).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText( LoginActivity.this,""+e.getMessage()
                        ,Toast.LENGTH_SHORT ).show();

            }
        } );
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult( ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
               Toast.makeText( this,""+e.getMessage(),Toast.LENGTH_SHORT ).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.show();
                           /* try {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                // Get user email anduid from auth
                                /*String email = user.getEmail();
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
                                DatabaseReference reference = database.getReference("Users");
                                //put data within hashmap in database
                                reference.child( uid ).setValue( hashMap );

                                //show email account in Toast
                                Toast.makeText( LoginActivity.this,"Giriş başarılı /n"+user.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                                //go to profil activity after logged in
                                startActivity( new Intent(LoginActivity.this,ProfileActivity.class) );
                                finish();

                            }*/
                            /*catch (Exception hata)

                            {
                                Toast.makeText( LoginActivity.this,"aşağıdaki hatayı incele /n" +hata,
                                        Toast.LENGTH_SHORT).show();
                            }*/
                            /*finally {
                                startActivity( new Intent( getApplicationContext(),ProfileActivity.class ) );
                                finish();
                            }*/
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();



                            //show email account in Toast
                            Toast.makeText( LoginActivity.this,"Giriş başarılı /n"+user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            //go to profil activity after logged in
                            startActivity( new Intent(LoginActivity.this,ProfileActivity.class) );
                            finish();
                           // updateUI(user);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText( LoginActivity.this,"Giriş Başarısız",Toast.LENGTH_SHORT ).show();
                           // updateUI(null);
                        }

                        // ...
                    }
                }).addOnFailureListener( new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //get and show error message
                Toast.makeText( LoginActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT ).show();
            }
        } );
    }
}
