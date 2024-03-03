package com.example.resumenev2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SpinnerFrac extends Fragment {

    List<Integer> imagenes = new ArrayList<>();
    List<String> casas = new ArrayList<>();
    String casaSeleccionada;
    Spinner spinner;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public SpinnerFrac() {
        // Required empty public constructor
    }

    public static SpinnerFrac newInstance(String param1, String param2) {
        SpinnerFrac fragment = new SpinnerFrac();
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

        cargarDatos();
    }

    private void cargarDatos() {
        imagenes.add(R.drawable.gryffindor);
        imagenes.add(R.drawable.hufflepuff);
        imagenes.add(R.drawable.ravenclaw);
        imagenes.add(R.drawable.slytherin);

        casas.add("Gryffindor");
        casas.add("Hufflepuff");
        casas.add("Ravenclaw");
        casas.add("Slytherin");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spinner_frac, container, false);

        iconosAdapter adaptadorIconos = new iconosAdapter();

        spinner = view.findViewById(R.id.spinner);
        spinner.setAdapter(adaptadorIconos);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                casaSeleccionada = casas.get(i);
                Toast.makeText(getContext(), "Perteneces a la casa " + casaSeleccionada, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    class iconosAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imagenes.size();
        }

        @Override
        public Object getItem(int position) {
            return imagenes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.itemspinner, null);

            ImageView iv1 = convertView.findViewById(R.id.imageView);
            TextView tv1 = convertView.findViewById(R.id.tvClase);

            iv1.setImageResource(imagenes.get(position));
            tv1.setText(casas.get(position));

            return convertView;
        }
    }
}
