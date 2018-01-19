package com.tristanduhesme.flicdeclasse;

        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.support.design.widget.Snackbar;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;

public class TestInternet extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout linearLayout;
    private Button requestButton;
    private TextView resultsTextView;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeView();
        setContentView(linearLayout);
        snackbar = Snackbar.make(linearLayout, "Requête en cours d'exécution",
                Snackbar.LENGTH_INDEFINITE);
    }

    // Le layout est généré programmatiquement pour simplifier l'article
    private void makeView() {
        requestButton = new Button(this);
        requestButton.setText("Lancer une requête");
        requestButton.setOnClickListener(this);

        resultsTextView = new TextView(this);

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(requestButton);
        linearLayout.addView(resultsTextView);
    }

    @Override
    public void onClick(View view) {
        if (!isConnected()) {
            Snackbar.make(view, "Aucune connexion à internet.", Snackbar.LENGTH_LONG).show();
            return;
        }
        String url = "http://httpbin.org/ip";//"www.google.com";//
        new FetchTask().execute(url);
    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private class FetchTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            snackbar.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            InputStream inputStream = null;
            HttpURLConnection conn = null;

            String stringUrl = strings[0];
            try {
                URL url = new URL(stringUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int response = conn.getResponseCode();
                if (response != 200) {
                    return null;
                }

                inputStream = conn.getInputStream();
                if (inputStream == null) {
                    return null;
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                    buffer.append("\n");
                }

                return new String(buffer);
            } catch (IOException e) {
                return null;
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException ignored) {
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                resultsTextView.setText("Erreur");
            } else {
                resultsTextView.setText(s);
            }
            snackbar.dismiss();
        }
    }
}