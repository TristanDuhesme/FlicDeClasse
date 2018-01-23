package com.tristanduhesme.flicdeclasse;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestInternet extends AppCompatActivity {

    private LinearLayout linearLayout;
    private Button requestButton;
    //private TextView resultsTextView;
    private ListView listView;
    private Snackbar snackbar;
    private Snackbar snackbar2;
    private HashMap<String, Bitmap> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeView();
        setContentView(linearLayout);
        snackbar = Snackbar.make(linearLayout, "Requête en cours d'exécution",
                Snackbar.LENGTH_INDEFINITE);
        snackbar2 = Snackbar.make(linearLayout, "Connexion impossible",
                Snackbar.LENGTH_INDEFINITE);
    }

    private void makeView() {
        /*requestButton = new Button(this);
        requestButton.setText("Lancer une requête");
        requestButton.setOnClickListener(this);
        */
        listView = new ListView(this);

        //resultsTextView = new TextView(this);
        //resultsTextView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //linearLayout.addView(requestButton);
        linearLayout.addView(listView);
        //linearLayout.addView(resultsTextView);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isConnected()) {
            Snackbar.make(this.linearLayout, "Aucune connexion à internet.", Snackbar.LENGTH_LONG).show();
            return;
        }
        // images : https://dummyimages.com/64/[hexa random]/fff
        //String url = "https://my-json-server.typicode.com/TristanDuhesme/FakeServer/users";//"http://echo.jsontest.com/key1/value1/key2/value2/ke3/value3";//"http://httpbin.org/ip";//"www.google.com";//
        String url = "http://172.17.3.42:8888/index.py";
        FetchTask mAsyncTask;
        mAsyncTask = (FetchTask) new FetchTask().execute(url);

    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void populateList(ListView vue, String s) throws JSONException {
        JSONArray pupils;
        List<HashMap<String, Object>> liste = new ArrayList<>();
        HashMap<String,Object> element;

        try {
            pupils = new JSONArray (s);
        } catch (Throwable t) {
            Log.e("INFODEBUG JSON : ", "Error parsing json: "+ t.getMessage() );
            return;
        }

        for(int i = 0 ; i < pupils.length() ; i++) {
            Log.i("INFODEBUG","eleve "+i);
            element = new HashMap<>();
            JSONObject temp = pupils.getJSONObject(i);
            String identifiant = temp.getString("id");
            element.put("id", identifiant);
            element.put("identite", temp.getString("identite"));
            element.put("date", temp.getString("date"));
            element.put("heure", temp.getString("heure"));
            element.put("adressse_photo", temp.getString("photo"));
            element.put("approbation", temp.getString("approbation"));
            //String tempUrl = temp.getString("image");
            //element.put("url_image", tempUrl);
            Log.i("INFODEBUG", "element : "+element.toString());
            /*FetchTaskImage myFetchTaskImage = null;
            try {
                myFetchTaskImage = (FetchTaskImage) new FetchTaskImage().execute("http://172.17.3.42:8888/image/farah/img.png", identifiant);
            } catch (Throwable t) {
                Log.e("INFODEBUG", t.getMessage());
            }
            myFetchTaskImage.cancel(true);
            element.put("image", images.get(identifiant));*/
            Log.i("INFODEBUG",element.toString());
            liste.add(element);
        }
        ListAdapter adapter = new SimpleAdapter(this,
                liste,
                R.layout.item_liste_test_internet,
                //new String[] {"image","prenom","nom", "date", "heure",},
                new String[] {"identite", "date", "heure",},
                //new int[] {R.id.image,R.id.prenom, R.id.nom, R.id.date, R.id.heure});
                new int[] {R.id.identite, R.id.date, R.id.heure});
        vue.setAdapter(adapter);
    }

    private class FetchTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            snackbar.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.i("INFODEBUG doInBackgroun","url = "+strings[0]);
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
                    //Log.i("INFODEBUG", line);
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
                snackbar2.show();
            } else {
                try {
                    populateList(listView, s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            snackbar.dismiss();
        }
    }
    private class FetchTaskImage extends AsyncTask<String, Void, String> {
    /*    protected Drawable drawableFromUrl(URL url) throws IOException {
            Log.i("INFODEBUG","drawableFromUrl "+url);
            Drawable ret = null;
            InputStream stream = null;
            try {
                stream = url.openStream();
                ret = Drawable.createFromStream(stream, "src");
            } catch (IOException ioe) {
                throw ioe;
            } catch (Exception e) {
                Log.e("INFODEBUG","Failed to read image from URL !",e);
                throw new IOException("ImageFromUrlFailure",e);
            } finally {
                if (stream != null) try { stream.close(); } catch (Exception ex) { Log.wtf("INFODEBUG","Failed to gracefully close the stream !",ex); }
            }
            return ret;
        }

        protected Drawable imageOperations(String urls) throws IOException {
            Log.i("INFODEBUG","imageOperations "+urls);
            URL url = new URL(urls);
            return drawableFromUrl(url);
        }
    */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i("INFODEBUG","onPreExecute");
            snackbar.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.i("INFODEBUG","doInBackground"+strings);
            String stringUrl = strings[0];
            String identifiant = strings[1];
            // URL url = new URL(stringUrl);
            Log.i("INFODEBUG", "url = " + stringUrl);
            Log.i("INFODEBUG", "id = " + identifiant);
            /*Drawable temp2 = null;
            try {
                temp2 = imageOperations(stringUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            images.put(identifiant, temp2);
            return identifiant;
*/
            try {
                //ImageView photo = (ImageView)findViewById(R.id.photoActivite);
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(stringUrl).getContent());
                images.put(identifiant, bitmap);
                //photo.setImageBitmap(bitmap);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("INFODEBUG",e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("INFODEBUG",e.getMessage());
            }
            return identifiant;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("INFODEBUG","image onPostExecute");
            if (s == null) {
                snackbar2.show();
                //resultsTextView.setText("Erreur");
            } else {
                //populateList(listView, s);
                //resultsTextView.setText(s);
            }
            snackbar.dismiss();
        }
    }


}