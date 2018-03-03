package edu.edx.yuri.photofeed.domain;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import edu.edx.yuri.photofeed.entities.Photo;

/**
 * Created by yuri_ on 27/12/2017.
 */

public class FirebaseAPI {

    //private Firebase firebase;
    private DatabaseReference mPhotoDatabaseReference;//private Firebase firebase; Firebase trocado por DatabaseReference
    private ChildEventListener photosEventListener;

    public FirebaseAPI(DatabaseReference databaseReference) {
        mPhotoDatabaseReference = databaseReference;//FirebaseDatabase.getInstance().getReference();
    }

    public void checkForData(final FirebaseActionListenerCallback listener){//Checa se a lista de photos esta vazia//quando PhotoListRepositoty via se inscrever para "ouvir" eventos ele verifica os dados antes
        ValueEventListener postListener = new ValueEventListener() {
            @Override public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    listener.onSuccess();
                } else {
                    listener.onError(null);
                }
            }

            @Override public void onCancelled(DatabaseError databaseError) {
                Log.d("FIREBASE API", databaseError.getMessage());
                //nao seria necessario essa linha: listener.onError(databaseError.getMessage());?
            }
        };
        mPhotoDatabaseReference.addValueEventListener(postListener);
    }

    public void subscribe(final FirebaseEventListenerCallback listener) {
        if (photosEventListener == null) {
            photosEventListener = new ChildEventListener() {
                @Override public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                    listener.onChildAdded(dataSnapshot);
                }

                @Override public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

                }

                @Override public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {
                    listener.onChildRemoved(dataSnapshot);
                }

                @Override public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

                }

                @Override public void onCancelled(DatabaseError databaseError) {//erro no servidor ou quando é removido devido a regras de segurança do firebase
                    listener.onCancelled(databaseError);
                }
            };
            mPhotoDatabaseReference.addChildEventListener(photosEventListener);
        }
    }

    public void unsubscribe() {
        mPhotoDatabaseReference.removeEventListener(photosEventListener);
    }

    public String create() {
        return mPhotoDatabaseReference.push().getKey();
    }

    public void update(Photo photo) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(photo.getId()).setValue(photo);
    }

    public void remove(Photo photo, FirebaseActionListenerCallback listener) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(photo.getId()).removeValue();

        listener.onSuccess();
    }

    public String getAuthEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getEmail();
        }
        return null;
    }

    /*public void signUp(String email, String password, final FirebaseActionListenerCallback listener){
        firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                listener.onSuccess();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                listener.onError(firebaseError);
            }
        });
    }

    public void login(String email, String password, final FirebaseActionListenerCallback listener){
        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                listener.onSuccess();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                listener.onError(firebaseError);
            }
        });
    }*/

    public void checkForSession(FirebaseActionListenerCallback listener) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            listener.onSuccess();
        } else {
            listener.onError(null);
        }
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
    }



}
