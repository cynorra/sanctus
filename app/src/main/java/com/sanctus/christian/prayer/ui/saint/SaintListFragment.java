package com.sanctus.christian.prayer.ui.saint;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sanctus.christian.prayer.R;

public class SaintListFragment extends Fragment {

    private SaintViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saint_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SaintViewModel.class);

        RecyclerView rv = view.findViewById(R.id.rv_saints);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        SaintListAdapter adapter = new SaintListAdapter(saint -> {
            Bundle args = new Bundle();
            args.putInt(SaintDetailFragment.ARG_SAINT_ID, saint.id);
            Navigation.findNavController(view).navigate(R.id.saintDetailFragment, args);
        });
        rv.setAdapter(adapter);

        EditText etSearch = view.findViewById(R.id.et_search_saints);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    viewModel.search(s.toString()).observe(getViewLifecycleOwner(), adapter::submitList);
                } else {
                    viewModel.getAllSaints().observe(getViewLifecycleOwner(), adapter::submitList);
                }
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        viewModel.getAllSaints().observe(getViewLifecycleOwner(), adapter::submitList);
    }
}
