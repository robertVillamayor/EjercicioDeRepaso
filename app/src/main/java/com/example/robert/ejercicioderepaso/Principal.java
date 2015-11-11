package com.example.robert.ejercicioderepaso;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ThemedSpinnerAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends AppCompatActivity {

    private TextView msg,msg1,msg2,msg3;
    private Button boton;
    private int contadorr;

    public static final int ACTIVITY_DOS=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        accionBotones();
        reseteaContinuaDesapareceNotificacion();


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*Si ha ido bien el paso de activity dos al principal, nos guardaremos los datos
         del intent y emplearemos la logica necesaria para cumplir con la actividad */
        if(resultCode==RESULT_OK&&requestCode==ACTIVITY_DOS){

            String nom=data.getExtras().getString("nombreIntent");
            String loc=data.getExtras().getString("localidadIntent");
            String pro=data.getExtras().getString("provinciaIntent");
            boolean bueno = data.getExtras().getBoolean("eleccionIntent");
            if(bueno==true) {
                notificationExtendida(nom, "De " + loc + ", ("+pro+")",nom,loc,pro,1);
            }else{
                msg1.setText(nom);
                msg2.setText(loc);
                msg3.setText(pro);
            }
        }
    else{
            Toast.makeText(getApplicationContext(),"ERROR EN LA APLICACION", Toast.LENGTH_SHORT).show();
        }
    }

    public void accionBotones(){
        inicializaVariables();
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contador();
            }
        });
    }

    public void reseteaContinuaDesapareceNotificacion(){

        /*Si recojemos el intent que hemos creado y no contine datos del bundle,
          haremos que desaparezca la notificacion y pasamos a la siguiente pagina*/
        if(getIntent().getExtras()==null){

            NotificationManager n = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            n.cancel(1);
        }
        /*Si no, los datos que recojemos, es lo que habia en la variable contador,
          escribimos contadorr en el label y nos guardamos el valor del dato en la variabe,
          luego haremos desaparecer la notificacion */
        else{

            Bundle bundle=getIntent().getExtras();
            msg.setText(String.valueOf(bundle.getInt("contador")));
            contadorr=bundle.getInt("contador");

            NotificationManager n = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            n.cancel(1);
        }
    }

    public void contador() {

        contadorr=contadorr+1;
        msg.setText(String.valueOf(contadorr));
        if(contadorr<=5){
            notification("PULSACIONES", "Hemos pulsado "+contadorr+ " veces el boton",1);
        }
        if(contadorr==5){
            //Pasamos al sguiente activity
            Intent i = new Intent(getApplicationContext(), SegundoActivity.class);
            startActivityForResult(i, ACTIVITY_DOS);
        }


    }

    public void notificationExtendida(String titulo,String contenido,String msgAnyadido,String msgAnyadido1,String msgAnyadido2,int id){

        Notification.Builder n = new Notification.Builder(this);
        n.setContentTitle(titulo);
        n.setContentText(contenido);
        n.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        n.setSmallIcon(R.mipmap.ic_launcher);
        n.setDefaults(Notification.DEFAULT_VIBRATE);
        n.setDefaults(Notification.DEFAULT_SOUND) ;

        //Definim l'objecte InboxStyle
        Notification.InboxStyle iS = new Notification.InboxStyle();

        //Li afegim les línies de text que mostrarà quan s'expandisca
        iS.addLine("Este és tu nombre: "+msgAnyadido);
        iS.addLine("Esta es tu poblacion: "+msgAnyadido1);
        iS.addLine("Esta és tu provincia: " + msgAnyadido2);

        //Afegim l'InboxStyle a la notificació
        n.setStyle(iS);

        //Intent que utilizaremos para resetear (no nos guardamos nada porque la variable contadorr==0)
        Intent resultIntent = new Intent(this,Principal.class);

        //Intemt que utilizaremos para continuar, donde generaremos un bundle para guardarnos la variable contadorr
        Intent resultIntent1 = new Intent(this,Principal.class);
        Bundle b = new Bundle();
        b.putInt("contador", contadorr);
        resultIntent1.putExtras(b);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent resultPendingIntent1 = PendingIntent.getActivity(this,1,resultIntent1,PendingIntent.FLAG_UPDATE_CURRENT);

        n.setContentIntent(resultPendingIntent);
        n.setContentIntent(resultPendingIntent1);

        n.addAction(R.mipmap.ic_launcher, "Resetear", resultPendingIntent);
        n.addAction(R.mipmap.ic_launcher, "Continuar", resultPendingIntent1);

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(id, n.build());



    }

    public void notification(String titulo,String contenido,int id){

        Notification.Builder n = new Notification.Builder(this);
        n.setContentTitle(titulo);
        n.setContentText(contenido);
        n.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        n.setSmallIcon(R.mipmap.ic_launcher);
        n.setDefaults(Notification.DEFAULT_VIBRATE);
        n.setDefaults(Notification.DEFAULT_SOUND) ;

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(id, n.build());
    }

    //Inicializamos las variables
    public void inicializaVariables(){

        boton= (Button) findViewById(R.id.button);
        msg= (TextView) findViewById(R.id.textView);
        msg1= (TextView) findViewById(R.id.textView2);
        msg2= (TextView) findViewById(R.id.textView3);
        msg3= (TextView) findViewById(R.id.textView4);
        contadorr = 0;
    }

}
