package com.sanctus.christian.prayer.ui.saint;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.sanctus.christian.prayer.R;

public class SaintDetailFragment extends Fragment {
    public static final String ARG_SAINT_ID = "saint_id";

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saint_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int saintId = getArguments() != null ? getArguments().getInt(ARG_SAINT_ID, -1) : -1;
        SaintViewModel vm = new ViewModelProvider(this).get(SaintViewModel.class);

        ImageButton btnBack = view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        if (saintId != -1) {
            vm.getSaintById(saintId).observe(getViewLifecycleOwner(), saint -> {
                if (saint == null) return;
                ((TextView) view.findViewById(R.id.tv_saint_name_detail)).setText(saint.name);
                ((TextView) view.findViewById(R.id.tv_feast_date_detail)).setText("Feast: " + saint.feastDate);
                ((TextView) view.findViewById(R.id.tv_saint_desc_detail)).setText(saint.description);
                ((TextView) view.findViewById(R.id.tv_saint_patron_detail)).setText("Patron of: " + saint.patronOf);
            });
        }
    }
}
