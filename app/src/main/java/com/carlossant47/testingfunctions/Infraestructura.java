package com.carlossant47.testingfunctions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import com.carlossant47.testingfunctions.Clases.*;
import static com.carlossant47.testingfunctions.MainSupervision.*;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.carlossant47.testingfunctions.Permissions.returnRadioButtonPosition;
public class Infraestructura extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final int MY_ALLPERMISSIONS = 3;
    private int idinstitucion = 0;
    private Menu menu;
    private String pal;
    private NavigationView navigationView;
    private ScrollView prControlEsc, prPlantadocente, prcomentarios, prcondiciones, prRevision, txtComentRev;
    int il;
    CheckBox
            //ControlEscolar
            cbmovilidad,cbRVOE1,cbRVOEfederal1,cbIngles,cbChino,cbFrances,cbOtrosIdi,
    //Condiciones
    cbpup, cbbanco,
    //Comentarios
    cbinternet, cbmatdidactico, cbbibloteca, cbplatforweb, cblab, cbpuntoen, cbinvernaderos,
            cbsustentable, cbhornos, cbbuenamlab, cbtaller, cbinstalacion, cbactualizarpd, cbrpfalumnos,
            cbpropuestad, cbactualizarRVOE, cbrvoesoperar,  cbrspeyc, rbElevador, rbRampa, rbEstacionamiento;

    TextView lblistRVOE, lbescuela, txtViwTotal_PD, txtDescripccion, lbtotalrvoe, txtAutorizado, lbNombreCarr;
    String foliosRVOE = "";
    AlertDialog dialog;
    Spinner cmbBecas;
    EditText
            //Primera
            txtNomDirec,txtNomT1,txtNomT2,
    //PlantaDocente
    txtSAutorizado,txtTSU_PD, txtESP_PD, txtLIC_PD, txtMTR_PD, txtDOCT_PD,
            txtanoInSSI, txtsuger,
    //ControlEscolar
    txttainscrito,txttabajo,txttatec,txttsu,txtesp,txtlic, txtmtr, txtdoc,txtRVOE2, txtREVOEoperando,
            txtRVOEsinoperar, txtRVOEfederal2,
    //Revivion Aula
    txtcantidadalumnosfuera,txtCantidaddiscapacitados,txtCantidadbecados, txtCantidadindigena,txtCantidadidioma,
            txtSemestre, txtNmsemestre, txtAsignatura, txtGrupo,txtCometariosP,txtCantidadasistencia,
    //Condiciones
    txtnumaulas, txtnumtall, txtnumlab, txtnumbhom, txtnummuj,txtproveedor, txtPaquete,txtmegas,txtObAcreProt,
    //comentarios
    txtotros, txtREVOESoperando, txtDoAutoriza, txtNAlumProg, txtanoingSi, txtObAcreBom, txtObImpVial, txtGrado;
    //Nose xD

    Button btnguardar, btnver, btnFolios, btnanoInSSI, btnpdf,btnComtariosAntes,button3, btnPlantaDoc;

    LinearLayout linRevision, prta;

    BottomNavigationView navigation;

    Spinner cmbNumPer, cmbRvoe, cmbAsistencias, cmbCarDescrip, cmbReglamento, cmbMaesDiscut, cmbEvaluar;

    RadioGroup
            //Primera pantalla
            RG_Programa, RG_Modalidad,
    //PlantaDocente
    RG_Curi, RG_Acta, RG_Nacion,RG_Titulo, RG_Cedula,RG_Consta, RG_Cumple, RG_Inves,RG_Nivel,RG_SSIT,RG_NLSSI,
    //Revivion aula
    rbfuera,rbdiscapacitados,rbindigenas,rbidioma,RGProgA,
    //Condiciones
    RG_Dic, RG_Hig, RG_Seg, RG_Bil, RG_Com, RG_Inter, RG_Audi, RG_Verde, RG_Civil, RG_Bom,RG_ImpVial,
            RG_Encuentro, RB_Desable, RGB_NivelISS;

    EditText[] txtinter = new EditText[2];
    DBSupervicion dbSupervicion = new DBSupervicion(Infraestructura.this);
    ArrayList<RevisionAula> revision = new ArrayList<>();
    ArrayList<Rvoe> rvoeList = new ArrayList<>();
    Supervision supervision = new Supervision();
    ArrayList<Representantes> representantes = new ArrayList<>();
    ArrayList<PlantaDocente> plantaDocentes = new ArrayList<>();
    FloatingActionButton fab, btnNext;
    ProgressDialog progress;
    private int numGroup = 2, rev = 0;
    private int TIMEPHOTOSTART = 3000;
    private int[] NumProfeT = new int[5];
    private final EditText[] txtSuma = new EditText[5];
    private SupervisionInfo sup;
    private int intentos = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infraestructura);
        DeclarationOBjetsGui();//<- NO MOVER DE AQUI
        txtMultiFuntions();
        btnFuncions();
        ///----------------------------NO MOVER
        RadioGroufFunciones();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        NumProfeT= new int[]{0, 0, 0, 0, 0};
        sup = new SupervisionInfo((SupervisionInfo) getIntent().getSerializableExtra("supervision"));
        idinstitucion = sup.getIdInstitucion();
        sup.setIdSupervision(sup.getIdSupervision());
        supervision.setId_supervision(sup.idSupervision);
        lbescuela.setText(sup.getNombreInstitucion());
        ConsltRVOE(sup.getIdInstitucion());
        btnpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ViewPDF(getApplicationContext());///ES LA FUNCION QUE VE LO DL PDF
            }
        });
        btnPlantaDoc.setText(getResources().getString(R.string.guardar) + " " + plantaDocentes.size() + " de " + numGroup);


        if (!Permissions.isNettworkConnect(getApplicationContext())) {
            alertDialog(getResources().getString(R.string.Advertencia), getResources().getString(R.string.sConexion), 0);
        }

        btnver.setText(getResources().getString(R.string.guardar) + " " + rev + " de " + numGroup);
        StartMessage();
    }
    public void btnFuncions()
    {
        btnver.setOnClickListener(insertRevisionAula());
        btnguardar.setOnClickListener(sendSupervision());
        btnPlantaDoc.setOnClickListener(insertPlantaDocente());
        if (getResources().getBoolean(R.bool.istablet)) {//VALIDA SI ES TABLETA, NUNCA DETECTARA UN TELEFONO POR LA ANCHURA
            Log.w("Tablet", "Detectada");
            txtDescripccion = findViewById(R.id.lbdescripcion);
            navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            btnNext = findViewById(R.id.btnNextPart);
            menu = navigationView.getMenu();
            txtDescripccion.setText(getStringR(R.string.controlescDes));
        }
        cmbRvoe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtNAlumProg.setText(convertString(rvoeList.get(position).getNumalumnos()));
                lbNombreCarr.setText(rvoeList.get(position).getNombrecarrera());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //txtanoingSi
        txtNAlumProg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rvoeList.get(cmbRvoe.getSelectedItemPosition()).setNumalumnos(atoi(txtNAlumProg));
            }
        });
    }


    void txtMultiFuntions()
    {
        txtDoAutoriza.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0 && lengthtxt(txtSAutorizado) > 0) {
                    txtAutorizado.setText("Numero total de docentes " + convertString(atoi(txtSAutorizado) + atoi(txtDoAutoriza)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        txtREVOEoperando.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0 && lengthtxt(txtREVOESoperando) > 0)
                {
                    txtAutorizado.setText("Numero total de docentes " + convertString(atoi(txtSAutorizado) + atoi(txtDoAutoriza)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtREVOESoperando.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0 && lengthtxt(txtREVOEoperando) > 0)
                {
                    lbtotalrvoe.setText("Total de RVOE " + convertString(atoi(txtREVOEoperando) + atoi(txtREVOESoperando)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        txtSuma[0].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0)
                {
                    NumProfeT[0] = Integer.parseInt(txtSuma[0].getText().toString());
                }
                else
                {
                    NumProfeT[0] = 0;

                }
                SumaProfesores();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        txtSuma[1].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0)
                {
                    NumProfeT[1] = Integer.parseInt(txtSuma[1].getText().toString());
                }
                else
                {
                    NumProfeT[1] = 0;

                }
                SumaProfesores();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        txtSuma[2].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0)
                {
                    NumProfeT[2] = Integer.parseInt(txtSuma[2].getText().toString());
                }
                else
                {
                    NumProfeT[2] = 0;

                }
                SumaProfesores();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        txtSuma[3].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0)
                {
                    NumProfeT[3] = Integer.parseInt(txtSuma[3].getText().toString());
                }
                else
                {
                    NumProfeT[3] = 0;

                }
                SumaProfesores();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        txtSuma[4].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0)
                {
                    NumProfeT[4] = Integer.parseInt(txtSuma[4].getText().toString());
                }
                else
                {
                    NumProfeT[4] = 0;

                }
                SumaProfesores();
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtSAutorizado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0)
                {
                    if(atoi(txtSAutorizado) > 0)
                    {
                        cbactualizarpd.setChecked(true);
                    }
                    else {
                        cbactualizarpd.setChecked(false);
                    }
                    txtAutorizado.setText(convertString(atoi(txtSAutorizado) + atoi(txtDoAutoriza)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txttabajo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0 && lengthtxt(txttainscrito) > 0 && atoi(txttainscrito) > atoi(txttabajo))
                {
                    txttatec.setText(convertString(atoi(txttainscrito) - atoi(txttabajo)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txttainscrito.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count > 0 && lengthtxt(txttabajo) > 0 &&  atoi(txttainscrito) > atoi(txttabajo))
                {
                    txttatec.setText(convertString(atoi(txttainscrito) - atoi(txttabajo)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    void SumaProfesores()
    {
        int suma = 0;
        for(int x = 0; x < NumProfeT.length; x++)
        {
            suma += NumProfeT[x];
        }
        if(txtDoAutoriza.getText().length() > 0 && txtSAutorizado.getText().length() > 0)
        {
            if(suma != (atoi(txtDoAutoriza.getText().toString()) + atoi(txtSAutorizado.getText().toString())))
            {
                txtViwTotal_PD.setText("Numero de docentes " + String.valueOf(suma) + "\n" + getStringR(R.string.nProfeM));
            }
            else
            {
                txtViwTotal_PD.setText("Numero de docentes " + String.valueOf(suma));
            }
        }


    }
    public void setComplete(MenuItem i) {
        TextView txt;
        txt = (TextView) MenuItemCompat.getActionView(i);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setTypeface(null, Typeface.BOLD);
        txt.setTextColor(getResources().getColor(R.color.colorAccent));
        txt.setText("Completado");
    }

    private Boolean NoCamposVaciosInfraestructura() {
        Boolean vacios = false;
        if (rbfuera.getCheckedRadioButtonId() == -1||rbdiscapacitados.getCheckedRadioButtonId() == -1||rbindigenas.getCheckedRadioButtonId() == -1
                ||rbidioma.getCheckedRadioButtonId() == -1||RGProgA.getCheckedRadioButtonId() == -1||txtCantidadasistencia.getText().toString().matches("")
                ||txtCantidaddiscapacitados.getText().toString().matches("")||txtNmsemestre.getText().toString().matches("")||txtGrupo.getText().toString().matches("")
                ||txtCantidadindigena.getText().toString().matches("")||txtGrado.getText().toString().matches("")||txtCometariosP.getText().toString().matches("")
                ||txtCantidadidioma.getText().toString().matches("")){
            vacios = true;
        }
        return vacios;
    }


    private Boolean NoCamposVaciosPlanta() {
        Boolean vacios = false;

        if(returnRadioButtonPosition(RG_Inves) == 0 && returnRadioButtonPosition(RG_SSIT) == 0)
        {
            if (RG_Curi.getCheckedRadioButtonId() == -1 ||RG_Acta.getCheckedRadioButtonId() == -1||RG_Nacion.getCheckedRadioButtonId() == -1||RG_Titulo.getCheckedRadioButtonId() == -1
                    ||RG_Cedula.getCheckedRadioButtonId() == -1||RG_Consta.getCheckedRadioButtonId() == -1||RG_Cumple.getCheckedRadioButtonId() == -1||RG_Inves.getCheckedRadioButtonId() == -1
                    ||RG_SSIT.getCheckedRadioButtonId() == -1    ||RGB_NivelISS.getCheckedRadioButtonId() == -1
                    ||RG_Nivel.getCheckedRadioButtonId() == -1||txtanoingSi.getText().toString().matches("")||txtanoInSSI.getText().toString().matches("")||txtsuger.getText().toString().matches("")) {
                vacios = true;
            }

        }
        else
        {
            if (RG_Curi.getCheckedRadioButtonId() == -1 ||RG_Acta.getCheckedRadioButtonId() == -1||RG_Nacion.getCheckedRadioButtonId() == -1||RG_Titulo.getCheckedRadioButtonId() == -1
                    ||RG_Cedula.getCheckedRadioButtonId() == -1||RG_Consta.getCheckedRadioButtonId() == -1||RG_Cumple.getCheckedRadioButtonId() == -1||RG_Inves.getCheckedRadioButtonId() == -1
                    ||RG_SSIT.getCheckedRadioButtonId() == -1
                    ||txtanoingSi.getText().toString().matches("")||txtanoInSSI.getText().toString().matches("")||txtsuger.getText().toString().matches("")) {
                vacios = true;
            }
        }

        return vacios;
    }


    private void limpiarPreguntasDoc() {
        RGProgA.clearCheck();
        //RGAsisA.clearCheck();
        rbindigenas.clearCheck();
        rbdiscapacitados.clearCheck();
        rbfuera.clearCheck();
        rbidioma.clearCheck();
        txtCometariosP.setText("");
        txtCantidadasistencia.setText("");
        txtCantidaddiscapacitados.setText("");
        txtGrado.setText("");
        txtGrupo.setText("");
        txtCantidadidioma.setText("");
        txtCantidadindigena.setText("");
        //txtSemestre.setText("");
        txtNmsemestre.setText("");
        txtAsignatura.setText("");
        //txtSemestre.setText("");
        txtCantidadasistencia.setEnabled(true);
        txtCantidaddiscapacitados.setEnabled(true);

        txtCantidadidioma.setEnabled(true);
        txtCantidadindigena.setEnabled(true);
        //txtSemestre.setEnabled(true);
        txtNmsemestre.setEnabled(true);
        txtAsignatura.setEnabled(true);
        txtGrupo.setEnabled(true);

        txtCantidadasistencia.requestFocus();
    }
    private void DeclarationOBjetsGui() {
        btnguardar = findViewById(R.id.btnguardar);
        txtSuma[0] = findViewById(R.id.txtTSU_PD);
        txtSuma[1] = findViewById(R.id.txtESP_PD);
        txtSuma[2] = findViewById(R.id.txtLIC_PD);
        txtSuma[3] = findViewById(R.id.txtMTR_PD);
        txtSuma[4] = findViewById(R.id.txtDOCT_PD);
        txtsuger = findViewById(R.id.txtsuger);
        btnPlantaDoc = findViewById(R.id.btnver2);
        cmbEvaluar = findViewById(R.id.cmbEvaluar);
        cmbAsistencias = findViewById(R.id.cmbAsistencias);
        cmbCarDescrip= findViewById(R.id.cmbCarDescrip);
        cmbMaesDiscut = findViewById(R.id.cmbMaesDiscut);
        cmbReglamento = findViewById(R.id.cmbReglamento);
        txtGrado = findViewById(R.id.grado);
        linRevision = findViewById(R.id.linRevision);
        // botones ventana
        lblistRVOE = findViewById(R.id.lblistRVOE);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        prcomentarios = findViewById(R.id.prComentarios);
        prRevision = findViewById(R.id.prRevision);
        prcondiciones = findViewById(R.id.prCodiciones);
        prControlEsc = findViewById(R.id.prcontrolesc);
        prPlantadocente = findViewById(R.id.prPlataDoc);
        txtanoInSSI = findViewById(R.id.txtanoInSSI);
        //Button
        btnFolios = findViewById(R.id.btnFolios);
        btnanoInSSI = findViewById(R.id.btnanoInSSI);
        cmbRvoe = findViewById(R.id.cmbRvoe);
        lbNombreCarr = findViewById(R.id.lbNombreCarr);
        txtNAlumProg = findViewById(R.id.txtNAlumProg);
        btnpdf = findViewById(R.id.btnpdf);
        btnver = findViewById(R.id.btnver1);
        txtViwTotal_PD = findViewById(R.id.txtViwTotal_PD);
        lbtotalrvoe = findViewById(R.id.lbtotalrvoe);
        txtDoAutoriza = findViewById(R.id.txtDoAutoriza);
        RG_Programa = findViewById(R.id.RG_Programa);
        RG_Modalidad = findViewById(R.id.RG_Modalidad);
        txtanoingSi = findViewById(R.id.txtanoingSi);
        RG_Curi = findViewById(R.id.rbbcur);
        RG_Acta = findViewById(R.id.rbbacta);
        RG_Nacion = findViewById(R.id.rbbnac);
        RG_Titulo = findViewById(R.id.rbbtil);
        RG_Cedula = findViewById(R.id.rbbceld);
        RG_Consta = findViewById(R.id.rbbcon);
        RG_Cumple = findViewById(R.id.rbbcumple);
        RG_Inves = findViewById(R.id.rbbinve);
        RG_Nivel=findViewById(R.id.RG_Nivel);
        RG_SSIT=findViewById(R.id.rbbssit);
        rbfuera = findViewById(R.id.rbbfuera);
        rbdiscapacitados = findViewById(R.id.rbdiscapacitados);
        rbindigenas = findViewById(R.id.rbindigenas);
        rbidioma = findViewById(R.id.rbidioma);
        RGProgA = findViewById(R.id.RGProgA);
        RG_Dic = findViewById(R.id.rbbrdic);
        RG_Hig = findViewById(R.id.rbbrhig);
        RG_Seg = findViewById(R.id.rbbseg);
        RG_Bil = findViewById(R.id.rbbbil);
        RG_Com = findViewById(R.id.rbbcom);
        RG_Inter = findViewById(R.id.rbbinter);
        RG_Audi = findViewById(R.id.rbbaudi);
        RG_Verde = findViewById(R.id.rbbverde);
        RG_Civil = findViewById(R.id.rbbcivil);
        RG_Bom = findViewById(R.id.rbbbom);
        RG_ImpVial=findViewById((R.id.RG_ImpVial));
        RG_Encuentro = findViewById(R.id.rbbencuentro);
        cmbBecas = findViewById(R.id.cmbBecas);
        txtObAcreProt= findViewById(R.id.txtObAcreProt);
        txtproveedor = findViewById(R.id.txtproveedor);
        txtAutorizado = findViewById(R.id.txtAutorizado);
        txtSAutorizado = findViewById(R.id.txtSAutorizado);
        prta = findViewById(R.id.prta);
        lbescuela = findViewById(R.id.lbescuela);
        cbinternet = findViewById(R.id.cbinternet);
        cbmatdidactico = findViewById(R.id.cbmatdidactico);
        cbbibloteca = findViewById(R.id.cbbibloteca);
        cbplatforweb = findViewById(R.id.cbplatforweb);
        cblab = findViewById(R.id.cblab);
        cbinvernaderos = findViewById(R.id.cbinvernaderos);
        cbpuntoen = findViewById(R.id.cbpuntoen);
        cbsustentable = findViewById(R.id.cbsustentable);
        cbhornos = findViewById(R.id.cbhornos);
        cbbuenamlab = findViewById(R.id.cbbuenamlab);
        cbtaller = findViewById(R.id.cbtaller);
        cbinstalacion = findViewById(R.id.cbinstalacion);
        cbactualizarpd = findViewById(R.id.cbactualizarpd);
        cbrpfalumnos = findViewById(R.id.cbrpfalumnos);
        txtREVOESoperando = findViewById(R.id.txtREVOESoperando);
        RGB_NivelISS = findViewById(R.id.RGB_NivelISS);
        txtotros = findViewById(R.id.txtotros);
        txtmegas = findViewById(R.id.txtmegas);
        txtPaquete=findViewById(R.id.txtpaquete);
        cbpup = findViewById(R.id.cbpup);
        cbbanco = findViewById(R.id.cbbanco);
        txtnumaulas = findViewById(R.id.txtnumaulas);
        txtObImpVial = findViewById(R.id.txtObImpVial);
        txtnumtall = findViewById(R.id.txtnumtall);
        txtnumlab = findViewById(R.id.txtnumlab);
        txtnumbhom = findViewById(R.id.txtnumbhom);
        txtnummuj = findViewById(R.id.txtnummuj);
        lbtotalrvoe = findViewById(R.id.lbtotalrvoe);
        cbpropuestad = findViewById(R.id.cbpropuestad);
        cbactualizarRVOE = findViewById(R.id.cbactualizarRVOE);
        cbrvoesoperar = findViewById(R.id.cbrvoesoperar);
        txtCantidadasistencia = findViewById(R.id.txtcantidadalumnosfuera);
        txtCantidaddiscapacitados = findViewById(R.id.cantidaddiscapacitados);
        txtCantidadindigena = findViewById(R.id.cantidadindigena);
        txtCantidadidioma = findViewById(R.id.cantidadidioma);
        txtSemestre = findViewById(R.id.semestre);
        txtNmsemestre = findViewById(R.id.nmsemestre);
        txtAsignatura = findViewById(R.id.asignatura);
        txtGrupo = findViewById(R.id.grupo);
        txttainscrito = findViewById(R.id.txttainscrito);
        txttabajo = findViewById(R.id.txttabajo);
        txttatec = findViewById(R.id.txttatec);
        txttsu = findViewById(R.id.txttsu);
        txtlic=  findViewById(R.id.txtlic);
        txtmtr = findViewById(R.id.txtmtr);
        txtdoc = findViewById(R.id.txtdoc);
        txtesp = findViewById(R.id.txtesp);
        cbmovilidad = findViewById(R.id.cbmovilidad);
        cbRVOE1 = findViewById(R.id.cbRVOE1);
        txtRVOE2 = findViewById(R.id.txtRVOE2);
        txtREVOEoperando = findViewById(R.id.txtREVOEoperando);
        txtRVOEsinoperar = findViewById(R.id.txtRVOEsinoperar);
        cbRVOEfederal1 = findViewById(R.id.cbRVOEfederal1);
        txtRVOEfederal2 = findViewById(R.id.txtRVOEfederal2);
        cbrspeyc = findViewById(R.id.cbrspeyc);
        cbIngles=findViewById(R.id.Ingles);
        cbChino=findViewById(R.id.Chino);
        cbFrances=findViewById(R.id.Frances);
        rbElevador=findViewById(R.id.rbElevador);
        rbRampa=findViewById(R.id.rbRampa);
        rbEstacionamiento=findViewById(R.id.rbEstacionamiento);
        RGProgA = findViewById(R.id.RGProgA);
        txtCometariosP = findViewById(R.id.txtComentRev);
        txttatec = findViewById(R.id.txttatec);
        txtObAcreBom = findViewById(R.id.txtObAcreBom);
    }

    private void insertDataSupervision()
    {

        supervision.setTsu(atoi(txtSuma[0]));
        supervision.setMtr(atoi(txtSuma[3]));
        supervision.setEsp(atoi(txtSuma[1]));
        supervision.setLic(atoi(txtSuma[2]));
        supervision.setDoct(atoi(txtSuma[4]));
        supervision.setDocentes_regis(atoi(txtDoAutoriza));
        supervision.setDocentes_sregi(atoi(txtSAutorizado));
        supervision.setRegistados_sepyc(ConverBooleanInt(cbrspeyc.isChecked()));
        supervision.setAlumnos_ins(atoi(txttainscrito));
        supervision.setAlumnos_baja(atoi(txttabajo));
        supervision.setMovilidad_inter(ConverBooleanInt(cbmovilidad.isChecked()));
        supervision.setProgramas_actua(ConverBooleanInt(cbRVOE1.isChecked()));
        supervision.setCuantos_pactua(atoi(txtRVOE2));
        supervision.setRvoe_operando(atoi(txtREVOEoperando));
        supervision.setRvoe_soperar(atoi(txtREVOESoperando));
        supervision.setRvoe_federales(ConverBooleanInt(cbRVOEfederal1.isChecked()));
        supervision.setCuales_rvfed(txtRVOEfederal2.getText().toString());
        supervision.setPupitre(ConverBooleanInt(cbpup.isChecked()));
        supervision.setMesa_banco(ConverBooleanInt(cbbanco.isChecked()));
        supervision.setNumtalleres(atoi(txtnumtall));
        supervision.setNumaulas(atoi(txtnumaulas));
        supervision.setNumlaboratorios(atoi(txtnumlab));
        supervision.setSanitarios_hom(atoi(txtnumbhom));
        supervision.setSnitarios_muj(atoi(txtnummuj));
        supervision.setRecursos_dic(1);
        supervision.setHigiene(returnRadioButtonPosition(RG_Hig));
        supervision.setSeguridad(returnRadioButtonPosition(RG_Seg));
        supervision.setBibloteca(returnRadioButtonPosition(RG_Bil));
        supervision.setCentro_computo(returnRadioButtonPosition(RG_Com));
        supervision.setInternet(returnRadioButtonPosition(RG_Inter));
        supervision.setInter_paquete(txtPaquete.getText().toString());
        supervision.setInter_proveedor(txtproveedor.getText().toString());
        supervision.setAuditorio(returnRadioButtonPosition(RG_Audi));
        supervision.setArea_verde(returnRadioButtonPosition(RG_Verde));
        supervision.setAcre_proccivil(returnRadioButtonPosition(RG_Civil));
        supervision.setComen_procivil(txtObAcreProt.getText().toString());
        supervision.setAcredi_bom(returnRadioButtonPosition(RG_Bom));
        supervision.setComent_bom(txtObAcreBom.getText().toString());
        supervision.setImpacto_vial(returnRadioButtonPosition(RG_ImpVial));
        supervision.setComen_impactovial(txtObImpVial.getText().toString());
        supervision.setBibloteca_digi(ConverBooleanInt(cbbibloteca.isChecked()));
        supervision.setPlataforma_web(ConverBooleanInt(cbplatforweb.isChecked()));
        supervision.setLaboratorios(ConverBooleanInt(cblab.isChecked()));
        supervision.setEncuentro_sen(ConverBooleanInt(cbpuntoen.isChecked()));
        supervision.setInvernaderos(ConverBooleanInt(cbinvernaderos.isChecked()));
        supervision.setSustentable(ConverBooleanInt(cbsustentable.isChecked()));
        supervision.setHornos(ConverBooleanInt(cbhornos.isChecked()));
        supervision.setBuen_laboral(ConverBooleanInt(cbbuenamlab.isChecked()));
        supervision.setTaller_esp(ConverBooleanInt(cbtaller.isChecked()));
        supervision.setExelente_insta(ConverBooleanInt(cbinstalacion.isChecked()));
        supervision.setElevador(ConverBooleanInt(rbElevador.isChecked()));
        supervision.setRampa(ConverBooleanInt(rbRampa.isChecked()));
        supervision.setEstacionamiento(ConverBooleanInt(rbEstacionamiento.isChecked()));
        supervision.setActua_plandocen(ConverBooleanInt(cbactualizarpd.isChecked()));
        supervision.setRegla_progeva(ConverBooleanInt(cbpropuestad.isChecked()));
        supervision.setPropuestas_docente(ConverBooleanInt(cbpropuestad.isChecked()));
        supervision.setActualizar_rvoe(ConverBooleanInt(cbactualizarRVOE.isChecked()));
        supervision.setOtros(txtotros.getText().toString());
        supervision.setRvoe_avisosoperar(ConverBooleanInt(cbrvoesoperar.isChecked()));
    }


    private void limpiarPlanta()
    {
        RG_Curi.clearCheck();
        RG_Acta.clearCheck();
        RG_Nacion.clearCheck();
        RG_Titulo.clearCheck();
        RG_Cedula.clearCheck();
        RG_Consta.clearCheck();
        RG_Cumple.clearCheck();
        RG_Inves.clearCheck();
        RG_SSIT.clearCheck();
        RGB_NivelISS.clearCheck();
        RG_Nivel.clearCheck();
        txtanoingSi.setText("");
        txtanoInSSI.setText("");
        txtsuger.setText("");
        RG_Curi.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.infraestructura_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                alertDialog(getResources().getString(R.string.cancelar_supervision), getResources().getString(R.string.cancelar_sup2), 1);
                return true;
            case R.id.btnhide:
                if (prta.getVisibility() == View.VISIBLE)
                    prta.setVisibility(View.GONE);
                else
                    prta.setVisibility(View.VISIBLE);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    prPlantadocente.setVisibility(View.VISIBLE);
                    prcomentarios.setVisibility(View.GONE);
                    prcondiciones.setVisibility(View.GONE);
                    prControlEsc.setVisibility(View.GONE);
                    prRevision.setVisibility(View.GONE);
                    il = item.getItemId();
                    return true;
                case R.id.navigation_control:
                    prPlantadocente.setVisibility(View.GONE);
                    prcomentarios.setVisibility(View.GONE);
                    prcondiciones.setVisibility(View.GONE);
                    prControlEsc.setVisibility(View.VISIBLE);
                    prRevision.setVisibility(View.GONE);
                    il = item.getItemId();
                    return true;
                case R.id.navigation_codiciones:
                    prPlantadocente.setVisibility(View.GONE);
                    prcomentarios.setVisibility(View.GONE);
                    prcondiciones.setVisibility(View.VISIBLE);
                    prControlEsc.setVisibility(View.GONE);
                    prRevision.setVisibility(View.GONE);
                    il = item.getItemId();
                    return true;
                case R.id.navigation_comentarios:
                    prPlantadocente.setVisibility(View.GONE);
                    prcomentarios.setVisibility(View.VISIBLE);
                    prcondiciones.setVisibility(View.GONE);
                    prControlEsc.setVisibility(View.GONE);
                    prRevision.setVisibility(View.GONE);
                    il = item.getItemId();
                    return true;
                case R.id.raula:
                    if (rev >= numGroup) {
                        alertDialog(getResources().getString(R.string.error), getResources().getString(R.string.termino), 0);
                        navigation.setSelectedItemId(il);
                    } else {
                        prPlantadocente.setVisibility(View.GONE);
                        prcomentarios.setVisibility(View.GONE);
                        prcondiciones.setVisibility(View.GONE);
                        prControlEsc.setVisibility(View.GONE);
                        prRevision.setVisibility(View.VISIBLE);
                    }

                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Permissions.alertDialogDesision(getResources().getString(R.string.cancelar_sup2), getResources().getString(R.string.cancelar_supervision),
                    Infraestructura.this, new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
            return true;
        }
        else if(keyCode == KeyEvent.KEYCODE_HOME){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void alertDialog(String title, String msg, final int fisnish) {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage(msg);


        if (fisnish == 1)//INDICA SI ES PARA SALIR DE APP
        {
            alertDialogBuilder.setPositiveButton(getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertDialogBuilder.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        } else //SI ES 0 U OTRO VALOR SERA UN DIALOGO NORMAL
        {
            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }

        alertDialogBuilder.show();
    }

    public void RadioGroufFunciones() {

        RG_Inter.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbintersi:

                        txtproveedor.setEnabled(true);
                        txtPaquete.setEnabled(true);
                        txtproveedor.setText("");
                        txtPaquete.setText("");
                        break;
                    case R.id.rbinterno:
                        txtproveedor.setEnabled(false);
                        txtPaquete.setEnabled(false);
                        txtproveedor.setText("Ninguno");
                        txtPaquete.setText("Ninguno");
                        break;

                }


            }
        });
        RG_Inves.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbinvesi:
                        txtanoingSi.setEnabled(true);
                        DisableRadioGroup(RG_Nivel, true);
                        txtanoingSi.setText("");
                        break;
                    case R.id.rbinveno:
                        txtanoingSi.setEnabled(false);
                        txtanoingSi.setText("No pertenece al sistema de investigación");
                        DisableRadioGroup(RG_Nivel, false);
                        break;

                }

            }
        });

        RG_SSIT.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.rbssSI:
                        DisableRadioGroup(RGB_NivelISS, true);
                        txtanoInSSI.setEnabled(true);
                        txtanoInSSI.setText("");
                        break;
                    case R.id.rbSSNo:
                        txtanoInSSI.setText("No pertenece al sistema de investigación");
                        DisableRadioGroup(RGB_NivelISS, false);
                        txtanoInSSI.setEnabled(false);
                        break;
                }
            }
        });
        rbfuera.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.sifuera:// REGULAR
                        txtCantidadasistencia.setEnabled(true);
                        txtCantidadasistencia.setText("");
                        break;
                    case R.id.nofuera: //buena obvio
                        txtCantidadasistencia.setEnabled(false);
                        txtCantidadasistencia.setText("0");

                        break;

                }

            }

        });
        rbidioma.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.siidioma:// REGULAR
                        txtCantidadidioma.setEnabled(true);
                        txtCantidadidioma.setText("");

                        break;
                    case R.id.noidioma: //buena obvio
                        txtCantidadidioma.setEnabled(false);
                        txtCantidadidioma.setText("0");

                        break;

                }

            }
        });

        rbdiscapacitados.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.sidiscapacitados:// REGULAR
                        txtCantidaddiscapacitados.setEnabled(true);
                        txtCantidaddiscapacitados.setText("");

                        break;
                    case R.id.nodiscapacitados: //buena obvio
                        txtCantidaddiscapacitados.setEnabled(false);
                        txtCantidaddiscapacitados.setText("0");

                        break;
                }
            }
        });
        rbindigenas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.siindigena:// REGULAR
                        txtCantidadindigena.setEnabled(true);
                        txtCantidadindigena.setText("");
                        //PreguntasDoc.indigena1 = 1;
                        break;
                    case R.id.noindigena: //buena obvio
                        txtCantidadindigena.setEnabled(false);
                        txtCantidadindigena.setText("0");
                        //PreguntasDoc.indigena1 = 0;
                        break;
                }
            }

        });

        cbRVOE1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    txtRVOE2.setEnabled(true);
                    txtRVOE2.setText("");
                } else {
                    txtRVOE2.setEnabled(false);
                    txtRVOE2.setText("0");
                }

            }
        });
        cbRVOEfederal1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    txtRVOEfederal2.setEnabled(true);
                    txtRVOEfederal2.setText("");
                } else {
                    txtRVOEfederal2.setEnabled(false);
                    txtRVOEfederal2.setText("Sin RVOES Federales");
                }

            }
        });

        RG_Civil.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.rbcivilsi:
                        txtObAcreProt.setEnabled(true);
                        txtObAcreProt.setText("");
                        break;
                    case R.id.rbcivilno:
                        txtObAcreProt.setEnabled(false);
                        txtObAcreProt.setText("Sin Comentarios");
                        break;
                }
            }
        });
        RG_ImpVial.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.RB_Impsi:
                        txtObImpVial.setEnabled(true);
                        txtObImpVial.setText("");
                        break;
                    case R.id.RB_Impno:
                        txtObImpVial.setEnabled(false);
                        txtObImpVial.setText("Sin Comentarios");
                        break;
                }
            }
        });

        RG_Bom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId)
                {
                    case R.id.rbbomsi:
                        txtObAcreBom.setEnabled(true);
                        txtObAcreBom.setText("");
                        break;
                    case R.id.rbbomno:
                        txtObAcreBom.setEnabled(false);
                        txtObAcreBom.setText("Sin Comentarios");
                        break;
                }
            }
        });
    }

    private void DisableRadioGroup(RadioGroup rb, boolean disen)
    {
        for(int x = 0; x < rb.getChildCount(); x++)
        {
            if(disen)
            {
                rb.getChildAt(x).setEnabled(disen);
            }
            else
            {

                rb.getChildAt(x).setEnabled(disen);
                ((RadioButton)rb.getChildAt(x)).setChecked(disen);
            }
        }
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
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    startService(new Intent(Infraestructura.this, CameraService.class));
                                }

                                ;
                            }, TIMEPHOTOSTART);
                        }
                    } else {
                        ActivityCompat.requestPermissions(Infraestructura.this, new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}, MY_ALLPERMISSIONS);
                        Log.i("Permisos", "Denagados");
                    }
                }
        }
    }


    private void StartMessage() {
        final boolean[] exit = {false};
        final Spinner cmbNumPer;
        final RadioGroup rbPEstudios, RG_Modalidad;
        Button btncam;
        final AlertDialog dialog;
        final EditText[] txtMessagePer = new EditText[3], txtNumCrend = new EditText[3];
        final Spinner[] spinnersDocument = new Spinner[3];
        AlertDialog.Builder promptFolios = new AlertDialog.Builder(Infraestructura.this);
        View Mv = getLayoutInflater().inflate(R.layout.primera_layout, null);
        //ASIGNACION DE ID
        txtMessagePer[0] = Mv.findViewById(R.id.txtNomDirec);
        txtMessagePer[1] = Mv.findViewById(R.id.txtNomT1);
        txtMessagePer[2] = Mv.findViewById(R.id.txtNomT2);
        spinnersDocument[0] = Mv.findViewById(R.id.cmbDocuments1);
        spinnersDocument[1] = Mv.findViewById(R.id.cmbDocuments2);
        spinnersDocument[2] = Mv.findViewById(R.id.cmbDocuments3);
        txtNumCrend[0] = Mv.findViewById(R.id.txtnumcrend1);
        txtNumCrend[1] = Mv.findViewById(R.id.txtnumcrend2);
        txtNumCrend[2] = Mv.findViewById(R.id.txtnumcrend3);
        cmbNumPer = Mv.findViewById(R.id.cmbNumPer);
        rbPEstudios = Mv.findViewById(R.id.RG_Programa);
        RG_Modalidad = Mv.findViewById(R.id.RG_Modalidad);
        rbPEstudios.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.RB_Semestral:
                        pal = getResources().getString(R.string.sem);
                        cmbNumPer.setAdapter(Permissions.selectPlan(R.string.sem, Infraestructura.this));
                        supervision.setPlan_estudios(3);
                        cambiarPlan(pal);
                        break;
                    case R.id.RB_Cuatrimestrak:
                        pal = getResources().getString(R.string.cua);
                        cmbNumPer.setAdapter(Permissions.selectPlan(R.string.cua, Infraestructura.this));
                        supervision.setPlan_estudios(2);
                        cambiarPlan(pal);
                        break;
                    case R.id.RB_Trimestral:
                        pal = getResources().getString(R.string.tri);
                        cmbNumPer.setAdapter(Permissions.selectPlan(R.string.tri, Infraestructura.this));
                        cambiarPlan(pal);
                        supervision.setPlan_estudios(1);
                        break;
                }
            }
        });
        cmbNumPer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                supervision.setNum_etapa(position + 1);
                txtSemestre.setText(String.valueOf(position + 1));
                txtSemestre.setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });

        promptFolios.setView(Mv);

        promptFolios.setPositiveButton(getStringR(R.string.aceptar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                supervision.setModalidad(returnRadioButtonPosition(RG_Modalidad));
                for(int t = 0; t < txtMessagePer.length; t++)
                {
                    Representantes rep = new Representantes();
                    rep.setTipo_rep(t + 1);
                    rep.setIdsupervision(2);
                    rep.setNombre(txtMessagePer[t].getText().toString());
                    rep.setTipo_credencial(spinnersDocument[t].getSelectedItemPosition() + 1);
                    rep.setNumcredencial(txtNumCrend[t].getText().toString());
                    representantes.add(t, rep);
                }
            }
        });
        promptFolios.setCancelable(false);

        dialog = promptFolios.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                prPlantadocente.setVisibility(View.VISIBLE);
                prcomentarios.setVisibility(View.GONE);
                prcondiciones.setVisibility(View.GONE);
                prControlEsc.setVisibility(View.GONE);
                prRevision.setVisibility(View.GONE);
                txtDescripccion.setText(getStringR(R.string.plantaDocDes));
                il = item.getItemId();
                return true;
            case R.id.navigation_control:
                prPlantadocente.setVisibility(View.GONE);
                prcomentarios.setVisibility(View.GONE);
                prcondiciones.setVisibility(View.GONE);
                prControlEsc.setVisibility(View.VISIBLE);
                prRevision.setVisibility(View.GONE);
                txtDescripccion.setText(getStringR(R.string.controlescDes));
                il = item.getItemId();
                return true;
            case R.id.navigation_codiciones:
                prPlantadocente.setVisibility(View.GONE);
                prcomentarios.setVisibility(View.GONE);
                prcondiciones.setVisibility(View.VISIBLE);
                prControlEsc.setVisibility(View.GONE);
                prRevision.setVisibility(View.GONE);
                txtDescripccion.setText(getStringR(R.string.condicionDes));
                il = item.getItemId();
                return true;
            case R.id.navigation_comentarios:
                prPlantadocente.setVisibility(View.GONE);
                prcomentarios.setVisibility(View.VISIBLE);
                prcondiciones.setVisibility(View.GONE);
                prControlEsc.setVisibility(View.GONE);
                txtDescripccion.setText(getStringR(R.string.comentariosDes));
                prRevision.setVisibility(View.GONE);
                il = item.getItemId();
                return true;
            case R.id.raula:
                txtDescripccion.setText(getStringR(R.string.revAulaDes));
                if (rev >= numGroup) {
                    alertDialog(getResources().getString(R.string.error), getResources().getString(R.string.termino), 0);
                    navigation.setSelectedItemId(il);
                } else {
                    prPlantadocente.setVisibility(View.GONE);
                    prcomentarios.setVisibility(View.GONE);
                    prcondiciones.setVisibility(View.GONE);
                    prControlEsc.setVisibility(View.GONE);
                    prRevision.setVisibility(View.VISIBLE);
                }

                return true;
        }
        return false;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void ViewPDF(Context context) {

        //PROGRAMING 4:20
        String dir = Environment.getExternalStorageDirectory() + Permissions.LSD2 + "/";
        try {

            copyFile(getResources().openRawResource(R.raw.pdf1pro),
                    new FileOutputStream(new File(dir, Permissions.PDFFILE)));

            File pdfFile = new File(dir + Permissions.PDFFILE);
            Log.w("PDF", pdfFile.toString());
            Uri path = Uri.fromFile(pdfFile);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setDataAndType(path, "application/pdf");
            startActivity(intent);

        } catch (Exception ex) {
            ex.toString();
            Log.w("ERROR", ex.toString());
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        try {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (Exception exp) {

        }
    }

    private void cambiarPlan(String plan){
        txtSemestre.setHint(plan);
        txtNmsemestre.setHint(getStringR(R.string.nmaterias) + " " + plan);
    }

    private String getStringR(int string)
    {
        return getResources().getString(string);
    }

    private void ConsltRVOE(int idinstitucion)
    {
        final ArrayAdapter<String> viewIdRevoe = new ArrayAdapter<>(Infraestructura.this, android.R.layout.simple_dropdown_item_1line);
        if(!Permissions.isNettworkConnect(getApplicationContext()))//SI NO TIENE ENCENDIDO EL WIFI O DATOS SE LEERAN DELA BASE DE DATOS INTERNA DEL TELEFONO
        {
            DBSupervicion db = new DBSupervicion(getApplicationContext());
            db.openDatabase();
            rvoeList = db.consultRvoes(idinstitucion);//LEE LOS DATOS DE LA BASE DE DATOS INTERNA
            db.closeDatabase();

            if(rvoeList.size() <= 0)
            {
                cmbRvoe.setEnabled(false);
                txtNAlumProg.setEnabled(false);
                lbNombreCarr.setText("");
                TextView lbnorvoes = findViewById(R.id.lbnorvoes);
                lbnorvoes.setVisibility(View.VISIBLE);
            }
            else
            {
                for(int x = 0; x < rvoeList.size(); x++)
                {
                    viewIdRevoe.add("RVOE " + rvoeList.get(x).getClave_rvoe());
                }
                cmbRvoe.setAdapter(viewIdRevoe);
            }
        }
        else //EN CASO DE TENER INTERNET TRAERA LOS DATOS DEL SERVIDOR
        {
            SharedPreferences pref = getSharedPreferences(Permissions.fileconfig, MODE_PRIVATE);
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.add(DBStruct.IDINSTITUCION, convertString(idinstitucion));
            params.add("email", pref.getString("email", ""));
            client.post(Config.concatUrlConection(Permissions.urlRVOES, getApplicationContext()), params, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headerses, JSONArray responseArray) {
                    try {

                        for (int i = 0; i < responseArray.length(); i++) {
                            Rvoe rvoe = new Rvoe();
                            JSONObject data = responseArray.getJSONObject(i);
                            rvoe.setNombrecarrera(data.getString("Carrera"));
                            rvoe.setClave_rvoe(data.getString("Clave"));
                            rvoe.setNumalumnos(0);
                            rvoe.setIdsupervision(0);
                            viewIdRevoe.add("RVOE " + rvoe.getClave_rvoe());
                            rvoeList.add(i, rvoe);
                        }
                        cmbRvoe.setAdapter(viewIdRevoe);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable
                        throwable) {
                    cmbRvoe.setEnabled(false);
                    txtNAlumProg.setEnabled(false);
                    lbNombreCarr.setText("");
                    TextView lbnorvoes = findViewById(R.id.lbnorvoes);
                    lbnorvoes.setVisibility(View.VISIBLE);
                    Log.e("Error", responseString);
                }

            });
        }

    }
    public int ConverBooleanInt(Boolean data) {//CONVERITE DE BOOLEAN A INT solo dos valores como SI y No, no tres
        int dat = 0;
        if (data)
            dat = 1;
        return dat;
    }


    private View.OnClickListener sendSupervision()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                insertDataSupervision();
                final SupervicionPendiente supervicion = new SupervicionPendiente(sup.getIdSupervision(), supervision, revision, representantes, rvoeList, plantaDocentes, dateFormat.format(cal.getTime()));
                final ProgressDialog progress = new ProgressDialog(Infraestructura.this);
                progress.setTitle("Subiendo Informacion");
                progress.setIndeterminate(true);
                progress.setMessage("Subiendo datos de la supervision, por favor espere");
                progress.show();
                AsyncHttpClient client = new AsyncHttpClient();
                if(Permissions.isNettworkConnect(getApplicationContext()) && intentos <= 3)
                {
                    client.post(Config.concatUrlConection(Permissions.UPLOADSUPERVISION, getApplicationContext()), supervicion.getParamsServer(), new TextHttpResponseHandler(){
                        @Override
                        public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                            //Asyn
                            //Log.e("Error", responseString)
                            progress.hide();
                            Permissions.alertDialog("Error al subir la informacion, vuelva a intentarlo otra vez", "Error", getApplicationContext(), new Runnable() {
                                @Override
                                public void run() {
                                    view.performClick();
                                    intentos++;
                                }
                            });
                        }
                        @Override
                        public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
                            progress.hide();
                            for (int x = 0; x < responseString.length(); x++)
                            {
                                if(responseString.charAt(x) == '1') {
                                    Permissions.alertDialog("La supervision se a completado con exito", "Completado", Infraestructura.this, new Runnable() {
                                        @Override
                                        public void run() {

                                            DBSupervicion db = new DBSupervicion(getApplicationContext());
                                            db.openDatabase();
                                            db.updateSupervision(sup.getIdSupervision(), 2);
                                            db.closeDatabase();
                                            Intent i = new Intent();
                                            i.putExtra("complete", 2);
                                            setResult(Activity.RESULT_OK, i);
                                            finish();
                                        }
                                    });
                                }
                                else if(responseString.charAt(x) == '0')
                                {
                                    Permissions.alertDialog("Error al subir la informacion, vuelva a intentarlo otra vez", "Error", Infraestructura.this, new Runnable() {
                                        @Override
                                        public void run() {
                                            view.performClick();
                                            intentos++;
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
                else {
                    progress.dismiss();
                    Permissions.alertDialog(getResources().getString(R.string.supervicion_pendiente_local),
                            "Completada", Infraestructura.this, new Runnable() {
                                @Override
                                public void run() {
                                    DBSupervicion db = new DBSupervicion(Infraestructura.this);

                                    db.openDatabase();
                                    db.insertSupervicionPendiente(supervicion);
                                    db.updateSupervision(sup.getIdSupervision(), 0);
                                    db.closeDatabase();
                                    finish();
                                }
                            });
                }
            }
        };
    }

    public View.OnClickListener insertRevisionAula()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NoCamposVaciosInfraestructura())
                {
                    Permissions.toastMessage("Error Campos Vacios",getApplicationContext());
                }
                else {
                    RevisionAula revi = new RevisionAula();
                    revi.setAlumfuera(returnRadioButtonPosition(rbfuera) + 1);
                    revi.setCuanto_alumfuera(atoi(txtCantidadasistencia));
                    revi.setAlumdiscapacitados(returnRadioButtonPosition(rbdiscapacitados) + 1);
                    revi.setCuanto_alumdis(atoi(txtCantidaddiscapacitados));
                    revi.setBecas_porcentaje(cmbBecas.getSelectedItemPosition() + 1);
                    revi.setAlumos_lenguainde(returnRadioButtonPosition(rbindigenas) + 1);
                    revi.setCuantos_alumleninde(atoi(txtCantidadindigena));
                    revi.setAlumnos_idioma(returnRadioButtonPosition(rbidioma) + 1);
                    revi.setCuantos_alumidiom(atoi(txtCantidadidioma));
                    revi.setPrograma_academico(returnRadioButtonPosition(RGProgA) + 1);
                    revi.setEtapa_plan(atoi(txtSemestre));
                    revi.setGrado(atoi(txtGrado));
                    revi.setIdsupervision(0);
                    revi.setGrupo(txtGrupo.getText().toString());
                    revi.setMaterias_plan(atoi(txtNmsemestre));
                    revi.setMaestros_asis(cmbAsistencias.getSelectedItemPosition() + 1);
                    revi.setMaestros_cartades(cmbCarDescrip.getSelectedItemPosition() + 1);
                    revi.setMaestros_regla(cmbReglamento.getSelectedItemPosition() + 1);
                    revi.setMaestros_evaluacion(cmbEvaluar.getSelectedItemPosition() + 1);
                    revi.setEstudiantes_regla(cmbReglamento.getSelectedItemPosition() + 1);
                    revi.setMaestros_regla(cmbMaesDiscut.getSelectedItemPosition() + 1);
                    revi.setComentarios(txtCometariosP.getText().toString());
                    revision.add(revi);
                    btnver.setText(getResources().getString(R.string.guardar) + " " + revision.size() + " de " + numGroup);
                    limpiarPreguntasDoc();
                    if (revision.size() >= numGroup) {
                        MenuItem i;
                        linRevision.setEnabled(false);
                        btnver.setText("Completado");
                        btnver.setEnabled(false);
                        Permissions.alertDialog(getResources().getString(R.string.completado), getResources().getString(R.string.termino), Infraestructura.this);
                    /*
                    //parts = 2;
                    //navigationView.setCheckedItem(R.id.navigation_home);
                    menu = navigationView.getMenu();
                    i = menu.findItem(R.id.navigation_home);
                    i.setEnabled(true);
                    i = menu.findItem(R.id.raula);
                    i.setEnabled(false);
                    */
                        ObjectMapper mapper = new ObjectMapper();
                        String json = null;
                        try {
                            json = mapper.writeValueAsString(revision);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        Log.e("JSON", json);
                    }
                }
            }

        };
    }
    private View.OnClickListener insertPlantaDocente()
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NoCamposVaciosPlanta())
                {
                    Permissions.toastMessage("Error no puedes dejar los campos vacios",getApplicationContext());
                }
                else {
                    PlantaDocente plantaDocente = new PlantaDocente();
                    plantaDocente.setCurriculum(returnRadioButtonPosition(RG_Curi) + 1);
                    plantaDocente.setActa_naci(returnRadioButtonPosition(RG_Acta) + 1);
                    plantaDocente.setIdsupervision(0);
                    plantaDocente.setNacionalidad(returnRadioButtonPosition(RG_Nacion) + 1);
                    plantaDocente.setTitulo(returnRadioButtonPosition(RG_Titulo) + 1);
                    plantaDocente.setCedula(returnRadioButtonPosition(RG_Cedula) + 1);
                    plantaDocente.setDiplomas(returnRadioButtonPosition(RG_Consta) + 1);
                    plantaDocente.setPerfil_materia(returnRadioButtonPosition(RG_Cumple));
                    plantaDocente.setSsniv(returnRadioButtonPosition(RG_Inves) + 1);
                    plantaDocente.setNivel_sniv(returnRadioButtonPosition(RG_Nivel) + 1);
                    plantaDocente.setAñoing_sniv(txtanoingSi.getText().toString());
                    plantaDocente.setAño_ssnit(txtanoingSi.getText().toString());
                    plantaDocente.setSsit(returnRadioButtonPosition(RG_SSIT) + 1);
                    plantaDocente.setAño_ssnit(txtanoInSSI.getText().toString());
                    plantaDocente.setNivel_ssnit(returnRadioButtonPosition(RGB_NivelISS) + 1);
                    plantaDocente.setSugerencias(txtsuger.getText().toString());
                    plantaDocentes.add(plantaDocente);
                    btnPlantaDoc.setText(getResources().getString(R.string.guardar) + " " + plantaDocentes.size() + " de " + numGroup);
                    limpiarPlanta();
                    if (plantaDocentes.size() >= numGroup) {
                        linRevision.setEnabled(false);
                        btnPlantaDoc.setText("Completado");
                        btnPlantaDoc.setEnabled(false);
                        ObjectMapper mapper = new ObjectMapper();
                        String json = null;
                        try {
                            json = mapper.writeValueAsString(plantaDocentes);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        Log.w("JSON", json);
                    }
                }

            }
        };
    }


    public static class SupervisionInfo implements Serializable {
        private int idSupervision;
        private String nombreInstitucion;
        private int idInstitucion;

        public SupervisionInfo(int idSupervision, String nombreInstitucion, int idInstitucion) {
            this.idSupervision = idSupervision;
            this.nombreInstitucion = nombreInstitucion;
            this.idInstitucion = idInstitucion;
        }

        public SupervisionInfo() {
            this.idInstitucion = 1;
            this.idSupervision = 1;
            this.nombreInstitucion = "ejemplo";
        }

        public SupervisionInfo(SupervisionInfo supervision) {
            this.idInstitucion = supervision.idInstitucion;
            this.idSupervision = supervision.idSupervision;
            this.nombreInstitucion = supervision.nombreInstitucion;
        }

        public int getIdSupervision() {
            return idSupervision;
        }

        public void setIdSupervision(int idSupervision) {
            this.idSupervision = idSupervision;
        }

        public String getNombreInstitucion() {
            return nombreInstitucion;
        }

        public void setNombreInstitucion(String nombreInstitucion) {
            this.nombreInstitucion = nombreInstitucion;
        }

        public int getIdInstitucion() {
            return idInstitucion;
        }

        public void setIdInstitucion(int idInstitucion) {
            this.idInstitucion = idInstitucion;
        }
    }







}
