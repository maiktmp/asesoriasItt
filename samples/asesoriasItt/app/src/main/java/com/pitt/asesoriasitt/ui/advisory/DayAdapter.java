package com.pitt.asesoriasitt.ui.advisory;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.pitt.asesoriasitt.R;
import com.pitt.asesoriasitt.db.models.Day;

import java.util.ArrayList;
import java.util.Calendar;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {
    private ArrayList<Day> days = Day.days();

    private ArrayList<MaterialCardView> inputCards = new ArrayList();
    public final Calendar c = Calendar.getInstance();

    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    private Context ctx;
    private OnChangeFormData onChangeFormData;

    public DayAdapter(Context ctx, OnChangeFormData onChangeFormData) {
        this.ctx = ctx;
        this.onChangeFormData = onChangeFormData;
    }

    public DayAdapter(ArrayList<Day> days, Context ctx, OnChangeFormData onChangeFormData) {
        this.days = days;
        this.ctx = ctx;
        this.onChangeFormData = onChangeFormData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_toogle_card,
                parent,
                false
        );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Day day = days.get(position);

        if (day.getAdvisoryHasDay() != null) {
            holder.etStartHour.setText(day.getAdvisoryHasDay().getStartHour());
            holder.etEndHour.setText(day.getAdvisoryHasDay().getEndHour());
        }

        holder.tvDayName.setText(day.getName());
        holder.cvDay.setOnClickListener(v -> {
            for (MaterialCardView inputCard : inputCards) {
                inputCard.setVisibility(View.GONE);
            }
            holder.cvInputs.setVisibility(View.VISIBLE);
        });
        inputCards.add(holder.cvInputs);

        holder.etStartHour.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                displayTimePicker(holder.etStartHour, day, true);
            }
        });

        holder.etEndHour.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                displayTimePicker(holder.etEndHour, day, false);
            }
        });

        holder.tilStartHour.setEndIconOnClickListener(v -> {
            holder.etStartHour.setText("");
            onChangeFormData.onDayClickListener(day, true, null);
        });
        holder.tilEndHour.setEndIconOnClickListener(v -> {
            holder.etEndHour.setText("");
            onChangeFormData.onDayClickListener(day, false, null);
        });


        holder.etStartHour.setOnClickListener(v -> displayTimePicker(holder.etStartHour, day, true));
        holder.etEndHour.setOnClickListener(v -> displayTimePicker(holder.etEndHour, day, false));

    }

    private void displayTimePicker(EditText editText, Day day, boolean isStart) {
        TimePickerDialog timePicker = new TimePickerDialog(ctx, (view, hourOfDay, minute) -> {
            String hour = String.valueOf(hourOfDay);
            String minutes = String.valueOf(minute);

            if (hourOfDay < 10) {
                hour = "0" + hour;
            }
            if (minute == 0) {
                minutes = "0" + minutes;
            }
            String result = hour + ":" + minutes + ":00";
            editText.setText(result);
            onChangeFormData.onDayClickListener(day, isStart, result);
        }, hora, minuto, true);
        timePicker.show();
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cvDay;
        MaterialCardView cvInputs;
        TextView tvDayName;
        TextInputEditText etStartHour;
        TextInputEditText etEndHour;
        TextInputLayout tilStartHour;
        TextInputLayout tilEndHour;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvDay = itemView.findViewById(R.id.cv_day);
            cvInputs = itemView.findViewById(R.id.cv_inputs);
            tvDayName = itemView.findViewById(R.id.tv_day_name);
            etStartHour = itemView.findViewById(R.id.ti_start_hour);
            etEndHour = itemView.findViewById(R.id.ti_end_hour);

            tilStartHour = itemView.findViewById(R.id.til_start_hour);
            tilEndHour = itemView.findViewById(R.id.til_end_hour);
        }

    }

    public interface OnChangeFormData {
        public void onDayClickListener(Day day, boolean isStartHour, String hour);

    }
}
