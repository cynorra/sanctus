package com.sanctus.christian.prayer.ui.saint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.domain.model.Saint;

public class SaintFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saint, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Load saint of the day
        Saint saintOfDay = new Saint(1, "Saint Anthony of Padua",
                "Doctor of the Church, known for his powerful preaching and expert knowledge of Scripture.",
                "June 13", "Lost items, poor, travelers", "");
        TextView tvName = view.findViewById(R.id.tv_saint_name);
        TextView tvDesc = view.findViewById(R.id.tv_saint_description);
        TextView tvFeast = view.findViewById(R.id.tv_feast_date);
        tvName.setText(saintOfDay.getName());
        tvDesc.setText(saintOfDay.getDescription());
        tvFeast.setText("Feast: " + saintOfDay.getFeastDate());
    }
}
