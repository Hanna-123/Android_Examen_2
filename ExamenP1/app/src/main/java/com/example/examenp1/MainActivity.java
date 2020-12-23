package com.example.examenp1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnTouchListener{
    TextView jtv;
    Button btn;
    RelativeLayout jll = null;
    double x ;
    double y;
    int countPuntos = 0;
    double [] puntosX = new double[3];
    double [] puntosY = new double[3];
    Lienzo l;
    boolean borrarCanvas = false;
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        jtv = (TextView) findViewById(R.id.xtv);
        jll= (RelativeLayout) findViewById(R.id.xll);
        btn = (Button) findViewById(R.id.xbtn);
        l = new Lienzo(this);
        l.setOnTouchListener(this);
        jll.addView(l);





    }


    ///////aun no sirve
    public void borrarTodo(View v){
        jtv.setText("");
        for(int i =0; i < 3; i++){
            puntosX[i] = 0.0;
            puntosY[i] = 0.0;
            x = 0;
            y = 0;
        }
        countPuntos = 0;
        jtv.append("\t ====Vuelva a pulsar 3 veces=== \n\n");


    }
    public boolean onTouch(View v, MotionEvent event) {
        if (countPuntos < 3){
            if(event.getAction() == MotionEvent.ACTION_DOWN){

                x = event.getX();
                y = event.getY();
                puntosX[countPuntos] = x;
                puntosY[countPuntos] = y;
                countPuntos ++;
                l.invalidate();
            }

        }


        return true;
    }

    public double magnitud(double x1, double y1, double x2, double y2){
        double redMagnitud = 0;

        double difX = x2 - x1;
        double difY = y2 - y1;

        double powX = Math.pow(difX,2);
        double powY = Math.pow(difY,2);

        redMagnitud = Math.sqrt(powX+powY);

        return redMagnitud;
    }
    public double pendiente(double x1, double y1, double x2, double y2){

        double difX = x2 - x1;
        double difY = y2 - y1;
        if (difX == 0) difX = 1;
        if (difY == 0) difY = 1;

        return  difY/difX;
    }
    public double angulo(double x1, double y1, double x2, double y2, double xr, double yr ){

        double angle1 = Math.atan2(y1 - yr, x1 - xr);
        double angle2 = Math.atan2(y2 - yr, x2 - xr);

        return Math.toDegrees(angle1 - angle2);
    }


    public class Lienzo extends View{


        public Lienzo(Context c){
            super(c);
        }
        protected void onDraw(Canvas c) {
            super.onDraw(c); // Canvas pinta atributos
            Paint p = new Paint(); // Paint asigna atributo
            p.setColor(Color.WHITE); // Fondo blanco
            c.drawPaint(p);
            p.setColor(Color.rgb(0, 0, 255)); // Ejes azules
            for (int i = 0; i < 3; i++){
                c.drawCircle((float) puntosX[i], (float) puntosY[i], 10, p);
                p.setTextSize(30);
                c.drawText("P"+(i+1),(float) puntosX[i]+20,(float) puntosY[i]+20, p);

            }

            p.setStrokeWidth(5);

            if (countPuntos > 2){
                c.drawLine((float) puntosX[0],(float) puntosY[0], (float) puntosX[1],(float) puntosY[1], p);
                c.drawLine((float) puntosX[0],(float) puntosY[0], (float) puntosX[2],(float) puntosY[2], p);
            }

            if (countPuntos > 2) {
                jtv.append("Magnitud P1P2: " + magnitud(puntosX[0], puntosY[0], puntosX[1], puntosY[1])+"\n");
                jtv.append("Magnitud P1P3: " + magnitud(puntosX[0], puntosY[0], puntosX[2], puntosY[2])+"\n");
                jtv.append("Magnitud P2P3: " + magnitud(puntosX[1], puntosY[1], puntosX[2], puntosY[2])+"\n");
                double p1 = pendiente(puntosX[0], puntosY[0], puntosX[1], puntosY[1]);
                double p2 = pendiente(puntosX[0], puntosY[0], puntosX[2], puntosY[2]);
                double anguloTot = angulo(puntosX[1], puntosY[1], puntosX[2], puntosY[2], puntosX[0], puntosY[0]);
                if (anguloTot < 0) anguloTot = anguloTot *-1;
                if (anguloTot > 180) anguloTot = 360 - anguloTot;
                jtv.append("Angulo: " +  anguloTot+"\n");
                p.setColor(Color.RED);
                double [] puntoMedioX = new double[2];
                double [] puntoMedioY = new double[2];
                puntoMedioX[0] =  (puntosX[1] + puntosX[0])/2;
                puntoMedioY[0] =  (puntosY[1] + puntosY[0])/2;
                puntoMedioX[1] =  (puntosX[2] + puntosX[0])/2;
                puntoMedioY[1] =  (puntosY[2] + puntosY[0])/2;

                c.drawCircle((float) puntoMedioX[0], (float) puntoMedioY[0], 11, p);
                c.drawCircle((float) puntoMedioX[1], (float) puntoMedioY[1], 11, p);
                c.drawLine((float) puntoMedioX[0],(float) puntoMedioY[0], (float) puntoMedioX[1],(float) puntoMedioY[1], p);


            }
            if(borrarCanvas){
                p.setColor(Color.WHITE); // Fondo blanco
                c.drawPaint(p);
            }



        }
    }
}
