package com.sanctus.christian.prayer.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.sanctus.christian.prayer.R;

/**
 * Bottom sheet offering direct share-to-platform buttons for WhatsApp/TikTok (feed-style) and
 * Facebook/Instagram (both feed post AND a dedicated Story option), rendering a branded image
 * card since those platforms mostly ignore a plain-text caption on ACTION_SEND. Also offers a
 * "More…" row that falls back to the system share sheet with the plain-text version.
 */
public class ShareSheetDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_NAME = "name";
    private static final String ARG_CONTENT = "content";
    private static final String ARG_PLAIN_TEXT = "plain_text";
    private static final String ARG_CHOOSER_TITLE = "chooser_title";

    public static ShareSheetDialogFragment create(String name, String content, String plainText, String chooserTitle) {
        ShareSheetDialogFragment f = new ShareSheetDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        args.putString(ARG_CONTENT, content);
        args.putString(ARG_PLAIN_TEXT, plainText);
        args.putString(ARG_CHOOSER_TITLE, chooserTitle);
        f.setArguments(args);
        return f;
    }

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_share_sheet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = requireArguments();
        String name = args.getString(ARG_NAME, "");
        String content = args.getString(ARG_CONTENT, "");
        String plainText = args.getString(ARG_PLAIN_TEXT, "");
        String chooserTitle = args.getString(ARG_CHOOSER_TITLE, "");

        bindPostRow(view, R.id.row_whatsapp, SocialShareHelper.Platform.WHATSAPP, name, content, plainText);
        bindPostRow(view, R.id.row_facebook, SocialShareHelper.Platform.FACEBOOK, name, content, plainText);
        bindPostRow(view, R.id.row_instagram, SocialShareHelper.Platform.INSTAGRAM, name, content, plainText);
        bindPostRow(view, R.id.row_tiktok, SocialShareHelper.Platform.TIKTOK, name, content, plainText);

        bindStoryRow(view, R.id.row_facebook_story, SocialShareHelper.Platform.FACEBOOK, name, content,
                (ctx, uri) -> SocialShareHelper.shareToFacebookStory(ctx, uri));
        bindStoryRow(view, R.id.row_instagram_story, SocialShareHelper.Platform.INSTAGRAM, name, content,
                (ctx, uri) -> SocialShareHelper.shareToInstagramStory(ctx, uri));

        view.findViewById(R.id.row_more).setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, plainText);
            startActivity(Intent.createChooser(shareIntent, chooserTitle));
            dismiss();
        });
    }

    private interface StoryShareFn { boolean share(android.content.Context ctx, Uri uri); }

    private void bindPostRow(View root, int rowId, SocialShareHelper.Platform platform,
                              String name, String content, String plainText) {
        View row = root.findViewById(rowId);
        if (!SocialShareHelper.isInstalled(requireContext(), platform)) {
            row.setVisibility(View.GONE);
            return;
        }
        row.setOnClickListener(v -> {
            Uri cardUri = ShareCardGenerator.generate(requireContext(), ShareCardGenerator.Format.POST, name, content);
            boolean started = SocialShareHelper.share(requireContext(), platform, cardUri, plainText);
            if (!started) {
                Toast.makeText(requireContext(), R.string.share_app_not_installed, Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });
    }

    private void bindStoryRow(View root, int rowId, SocialShareHelper.Platform platform,
                               String name, String content, StoryShareFn shareFn) {
        View row = root.findViewById(rowId);
        if (!SocialShareHelper.isInstalled(requireContext(), platform)) {
            row.setVisibility(View.GONE);
            return;
        }
        row.setOnClickListener(v -> {
            Uri cardUri = ShareCardGenerator.generate(requireContext(), ShareCardGenerator.Format.STORY, name, content);
            boolean started = shareFn.share(requireContext(), cardUri);
            if (!started) {
                Toast.makeText(requireContext(), R.string.share_app_not_installed, Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });
    }
}
