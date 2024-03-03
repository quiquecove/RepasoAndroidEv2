package com.example.resumenev2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Inicializar el FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.flContenedor, new SpinnerFrac());
        ft.addToBackStack(null);
        ft.commit();

        tb = findViewById(R.id.tabLayout);
        tb.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                switch (tab.getPosition()) {
                    case 0:
                        ft.replace(R.id.flContenedor, new SpinnerFrac());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case 1:
                        ft.replace(R.id.flContenedor, new RecyclerView1());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case 2:
                        ft.replace(R.id.flContenedor, new IntentsExplicitos());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case 3:
                        ft.replace(R.id.flContenedor, new LoginFBFrag());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case 4:
                        ft.replace(R.id.flContenedor, new FBDataBasePersonajes());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case 5:
                        ft.replace(R.id.flContenedor, new LoginFBFrag());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //No se usa
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //No se usa
            }
        });


    }


//public void cambiarFragmento(){
//        ft = fm.beginTransaction();
//        ft.replace(R.id.flContenedor, new CabeceraFragment());
//        ft.addToBackStack(null);
//        ft.commit();
//    }
//

}


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//    }
//}