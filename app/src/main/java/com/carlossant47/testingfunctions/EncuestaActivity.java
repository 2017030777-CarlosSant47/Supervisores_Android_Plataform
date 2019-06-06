package com.carlossant47.testingfunctions;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class EncuestaActivity extends AppCompatActivity {

    BottomNavigationView menuEncuesta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        menuEncuesta.setOnNavigationItemSelectedListener(menuEncuestaAction());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener menuEncuestaAction()
    {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.raula:
                    {

                        return false;
                    }
                    case R.id.navigation_codiciones:
                    {
                        return false;
                    }

                    case R.id.navigation_comentarios:
                    {
                        return false;
                    }

                    case R.id.navigation_control:
                    {
                        return false;
                    }

                    case R.id.navigation_home:
                    {
                        return false;

                    }
                }
                return false;
            }
        };
    }
}
