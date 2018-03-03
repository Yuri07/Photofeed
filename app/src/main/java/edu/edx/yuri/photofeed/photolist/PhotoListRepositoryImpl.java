package edu.edx.yuri.photofeed.photolist;

import android.text.TextUtils;

import com.google.firebase.database.DatabaseError;

import edu.edx.yuri.photofeed.domain.FirebaseAPI;
import edu.edx.yuri.photofeed.domain.FirebaseActionListenerCallback;
import edu.edx.yuri.photofeed.domain.FirebaseEventListenerCallback;
import edu.edx.yuri.photofeed.entities.Photo;
import edu.edx.yuri.photofeed.lib.base.EventBus;
import edu.edx.yuri.photofeed.photolist.events.PhotoListEvent;

/**
 * Created by yuri_ on 04/01/2018.
 */

public class PhotoListRepositoryImpl implements PhotoListRepository {

    private EventBus eventBus;
    private FirebaseAPI firebase;

    public PhotoListRepositoryImpl(FirebaseAPI firebase, EventBus eventBus) {
        this.firebase = firebase;
        this.eventBus = eventBus;
    }

    @Override
    public void subscribe() {
        firebase.checkForData(new FirebaseActionListenerCallback() {//addvalueEventListener
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(DatabaseError error) {
                if (error != null) {
                    post(PhotoListEvent.READ_EVENT, error.getMessage());
                } else {//error==null não ha postagens(via FirebaseAPI.checkForData())
                    post(PhotoListEvent.READ_EVENT, "");
                }

            }
        });
        firebase.subscribe(new FirebaseEventListenerCallback() {//addChildEventListener
            @Override public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);
                photo.setId(dataSnapshot.getKey());

                String email = firebase.getAuthEmail();

                boolean publishedByMy = !TextUtils.isEmpty(email) && !TextUtils.isEmpty(photo.getEmail()) && photo.getEmail().equals(email);
                photo.setPublishedByMe(publishedByMy);
                post(PhotoListEvent.READ_EVENT, photo);
            }

            @Override public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);
                photo.setId(dataSnapshot.getKey());

                post(PhotoListEvent.DELETE_EVENT, photo);
            }

            @Override public void onCancelled(DatabaseError error) {
                post(PhotoListEvent.READ_EVENT, error.getMessage());
            }
        });
    }

    @Override
    public void unSubscribe() {
        firebase.unsubscribe();

    }

    @Override
    public void remove(final Photo photo) {
        firebase.remove(photo, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                post(PhotoListEvent.DELETE_EVENT, photo);
            }

            @Override
            public void onError(DatabaseError error) {

            }
        });
    }

    private void post(int type, Photo photo){
        post(type, photo, null);
    }

    private void post(int type, String error){
        post(type, null, error);
    }

    private void post(int type, Photo photo, String error){
        PhotoListEvent event = new PhotoListEvent();
        event.setType(type);
        event.setError(error);
        event.setPhoto(photo);
        eventBus.post(event);
    }

}
