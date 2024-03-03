package com.example.resumenev2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntentsExplicitos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntentsExplicitos extends Fragment implements View.OnClickListener {

    // Botones
    private Button btnMapa, btnLlamar, btnSMS, btnContactos, btnEmail, btnWeb, btnAlarma, btnFoto;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IntentsExplicitos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntentsExplicitos.
     */
    // TODO: Rename and change types and number of parameters
    public static IntentsExplicitos newInstance(String param1, String param2) {
        IntentsExplicitos fragment = new IntentsExplicitos();
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
        View view = inflater.inflate(R.layout.fragment_intents_explicitos, container, false);

        // Botones
        btnMapa = view.findViewById(R.id.btnMapa);
        btnLlamar = view.findViewById(R.id.btnLlamar);
        btnSMS = view.findViewById(R.id.btnSMS);
        btnContactos = view.findViewById(R.id.btnContactos);
        btnEmail = view.findViewById(R.id.btnEmail);
        btnWeb = view.findViewById(R.id.btnWeb);
        btnAlarma = view.findViewById(R.id.btnAlarma);
        btnFoto = view.findViewById(R.id.btnFoto);

        // Configurando OnClickListener para cada botón
        btnMapa.setOnClickListener(this);
        btnLlamar.setOnClickListener(this);
        btnSMS.setOnClickListener(this);
        btnContactos.setOnClickListener(this);
        btnEmail.setOnClickListener(this);
        btnWeb.setOnClickListener(this);
        btnAlarma.setOnClickListener(this);
        btnFoto.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        if (v == btnMapa) {
            Uri locationUri = Uri.parse("geo:37.422855,-2.570629?q=Congreso+de+los+Diputados");
            intent = new Intent(Intent.ACTION_VIEW, locationUri);
        } else if (v == btnLlamar) {
            Uri callUri = Uri.parse("tel:112");
            intent = new Intent(Intent.ACTION_DIAL, callUri);
        } else if (v == btnSMS) {
            sendSMS("693726120", "Esto es un mensaje de prueba");

        } else if (v == btnContactos) {
            intent = new Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI);

        } else if (v == btnEmail) {
            intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Profesor@UniversidadEuropea.es"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Prueba Intent Implícito");
            intent.putExtra(Intent.EXTRA_TEXT, "Este mensaje es una prueba de un correo enviado a través de un Intent Implícito");

        } else if (v == btnWeb) {
            Uri webUri = Uri.parse("https://universidadeuropea.com/");
            intent = new Intent(Intent.ACTION_VIEW, webUri);

        } else if (v == btnAlarma) {
            createAlarm("Ir al gimnasio", 9, 15);


        } else if (v == btnFoto) {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void sendSMS(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
    }

    private void createAlarm(String message, int hour, int minutes) {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_HOUR, hour)
                .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                .putExtra(AlarmClock.EXTRA_MESSAGE, message);
        startActivity(intent);
    }

}
