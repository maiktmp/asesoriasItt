package com.tec.ine.ui.form;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tec.ine.R;
import com.tec.ine.databinding.FragmentFormBinding;
import com.tec.ine.interactors.FBInteractors;
import com.tec.ine.models.INE;
import com.tec.ine.models.Town;
import com.tec.ine.ui.fragment_container.FragmentContainerActivity;
import com.tec.ine.utils.CBGeneric;
import com.tec.ine.utils.Dialogs;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class FragmentForm extends Fragment implements DatePickerDialog.OnDateSetListener {

    private FragmentFormBinding vBind;
    private FragmentFormInterface fragmentFormInterface;
    private Bitmap selectedImage;
    private FBInteractors fbInteractors;
    private INE ine;

    private String municipality;
    private String locality;
    private String section;
    private String gender = "M";

    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day;

    private String birthDate;
    private Date date;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.fragmentFormInterface = (FragmentFormInterface) context;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ine = (INE) getArguments().getSerializable(FragmentContainerActivity.INE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fbInteractors = FBInteractors.getInstance();

        vBind = FragmentFormBinding.inflate(inflater, container, false);
        setUpCameraBtn();
        setUpInputs();
        setUpSaveBtn();
        if (ine != null) {
            vBind.setIne(ine);
            setUpUpdateForm();
        }
        return vBind.getRoot();
    }

    private void setUpUpdateForm() {
        if (ine.getGender().equals("M")) {
            vBind.rbMale.setChecked(true);
            vBind.rbFemale.setChecked(false);
            this.gender = "M";
        } else {
            vBind.rbMale.setChecked(false);
            vBind.rbFemale.setChecked(true);
            this.gender = "F";
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(ine.getBirthDate());
            Year = 2000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        vBind.etEmitYear.setEnabled(true);
        vBind.etExpired.setEnabled(true);

        this.birthDate = ine.getBirthDate();

        for (Town town : Town.getTowns()) {
            if (town.getId().equals(this.ine.getMunicipality())) {
                vBind.listMunicipality.setText(town.getId() + " | " + town.getName());
                setUpSelectTown(town.getLocalities(), vBind.listLocality, result -> this.locality = result);
                setUpSelectTown(town.getSections(), vBind.listSection, result -> this.section = result);

                this.municipality = ine.getMunicipality();
                this.section = ine.getSection();
                this.locality = ine.getLocality();
                break;
            }
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        storageRef.child(ine.getImg()).getDownloadUrl().addOnSuccessListener(uri ->
                Glide.with(getContext())
                        .load(uri)
                        .into(vBind.ivCamera)
        );


    }

    private void setUpInputs() {
        setUpTownsList();
        setUpDateFields();
        setUpGenderBtn();
        setUpToUppsercase();
    }

    private void setUpGenderBtn() {
        vBind.rbMale.setOnClickListener(v -> gender = "M");
        vBind.rbFemale.setOnClickListener(v -> gender = "F");
    }

    private void setUpDateFields() {
        calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);

        vBind.etEmitYear.setText("2019");
        vBind.etExpired.setText("2029");

        vBind.tilBirthDate.setOnClickListener(v -> setUpCalendarDialog());
        vBind.etBirthDate.setOnClickListener(v -> setUpCalendarDialog());


    }

    private void setUpCalendarDialog() {
        datePickerDialog = new DatePickerDialog(getContext(), this, Year, Month, Day);
        datePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void setUpTownsList() {
        if (getContext() == null) {
            return;
        }


        TownAdapter adapter = new TownAdapter(getContext(), Town.getTowns());

        vBind.listMunicipality.setAdapter(adapter);
        vBind.listMunicipality.setOnItemClickListener((parent, view, position, id) -> {
            Town town = adapter.getTowns().get(position);
            this.municipality = town.getId();
            vBind.listMunicipality.setText(town.getName());
            setUpSelectTown(town.getLocalities(), vBind.listLocality, result -> this.locality = result);
            setUpSelectTown(town.getSections(), vBind.listSection, result -> this.section = result);
        });
        vBind.listMunicipality.setAdapter(adapter);

    }

    private void setUpSelectTown(Map<String, String> property, AutoCompleteTextView atv, CBGeneric<String> cb) {
        if (getContext() == null) {
            return;
        }
        atv.setText(null);
        this.locality = null;
        this.section = null;

        List<String> arrayList = new ArrayList<>();
        for (Map.Entry<String, String> entry : property.entrySet()) {
            arrayList.add(entry.getKey());
        }
        MapAdapter adapter = new MapAdapter(getContext(), arrayList);
        atv.setAdapter(adapter);
        atv.setOnItemClickListener((parent, view, position, id) -> {
            String result = arrayList.get(position);
            atv.setText(result);
            cb.onResult(result);
        });
        atv.setAdapter(adapter);
    }


    private void setUpSaveBtn() {
        vBind.btnSave.setOnClickListener(v -> {
            clearErrors();
            if (validate()) {
                AlertDialog progress = Dialogs.progress(getContext(), R.string.wait);

                String img = ine == null ?
                        (String.valueOf(System.nanoTime())) + ".jpeg" :
                        ine.getImg();

                uploadImage(img,
                        result -> {
                            if (!result) {
                                progress.dismiss();
                                return;
                            }
                            ine = ine == null ? new INE() : ine;

                            ine.setBirthDate(this.birthDate);
                            ine.setColony(vBind.etColony.getText().toString());
                            ine.setCurp(vBind.etCurp.getText().toString());
                            ine.setEmissionYear(vBind.etEmitYear.getText().toString());
                            ine.setExpiredYear(vBind.etExpired.getText().toString());
                            ine.setExtNum(vBind.etExtNum.getText().toString());
                            ine.setFirstLastName(vBind.etFirstLastName.getText().toString());
                            ine.setImg(img);
                            ine.setIneNumber(vBind.etIneNumber.getText().toString());
                            ine.setIneNumber(vBind.etIneNumber.getText().toString());
                            ine.setLocality(this.locality);
                            ine.setMunicipality(this.municipality);
                            ine.setName(vBind.etName.getText().toString());
                            ine.setRegisterYear(vBind.etRegisterYear.getText().toString());
                            ine.setSecondLastName(vBind.etSecondLastName.getText().toString());
                            ine.setSection(this.section);
                            ine.setStreet(vBind.etStreet.getText().toString());
                            ine.setVersion(String.valueOf(Integer.valueOf(ine.getVersion()) + 1));
                            ine.setZipCode(vBind.etZipCode.getText().toString());
                            ine.setGender(this.gender);
                            ine.setFullName(
                                    vBind.etFirstLastName.getText().toString() + " " +
                                            vBind.etSecondLastName.getText().toString() + " " +
                                            vBind.etName.getText().toString()

                            );

                            if (ine.getFBID() != null) {
                                fbInteractors.updateINE(ine, result1 -> {
                                    progress.dismiss();
                                    if (result1) {
                                        Dialogs.alert(getContext(), "Registro actualizado.");
                                        getActivity().finish();
                                    } else {
                                        Dialogs.alert(getContext(), "Ocurrio un error al actualizar el registro.");
                                    }
                                });

                            } else {
                                fbInteractors.createINE(ine, result1 -> {
                                    progress.dismiss();
                                    if (result1) {
                                        Dialogs.alert(getContext(), "Registro creado.");
                                        clearData();
                                    } else {
                                        Dialogs.alert(getContext(), "Ocurrio un error al crear el registro.");
                                    }
                                });


                            }
                        });
            }
        });
    }

    private void uploadImage(String name, CBGeneric<Boolean> cb) {
        if (this.ine != null && this.selectedImage == null) {
            cb.onResult(true);
            return;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dataImage = baos.toByteArray();
        fbInteractors.uploadImage(name, dataImage, cb);
    }


    private boolean validate() {
        boolean isValid = true;

        if (vBind.etFirstLastName.getText().toString().equals("")) {
            vBind.tilFirstLastName.setError("El campo es requerido");
            isValid = false;
        }

        if (vBind.etSecondLastName.getText().toString().equals("")) {
            vBind.tilSecondLastName.setError("El campo es requerido");
            isValid = false;
        }

        if (vBind.etName.getText().toString().equals("")) {
            vBind.tilName.setError("El campo es requerido");
            isValid = false;
        }

        if (vBind.etStreet.getText().toString().equals("")) {
            vBind.tilStreet.setError("El campo es requerido");
            isValid = false;
        }

        if (vBind.etExtNum.getText().toString().equals("")) {
            vBind.tilExtNum.setError("El campo es requerido");
            isValid = false;
        }

        if (vBind.etColony.getText().toString().equals("")) {
            vBind.tilColony.setError("El campo es requerido");
            isValid = false;
        }

        if (vBind.etZipCode.getText().toString().equals("")) {
            vBind.tilZipCode.setError("El campo es requerido");
            isValid = false;
        }

        if (vBind.etBirthDate.getText().toString().equals("")) {
            vBind.tilBirthDate.setError("El campo es requerido");
            isValid = false;
        } else {
            if (this.date.after(new Date())) {
                vBind.tilBirthDate.setError("Ingrese una fecha válida");
                isValid = false;
            } else if (2019 - Year < 18) {
                vBind.tilBirthDate.setError("Debe ser mayor de 18 años.");
                isValid = false;
            }
        }

        if (vBind.etIneNumber.getText().toString().equals("")) {
            vBind.tilIneNumber.setError("El campo es requerido");
            isValid = false;
        }

        if (vBind.etCurp.getText().toString().equals("")) {
            vBind.tilCurp.setError("El campo es requerido");
            isValid = false;
        }

        if (vBind.etRegisterYear.getText().toString().equals("")) {
            vBind.tilRegisterYear.setError("El campo es requerido");
            isValid = false;
        }


        if (vBind.etRegisterYear.getText().toString().equals("")) {
            vBind.tilRegisterYear.setError("El campo es requerido");
            isValid = false;
        }

        if (this.ine == null && this.selectedImage == null) {
            vBind.tvErrorsGeneral.setVisibility(View.VISIBLE);
            vBind.tvErrorsGeneral.setText("Ingrese una imágen");
            isValid = false;
        }


        return isValid;

    }

    private void clearErrors() {
        vBind.tilFirstLastName.setError(null);
        vBind.tilSecondLastName.setError(null);
        vBind.tilName.setError(null);
        vBind.tilStreet.setError(null);
        vBind.tilExtNum.setError(null);
        vBind.tilColony.setError(null);
        vBind.tilZipCode.setError(null);
        vBind.tilBirthDate.setError(null);
        vBind.tilIneNumber.setError(null);
        vBind.tilCurp.setError(null);
        vBind.tilRegisterYear.setError(null);
        vBind.tilRegisterYear.setError(null);
        vBind.tvErrorsGeneral.setVisibility(View.GONE);
    }

    private void clearData() {
        vBind.etFirstLastName.setText(null);
        vBind.etSecondLastName.setText(null);
        vBind.etName.setText(null);
        vBind.etStreet.setText(null);
        vBind.etExtNum.setText(null);
        vBind.etColony.setText(null);
        vBind.etZipCode.setText(null);
        vBind.etBirthDate.setText(null);
        vBind.etIneNumber.setText(null);
        vBind.etCurp.setText(null);
        vBind.etRegisterYear.setText(null);
        vBind.etRegisterYear.setText(null);
        vBind.listMunicipality.setText(null);
        vBind.listLocality.setText(null);
        vBind.listLocality.setText(null);
        vBind.ivCamera.setImageDrawable(getContext().getDrawable(R.drawable.ic_camera));

        this.birthDate = null;
        this.locality = null;
        this.municipality = null;
        this.section = null;
        this.gender = null;
        this.selectedImage = null;
    }

    private void setUpToUppsercase() {
        vBind.etFirstLastName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        vBind.etSecondLastName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        vBind.etName.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        vBind.etStreet.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        vBind.etExtNum.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        vBind.etColony.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        vBind.etZipCode.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        vBind.etBirthDate.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        vBind.etIneNumber.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        vBind.etCurp.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        vBind.etRegisterYear.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        vBind.etRegisterYear.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
    }

    private void setUpCameraBtn() {
        vBind.ivCamera.setOnClickListener(v -> {
            fragmentFormInterface.onOpenCamera();
        });
    }

    public void setSelectedImage(Bitmap selectedImage) {
        this.selectedImage = selectedImage;
        vBind.ivCamera.setImageBitmap(selectedImage);
    }


    public interface FragmentFormInterface {
        void onOpenCamera();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        try {
            this.Year = year;

            String _date = year + "-" + month + "-" + dayOfMonth;
            vBind.etBirthDate.setText(_date);
            birthDate = _date;
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse(_date);


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
