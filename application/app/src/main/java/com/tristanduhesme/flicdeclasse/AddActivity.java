package com.tristanduhesme.flicdeclasse;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

/**
 * Created by Tristan on 20/12/2017.
 */

public class AddActivity extends Activity {
    ListView vue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        vue = (ListView) findViewById(R.id.activityAddId);
    }
}
