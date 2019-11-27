package com.pitt.asesoriasitt.ui.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.pitt.asesoriasitt.R;
import com.pitt.asesoriasitt.databinding.ItemAdvisoryBinding;
import com.pitt.asesoriasitt.databinding.UserProfileActivityBinding;
import com.pitt.asesoriasitt.db.models.Advisory;
import com.pitt.asesoriasitt.db.models.Day;
import com.pitt.asesoriasitt.db.models.User;
import com.pitt.asesoriasitt.interactors.UserInteractor;
import com.pitt.asesoriasitt.ui.Codes;
import com.pitt.asesoriasitt.ui.advisory.ActivityAdvisoryForm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class ActivityUserDetail extends AppCompatActivity {

    public static String USER_ID_TAG = "USER_ID";
    public static String IS_MAIN = "IS_MAIN";

    private UserProfileActivityBinding vBind;
    private UserInteractor userInteractor;
    private long userId;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInteractor = UserInteractor.getInstance();
        vBind = DataBindingUtil.setContentView(this, R.layout.user_profile_activity);
        userId = getIntent().getLongExtra(USER_ID_TAG, 0);
        boolean isMain = getIntent().getBooleanExtra(IS_MAIN, false);
        setUpAppendBtn();
        setUpEditBtn();
        if (!isMain) {
            setUpBackButton();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        userInteractor.getUserDetail(userId, this::setUpProfile);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Codes.UPSERT_ADVISORY) {
            if (resultCode == RESULT_OK) {
                userInteractor.getUserDetail(userId, this::setUpProfile);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void setUpProfile(User user) {
        this.user = user;
        vBind.tvUserName.setText("Nombre: " + user.getFullName());
        vBind.tvUserCarrier.setText("Carrera: " + user.getCarrier().getName());
        vBind.tvUserUsername.setText("Usuario: " + user.getUsername());
        vBind.tvUserAdvisoryCount.setText("#Asesorías: " + String.valueOf(user.getAdvisories().size()));
        vBind.tvContact.setText("Contacto: \n " + user.getContact().getName());


        vBind.pbLoading.setVisibility(View.GONE);
        vBind.cvDetails.setVisibility(View.VISIBLE);
        vBind.llAdvisory.setVisibility(View.VISIBLE);

        vBind.llAdvisory.removeAllViews();
        for (Advisory advisory : user.getAdvisories()) {
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
            itemAdvisoryBinding.tvAdvisoryDescription.setText(label.toString());

            itemAdvisoryBinding.itemRoot.setOnClickListener(v -> {
                Intent i = new Intent(this, ActivityAdvisoryForm.class);
                Bundle args = new Bundle();
                args.putSerializable(ActivityAdvisoryForm.ADVISORY, advisory);

                i.putExtras(args);
                i.putExtra(ActivityAdvisoryForm.USER_ID, userId);
                startActivityForResult(i, Codes.UPSERT_ADVISORY);
            });
            vBind.llAdvisory.addView(itemAdvisory);
        }
    }

    private void setUpAppendBtn() {
        vBind.btnAppendAdvisory.setOnClickListener(v -> {
            Intent i = new Intent(this, ActivityAdvisoryForm.class);
            i.putExtra(ActivityAdvisoryForm.USER_ID, userId);
            startActivityForResult(i, Codes.UPSERT_ADVISORY);
        });
    }

    private void setUpEditBtn() {
        vBind.btnEditUser.setOnClickListener(v -> {
            Intent i = new Intent(this, UserFormActivity.class);
            i.putExtra(UserFormActivity.USER, userId);
            startActivity(i);
        });
    }
}
