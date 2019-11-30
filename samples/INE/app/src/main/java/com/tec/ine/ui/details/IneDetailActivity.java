package com.tec.ine.ui.details;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tec.ine.R;
import com.tec.ine.databinding.ActivityIneDetailBinding;
import com.tec.ine.interactors.FBInteractors;
import com.tec.ine.models.INE;
import com.tec.ine.ui.form.FragmentForm;
import com.tec.ine.ui.fragment_container.FragmentContainerActivity;
import com.tec.ine.utils.Dialogs;

import java.util.Objects;

public class IneDetailActivity extends AppCompatActivity {

    public static final String INE_ID = "INE_ID";

    private String documentId;
    private ActivityIneDetailBinding vBind;
    private FBInteractors fbInteractors;
    private INE ine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_ine_detail);
//        documentId = "DokkpgYymwiHZ1UChbXU";
        documentId = getIntent().getStringExtra(INE_ID);
        fbInteractors = FBInteractors.getInstance();
        setSupportActionBar(vBind.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    @Override

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchDetails();
    }

    private void fetchDetails() {
        vBind.progressBar.setVisibility(View.VISIBLE);
        vBind.tvDetails.setText(null);


        FBInteractors.getInstance().getAuthUser(user -> {
            if (!Objects.equals(user.getEmail(), FBInteractors.ADMIN)) {
                vBind.btnDelete.setVisibility(View.GONE);
                vBind.btnUpdate.setVisibility(View.GONE);
            }
        });


        fbInteractors.getINE(documentId, ine -> {
            this.ine = ine;

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference();

            storageRef.child(ine.getImg()).getDownloadUrl().addOnSuccessListener(uri ->
                    Glide.with(this)
                            .load(uri)
                            .into(vBind.ivIne));


            String details = "";
            details += " <b>Nombre: </b> " + ine.getFirstLastName() + " " + ine.getSecondLastName() + " " + ine.getName();
            details += " <br/>";
            details += " <br/>";
            details += " <b>Fecha de nacimiento: </b> " + ine.getBirthDate();
            details += " <br/>";
            details += " <br/>";
            details += " <b>Clave electoral: </b> " + ine.getIneNumber();
            details += " <br/>";
            details += " <br/>";
            details += " <b>CURP: </b> " + ine.getCurp();
            details += " <br/>";
            details += " <br/>";
            details += " <b>Fecha de emisión: </b> " + ine.getEmissionYear();
            details += " <b>Fecha de expiración: </b> " + ine.getExpiredYear();
            details += " <br/>";
            details += " <br/>";
            details += " <b>Genero: </b> " + ine.getGender();
            details += " <br/>";
            details += " <br/>";
            details += " <b>Estado: </b> " + "15";
            details += " <b>Municipio: </b> " + ine.getMunicipality();
            details += " <br/>";
            details += " <br/>";
            details += " <b>Sección: </b> " + ine.getSection();
            details += " <b>Localidad: </b> " + ine.getLocality();
            details += " <br/>";
            details += " <br/>";
            details += " <b>Calle: </b> " + ine.getSecondLastName();
            details += " <b>Colonia: </b> " + ine.getColony();
            details += " <b>C.P.: </b> " + ine.getZipCode();
            details += " <br/>";
            details += " <br/>";
            details += " <b>Num. Int.: </b> " + ine.getIntNum();
            details += " <b>Num. Ext.: </b> " + ine.getExtNum();
            details += " <br/>";
            details += " <br/>";
            details += " <b>Versión: </b> " + ine.getVersion();
            details += " <br/>";
            details += " <br/>";
            details += " <b>Estatus: </b> " + (ine.getActive() ? "Activo" : "Eliminado");

            vBind.progressBar.setVisibility(View.GONE);
            vBind.tvDetails.setText(Html.fromHtml(details));


            if (ine.getActive()) {
                setUpUpdateBtn();
                setUpDeleteBtn();
            } else {
                vBind.btnDelete.setVisibility(View.GONE);
                vBind.btnUpdate.setVisibility(View.GONE);
            }
        });

    }

    private void setUpUpdateBtn() {
        vBind.btnUpdate.setOnClickListener(v -> {
            ine.setFBID(this.documentId);
            Intent i = new Intent(this, FragmentContainerActivity.class);
            Bundle args = new Bundle();
            args.putSerializable(FragmentContainerActivity.INE, ine);
            i.putExtras(args);
            startActivity(i);
        });
    }

    private void setUpDeleteBtn() {
        vBind.btnDelete.setOnClickListener(v -> {
            Dialogs.alert(this, "¿Seguro que desea eliminar?", () -> {
                ine.setFBID(this.documentId);
                ine.setActive(false);
                ProgressDialog progress = Dialogs.progress(this, R.string.wait);
                fbInteractors.updateINE(ine, result -> {
                    progress.hide();
                    if (result) {
                        Dialogs.alert(this, "Registro eliminado");
                        finish();
                    } else {
                        Dialogs.alert(this, "Ocurrio un error al eliminar el registro");
                    }
                });
            });
        });
    }
}
