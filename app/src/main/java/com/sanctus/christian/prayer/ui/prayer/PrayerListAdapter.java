package com.sanctus.christian.prayer.ui.prayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.util.AnimUtils;
import java.util.Locale;
import java.util.Objects;

public class PrayerListAdapter extends ListAdapter<PrayerEntity, PrayerListAdapter.ViewHolder> {

    public interface OnPrayerClick { void onClick(PrayerEntity prayer); }
    public interface OnFavoriteClick { void onClick(PrayerEntity prayer); }
    private final OnPrayerClick listener;
    private final OnFavoriteClick favoriteListener;

    public interface OnShareClick { void onClick(PrayerEntity prayer); }
    private final OnShareClick shareListener;

    public PrayerListAdapter(OnPrayerClick listener, OnFavoriteClick favoriteListener, OnShareClick shareListener) {
        super(new DiffUtil.ItemCallback<PrayerEntity>() {
            @Override public boolean areItemsTheSame(@NonNull PrayerEntity a, @NonNull PrayerEntity b) { return a.id == b.id; }
            @Override public boolean areContentsTheSame(@NonNull PrayerEntity a, @NonNull PrayerEntity b) { return Objects.equals(a.name, b.name) && a.isFavorite == b.isFavorite; }
        });
        this.listener = listener;
        this.favoriteListener = favoriteListener;
        this.shareListener = shareListener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prayer, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PrayerEntity prayer = getItem(position);
        holder.tvName.setText(prayer.name);
        holder.tvCategory.setText(prayer.category != null ? prayer.category.toUpperCase(Locale.ROOT) : "");
        holder.btnFavorite.setSelected(prayer.isFavorite);
        holder.itemView.setOnClickListener(v -> {
            AnimUtils.pressFeedback(v);
            listener.onClick(prayer);
        });
        holder.btnShare.setOnClickListener(v -> shareListener.onClick(prayer));
        holder.btnFavorite.setOnClickListener(v -> {
            holder.btnFavorite.setSelected(!holder.btnFavorite.isSelected());
            AnimUtils.bounce(holder.btnFavorite);
            favoriteListener.onClick(prayer);
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCategory;
        ImageButton btnFavorite, btnShare;
        ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_prayer_name);
            tvCategory = v.findViewById(R.id.tv_prayer_category);
            btnFavorite = v.findViewById(R.id.btn_favorite);
            btnShare = v.findViewById(R.id.btn_share_item);
        }
    }
}
