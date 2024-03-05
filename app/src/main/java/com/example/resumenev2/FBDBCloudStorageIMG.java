package com.example.resumenev2;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Random;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FBDBCloudStorageIMG#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FBDBCloudStorageIMG extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView imagen;
    Bitmap foto;
    Button tomarFoto, subirFoto,imagenAleatoria;
    ActivityResultLauncher<Intent> cameraLauncher;
    public FBDBCloudStorageIMG() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FireBaseStorageIMG.
     */
    // TODO: Rename and change types and number of parameters
    public static FBDBCloudStorageIMG newInstance(String param1, String param2) {
        FBDBCloudStorageIMG fragment = new FBDBCloudStorageIMG();
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
        View view = inflater.inflate(R.layout.fragment_fire_base_storage_i_m_g, container, false);
        imagen = view.findViewById(R.id.imageView);
        tomarFoto = view.findViewById(R.id.caputurar);
        subirFoto = view.findViewById(R.id.subir);
        imagenAleatoria = view.findViewById(R.id.aleatoria);
        tomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturar(v);
            }
        });
        subirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subir(v);
            }
        });
        imagenAleatoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarImagenAleatoria(v);
            }
        });


        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            // La foto fue tomada con éxito
                            Toast.makeText(getContext(), "Foto tomada correctamente", Toast.LENGTH_SHORT).show();
                            foto = (Bitmap) result.getData().getExtras().get("data");
                            imagen.setImageBitmap(foto);
                        } else {
                            // Error al tomar la foto
                            Toast.makeText(getContext(), "Error al tomar la foto", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return view;
    }

    public void capturar(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            cameraLauncher.launch(takePictureIntent);
        }
    }

    public void subir(View view) {
        foto = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
        // Convertir el bitmap en un array de bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        // Generar un nombre único para el archivo
        String fileName = UUID.randomUUID().toString() + ".jpg";

        // Referencia a la ubicación en Cloud Storage donde se almacenará la imagen
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("imagenes/" + fileName);

        // Subir la imagen a Cloud Storage
        imageRef.putBytes(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // La imagen se ha subido correctamente
                        Toast.makeText(getContext(), "Foto subida correctamente", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error al subir la imagen
                        Toast.makeText(getContext(), "Error al subir la foto: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void seleccionarImagenAleatoria(View view) {
        StorageReference fotosRef = FirebaseStorage.getInstance().getReference().child("imagenes");
        fotosRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                // Obtener una imagen aleatoria de la lista de imágenes disponibles
                int randomIndex = new Random().nextInt(listResult.getItems().size());
                StorageReference randomImageRef = listResult.getItems().get(randomIndex);

                // Descargar la imagen aleatoria y mostrarla usando Glide
                randomImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getContext()).load(uri).into(imagen);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Error al obtener la lista de imágenes", Toast.LENGTH_SHORT).show();
            }
        });
    }


}