package com.sanctus.christian.prayer.ui.saint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.sanctus.christian.prayer.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SaintFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saint, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvName = view.findViewById(R.id.tv_saint_name);
        TextView tvDesc = view.findViewById(R.id.tv_saint_description);
        TextView tvFeast = view.findViewById(R.id.tv_feast_date);

        String today = new SimpleDateFormat("MMMM d", Locale.ENGLISH).format(new Date());
        SaintViewModel viewModel = new ViewModelProvider(this).get(SaintViewModel.class);
        viewModel.getSaintByFeastDate(today).observe(getViewLifecycleOwner(), saint -> {
            if (saint != null) {
                tvName.setText(saint.name);
                tvDesc.setText(saint.description);
                tvFeast.setText(getString(R.string.saint_feast_prefix, com.sanctus.christian.prayer.util.DateUtils.localizeFeastDate(saint.feastDate)));
            } else {
                tvName.setText(R.string.no_saint_today_title);
                tvDesc.setText(R.string.no_saint_today_desc);
                tvFeast.setText("");
            }
        });
    }
}
