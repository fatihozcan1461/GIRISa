package com.example.giris;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    TextView text_profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_profile );

        text_profil = findViewById( R.id.text_view_profil );

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle( "Profile" );

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void kullanicidurumukontrol()
    {
        //firebase son kullanıcı görüntüleme
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null)
        {
            text_profil.setText( user.getEmail() );

        }
        else
        {
            startActivity( new Intent( ProfileActivity.this,MainActivity.class ) );
            finish();
        }

    }

    @Override
    protected void onStart() {
        kullanicidurumukontrol();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate( R.menu.main_menu,menu );
        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.action_cikis)
        {
            firebaseAuth.signOut();
            kullanicidurumukontrol();
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
