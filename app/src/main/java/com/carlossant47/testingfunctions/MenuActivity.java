package com.carlossant47.testingfunctions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.carlossant47.testingfunctions.Clases.Fecha;
import com.carlossant47.testingfunctions.Clases.ListInstituciones;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lucasurbas.listitemview.ListItemView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout secList, vNoSup, linNoSupervicion;
    public static String action = "action";
    private ListView lv;
    private RecyclerView listRecycler;
    private static final int MY_ALLPERMISSIONS = 3;
    TextView lbNombe, lbCorreo;
    DBSupervicion supervicion = new DBSupervicion(MenuActivity.this);
    Permissions.URLDEFINIIONSSERVER urlsData;
    private BottomNavigationView mNavigationMenu;
    private LinearLayout mLinSupervicion;
    private TextView mLbInstitucion;
    private TextView mLbFecha;
    private LinearLayout mLinSupervicionActual;
    private TextView mLbDomicilio;
    private LinearLayout mLinNoSupervicion;
    private TextView mLbZona;
    private Button mBtnStart;
    {
        urlsData = new Permissions.URLDEFINIIONSSERVER();
    }

    SharedPreferences pref;
    ArrayList<ListInstituciones> listSupervision = new ArrayList<>();
    ListInstituciones supervicionHoy = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Calendar calendar = Calendar.getInstance();
        Log.w("DIA", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.MONTH)));
        pref = getSharedPreferences(Permissions.fileconfig, MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        secList = findViewById(R.id.secList);
        vNoSup = findViewById(R.id.vNoSup);
        Permissions();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //deterScreenSize();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mNavigationMenu = findViewById(R.id.NavigationMenu);
        mNavigationMenu.setOnNavigationItemSelectedListener(navigationBottom);
        mLinSupervicion = findViewById(R.id.linSupervicion);
        mLinSupervicion.setVisibility(View.VISIBLE);
        secList.setVisibility(View.GONE);
        linNoSupervicion = findViewById(R.id.linNoSupervicion);
        setInformation(navigationView);
        mLbInstitucion = findViewById(R.id.lbInstitucion);
        mLbFecha = findViewById(R.id.lbFecha);
        mLinSupervicionActual = findViewById(R.id.linSupervicionActual);
        mLbDomicilio = findViewById(R.id.lbDomicilio);
        mLbZona = findViewById(R.id.lbZona);
        mBtnStart = findViewById(R.id.btnStart);

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Infraestructura.SupervisionInfo supervision = new Infraestructura.SupervisionInfo();
                Intent intent = new Intent(MenuActivity.this, Infraestructura.class);
                supervision.setIdInstitucion(supervicionHoy.getIdIntitucion());
                supervision.setIdSupervision(supervicionHoy.getId());
                supervision.setNombreInstitucion(supervicionHoy.getNombre());
                intent.putExtra("supervision", supervision);
                startActivity(intent);
            }
        });
        if (Config.getDowloadData(getApplicationContext()) == 1)//ESTE ES COMO CONFIGURACION
        {
            //EN CASO DE SER DESCARGADOS LOS SACARA DE SQLLITE PARA CONSULTA MAS RAPIDA
            Log.w("Datos", "Descargados");
            adapterList();

        } else {
            ViewDataSupervision();


        }
        supervicionActual();
    }

    private void setInformation(NavigationView nav) {
        View v = nav.getHeaderView(0);
        lbCorreo = v.findViewById(R.id.lbCorreo);
        lbNombe = v.findViewById(R.id.lbNombre);
        Config config = new Config(MenuActivity.this);
        lbNombe.setText(config.getNombre());
        lbCorreo.setText(config.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(MenuActivity.this, settings.class);
            int actions = 0;
            i.putExtra(action, actions);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_ALLPERMISSIONS:

                if (grantResults.length > 0) {
                    boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (CameraPermission && ExternalStorage) {

                        Log.i("Permisos", "Habilitados");
                        if (Permissions.CrearCarpeta(getApplicationContext())) {
                            Log.w("Carpeta", "Creada");
                        }
                    } else {

                        Log.i("Permisos", "Denagados");
                    }
                }
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationBottom = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.btnActual:
                    mLinSupervicion.setVisibility(View.VISIBLE);
                    secList.setVisibility(View.GONE);
                    return true;
                case R.id.btnPendientes: {
                    mLinSupervicion.setVisibility(View.GONE);
                    secList.setVisibility(View.VISIBLE);
                    return true;
                }
                case R.id.btnCompletadas: {
                   return true;
                }

            }
            return false;
        }
    };


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnlogout: {

                Permissions.alertDialogDesision(R.string.cerrar_s_mensaje, R.string.cerrar_sesion, MenuActivity.this, new Runnable() {
                    @Override
                    public void run() {
                        DBSupervicion db = new DBSupervicion(getApplicationContext());
                        db.openDatabase();
                        db.deleteDataTable();
                        db.closeDatabase();
                        Config config = new Config(getApplicationContext());
                        config.LogutOption();
                        finish();
                        Intent i = new Intent(MenuActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                });
                break;  
            }
            case R.id.btnconfig: {
                Intent intent = new Intent(MenuActivity.this, settings.class);
                startActivity(intent);
                break;
            }
            case R.id.btnPendientes: {
                Intent intent = new Intent(MenuActivity.this, Pendientes.class);
                startActivity(intent);
                break;
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //ESTE CODIGO SOLO FUNCIONA SI, SE USA UNA TABLET
    private class SupervisionesListView extends ArrayAdapter<ListInstituciones>
    {
        private Context context;
        private ArrayList<ListInstituciones> objects;
        private TextView lbEscuela;
        private TextView lbInformacion;
        private Button btnSupervision;
        private ListInstituciones selectObjet;
        private Calendar calendar = Calendar.getInstance();

        public SupervisionesListView(Context contexto, final ArrayList<ListInstituciones> objects) {
            super(contexto, R.layout.instituciones_listlayout, objects);
            this.context = contexto;
            this.objects = objects;
            lbEscuela = findViewById(R.id.lbEscuela);
            lbInformacion = findViewById(R.id.lbInformacion);
            btnSupervision = findViewById(R.id.btnSupervision);
            btnSupervision.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (selectObjet.getStatus()) {//DEPENDIENDO DEL ESTATUSO QUE ESTE EL BOTON HARA UNA OPCCION
                        case 0: {
                            Permissions.alertDialog("Esta supervision esta completada y pendiente de subir", "Advertencia", MenuActivity.this);
                        }
                        case 1: {
                            //DETECTA SI LA SUPERVISION ES PARA UN DIA DESPUES DE LAS ACTULES O PASADOS EN CASO DE SER QUE SI NO SE PODRA REALIZAR LA SUPERVISION
                            if (selectObjet.getFecha().getMonth() >= (calendar.get(Calendar.MONTH) + 1) && selectObjet.getFecha().getYear() >= calendar.get(Calendar.YEAR) &&
                                    selectObjet.getFecha().getDay() > calendar.get(Calendar.DAY_OF_MONTH)) {
                                Permissions.alertDialog("Esta supervision no esta programada para el dia de hoy", "Error", MenuActivity.this);
                            }
                            else
                            {
                                openSupervisionActivity(selectObjet);
                            }
                        }
                        break;
                        case 2: {
                            Permissions.alertDialog("Esta supervision esta completada", "Supervision", MenuActivity.this);
                        }
                        break;
                    }
                }

            });
        }
        public View getView(final int position, View view, ViewGroup viewGroup)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View layout = layoutInflater.inflate(R.layout.instituciones_listlayout, null);
            final ListItemView item = layout.findViewById(R.id.livInstitucion);
            item.setChecked(true);
            item.setTitle(objects.get(position).getNombre());
            item.setSubtitle("Zona " + objects.get(position).getZona());
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectObjet = new ListInstituciones(objects.get(position));
                    lbEscuela.setText(selectObjet.getNombre());
                    lbInformacion.setText(selectObjet.getInformacion());
                    if(selectObjet.getStatus() == 2 || selectObjet.getStatus() == 0){//CAMBIA EL TEXTO DEL BOTON DEPENDIENDO DE EL ESTATUS DE LA SUPERVISION
                        btnSupervision.setText("Supervision Completa");
                    }
                    else
                    {
                        btnSupervision.setText("Iniciar Supervision");
                    }
                }
            });
            return layout;
        }
    }

    private class SupervisionesListRecycler extends RecyclerView.Adapter<SupervisionesListRecycler.ViewHolder> {
        private ArrayList<ListInstituciones> instituciones;
        private Context context;

        public SupervisionesListRecycler(ArrayList<ListInstituciones> instituciones, Context context) {
            this.instituciones = instituciones;
            this.context = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.instituciones_listlayout, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
            //viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if(instituciones.get(i).getStatus() == 2 || instituciones.get(i).getStatus() == 0)
            {
                viewHolder.btnSupervision.setEnabled(false);
                viewHolder.btnSupervision.setText("Supervision Completa");
            }
            viewHolder.lbEscuela.setText(this.instituciones.get(i).getNombre());//PONE EL NOMBRE DE LA ESCUELA
            viewHolder.lbInformacion.setText(this.instituciones.get(i).getInformacion());//PONE EL NOMBRE DE LA ESCUELA
            viewHolder.lbInformacion.append("\n" + this.instituciones.get(i).getFecha().returnFecha() + "\nZona " + this.instituciones.get(i).getZona());
            viewHolder.btnSupervision.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Permissions.alertDialogDesision("¿Deseas realizar la supervision?", "Supervision", context, new Runnable() {
                        @Override
                        public void run() {
                            openSupervisionActivity(instituciones.get(i));
                        }
                    });
                }
            });
        }

        @Override
        public int getItemCount() {
            return instituciones.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView lbInformacion, lbEscuela;
            private Button btnSupervision;

            public ViewHolder(View view) {
                super(view);
                lbInformacion = view.findViewById(R.id.lbInformacion);
                lbEscuela = view.findViewById(R.id.lbEscuela);
                btnSupervision = view.findViewById(R.id.btnSupervision);
            }
        }
    }

    private void openSupervisionActivity(ListInstituciones item)
    {
        Infraestructura.SupervisionInfo supervision = new Infraestructura.SupervisionInfo(item.getId(), item.getNombre(), item.getIdIntitucion());
        Intent intent = new Intent(MenuActivity.this, Infraestructura.class);
        intent.putExtra("supervision", supervision);
        startActivity(intent);
    }


    private void Permissions() {
        if (Permissions.chekStoragePermission(getApplicationContext()) && Permissions.checkCameraPermission(getApplicationContext())) {
            if (Permissions.CrearCarpeta(getApplicationContext())) {
                Log.w("Si", "Permisos");
            }
        } else {
            if (Build.VERSION.SDK_INT >= 23) {
                ActivityCompat.requestPermissions(MenuActivity.this, new String[]
                        {
                                CAMERA,
                                WRITE_EXTERNAL_STORAGE, READ_PHONE_STATE
                        }, MY_ALLPERMISSIONS);
            }
        }
    }
        //listView.getAdapter().getView(0, null, null).performClick();


    @Override //STE METODO DETECTA CUANDO LA ACTIVIDAD QUE SE ABRIO RENCIENTE REVUELDA DATOS
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    private void ViewDataSupervision() {
        final String[] colors;
        colors = new String[4];
        colors[0] = "#0B8043";
        colors[1] = "#4285F4";
        colors[2] = "#4B4B4B";
        colors[3] = "#4A148C";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.add("IDSUP", String.valueOf(Config.getIdSupervisor(getApplicationContext())));
        client.post(Config.concatUrlConection(Permissions.urlDataSupervision, getApplicationContext()), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headerses, JSONArray responseArray) {
                try {
                    int co = 0;
                    if (responseArray.length() == 0) {
                        vNoSup.setVisibility(View.VISIBLE);
                    } else {
                        ArrayList<ListInstituciones> list = new ArrayList<>();
                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject data = responseArray.getJSONObject(i);
                            ListInstituciones ListSuper = new ListInstituciones();
                            ListSuper.setNombre(data.getString(urlsData.NombreEscuela));
                            ListSuper.setId(Integer.valueOf(data.getString(urlsData.id)));
                            ListSuper.setZona(data.getString(urlsData.Zona));
                            ListSuper.setIdIntitucion((Integer.valueOf(data.getString(urlsData.IDINSTITITUCION))));
                            ListSuper.setFecha(new Fecha(data.getString(urlsData.fechaSupernvision)));
                            //ListSuper.numRVOE = (data.getInt(urlsData.RVOE));
                            ListSuper.setStatus(Integer.parseInt(data.getString("estado")));
                            ListSuper.setInformacion(data.getString(urlsData.Direccion));
                            int color = Color.parseColor(colors[co]);
                            ListSuper.setColor(color);
                            list.add(i, ListSuper);
                            co = co + 1;
                            if (co >= 4) {
                                co = 0;
                            }
                        }
                        supervicion.openDatabase();
                        supervicion.insertSupervisiones(list);
                        supervicion.closeDatabase();
                        adapterList();
                        Config config = new Config(getApplicationContext());
                        config.setDowloadData(1); }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable
                    throwable) {
                Log.i("Error", responseString);
            }
        });


    }

    //DESABILITADO DA ERROR :v
    public void consultRvoeAll() {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        Config config = new Config(getApplicationContext());

        params.put("email", config.getEmail());
        client.post(Config.concatUrlConection("ConData/OnConsultRVOEAll", getApplicationContext()), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headerses, JSONArray responseArray) {
                DBSupervicion dbSupervicion = new DBSupervicion(MenuActivity.this);
                dbSupervicion.openDatabase();
                dbSupervicion.insertarRvoes(responseArray);
                dbSupervicion.closeDatabase();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable
                    throwable) {
                Log.i("Error", responseString);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void supervicionActual() {
        final Calendar calendar = Calendar.getInstance();
        int x = 0;
        for (x = 0; x < listSupervision.size(); x++) {
            if (listSupervision.get(x).getFecha().getMonth() == (calendar.get(Calendar.MONTH) + 1) && listSupervision.get(x).getFecha().getDay() == calendar.get(Calendar.DAY_OF_MONTH)
                    && listSupervision.get(x).getFecha().getYear() == calendar.get(Calendar.YEAR) && listSupervision.get(x).getStatus() == 1) {
                supervicionHoy = new ListInstituciones(listSupervision.get(x));
                mLbDomicilio.setText(supervicionHoy.getInformacion());
                mLbFecha.setText("nin");
                mLbInstitucion.setText(supervicionHoy.getNombre());
                linNoSupervicion.setVisibility(View.GONE);
                mLinSupervicionActual.setVisibility(View.VISIBLE);
                mLbZona.setText("Zona " + supervicionHoy.getZona());
                Log.w("Ecnontra", "1");
                break;
            }
        }
    }

    private void adapterList()
    {
        supervicion.openDatabase();
        listSupervision = supervicion.DataSupervisiones();
        supervicion.closeDatabase();
        if (getResources().getBoolean(R.bool.istablet)) {//SI ES TABLET USARA UN LISTVIEW
            lv = (ListView) findViewById(R.id.listInstituciones);
            lv.setVisibility(View.VISIBLE);
            SupervisionesListView adaptador = new SupervisionesListView(getApplicationContext(), listSupervision);
            lv.setAdapter(adaptador);
        } else {//SI ES CEL USARA EL RECICLER VIEW ES CASI LO MISMO PERO UNO SOPORTA IMAGENES Y EL OTRO NO
            Log.w("Celular", "SI");
            listRecycler = (RecyclerView) findViewById(R.id.listInstitucionesR);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MenuActivity.this, 1);
            listRecycler.setLayoutManager(layoutManager);
            SupervisionesListRecycler adapter = new SupervisionesListRecycler(listSupervision, MenuActivity.this);
            listRecycler.setAdapter(adapter);
            listRecycler.setVisibility(View.VISIBLE);


        }
    }




}
