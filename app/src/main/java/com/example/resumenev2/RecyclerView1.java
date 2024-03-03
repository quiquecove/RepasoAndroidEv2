package com.example.resumenev2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.resumenev2.recyclerview.Datos;
import com.example.resumenev2.recyclerview.ItemLista;
import com.example.resumenev2.recyclerview.MiAdaptador;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecyclerView1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecyclerView1 extends Fragment {
RecyclerView recyclerView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecyclerView1() {
        // Required empty public constructor
    }

    ArrayList<ItemLista> listaItems = new ArrayList<>(Datos.obtenerDatosDePrueba());

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecyclerView1.
     */
    // TODO: Rename and change types and number of parameters
    public static RecyclerView1 newInstance(String param1, String param2) {
        RecyclerView1 fragment = new RecyclerView1();
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

            // Obtener datos de prueba de la clase Datos
// Cambia esta línea

            // Configurar RecyclerView
            recyclerView = getView().findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            MiAdaptador adaptador = new MiAdaptador(listaItems);
            recyclerView.setAdapter(adaptador);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view1, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        // Configurar el RecyclerView, como el LinearLayoutManager y el adaptador
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        MiAdaptador adaptador = new MiAdaptador(listaItems);
        recyclerView.setAdapter(adaptador);
        return rootView;
    }

}