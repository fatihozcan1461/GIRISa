package com.example.giris.fragmentler;


import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.giris.D.SpeechAPI;
import com.example.giris.D.VoiceRecorder;
import com.example.giris.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


/**
 * A simple {@link Fragment} subclass.
 */
public class ZamanFragment extends Fragment {

    private ArrayList uyarikelimeleri  = new ArrayList();

    private ListView     listView;
    private List<String> list;
    private TextView     textView;
    private ArrayAdapter adapter;
    private static final int RECORD_Request_Code=101;
    private SpeechAPI speechAPI;
    private VoiceRecorder voiceRecorder;
    private final VoiceRecorder.Callback callback = new VoiceRecorder.Callback() {
        @Override
        public void onVoiceStart() {
            if (speechAPI != null )
            {
                speechAPI.startRecognizing( voiceRecorder.getSampleRate() );
            }
        }

        @Override
        public void onVoice(byte[] data, int size) {
            if (speechAPI != null )
            {
                speechAPI.recognize( data,size);
            }
        }

        @Override
        public void onVoiceEnd() {
            if (speechAPI != null )
            {
                speechAPI.finishRecognizing();
            }
        }
    };



    public ZamanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this
        View view = inflater.inflate( R.layout.fragment_zaman,container,false );

        listView = view.findViewById( R.id.listView );
        textView = view.findViewById( R.id.text_dinleme );
        list = new ArrayList<>(  );

        list.add( "Google Cloud Speech Text API" );
        list.add( "Dinleniyorsun Kardeş Herşey güvende sıkma canını " );
        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter( adapter );
        listView.setVerticalScrollBarEnabled( false );

        speechAPI = new SpeechAPI(getActivity());
        ButterKnife.bind( getActivity() );

        uyarikelimeleri.add( 0,"boş" );
        uyarikelimeleri.add( 1,"imdat" );
        uyarikelimeleri.add( 2,"yardım edin" );
        uyarikelimeleri.add( 3,"merhaba" );
        uyarikelimeleri.add( 4,"isim" );
        uyarikelimeleri.add( 5,"bakar mısın" );
        uyarikelimeleri.add( 6,"acil" );
        uyarikelimeleri.add( 7,"hey" );
        uyarikelimeleri.add( 8,"selam" );
        uyarikelimeleri.add( 9,"gelir misin" );
        uyarikelimeleri.add( 10,"gider misin" );
        uyarikelimeleri.add( 11,"İmdat" );
        uyarikelimeleri.add( 12,"Selam" );
        uyarikelimeleri.add( 13,"Merhaba " );
        uyarikelimeleri.add( 14,"dikkat" );

        //return inflater.inflate( R.layout.fragment_zaman, container, false );
        return view;
    }

    private int GrantedPermission(String permission){
        return ContextCompat.checkSelfPermission( getContext(),permission );
    }
    private void makeRequest(String permission)
    {
        ActivityCompat.requestPermissions( getActivity(),new String[]{permission},RECORD_Request_Code );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RECORD_Request_Code)
        {
            if (grantResults.length == 0 && grantResults[0] == PackageManager.PERMISSION_DENIED)
            {
                getActivity().finish();
            }
            else {
                startVoiceRecorder();
            }
        }
    }

    private final SpeechAPI.Listener listener = new SpeechAPI.Listener() {
        @Override
        public void onSpeechRecognized(final String text_dinlenen_ses, final boolean isFinal) {
            if(isFinal)
            {
                voiceRecorder.dismiss();
            }
            if(textView != null && !TextUtils.isEmpty( text_dinlenen_ses ))
            {
                getActivity().runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        if(isFinal)
                        {
                            textView.setText( null );
                            list.add( 0,text_dinlenen_ses );
                            adapter.notifyDataSetChanged();
                            listView.smoothScrollToPosition( 0 );
                            textView.setVisibility( View.GONE );
                        }
                        else {
                            textView.setText( text_dinlenen_ses );
                            textView.setVisibility( View.VISIBLE );
                            String uyari_ses = text_dinlenen_ses;
                            for (int i =0;i<uyarikelimeleri.size() ;i++){
                                if (uyari_ses.contains( (CharSequence) uyarikelimeleri.get( i ) )){
                                    titresim titret = new titresim();
                                    titresim.titrestir( getContext() );
                                    NotificationCompat.Builder notificationBuilder= new NotificationCompat.Builder(getContext());
                                    notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
                                    notificationBuilder.setContentTitle( (CharSequence) uyarikelimeleri.get( i ) );
                                    notificationBuilder.setContentText("Uyarı Var !");
                                    notificationBuilder.setTicker("Yeni bildiriminiz var !");
                                    NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE );
                                    mNotificationManager.notify( 001,notificationBuilder.build() );


                                }
                            }

                        }

                    }
                } );
            }
        }
    };
    private void startVoiceRecorder()
    {
        if (voiceRecorder != null)
        {
            voiceRecorder.stop();
        }
        voiceRecorder = new VoiceRecorder( callback );
        voiceRecorder.start();

    }
    private void stopVoiceRecorder()
    {
        if (voiceRecorder != null)
        {
            voiceRecorder.stop();
            voiceRecorder = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (GrantedPermission( Manifest.permission.RECORD_AUDIO ) == PackageManager.PERMISSION_GRANTED)
        {
            startVoiceRecorder();
        }
        else {
            makeRequest( Manifest.permission.RECORD_AUDIO );
        }
        speechAPI.addListener( listener );
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        stopVoiceRecorder();
        speechAPI.removeListener( listener );
        speechAPI.destroy();
        speechAPI=null;
        super.onStop();
    }

}
