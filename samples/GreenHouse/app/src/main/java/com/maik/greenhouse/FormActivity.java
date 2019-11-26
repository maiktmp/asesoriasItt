package com.maik.greenhouse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.firebase.firestore.FirebaseFirestore;
import com.maik.greenhouse.FB.FBInteractors;
import com.maik.greenhouse.databinding.ActivityFormBinding;
import com.maik.greenhouse.models.GreenHouse;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FormActivity extends AppCompatActivity {

    private ActivityFormBinding vBind;
    private FBInteractors fbInteractors;
    private int RESULT_LOAD_IMG = 601;
    private int RESULT_TAKE_IMG = 602;
    private GreenHouse greenHouse;
    private Bitmap selectedImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vBind = DataBindingUtil.setContentView(this, R.layout.activity_form);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        fbInteractors = FBInteractors.getInstance();
        fetchForm();
        setUpSaveBtn();
        changeImageBtn();

        setSupportActionBar(vBind.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            selectedImage = imageBitmap;
            vBind.ivPreview.setImageBitmap(imageBitmap);
        }
        if (resultCode == RESULT_OK && reqCode == RESULT_TAKE_IMG) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                vBind.ivPreview.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                e.printStackTrace();
                Toast.makeText(this, "Ocurrio un error...", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "No se selecciono ningun archivo", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void fetchForm() {
        fbInteractors.getGreenHouse(greenHouse -> {
            this.greenHouse = greenHouse;
            vBind.setGreenHouse(greenHouse);
        });
    }

    private void changeImageBtn() {
        vBind.btnChangeImage.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_TAKE_IMG);
        });
        vBind.btnSelectImage.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, RESULT_LOAD_IMG);
            }
        });
    }

    private void setUpSaveBtn() {
        vBind.btnSave.setOnClickListener(v -> {
            if (!validate()) return;

            GreenHouse greenHouse = vBind.getGreenHouse();
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Espere...");
            progressDialog.show();

            greenHouse.setName(vBind.etName.getText().toString());
            greenHouse.setDescription(vBind.etDescription.getText().toString());
            greenHouse.setLimit(Long.valueOf(vBind.etLimit.getText().toString()));

            fbInteractors.update(greenHouse, done -> {
                if (done) {
                    if (this.selectedImage != null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] dataImage = baos.toByteArray();
                        fbInteractors.updateImage(greenHouse.getImg().get(0), dataImage, result -> {
                            if (result) {
                                finish();
                            } else {
                                Toast.makeText(this, "Ocurrio un error...", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        finish();
                    }
                }
            });
        });
    }

    private boolean validate() {

        vBind.tilName.setError("");
        vBind.tilDescription.setError("");
        vBind.tilLimit.setError("");

        boolean isValid = true;

        if (vBind.etName.getText().toString().equals("")) {
            vBind.tilName.setError("Ingrese el nombre");
            isValid = false;
        }

        if (vBind.etDescription.getText().toString().equals("")) {
            vBind.tilDescription.setError("Ingrese la descripción");
            isValid = false;
        }

        if (vBind.etLimit.getText().toString().equals("")) {
            vBind.tilLimit.setError("Ingrese la temperatura");
            isValid = false;
        }


        return isValid;

    }


}
