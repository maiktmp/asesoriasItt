package com.maik.greenhouse;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.firestore.FirebaseFirestore;
import com.maik.greenhouse.FB.FBInteractors;
import com.maik.greenhouse.databinding.ActivityAverageBinding;

import java.util.Iterator;
import java.util.Map;

public class AverageActivity extends AppCompatActivity {
    private ActivityAverageBinding vBind;
    private FBInteractors fbInteractors;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_average);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        fbInteractors = FBInteractors.getInstance();
        fetchAvg();

        setSupportActionBar(vBind.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @SuppressLint("DefaultLocale")
    private void fetchAvg() {
        fbInteractors.getGreenHouse(greenHouse -> {
            Map map = greenHouse.getAverage();
            Iterator it = greenHouse.getAverage().keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                System.out.println("Clave: " + key + " -> Valor: " + map.get(key));

                View view = LayoutInflater.from(this).inflate(
                        android.R.layout.simple_list_item_2,
                        vBind.llRoot,
                        false
                );

                TextView tv1 = view.findViewById(android.R.id.text1);
                TextView tv2 = view.findViewById(android.R.id.text2);

                tv1.setText(String.format("FECHA DEL REGISTRO: %s", key));
                tv2.setText(String.format("Promedio de temperatura registrada: %s", (String) map.get(key)));

                vBind.llRoot.addView(view);
            }
        });
    }

}
