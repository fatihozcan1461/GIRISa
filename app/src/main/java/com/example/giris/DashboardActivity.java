package com.example.giris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.giris.LoginandRegister.MainActivity;
import com.example.giris.fragmentler.DuymaFragment;
import com.example.giris.fragmentler.EvFragment;
import com.example.giris.fragmentler.OlayFragment;
import com.example.giris.fragmentler.ZamanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    //TextView text_profil;

    ActionBar actionBar;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dashboard );

        //ActionBar and its title
        actionBar = getSupportActionBar();
        actionBar.setTitle( "Profile" );

        //init
        firebaseAuth = FirebaseAuth.getInstance();

        //bottom Navigation
        BottomNavigationView navigationView = findViewById( R.id.navigation_menu );
        navigationView.setOnNavigationItemSelectedListener( selectedListener );

       /* actionBar.setTitle( "Anasayfa" );
        EvFragment fragment1 = new EvFragment();
        FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
        ft1.replace( R.id.content,fragment1,"" );
        ft1.commit();*/
        actionBar.setTitle( "Zaman Ayarlama" );
        ZamanFragment fragment5 = new ZamanFragment();
        FragmentTransaction ft5 = getSupportFragmentManager().beginTransaction();
        ft5.replace( R.id.content,fragment5,"" );
        ft5.commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    //handle item clicks

                    switch (item.getItemId()){
                        case R.id.nav_ev:
                            //Ev fragment transaction
                            actionBar.setTitle( "Anasayfa" );
                            EvFragment fragment1 = new EvFragment();
                            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                            ft1.replace( R.id.content,fragment1,"" );
                            ft1.commit();
                            return true;
                        case R.id.nav_bluetooth_bağlanti:
                            //bluetooth fragment transaction
                            actionBar.setTitle( "Bluetooth" );
                            com.example.giris.fragmentler.BluetoothFragment fragment2 = new com.example.giris.fragmentler.BluetoothFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace( R.id.content,fragment2,"" );
                            ft2.commit();
                            return true;
                        case R.id.nav_duyma:
                            //Duyma fragment transaction
                            actionBar.setTitle( "Duyma" );
                            DuymaFragment fragment3 = new DuymaFragment();
                            FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                            ft3.replace( R.id.content,fragment3,"" );
                            ft3.commit();
                            return true;
                        case R.id.nav_olay:
                            // Olay fragment transaction
                            actionBar.setTitle( "Olay" );
                            OlayFragment fragment4 = new OlayFragment();
                            FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                            ft4.replace( R.id.content,fragment4,"" );
                            ft4.commit();
                            return true;
                        case R.id.nav_zaman_ayarlama:
                            //Zaman Ayarlama fragment transaction
                            actionBar.setTitle( "Zaman Ayarlama" );
                            ZamanFragment fragment5 = new ZamanFragment();
                            FragmentTransaction ft5 = getSupportFragmentManager().beginTransaction();
                            ft5.replace( R.id.content,fragment5,"" );
                            ft5.commit();
                            return true;
                    }
                    return false;
                }
            };

    private void kullanicidurumukontrol()
    {
        //firebase son kullanıcı görüntüleme
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null)
        {
            //text_profil.setText( user.getEmail() );


        }
        else
        {
            startActivity( new Intent( DashboardActivity.this, MainActivity.class ) );
            finish();
        }

    }

    @Override
    public void onStart() {

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
