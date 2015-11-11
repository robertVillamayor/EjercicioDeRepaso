package com.example.robert.ejercicioderepaso;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class SegundoActivity extends AppCompatActivity  {

    private Spinner spProvincias, spLocalidades;
    private EditText mensaje;
    private Switch eleccion;
    private Button boton;
    private ArrayAdapter<String> adap_provincias;
    private ArrayAdapter<CharSequence> adap_localidades;

    public static final int ACTIVITY_UNO=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo);

        continuar();
    }

    public void inicializarVariables(){
        //Inicializamos variables
        boton= (Button) findViewById(R.id.button2);
        spProvincias= (Spinner) findViewById(R.id.spinner);
        spLocalidades= (Spinner) findViewById(R.id.spinner2);
        eleccion= (Switch) findViewById(R.id.switch1);
        mensaje= (EditText) findViewById(R.id.editText);
    }

    public void rellenaSpinner(){

        String array_provincias[] = getResources().getStringArray(R.array.provincias); //en un array nos guardamos las provincias,que estan en un fichero en values
        adap_provincias = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,array_provincias);//Creamos ArrayAdapter para el Spinner de provincias
        spProvincias.setAdapter(adap_provincias);//Se lo asignamos al Spinner

        adap_localidades = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item);//Creamos ArrayAdapter para el Spinner de Localidades
        //Cuando el Spinner de provincias es seleccionado..
        spProvincias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Obtendremos la posicion de la provincia seleccionada y rellenaremos el Spinner de localidades segun sea la provincia seleccionada
                TypedArray ta = getResources().obtainTypedArray(R.array.array_provincia_a_localidades);
                CharSequence array_localidades[] = ta.getTextArray(position);
                ta.recycle();

                adap_localidades.clear();
                adap_localidades.addAll(array_localidades);
                spLocalidades.setAdapter(adap_localidades);//Asignamos en ArrayAdapter de localidades, creado anteriormente
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /*Nos guardamos la informacion introducida en los componentes del activity
      para crear un intent para pasar al otro activity con los datos guardados*/
    public void accion(){

        String nombre = mensaje.getText().toString();
        String provincia = spProvincias.getSelectedItem().toString();
        String localidad = spLocalidades.getSelectedItem().toString();
        boolean bueno = eleccion.isChecked();

        Intent i = new Intent(getApplicationContext(), Principal.class);
        i.putExtra("nombreIntent",nombre);
        i.putExtra("provinciaIntent",provincia);
        i.putExtra("localidadIntent",localidad);
        i.putExtra("eleccionIntent",bueno);

        setResult(RESULT_OK, i);
        super.finish();
    }

    public void continuar(){
        inicializarVariables();
        rellenaSpinner();
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accion();
            }
        });
    }
}
