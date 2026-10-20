package com.sanctus.christian.prayer.ui.saint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.util.DateUtils;
import com.sanctus.christian.prayer.util.MotionKit;
import com.sanctus.christian.prayer.util.SaintNames;
import com.sanctus.christian.prayer.data.local.entity.SaintEntity;
import com.sanctus.christian.prayer.util.AnimUtils;
import java.util.Objects;

public class SaintListAdapter extends ListAdapter<SaintEntity, SaintListAdapter.ViewHolder> {

    public interface OnSaintClick { void onClick(SaintEntity saint); }
    private final OnSaintClick listener;

    public interface OnShareClick { void onClick(SaintEntity saint); }
    private final OnShareClick shareListener;

    public SaintListAdapter(OnSaintClick listener, OnShareClick shareListener) {
        super(new DiffUtil.ItemCallback<SaintEntity>() {
            @Override public boolean areItemsTheSame(@NonNull SaintEntity a, @NonNull SaintEntity b) { return a.id == b.id; }
            @Override public boolean areContentsTheSame(@NonNull SaintEntity a, @NonNull SaintEntity b) { return Objects.equals(a.name, b.name); }
        });
        this.listener = listener;
        this.shareListener = shareListener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saint, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SaintEntity s = getItem(position);
        holder.tvName.setText(s.name);
        holder.tvInitial.setText(SaintNames.initialOf(s.name));
        MotionKit.pressable(holder.itemView);
        holder.tvFeast.setText(DateUtils.localizeFeastDate(s.feastDate));
        holder.tvPatron.setText(s.patronOf);
        holder.btnShare.setOnClickListener(v -> shareListener.onClick(s));
        holder.itemView.setOnClickListener(v -> {
            AnimUtils.pressFeedback(v);
            listener.onClick(s);
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvFeast, tvPatron, tvInitial;
        android.widget.ImageButton btnShare;
        ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tv_saint_name);
            tvInitial = v.findViewById(R.id.tv_saint_initial);
            tvFeast = v.findViewById(R.id.tv_feast_date);
            tvPatron = v.findViewById(R.id.tv_patron_of);
            btnShare = v.findViewById(R.id.btn_share_item);
        }
    }
}
