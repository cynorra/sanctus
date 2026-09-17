package com.sanctus.christian.prayer.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import androidx.core.content.FileProvider;
import com.sanctus.christian.prayer.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Renders a shareable "prayer card" image (dark purple/gold, matching the app's theme) so
 * prayers can be posted to Instagram/TikTok/Facebook/WhatsApp, which don't let a plain
 * ACTION_SEND text share carry custom caption text into a feed/story post the way a chat does.
 *
 * Two formats: POST (square, for feed posts) and STORY (9:16, for Instagram/Facebook Stories
 * and WhatsApp Status, where the image fills the whole screen so content is vertically centered).
 */
public class ShareCardGenerator {

    public enum Format {
        POST(1080, 1080),
        STORY(1080, 1920);

        final int width, height;
        Format(int width, int height) { this.width = width; this.height = height; }
    }

    private static final int PADDING = 90;

    private static final int COLOR_BACKGROUND = Color.parseColor("#0D0A1A");
    private static final int COLOR_CARD = Color.parseColor("#1A1530");
    private static final int COLOR_ACCENT = Color.parseColor("#C9A84C");
    private static final int COLOR_TITLE = Color.parseColor("#F0EAF8");
    private static final int COLOR_BODY = Color.parseColor("#D4C8E8");
    private static final int COLOR_FOOTER = Color.parseColor("#9985B8");

    public static Uri generate(Context context, Format format, String prayerName, String prayerContent) {
        int w = format.width, h = format.height;
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(COLOR_BACKGROUND);

        int contentWidth = w - 2 * PADDING;
        boolean isStory = format == Format.STORY;
        int maxChars = isStory ? 420 : 260;

        TextPaint titlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        titlePaint.setColor(COLOR_TITLE);
        titlePaint.setTextSize(isStory ? 62f : 56f);
        titlePaint.setTypeface(Typeface.create(Typeface.SERIF, Typeface.BOLD));
        StaticLayout titleLayout = buildLayoutWithLineSpacing(prayerName, titlePaint, contentWidth, 1.1f);

        TextPaint bodyPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        bodyPaint.setColor(COLOR_BODY);
        bodyPaint.setTextSize(isStory ? 42f : 38f);
        bodyPaint.setTypeface(Typeface.SERIF);
        String excerpt = truncate(prayerContent, maxChars);
        StaticLayout bodyLayout = buildLayoutWithLineSpacing(excerpt, bodyPaint, contentWidth, 1.35f);

        float lineY = 40f;
        float titleGap = 50f;
        float blockHeight = 4f + titleGap + titleLayout.getHeight() + 50f + bodyLayout.getHeight();

        // Post: content starts near the top, in a card. Story: content is centered in the
        // full-bleed vertical frame so it reads naturally when viewed full-screen.
        float cardTop, y;
        if (isStory) {
            cardTop = (h - blockHeight) / 2f - 60f;
            y = cardTop + lineY;
        } else {
            cardTop = PADDING - 30f;
            y = PADDING + lineY;
        }

        if (!isStory) {
            Paint cardPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            cardPaint.setColor(COLOR_CARD);
            canvas.drawRoundRect(PADDING - 30f, cardTop, w - PADDING + 30f, PADDING - 30f + blockHeight + 60f, 32f, 32f, cardPaint);
        }

        Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(COLOR_ACCENT);
        canvas.drawRect(PADDING, y, PADDING + 90f, y + 4f, linePaint);
        y += titleGap;

        canvas.save();
        canvas.translate(PADDING, y);
        titleLayout.draw(canvas);
        canvas.restore();
        y += titleLayout.getHeight() + 50f;

        canvas.save();
        canvas.translate(PADDING, y);
        bodyLayout.draw(canvas);
        canvas.restore();

        TextPaint footerPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        footerPaint.setColor(COLOR_FOOTER);
        footerPaint.setTextSize(30f);
        footerPaint.setTypeface(Typeface.SANS_SERIF);
        footerPaint.setLetterSpacing(0.05f);
        String footer = "✝  Sanctus: Christian Prayer";
        canvas.drawText(footer, PADDING, h - PADDING - 40f, footerPaint);

        TextPaint linkPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        linkPaint.setColor(COLOR_ACCENT);
        linkPaint.setTextSize(28f);
        canvas.drawText(BuildConfig.WEBSITE_URL, PADDING, h - PADDING, linkPaint);

        return saveToCache(context, bitmap, isStory ? "story" : "post");
    }

    private static StaticLayout buildLayoutWithLineSpacing(String text, TextPaint paint, int width, float spacingMult) {
        return StaticLayout.Builder.obtain(text, 0, text.length(), paint, width)
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setLineSpacing(0f, spacingMult)
                .setIncludePad(false)
                .build();
    }

    private static String truncate(String text, int maxChars) {
        if (text == null) return "";
        String collapsed = text.replace('\n', ' ').trim();
        if (collapsed.length() <= maxChars) return collapsed;
        return collapsed.substring(0, maxChars).trim() + "…";
    }

    private static Uri saveToCache(Context context, Bitmap bitmap, String suffix) {
        File dir = new File(context.getCacheDir(), "share_cards");
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, "prayer_card_" + suffix + "_" + System.currentTimeMillis() + ".png");
        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write share card", e);
        }
        return FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
    }
}
