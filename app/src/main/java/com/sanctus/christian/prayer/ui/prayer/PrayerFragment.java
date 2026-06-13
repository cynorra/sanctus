package com.sanctus.christian.prayer.ui.prayer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.ChipGroup;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;

public class PrayerFragment extends Fragment {
    private PrayerViewModel viewModel;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prayer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PrayerViewModel.class);

        RecyclerView recyclerView = view.findViewById(R.id.rv_prayers);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        PrayerListAdapter adapter = new PrayerListAdapter(prayer -> {
            Bundle args = new Bundle();
            args.putInt(PrayerDetailFragment.ARG_PRAYER_ID, prayer.id);
            Navigation.findNavController(view).navigate(R.id.action_prayer_to_detail, args);
        });
        recyclerView.setAdapter(adapter);

        ChipGroup chipGroup = view.findViewById(R.id.chip_group_categories);
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            // Filter by category when implemented
        });

        viewModel.getPrayers().observe(getViewLifecycleOwner(), adapter::submitList);
    }
}
