package com.example.giris.fragmentler;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.giris.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaatFragment extends Fragment {

    TextView textView;


    public SaatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate( R.layout.fragment_saat, container, false );
    }

}
