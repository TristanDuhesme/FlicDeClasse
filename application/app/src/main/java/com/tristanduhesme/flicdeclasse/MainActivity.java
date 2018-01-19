package com.tristanduhesme.flicdeclasse;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button historique = null;
    Button params= null;
    Button add = null;
    TextView maZoneDeTexte = null;
    int compteur = 0;
    String zePhrase = null;
    Resources res = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        res = getResources();
        zePhrase = res.getString(R.string.welcomeMessage);
        //String applicationName = getResources().getString(R.string.app_name);
        add = findViewById(R.id.addNewFaceId);
        add.setOnClickListener(addListener);
        params = findViewById(R.id.paramAlarmsId);
        params.setOnClickListener(paramsListener);
        historique = findViewById(R.id.historiqueId);
        historique.setOnClickListener(showHistorique);
        //maZoneDeTexte = findViewById(R.id.);
        //maZoneDeTexte.setText(zePhrase);
    }

    private View.OnClickListener addListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, TestInternet.class);
            startActivity(intent);
        }
    };
    private View.OnClickListener paramsListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, ParamActivity.class);
            Log.i("[DEBUG]","new Intent");
            startActivity(intent);
            Log.i("[DEBUG]","startActivity");
        }
    };
    private View.OnClickListener showHistorique = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, ListHistoryActivity.class);
            startActivity(intent);
        }
    };
}