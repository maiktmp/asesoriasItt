package com.pitt.asesoriasitt.ui.advisory;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pitt.asesoriasitt.R;
import com.pitt.asesoriasitt.databinding.AdvisoryFormActivityBinding;
import com.pitt.asesoriasitt.db.models.Advisory;
import com.pitt.asesoriasitt.db.models.AdvisoryHasDay;
import com.pitt.asesoriasitt.db.models.Day;
import com.pitt.asesoriasitt.interactors.UserInteractor;
import com.pitt.asesoriasitt.ui.Dialogs;

import java.util.ArrayList;

public class ActivityAdvisoryForm extends AppCompatActivity {
    public static final String USER_ID = "USER_ID";
    public static final String ADVISORY = "ADVISORY";


    private AdvisoryFormActivityBinding vBind;
    private ArrayList<Day> days = Day.days();
    private ArrayList<Day> formDay = new ArrayList<>();
    private long userId;
    private Advisory advisory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBind = DataBindingUtil.setContentView(this, R.layout.advisory_form_activity);
        userId = getIntent().getLongExtra(USER_ID, 0);
        advisory = (Advisory) getIntent().getSerializableExtra(ADVISORY);
        setUpToggleCards();
        setUpSaveButton();
        setUpUpdateData();
        setUpBackButton();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setUpBackButton() {
        setSupportActionBar(vBind.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
    }

    private void setUpUpdateData() {
        if (advisory != null) {
            vBind.etName.setText(advisory.getName());
            vBind.etPlace.setText(advisory.getPlaceName());
        }
    }

    private void setUpToggleCards() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        vBind.rvToggleCards.setLayoutManager(layoutManager);
        vBind.rvToggleCards.setItemAnimator(new DefaultItemAnimator());
        DayAdapter adapter;
        if (advisory == null) {
            adapter = new DayAdapter(
                    this,
                    (daySelected, isStart, hour) -> {
                        for (Day day : days) {
                            if (daySelected.getId() == day.getId()) {
                                if (isStart) {
                                    day.getAdvisoryHasDay().setStartHour(hour);
                                } else {
                                    day.getAdvisoryHasDay().setEndHour(hour);
                                }
                            }

                        }
                    }
            );
        } else {

            ArrayList<Day> days = Day.days();
            for (Day advisoryDay : advisory.getDays()) {
                for (Day day : days) {
                    if (day.getId() == advisoryDay.getId()) {
                        day.setAdvisoryHasDay(advisoryDay.getAdvisoryHasDay());
                    }
                }
            }
            this.days = days;
            adapter = new DayAdapter(
                    days,
                    this,
                    (daySelected, isStart, hour) -> {
                        for (Day day : days) {
                            if (daySelected.getId() == day.getId()) {
                                if (isStart) {
                                    day.getAdvisoryHasDay().setStartHour(hour);
                                } else {
                                    day.getAdvisoryHasDay().setEndHour(hour);
                                }
                            }

                        }
                    }
            );
        }
        vBind.rvToggleCards.setAdapter(adapter);
    }

    private void setUpSaveButton() {

        vBind.btnSave.setOnClickListener(v -> {
            if (validate()) {
                advisory = advisory == null ? new Advisory() : advisory;
                advisory.setName(vBind.etName.getText().toString());
                advisory.setPlaceName(vBind.etPlace.getText().toString());
                advisory.setDays(formDay);
                advisory.setFkIdUser(userId);

                UserInteractor.getInstance().createAdvisory(userId, advisory, gr -> {
                    if (gr == null) {
                        Dialogs.alert(this, "Ocurrio un error intente más tarde");
                        return;
                    }

                    if (!gr.isSuccess()) {
                        Dialogs.alert(this, "Ocurrio un error intente más tarde");
                        return;
                    }

                    if (gr.isSuccess()) {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private boolean validate() {
        boolean isValid = true;
        if (vBind.etName.getText().toString().trim().length() < 3) {
            isValid = false;
            vBind.tilName.setError("El nombre de la asesoria es requerida.");
        }
        boolean hoursIsEmpty = true;
        for (Day day : days) {
            AdvisoryHasDay advisoryHasDay = day.getAdvisoryHasDay();
            if (advisoryHasDay.getStartHour() != null ||
                    advisoryHasDay.getEndHour() != null) {

                hoursIsEmpty = false;

                if (advisoryHasDay.getStartHour() == null) {
                    isValid = false;
                    vBind.tvErrors.setText("Ingrese la hora de inicio del día " + day.getName());
                }

                if (advisoryHasDay.getEndHour() == null) {
                    isValid = false;
                    vBind.tvErrors.setText("Ingrese la hora de fin del día " + day.getName());
                }
            }

        }

        if (hoursIsEmpty) {
            vBind.tvErrors.setText("Ingrese al menos un horario a la asesoría");
        }

        if (!isValid || hoursIsEmpty) {
            vBind.cvErrors.setVisibility(View.VISIBLE);
        } else {
            vBind.cvErrors.setVisibility(View.GONE);
            for (Day day : days) {
                AdvisoryHasDay advisoryHasDay = day.getAdvisoryHasDay();
                if (advisoryHasDay.getStartHour() != null ||
                        advisoryHasDay.getEndHour() != null) {
                    formDay.add(day);
                }
            }

        }


        return isValid && !hoursIsEmpty;
    }
}
