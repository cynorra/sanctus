package com.sanctus.christian.prayer.auth;

import android.content.Context;
import android.os.CancellationSignal;
import android.util.Log;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialException;
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sanctus.christian.prayer.BuildConfig;
import com.sanctus.christian.prayer.analytics.AnalyticsLogger;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/** Wraps Credential Manager's "Sign in with Google" flow + Firebase Auth. */
public class AuthManager {
    private static final String TAG = "AuthManager";

    public interface SignInCallback {
        void onSuccess(FirebaseUser user);
        void onError(Exception e);
    }

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public void signOut() {
        firebaseAuth.signOut();
    }

    public void signIn(Context context, SignInCallback callback) {
        if (BuildConfig.GOOGLE_WEB_CLIENT_ID.startsWith("REPLACE_WITH")) {
            callback.onError(new IllegalStateException(
                    "GOOGLE_WEB_CLIENT_ID is not configured — set it in app/build.gradle " +
                    "from Firebase Console > Authentication > Sign-in method > Google."));
            return;
        }

        GetSignInWithGoogleOption option = new GetSignInWithGoogleOption
                .Builder(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                .build();
        GetCredentialRequest request = new GetCredentialRequest.Builder()
                .addCredentialOption(option)
                .build();

        CredentialManager credentialManager = CredentialManager.create(context);
        credentialManager.getCredentialAsync(
                context,
                request,
                new CancellationSignal(),
                executor,
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse result) {
                        handleCredential(context, result.getCredential(), callback);
                    }

                    @Override
                    public void onError(GetCredentialException e) {
                        Log.w(TAG, "Credential Manager sign-in failed", e);
                        callback.onError(e);
                    }
                });
    }

    private void handleCredential(Context context, Credential credential, SignInCallback callback) {
        if (!(credential instanceof CustomCredential) ||
                !GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(credential.getType())) {
            callback.onError(new IllegalStateException("Unexpected credential type: " + credential.getType()));
            return;
        }
        try {
            GoogleIdTokenCredential googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(((CustomCredential) credential).getData());
            AuthCredential firebaseCredential =
                    GoogleAuthProvider.getCredential(googleIdTokenCredential.getIdToken(), null);
            firebaseAuth.signInWithCredential(firebaseCredential)
                    .addOnSuccessListener(result -> {
                        AnalyticsLogger.logSignIn(context);
                        callback.onSuccess(result.getUser());
                    })
                    .addOnFailureListener(callback::onError);
        } catch (RuntimeException e) {
            callback.onError(e);
        }
    }
}
