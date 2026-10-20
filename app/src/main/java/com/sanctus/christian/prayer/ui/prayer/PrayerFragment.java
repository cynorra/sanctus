package com.sanctus.christian.prayer.ui.prayer;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.util.MotionKit;
import com.sanctus.christian.prayer.util.Tradition;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import java.util.List;

public class PrayerFragment extends Fragment {
    private PrayerViewModel viewModel;
    private PrayerListAdapter adapter;
    private LiveData<List<PrayerEntity>> currentSource;
    private TextView tvEmpty;
    private String currentCategory = "";

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
        tvEmpty = view.findViewById(R.id.tv_empty_prayers);

        adapter = new PrayerListAdapter(
                prayer -> {
                    Bundle args = new Bundle();
                    args.putInt(PrayerDetailFragment.ARG_PRAYER_ID, prayer.id);
                    Navigation.findNavController(view).navigate(R.id.action_prayer_to_detail, args);
                },
                prayer -> viewModel.toggleFavorite(prayer),
                prayer -> com.sanctus.christian.prayer.share.ShareActions.sharePrayer(requireContext(), getChildFragmentManager(), prayer)
        );
        recyclerView.setAdapter(adapter);

        TextView tvTraditionBadge = view.findViewById(R.id.tv_tradition_badge);
        tvTraditionBadge.setText(Tradition.labelRes(
                SanctusApplication.getInstance().getSettingsRepository().getTradition()));
        MotionKit.pressable(tvTraditionBadge);
        tvTraditionBadge.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.settingsFragment));

        ChipGroup chipGroup = view.findViewById(R.id.chip_group_categories);
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            currentCategory = "";
            if (!checkedIds.isEmpty()) {
                Chip chip = group.findViewById(checkedIds.get(0));
                if (chip != null && chip.getTag() != null) currentCategory = chip.getTag().toString();
            }
            observePrayers(currentCategory.isEmpty() ? viewModel.getPrayers() : viewModel.getPrayersByCategory(currentCategory));
        });

        EditText etSearch = view.findViewById(R.id.et_search_prayers);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    observePrayers(viewModel.search(s.toString()));
                } else {
                    observePrayers(currentCategory.isEmpty() ? viewModel.getPrayers() : viewModel.getPrayersByCategory(currentCategory));
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        TextView tvFallback = view.findViewById(R.id.tv_fallback_notice);
        viewModel.isUsingFallbackLanguage().observe(getViewLifecycleOwner(), fallback ->
                tvFallback.setVisibility(Boolean.TRUE.equals(fallback) ? View.VISIBLE : View.GONE));

        observePrayers(viewModel.getPrayers());
    }

    private void observePrayers(LiveData<List<PrayerEntity>> source) {
        if (currentSource != null) currentSource.removeObservers(getViewLifecycleOwner());
        currentSource = source;
        currentSource.observe(getViewLifecycleOwner(), list -> {
            adapter.submitList(list);
            tvEmpty.setVisibility(list == null || list.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }
}
