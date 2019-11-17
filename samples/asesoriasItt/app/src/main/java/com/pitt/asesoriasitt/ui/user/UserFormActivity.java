package com.pitt.asesoriasitt.ui.user;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TabHost;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.pitt.asesoriasitt.R;
import com.pitt.asesoriasitt.databinding.UserFormActivityBinding;
import com.pitt.asesoriasitt.db.models.Contact;
import com.pitt.asesoriasitt.db.models.User;
import com.pitt.asesoriasitt.interactors.UserInteractor;
import com.pitt.asesoriasitt.ui.Dialogs;


public class UserFormActivity extends AppCompatActivity {
    public static final String USER = "USER";

    private UserFormActivityBinding vBind;
    private long carrierId = 1;
    private UserInteractor userInteractor;
    private User user;
    private Long userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBind = DataBindingUtil.setContentView(this, R.layout.user_form_activity);
        userInteractor = UserInteractor.getInstance();
        setUpCarrier();
        setUpBackButton();
        setUpSAaveButton();
        if (getIntent() != null) {
            userId = getIntent().getLongExtra(USER, 0);
            userInteractor.getUserDetail(userId, user -> {
                this.user = user;
                vBind.setUser(user);
            });
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setUpBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setUpCarrier() {
        String[] carriers = new String[]{
                "Ing. Idustrial",
                "Ing. Logística",
                "Ing. Sistemas",
                "Ing. Química",
                "Ing. Mecatrónica",
                "T.I.C.S",
                "Ing. Electrónica",
        };

        vBind.listCarrier.setText(carriers[0]);

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        carriers);

        vBind.listCarrier.setAdapter(adapter);

        vBind.listCarrier.setOnItemClickListener((parent, view, position, id) -> carrierId = position + 1);
    }

    private void setUpSAaveButton() {
        vBind.btnSave.setOnClickListener(v -> {
            clearErrors();
            if (validate()) {
                user = user == null ? new User() : user;
                user.setFullName(vBind.etFullName.getText().toString());
                user.setUsername(vBind.etUserName.getText().toString());
                user.setFkIdCarrier(carrierId);

                Contact contact = user.getContact() == null ? new Contact() : user.getContact();
                contact.setName(vBind.etContact.getText().toString());
                user.setContact(contact);

                if (!vBind.etPassword.getText().equals("")) {
                    user.setPassword(vBind.etPassword.getText().toString());
                }

                userInteractor.updateUser(
                        user,
                        gr -> {
                            if (gr == null) {
                                Dialogs.alert(this, "Ocurrio un error intente más tarde");
                                return;
                            }
                            finish();
                        }
                );
            }
        });
    }

    private boolean validate() {
        boolean isValid = true;

        if (vBind.etUserName.getText().toString().equals("")) {
            vBind.tilUserName.setError("Ingrese el usuario");
            isValid = false;
        }

        if (vBind.etFullName.getText().toString().equals("")) {
            vBind.tilFullName.setError("Ingrese el nombre");
            isValid = false;
        }

        if (vBind.etContact.getText().toString().equals("")) {
            vBind.tilContact.setError("Ingrese el contacto");
            isValid = false;
        }

        return isValid;
    }

    private void clearErrors() {
        vBind.tilUserName.setError("");
        vBind.tilFullName.setError("");
        vBind.tilContact.setError("");
    }
}
