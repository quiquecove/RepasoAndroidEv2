package com.example.resumenev2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FBDataBasePersonajes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FBDataBasePersonajes extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseDatabase db;
    EditText nombre, apellido, edad, hobbies;
    Switch adulto;
    DatabaseReference raiz;
    ChildEventListener bartChildEventListener;

    String nombreRaiz;
    Button btnInsertar, btnInsertarAmigo, btnEliminarAmigo, btnListar;

    public FBDataBasePersonajes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FBDataBasePersonajes.
     */
    // TODO: Rename and change types and number of parameters
    public static FBDataBasePersonajes newInstance(String param1, String param2) {
        FBDataBasePersonajes fragment = new FBDataBasePersonajes();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f_b_data_base_personajes, container, false);
        db = FirebaseDatabase.getInstance();
        nombre = view.findViewById(R.id.etnombre);
        apellido = view.findViewById(R.id.apellido);
        edad = view.findViewById(R.id.edad);
        hobbies = view.findViewById(R.id.hobbies);
        adulto = view.findViewById(R.id.adulto);
        btnInsertar = view.findViewById(R.id.insertar);
        btnInsertarAmigo = view.findViewById(R.id.addAmigo);
        btnEliminarAmigo = view.findViewById(R.id.removeAmigo);
        btnListar = view.findViewById(R.id.listar);
        btnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar(view);
            }
        });
        btnInsertarAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarAmigo(view);
            }
        });
        btnEliminarAmigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarAmigo(view);
            }
        });
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listar(view);
            }
        });

        raiz = db.getReference();
//listeners
        bartChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String nuevoPersonaje = dataSnapshot.getKey();
                if (!nuevoPersonaje.equalsIgnoreCase("Milhouse")) {
                    insertarMilhouseAmigoOcupa(nuevoPersonaje);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        //vListener=bartChildEventListener;
        raiz.addChildEventListener(bartChildEventListener);


        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String nuevoPersonaje = snapshot.getKey();
                    if (!nuevoPersonaje.equalsIgnoreCase("Milhouse")) {
                        insertarMilhouseAmigoOcupa(nuevoPersonaje);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores de cancelación
            }
        };

        raiz.addListenerForSingleValueEvent(vListener);




        return view;
    }

    public void insertar(View view) {

        DatabaseReference ref = db.getReference().child(nombre.getText().toString()).child("Nombre");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (Objects.equals(dataSnapshot.getValue(), "Bart")) {
                    String data = nombre.getText().toString();
                    db.getReference().child("Bart").child("Amigos").child("Milhouse").child("Nombre").setValue("Milhouse");

                    DatabaseReference rf = db.getReference().child("Bart").child("Amigos").child("Milhouse").child("Apellido");
                    rf.setValue(apellido.getText().toString());

                    rf = db.getReference().child("Bart").child("Amigos").child("Milhouse").child("Edad");
                    rf.setValue(Integer.valueOf(edad.getText().toString()));

                    rf = db.getReference().child("Bart").child("Amigos").child("Milhouse").child("Hobbies");
                    List<String> hobysList = Arrays.asList(hobbies.getText().toString().split(","));
                    rf.setValue(hobysList);

                    rf = db.getReference().child("Bart").child("Amigos").child("Milhouse").child("Adulto");
                    rf.setValue(adulto.isChecked());

                    //childListener
//        ref.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                // ...
//
//                if(dataSnapshot.getKey().equalsIgnoreCase("Bart")){
//
//
//                    String data = nombre.getText().toString();
//                    db.getReference().child("Bart").child("Amigos").child("Milhouse").child("Nombre").setValue("Milhouse");
//
//                    DatabaseReference rf = db.getReference().child("Bart").child("Amigos").child("Milhouse").child("Apellido");
//                    rf.setValue(apellido.getText().toString());
//
//                    rf = db.getReference().child("Bart").child("Amigos").child("Milhouse").child("Edad");
//                    rf.setValue(Integer.valueOf(edad.getText().toString()));
//
//                    rf = db.getReference().child("Bart").child("Amigos").child("Milhouse").child("Hobbies");
//                    List<String> hobysList = Arrays.asList(hobbies.getText().toString().split(","));
//                    rf.setValue(hobysList);
//
//                    rf = db.getReference().child("Bart").child("Amigos").child("Milhouse").child("Adulto");
//                    rf.setValue(adulto.isChecked());


                } else {
                    // El nodo no existe

                    if (nombre.getText().toString().equalsIgnoreCase("Bart")) {

                        db.getReference().child("Milhouse").child("Nombre").setValue("Milhouse");

                        DatabaseReference rf = db.getReference().child("Milhouse").child("Apellido");

                        rf.setValue("VanPuto");

                        rf = db.getReference().child("Milhouse").child("Edad");
                        rf.setValue(Integer.valueOf(edad.getText().toString()));

                        rf = db.getReference().child("Milhouse").child("Hobys");

                        List<String> hobysList = Arrays.asList("jugar", "videojuegos");
                        rf.setValue(hobysList);

                        rf = db.getReference().child("Milhouse").child("Adulto");
                        rf.setValue(false);
                    }
                    String data = nombre.getText().toString();
                    db.getReference().child(nombre.getText().toString()).child("Nombre").setValue(data);

                    DatabaseReference rf = db.getReference().child(nombre.getText().toString()).child("Apellido");

                    rf.setValue(apellido.getText().toString());

                    rf = db.getReference().child(nombre.getText().toString()).child("Edad");
                    rf.setValue(Integer.valueOf(edad.getText().toString()));

                    rf = db.getReference().child(nombre.getText().toString()).child("Hobbies");

                    List<String> hobbiesList = Arrays.asList(hobbies.getText().toString().split(","));
                    rf.setValue(hobbiesList);

                    rf = db.getReference().child(nombre.getText().toString()).child("Adulto");
                    rf.setValue(adulto.isChecked());
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
//        String data = nombre.getText().toString();
//        db.getReference().child(nombre.getText().toString()).child("Nombre").setValue(data);
//        nombreRaiz = nombre.getText().toString();
//        DatabaseReference rf = db.getReference().child("Homer").child("Apellido");
//        rf.setValue(apellido.getText().toString());
//
//        rf = db.getReference().child("Homer").child("Edad");
//        rf.setValue(Integer.valueOf(edad.getText().toString()));
//
//        rf = db.getReference().child("Homer").child("Hobbies");
//        List<String> hobysList = Arrays.asList(hobbies.getText().toString().split(","));
//        rf.setValue(hobysList);
//
//        rf = db.getReference().child("Homer").child("Adulto");
//        rf.setValue(adulto.isChecked());
//
//        Toast.makeText(this, "Se ha insertado el nombre en la base de datos", Toast.LENGTH_SHORT).show();

    }
//    public void insertar(View view) {
//        db.getReference().child(nombre.getText().toString()).child("Nombre").setValue(nombre.getText().toString());
//
//    }


    private void insertarMilhouseAmigoOcupa(String nuevoPersonaje) {
        DatabaseReference nodoPadre = raiz.child(nuevoPersonaje).child("Amigos").child("Milhouse");
        nodoPadre.child("Nombre").setValue("Milhouse");
        nodoPadre.child("Apellido").setValue("Van Houten");
        nodoPadre.child("Edad").setValue(10);
        nodoPadre.child("Hobbies").setValue(Arrays.asList("Jugar con Bart", "Acosar a Lisa"));
        nodoPadre.child("EsAdulto").setValue(false);

        // Agregar a Milhouse como amigo del personaje recién insertado jeje
        DatabaseReference refAmigo = raiz.child("Milhouse").child("Amigos").child(nuevoPersonaje);
        refAmigo.child("Nombre").setValue(nuevoPersonaje);
        refAmigo.child("Apellido").setValue("");
        refAmigo.child("Edad").setValue(0);
        refAmigo.child("Hobbies").setValue(Arrays.asList());
        refAmigo.child("EsAdulto").setValue(false);

        Toast.makeText(getContext(), "Milhouse insertado como amigo de " + nuevoPersonaje, Toast.LENGTH_SHORT).show();
    }

    public void insertarAmigo(View view) {
//        DatabaseReference rf = db.getReference().child("Homer").child("Amigos");
//        rf.setValue(nombre.getText().toString());
        DatabaseReference rf = db.getReference().child("Homer").child("Amigos").push();
//        rf = db.getReference().child("Homer").child("Amigos").child(nombre.getText().toString());
        rf = db.getReference().child(nombreRaiz).child("Amigos").child(nombre.getText().toString());
        rf.child("Apellido").setValue(apellido.getText().toString());
        rf.child("Edad").setValue(Integer.valueOf(edad.getText().toString()));
        rf.child("Hobbies").setValue(Arrays.asList(hobbies.getText().toString().split(",")));
        rf.child("Adulto").setValue(adulto.isChecked());
    }

    public void eliminarAmigo(View view) {
        DatabaseReference rf = db.getReference().child("Homer").child("Amigos").child(nombre.getText().toString());
        rf.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(), "Amigo eliminado con éxito", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error al eliminar amigo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void  listar(View view){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Aquí puedes acceder a cada nodo
                    System.out.println(snapshot.getKey());
                    for (DataSnapshot snapshotHijo : snapshot.getChildren()) {
                        System.out.print(snapshotHijo.getKey() + " - " + snapshotHijo.getValue());
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores
            }
        });

    }

}