package com.sanctus.christian.prayer.ui.onboarding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sanctus.christian.prayer.R;

public class OnboardingPagerAdapter extends RecyclerView.Adapter<OnboardingPagerAdapter.VH> {
    private final String[][] pages;
    public OnboardingPagerAdapter(String[][] pages) { this.pages = pages; }

    @NonNull @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_onboarding_page, parent, false);
        return new VH(v);
    }

    @Override public void onBindViewHolder(@NonNull VH holder, int position) {}
    @Override public int getItemCount() { return pages.length; }

    static class VH extends RecyclerView.ViewHolder {
        VH(View v) { super(v); }
    }
}
