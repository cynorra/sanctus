package com.sanctus.christian.prayer.ui.feed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.firebase.auth.FirebaseUser;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.auth.AuthManager;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.local.entity.SaintEntity;
import com.sanctus.christian.prayer.data.remote.CommunityPost;
import com.sanctus.christian.prayer.data.remote.FeedRepository;
import com.sanctus.christian.prayer.data.remote.UserRepository;
import com.sanctus.christian.prayer.referral.InstallReferrerHelper;
import com.sanctus.christian.prayer.share.ShareActions;
import com.sanctus.christian.prayer.ui.dashboard.DashboardViewModel;
import com.sanctus.christian.prayer.ui.prayer.PrayerDetailFragment;
import com.sanctus.christian.prayer.ui.saint.SaintDetailFragment;
import com.sanctus.christian.prayer.ui.saint.SaintViewModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Feed tab: today's prayer and saint (always available, offline) followed by a community
 * prayer-intention stream backed by Firestore. Posting / saying Amen needs a Google sign-in;
 * reading the stream needs one too (see firestore.rules).
 */
public class FeedFragment extends Fragment implements FeedAdapter.Listener {

    private final AuthManager authManager = new AuthManager();
    private final UserRepository userRepository = new UserRepository();
    private FeedRepository feedRepository;

    private FeedAdapter adapter;
    private SwipeRefreshLayout swipe;

    private PrayerEntity prayerOfDay;
    private SaintEntity saintOfDay;
    private List<CommunityPost> posts = new ArrayList<>();
    private boolean communityLoaded;
    private boolean communityFailed;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        feedRepository = new FeedRepository(requireContext());

        RecyclerView rv = view.findViewById(R.id.rv_feed);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new FeedAdapter(this);
        rv.setAdapter(adapter);

        swipe = view.findViewById(R.id.swipe_refresh);
        swipe.setOnRefreshListener(this::loadCommunity);
        view.findViewById(R.id.fab_compose).setOnClickListener(v -> onComposeClicked());

        DashboardViewModel dashboardVm = new ViewModelProvider(this).get(DashboardViewModel.class);
        dashboardVm.getPrayerOfTheDay().observe(getViewLifecycleOwner(), prayer -> {
            prayerOfDay = prayer;
            rebuild();
        });

        String today = new SimpleDateFormat("MMMM d", Locale.ENGLISH).format(new Date());
        int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        SaintViewModel saintVm = new ViewModelProvider(this).get(SaintViewModel.class);
        saintVm.getSaintSpotlight(today, dayOfYear).observe(getViewLifecycleOwner(), saint -> {
            saintOfDay = saint;
            rebuild();
        });

        rebuild();
        loadCommunity();
    }

    @Override
    public void onResume() {
        super.onResume();
        // The feed tab stays alive across tab switches; pick up a changed block list / sign-in.
        if (adapter != null) loadCommunity();
    }

    private void loadCommunity() {
        FirebaseUser user = authManager.getCurrentUser();
        if (user == null) {
            posts = new ArrayList<>();
            communityLoaded = true;
            communityFailed = false;
            stopRefreshing();
            rebuild();
            return;
        }
        feedRepository.loadPosts(user.getUid(), result -> {
            if (getView() == null) return;
            posts = result;
            communityLoaded = true;
            communityFailed = false;
            stopRefreshing();
            rebuild();
        }, ok -> {
            if (getView() == null) return;
            communityLoaded = true;
            communityFailed = true;
            stopRefreshing();
            rebuild();
        });
    }

    private void stopRefreshing() {
        if (swipe != null) swipe.setRefreshing(false);
    }

    private void rebuild() {
        if (adapter == null) return;
        List<FeedItem> items = new ArrayList<>();
        items.add(FeedItem.header(R.string.feed_today_header));
        if (prayerOfDay != null) items.add(FeedItem.prayer(prayerOfDay));
        if (saintOfDay != null) items.add(FeedItem.saint(saintOfDay));

        items.add(FeedItem.header(R.string.feed_community_header));
        if (authManager.getCurrentUser() == null) {
            items.add(FeedItem.notice(R.string.feed_sign_in_required, R.string.feed_sign_in, this::signIn));
        } else if (communityFailed) {
            items.add(FeedItem.notice(R.string.feed_load_failed, 0, null));
        } else if (communityLoaded && posts.isEmpty()) {
            items.add(FeedItem.notice(R.string.feed_empty, 0, null));
        } else {
            for (CommunityPost p : posts) items.add(FeedItem.post(p));
        }
        adapter.submit(items);
    }

    // ── Auth ───────────────────────────────────────────────────────────────────────────────

    private interface SignedIn { void run(FirebaseUser user); }

    private void signIn() { signIn(user -> { }); }

    private void signIn(SignedIn then) {
        authManager.signIn(requireContext(), new AuthManager.SignInCallback() {
            @Override public void onSuccess(FirebaseUser user) {
                String pendingCode = InstallReferrerHelper.consumePendingCode(requireContext());
                userRepository.ensureProfile(user.getUid(), user.getDisplayName(),
                        user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null, pendingCode,
                        profile -> onSignedIn(user, then),
                        e -> onSignedIn(user, then));
            }
            @Override public void onError(Exception e) {
                if (getView() == null) return;
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), R.string.sign_in_failed, Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void onSignedIn(FirebaseUser user, SignedIn then) {
        if (getView() == null) return;
        requireActivity().runOnUiThread(() -> {
            loadCommunity();
            then.run(user);
        });
    }

    // ── Compose ────────────────────────────────────────────────────────────────────────────

    private void onComposeClicked() {
        FirebaseUser user = authManager.getCurrentUser();
        if (user == null) {
            signIn(this::showComposeDialog);
        } else {
            showComposeDialog(user);
        }
    }

    private void showComposeDialog(FirebaseUser user) {
        View content = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_compose_post, null);
        EditText et = content.findViewById(R.id.et_post);
        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setTitle(R.string.feed_compose_title)
                .setView(content)
                .setNegativeButton(R.string.feed_cancel, null)
                .setPositiveButton(R.string.feed_post, null)
                .create();
        dialog.setOnShowListener(d -> dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String text = et.getText().toString().trim();
            if (text.isEmpty()) return;
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            feedRepository.createPost(user.getUid(), user.getDisplayName(), text, success -> {
                if (getView() == null) return;
                if (success) {
                    dialog.dismiss();
                    loadCommunity();
                } else {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    Toast.makeText(requireContext(), R.string.feed_post_failed, Toast.LENGTH_SHORT).show();
                }
            });
        }));
        dialog.show();
    }

    // ── Adapter callbacks ──────────────────────────────────────────────────────────────────

    @Override public void onOpenPrayer(PrayerEntity prayer) {
        Bundle args = new Bundle();
        args.putInt(PrayerDetailFragment.ARG_PRAYER_ID, prayer.id);
        Navigation.findNavController(requireView()).navigate(R.id.prayerDetailFragment, args);
    }

    @Override public void onSharePrayer(PrayerEntity prayer) {
        ShareActions.sharePrayer(requireContext(), getChildFragmentManager(), prayer);
    }

    @Override public void onOpenSaint(SaintEntity saint) {
        Bundle args = new Bundle();
        args.putInt(SaintDetailFragment.ARG_SAINT_ID, saint.id);
        Navigation.findNavController(requireView()).navigate(R.id.saintDetailFragment, args);
    }

    @Override public void onShareSaint(SaintEntity saint) {
        ShareActions.shareSaint(requireContext(), getChildFragmentManager(), saint);
    }

    @Override public void onSharePost(CommunityPost post) {
        ShareActions.shareIntention(requireContext(), getChildFragmentManager(), post.text);
    }

    @Override public void onAmen(CommunityPost post) {
        FirebaseUser user = authManager.getCurrentUser();
        if (user == null) { signIn(); return; }
        if (post.amenedByMe) return;

        post.amenedByMe = true;
        post.amenCount++;
        rebuild();
        feedRepository.addAmen(user.getUid(), post, success -> {
            if (success || getView() == null) return;
            post.amenedByMe = false;
            post.amenCount--;
            rebuild();
        });
    }

    @Override public void onPostMenu(CommunityPost post, View anchor) {
        FirebaseUser user = authManager.getCurrentUser();
        if (user == null) { signIn(); return; }
        if (user.getUid().equals(post.authorUid)) {
            PopupMenu own = new PopupMenu(requireContext(), anchor);
            own.getMenu().add(0, 1, 0, R.string.feed_delete);
            own.setOnMenuItemClickListener(item -> {
                confirmDelete(post);
                return true;
            });
            own.show();
            return;
        }

        PopupMenu menu = new PopupMenu(requireContext(), anchor);
        menu.getMenu().add(0, 1, 0, R.string.feed_report);
        menu.getMenu().add(0, 2, 1, R.string.feed_block);
        menu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == 1) {
                feedRepository.report(user.getUid(), post, success -> { });
                posts.remove(post);
                rebuild();
                Toast.makeText(requireContext(), R.string.feed_report_done, Toast.LENGTH_SHORT).show();
            } else {
                feedRepository.blockUser(post.authorUid);
                loadCommunity();
                Toast.makeText(requireContext(), R.string.feed_block_done, Toast.LENGTH_SHORT).show();
            }
            return true;
        });
        menu.show();
    }

    private void confirmDelete(CommunityPost post) {
        new AlertDialog.Builder(requireContext())
            .setMessage(R.string.feed_delete_confirm)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(R.string.feed_delete, (d, w) ->
                feedRepository.deletePost(post, success -> {
                    if (getView() == null) return;
                    if (success) {
                        posts.remove(post);
                        rebuild();
                    }
                    Toast.makeText(requireContext(),
                        success ? R.string.feed_delete_done : R.string.feed_delete_failed,
                        Toast.LENGTH_SHORT).show();
                }))
            .show();
    }
}
