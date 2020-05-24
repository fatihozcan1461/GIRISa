package com.example.giris.LoginandRegister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.giris.R;

public class MainActivity extends AppCompatActivity {

    Button kaydet,giris_yap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        kaydet    = findViewById( R.id.kaydol );
        giris_yap = findViewById( R.id.giris_yap );
    }

    public void kaydolma(View view)
    {
        Intent kaydetintenti = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity( kaydetintenti );
    }

    public void girise_gecc(View view) {
        Intent kaydetintenti = new Intent(MainActivity.this, LoginActivity.class);
        startActivity( kaydetintenti );
    }
}
