package com.example.giris.fragmentler;

import android.content.Context;
import android.os.Vibrator;

public class titresim {
    public static void titrestir(Context context) {

            Vibrator v = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(400);
            try {
                Thread.sleep( 100 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            v.vibrate( 400 );

            try{
                Thread.sleep( 200 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



    }
}
