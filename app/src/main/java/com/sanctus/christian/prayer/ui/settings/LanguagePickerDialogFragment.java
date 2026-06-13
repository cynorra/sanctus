package com.sanctus.christian.prayer.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.util.LanguageManager;
import java.util.List;

public class LanguagePickerDialogFragment extends BottomSheetDialogFragment {

    public interface OnLanguageSelected { void onSelected(String code); }
    private OnLanguageSelected listener;
    public void setListener(OnLanguageSelected l) { this.listener = l; }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_language_picker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String current = SanctusApplication.getInstance().getSettingsRepository().getLanguage();

        RecyclerView rv = view.findViewById(R.id.rv_languages);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        rv.setAdapter(new LangAdapter(LanguageManager.SUPPORTED_LANGUAGES, current, code -> {
            if (listener != null) listener.onSelected(code);
            dismiss();
        }));
    }

    static class LangAdapter extends RecyclerView.Adapter<LangAdapter.VH> {
        private final List<LanguageManager.Language> langs;
        private final String current;
        private final OnLanguageSelected listener;

        LangAdapter(List<LanguageManager.Language> langs, String current, OnLanguageSelected listener) {
            this.langs = langs;
            this.current = current;
            this.listener = listener;
        }

        @NonNull @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_language, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            LanguageManager.Language lang = langs.get(position);
            holder.tvNative.setText(lang.nativeName);
            holder.tvEnglish.setText(lang.englishName);
            holder.tvPop.setText(lang.christianMillions > 0 ? lang.christianMillions + "M" : "");
            boolean sel = lang.code.equals(current);
            holder.tvCheck.setVisibility(sel ? View.VISIBLE : View.INVISIBLE);
            holder.itemView.setOnClickListener(v -> listener.onSelected(lang.code));
        }

        @Override public int getItemCount() { return langs.size(); }

        static class VH extends RecyclerView.ViewHolder {
            TextView tvNative, tvEnglish, tvPop, tvCheck;
            VH(View v) {
                super(v);
                tvNative = v.findViewById(R.id.tv_lang_native);
                tvEnglish = v.findViewById(R.id.tv_lang_english);
                tvPop = v.findViewById(R.id.tv_lang_pop);
                tvCheck = v.findViewById(R.id.tv_lang_check);
            }
        }
    }
}
