package com.example.resumenev2.recyclerview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.resumenev2.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Estatico#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Estatico extends Fragment implements View.OnClickListener {
    private Button github, linkedin, instagram;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Estatico() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Estatico.
     */
    // TODO: Rename and change types and number of parameters
    public static Estatico newInstance(String param1, String param2) {
        Estatico fragment = new Estatico();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_estatico, container, false);
        github = view.findViewById(R.id.btnGit);
        linkedin = view.findViewById(R.id.btnLink);
        instagram = view.findViewById(R.id.btnIg);

        // Asignar este fragmento como el OnClickListener para los botones
        github.setOnClickListener(this);
        linkedin.setOnClickListener(this);
        instagram.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        // Manejar los clics de los botones aquí
        if (v.getId() == R.id.btnGit) {
            abrirEnlace("https://github.com/quiquecove");
        } else if (v.getId() == R.id.btnLink) {
            abrirEnlace("https://www.linkedin.com/in/enrique-huerta-l%C3%B3pez/");
        } else {
            abrirEnlace("https://www.instagram.com/quiquecove/");

            Uri uri = Uri.parse("https://www.instagram.com/quiquecove/");
        }
    }

    // Método para abrir enlaces
    private void abrirEnlace(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
