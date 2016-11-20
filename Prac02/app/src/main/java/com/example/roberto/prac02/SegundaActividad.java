package com.example.roberto.prac02;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SegundaActividad extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda_actividad);

        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView1 = (TextView) findViewById(R.id.textView2);
        TextView textView2 = (TextView) findViewById(R.id.textView3);
        TextView textView3 = (TextView) findViewById(R.id.textView4);
        TextView textView4 = (TextView) findViewById(R.id.textView5);
        TextView textView5 = (TextView) findViewById(R.id.textView6);
        TextView textView6 = (TextView) findViewById(R.id.textView7);

        Bundle b = getIntent().getExtras();
        textView.setText("Nombre: "+b.getString("Nom"));
        textView1.setText("Calidad: "+b.getString("Calidad"));
        textView2.setText("Tipo: "+b.getString("Tipo"));
        textView3.setText("Vida: "+b.getString("Vida"));
        textView4.setText("Velocidad: "+b.getString("Velocidad"));
        textView5.setText("Daño: "+b.getString("Daño"));

        textView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = getIntent().getExtras();
                Uri url= Uri.parse(b.getString("Link"));
                Intent intent = new Intent(Intent.ACTION_VIEW,url);
                startActivity(intent);
            }
        });
    }
}
