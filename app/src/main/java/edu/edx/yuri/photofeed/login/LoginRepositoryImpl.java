package edu.edx.yuri.photofeed.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import edu.edx.yuri.photofeed.domain.FirebaseAPI;
import edu.edx.yuri.photofeed.domain.FirebaseActionListenerCallback;
import edu.edx.yuri.photofeed.lib.base.EventBus;
import edu.edx.yuri.photofeed.login.events.LoginEvent;

/**
 * Created by yuri_ on 27/12/2017.
 */

public class LoginRepositoryImpl implements LoginRepository {

    private EventBus eventBus;
    private FirebaseAPI firebase;
    private DatabaseReference dataReference;
    private DatabaseReference myUserReference;

    public LoginRepositoryImpl(FirebaseAPI firebase, EventBus eventBus) {
        this.firebase = firebase;
        this.eventBus = eventBus;
    }

    @Override
    public void signUp(final String email,final String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        post(LoginEvent.onSignUpSuccess);
                        signIn(email, password);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        post(LoginEvent.onSignUpError, e.getMessage());
                    }
                });
    }

    @Override
    public void signIn(String email, String password) {
        if (email != null && password != null) {
            try {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                String email = firebase.getAuthEmail();
                                post(LoginEvent.onSignInSuccess, null, email);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                post(LoginEvent.onSignInError, e.getMessage());
                            }
                        });
            } catch (Exception e) {
                post(LoginEvent.onSignInError, e.getMessage());
            }
        } else {
            firebase.checkForSession(new FirebaseActionListenerCallback(){
                @Override
                public void onSuccess() {
                    String email = firebase.getAuthEmail();
                    post(LoginEvent.onSignInSuccess, null, email);
                }

                @Override
                public void onError(DatabaseError error) {
                    post(LoginEvent.onFailedToRecoverSession);
                }
            });
        }
    }

    private void post(int type) {
        post(type, null);
    }

    private void post(int type, String errorMessage) {
        post(type, errorMessage, null);
    }

    private void post(int type, String errorMessage, String loggedUserEmail) {
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if (errorMessage != null) {
            loginEvent.setErrorMesage(errorMessage);
        }
        loginEvent.setLoggedUserEmail(loggedUserEmail);
        eventBus.post(loginEvent);
    }

}
