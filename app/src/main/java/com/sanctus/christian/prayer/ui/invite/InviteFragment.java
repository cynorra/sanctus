package com.sanctus.christian.prayer.ui.invite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseUser;
import com.sanctus.christian.prayer.BuildConfig;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.analytics.AnalyticsLogger;
import com.sanctus.christian.prayer.auth.AuthManager;
import com.sanctus.christian.prayer.data.remote.UserProfile;
import com.sanctus.christian.prayer.data.remote.UserRepository;
import com.sanctus.christian.prayer.referral.InstallReferrerHelper;
import com.sanctus.christian.prayer.util.AnimUtils;

public class InviteFragment extends Fragment {

    private final AuthManager authManager = new AuthManager();
    private final UserRepository userRepository = new UserRepository();

    private View groupSignedOut, groupSignedIn;
    private TextView tvDisplayName, tvReferralCode, tvInviteCount, tvSignInError;

    @Nullable @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_invite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_back).setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        groupSignedOut = view.findViewById(R.id.group_signed_out);
        groupSignedIn = view.findViewById(R.id.group_signed_in);
        tvDisplayName = view.findViewById(R.id.tv_display_name);
        tvReferralCode = view.findViewById(R.id.tv_referral_code);
        tvInviteCount = view.findViewById(R.id.tv_invite_count);
        tvSignInError = view.findViewById(R.id.tv_sign_in_error);

        MaterialButton btnSignIn = view.findViewById(R.id.btn_sign_in);
        MaterialButton btnShareInvite = view.findViewById(R.id.btn_share_invite);
        TextView btnSignOut = view.findViewById(R.id.btn_sign_out);

        btnSignIn.setOnClickListener(v -> doSignIn());
        btnSignOut.setOnClickListener(v -> {
            authManager.signOut();
            showSignedOut();
        });
        btnShareInvite.setOnClickListener(v -> {
            AnimUtils.pressFeedback(btnShareInvite);
            shareInviteLink();
        });

        refreshState();
    }

    private void refreshState() {
        FirebaseUser user = authManager.getCurrentUser();
        if (user == null) {
            showSignedOut();
            return;
        }
        userRepository.getProfile(user.getUid(),
                profile -> {
                    if (getView() == null) return;
                    if (profile == null) {
                        // Signed in with Firebase but the Firestore doc hasn't been created yet.
                        createProfileFor(user);
                    } else {
                        showSignedIn(user, profile);
                    }
                },
                e -> { if (getView() != null) showSignedOut(); });
    }

    private void doSignIn() {
        tvSignInError.setVisibility(View.GONE);
        authManager.signIn(requireContext(), new AuthManager.SignInCallback() {
            @Override public void onSuccess(FirebaseUser user) { createProfileFor(user); }
            @Override public void onError(Exception e) {
                if (getView() == null) return;
                tvSignInError.setText(getString(R.string.sign_in_failed));
                tvSignInError.setVisibility(View.VISIBLE);
            }
        });
    }

    private void createProfileFor(FirebaseUser user) {
        String pendingCode = InstallReferrerHelper.consumePendingCode(requireContext());
        userRepository.ensureProfile(user.getUid(), user.getDisplayName(),
                user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : null,
                pendingCode,
                profile -> { if (getView() != null) showSignedIn(user, profile); },
                e -> { if (getView() != null) showSignedOut(); });
    }

    private void showSignedOut() {
        groupSignedOut.setVisibility(View.VISIBLE);
        groupSignedIn.setVisibility(View.GONE);
    }

    private void showSignedIn(FirebaseUser user, UserProfile profile) {
        groupSignedOut.setVisibility(View.GONE);
        groupSignedIn.setVisibility(View.VISIBLE);
        tvDisplayName.setText(user.getDisplayName());
        tvReferralCode.setText(profile.referralCode);
        tvInviteCount.setText(getString(R.string.invite_count_format, profile.inviteCount));
    }

    private void shareInviteLink() {
        String code = tvReferralCode.getText().toString();
        if (code.isEmpty()) return;

        String link = BuildConfig.PLAY_STORE_URL + "&referrer=" + urlEncode("ref_code=" + code);
        String text = getString(R.string.invite_share_text, link);
        AnalyticsLogger.logInviteShared(requireContext());
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(shareIntent, getString(R.string.share_invite_link)));
    }

    private static String urlEncode(String s) {
        try {
            return java.net.URLEncoder.encode(s, "UTF-8");
        } catch (Exception e) {
            return s;
        }
    }
}
