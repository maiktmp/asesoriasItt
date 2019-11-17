package com.pitt.asesoriasitt.ui.user;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.pitt.asesoriasitt.R;
import com.pitt.asesoriasitt.databinding.ActivityLoginBinding;
import com.pitt.asesoriasitt.db.models.Rol;
import com.pitt.asesoriasitt.db.models.User;
import com.pitt.asesoriasitt.interactors.UserInteractor;
import com.pitt.asesoriasitt.ui.Dialogs;

public class ActivityLogin extends AppCompatActivity {

    private ActivityLoginBinding vBind;
    private UserInteractor userInteractor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_login);
        userInteractor = UserInteractor.getInstance();
        setUpLoginBtn();
        getSupportActionBar().hide();
    }

    private void setUpLoginBtn() {
        vBind.btnLogin.setOnClickListener(v -> {
            if (validate()) {
                userInteractor.login(
                        vBind.etUserName.getText().toString(),
                        vBind.etPassword.getText().toString(),
                        result -> {
                            if (result == null) {
                                Dialogs.alert(this, "Ocurrio un error intente más tarde");
                                return;
                            }
                            if (!result.isSuccess()) {
                                vBind.tilPassword.setError("Usuario | Contraseña incorrectos");
                                return;
                            }

                            User user = result.getData();
                            Intent i = null;

                            if (user.getFkIdRol() == Rol.ADMIN) {
                                i = new Intent(new Intent(this, ActivityListUser.class));
                            }
                            if (user.getFkIdRol() == Rol.TEACHER) {
                                i = new Intent(this, ActivityUserDetail.class);
                                i.putExtra(ActivityUserDetail.USER_ID_TAG, user.getId());
                                i.putExtra(ActivityUserDetail.IS_MAIN, true);
                            }

                            if (user.getFkIdRol() == Rol.STUDENT) {
                                i = new Intent(this, ActivityUserDetail.class);
                            }
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                            startActivity(i);
                        }
                );
            }
        });
    }

    private boolean validate() {
        boolean isValid = true;
        clearErrors();
        if (vBind.etUserName.getText().toString().equals("")) {
            isValid = false;
            vBind.tilUserName.setError("Ingrese su usuario");
        }
        if (vBind.etPassword.getText().toString().equals("")) {
            isValid = false;
            vBind.tilPassword.setError("Ingrese su contraseña");
        }

        return isValid;
    }

    private void clearErrors() {
        vBind.tilUserName.setError("");
        vBind.tilPassword.setError("");
    }
}
