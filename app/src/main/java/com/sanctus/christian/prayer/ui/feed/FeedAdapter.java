package com.sanctus.christian.prayer.ui.feed;

import android.content.Context;
import java.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.util.DateUtils;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.local.entity.SaintEntity;
import com.sanctus.christian.prayer.data.remote.CommunityPost;
import com.sanctus.christian.prayer.util.AnimUtils;
import java.util.ArrayList;
import java.util.List;

class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    interface Listener {
        void onOpenPrayer(PrayerEntity prayer);
        void onSharePrayer(PrayerEntity prayer);
        void onOpenSaint(SaintEntity saint);
        void onShareSaint(SaintEntity saint);
        void onAmen(CommunityPost post);
        void onSharePost(CommunityPost post);
        void onPostMenu(CommunityPost post, View anchor);
    }

    private final List<FeedItem> items = new ArrayList<>();
    private final Listener listener;

    FeedAdapter(Listener listener) { this.listener = listener; }

    void submit(List<FeedItem> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override public int getItemViewType(int position) { return items.get(position).type; }
    @Override public int getItemCount() { return items.size(); }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        LayoutInflater inf = LayoutInflater.from(parent.getContext());
        switch (type) {
            case FeedItem.HEADER: return new Simple(inf.inflate(R.layout.item_feed_header, parent, false));
            case FeedItem.DAILY_PRAYER:
            case FeedItem.DAILY_SAINT: return new Daily(inf.inflate(R.layout.item_feed_daily, parent, false));
            case FeedItem.POST: return new Post(inf.inflate(R.layout.item_feed_post, parent, false));
            default: return new Notice(inf.inflate(R.layout.item_feed_notice, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FeedItem item = items.get(position);
        if (holder instanceof Simple) {
            ((TextView) holder.itemView).setText(item.textRes);
        } else if (holder instanceof Daily) {
            bindDaily((Daily) holder, item);
        } else if (holder instanceof Post) {
            bindPost((Post) holder, item.post);
        } else {
            Notice n = (Notice) holder;
            n.text.setText(item.textRes);
            if (item.actionRes != 0 && item.action != null) {
                n.button.setText(item.actionRes);
                n.button.setVisibility(View.VISIBLE);
                n.button.setOnClickListener(v -> item.action.run());
            } else {
                n.button.setVisibility(View.GONE);
            }
        }
    }

    private void bindDaily(Daily h, FeedItem item) {
        if (item.type == FeedItem.DAILY_PRAYER) {
            PrayerEntity p = item.prayer;
            h.kicker.setText(R.string.prayer_of_day);
            h.title.setText(p.name);
            h.subtitle.setVisibility(View.GONE);
            h.body.setText(p.content);
            h.open.setOnClickListener(v -> listener.onOpenPrayer(p));
            h.share.setOnClickListener(v -> { AnimUtils.pressFeedback(h.share); listener.onSharePrayer(p); });
        } else {
            SaintEntity s = item.saint;
            h.kicker.setText(R.string.saint_of_day);
            h.title.setText(s.name);
            h.subtitle.setText(h.itemView.getContext().getString(R.string.saint_feast_day_format, DateUtils.localizeFeastDate(s.feastDate)));
            h.subtitle.setVisibility(View.VISIBLE);
            h.body.setText(s.description);
            h.open.setOnClickListener(v -> listener.onOpenSaint(s));
            h.share.setOnClickListener(v -> { AnimUtils.pressFeedback(h.share); listener.onShareSaint(s); });
        }
    }

    private void bindPost(Post h, CommunityPost p) {
        Context ctx = h.itemView.getContext();
        h.author.setText(p.authorName != null && !p.authorName.isEmpty() ? p.authorName : ctx.getString(R.string.feed_anonymous));
        h.time.setText(new SimpleDateFormat("MMM d, HH:mm", com.sanctus.christian.prayer.util.DateUtils.appLocale()).format(new java.util.Date(p.createdAt)));
        h.text.setText(p.text);
        h.amenIcon.setSelected(p.amenedByMe);
        h.amenCount.setText(p.amenCount > 0
                ? ctx.getString(R.string.feed_amen_count_format, (int) p.amenCount)
                : ctx.getString(R.string.feed_amen));
        h.amen.setOnClickListener(v -> listener.onAmen(p));
        h.share.setOnClickListener(v -> listener.onSharePost(p));
        h.more.setOnClickListener(v -> listener.onPostMenu(p, h.more));
    }

    static class Simple extends RecyclerView.ViewHolder { Simple(View v) { super(v); } }

    static class Daily extends RecyclerView.ViewHolder {
        final TextView kicker, title, subtitle, body, open;
        final ImageButton share;
        Daily(View v) {
            super(v);
            kicker = v.findViewById(R.id.tv_kicker);
            title = v.findViewById(R.id.tv_title);
            subtitle = v.findViewById(R.id.tv_subtitle);
            body = v.findViewById(R.id.tv_body);
            open = v.findViewById(R.id.btn_open);
            share = v.findViewById(R.id.btn_share);
        }
    }

    static class Post extends RecyclerView.ViewHolder {
        final TextView author, time, text, amenCount;
        final View amen, amenIcon;
        final ImageButton share, more;
        Post(View v) {
            super(v);
            author = v.findViewById(R.id.tv_author);
            time = v.findViewById(R.id.tv_time);
            text = v.findViewById(R.id.tv_post_text);
            amenCount = v.findViewById(R.id.tv_amen);
            amen = v.findViewById(R.id.btn_amen);
            amenIcon = v.findViewById(R.id.iv_amen);
            share = v.findViewById(R.id.btn_share);
            more = v.findViewById(R.id.btn_more);
        }
    }

    static class Notice extends RecyclerView.ViewHolder {
        final TextView text, button;
        Notice(View v) {
            super(v);
            text = v.findViewById(R.id.tv_notice);
            button = v.findViewById(R.id.btn_notice);
        }
    }
}
