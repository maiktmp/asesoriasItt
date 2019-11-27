package com.pitt.asesoriasitt.ui.advisory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pitt.asesoriasitt.R;
import com.pitt.asesoriasitt.databinding.ActivityListAdvisoriesBinding;
import com.pitt.asesoriasitt.databinding.ItemAdvisoryBinding;
import com.pitt.asesoriasitt.db.models.Advisory;
import com.pitt.asesoriasitt.db.models.Day;
import com.pitt.asesoriasitt.db.models.Rol;
import com.pitt.asesoriasitt.db.models.User;
import com.pitt.asesoriasitt.interactors.UserInteractor;
import com.pitt.asesoriasitt.ui.Codes;
import com.pitt.asesoriasitt.ui.Dialogs;
import com.pitt.asesoriasitt.ui.user.ActivityUserDetail;
import com.pitt.asesoriasitt.ui.user.UserAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class ActivityAdvisoryList extends AppCompatActivity {

    private ActivityListAdvisoriesBinding vBind;
    private UserInteractor userInteractor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_list_advisories);
        userInteractor = UserInteractor.getInstance();
        setUpBackButton();
        setUpFilter();
        filter(null);
    }

    private void setUpBackButton() {
        setSupportActionBar(vBind.toolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Dialogs.alert(this, "¿Desea cerrar salir?", this::finish);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setUpFilter() {
        vBind.etFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0 && editable.toString().length() % 2 == 0) {
                    filter(vBind.etFilter.getText().toString());
                }
                if (editable.toString().length() == 0) {
                    filter(null);
                }
            }
        });
    }

    private void filter(String query) {
        vBind.pbLoading.setVisibility(View.VISIBLE);
        vBind.llAdvisory.setVisibility(View.GONE);
        vBind.tvEmpty.setVisibility(View.GONE);
        vBind.llAdvisory.removeAllViews();

        userInteractor.getAdvisrories(
                query,
                this::setUpAdvisories
        );
    }

    @SuppressLint("SetTextI18n")
    private void setUpAdvisories(ArrayList<Advisory> advisories) {
        for (Advisory advisory : advisories) {
            View itemAdvisory = LayoutInflater.from(this).inflate(
                    R.layout.item_advisory,
                    vBind.llAdvisory,
                    false
            );
            ItemAdvisoryBinding itemAdvisoryBinding = ItemAdvisoryBinding.bind(itemAdvisory);
            itemAdvisoryBinding.tvAdvisoryName.setText(advisory.getName());
            itemAdvisoryBinding.tvAdvisoryPlace.setText(advisory.getPlaceName());

            StringBuilder label = new StringBuilder();
            for (Day day : advisory.getDays()) {
                try {
                    StringBuilder labelDay = new StringBuilder();
                    Formatter fmt = new Formatter(labelDay);
                    Date startHour = new SimpleDateFormat("HH:mm:ss", new Locale("es", "Mx")).parse(day.getAdvisoryHasDay().getStartHour());
                    Date endHour = new SimpleDateFormat("HH:mm:ss", new Locale("es", "Mx")).parse(day.getAdvisoryHasDay().getEndHour());

                    fmt.format("%s %tR - %tR", day.getName(), startHour, endHour);
                    label.append(labelDay);
                    label.append("\n");
                    System.out.println(label.toString());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            itemAdvisoryBinding.tvTeacher.setVisibility(View.VISIBLE);
            itemAdvisoryBinding.tvTeacher.setText(
                    advisory.getUser().getFullName() + "\n" +
                            advisory.getUser().getContact().getName() + "\n"
            );

            itemAdvisoryBinding.tvAdvisoryDescription.setText(label.toString());
            vBind.llAdvisory.addView(itemAdvisory);
        }

        vBind.pbLoading.setVisibility(View.GONE);
        vBind.llAdvisory.setVisibility(View.VISIBLE);
        if (advisories.isEmpty()) vBind.tvEmpty.setVisibility(View.VISIBLE);
    }
}
