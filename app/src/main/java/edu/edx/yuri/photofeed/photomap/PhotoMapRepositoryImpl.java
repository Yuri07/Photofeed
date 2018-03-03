package edu.edx.yuri.photofeed.photomap;

import android.text.TextUtils;

import com.google.firebase.database.DatabaseError;

import edu.edx.yuri.photofeed.domain.FirebaseAPI;
import edu.edx.yuri.photofeed.domain.FirebaseEventListenerCallback;
import edu.edx.yuri.photofeed.entities.Photo;
import edu.edx.yuri.photofeed.lib.base.EventBus;
import edu.edx.yuri.photofeed.photomap.events.PhotoMapEvent;

/**
 * Created by yuri_ on 04/01/2018.
 */

public class PhotoMapRepositoryImpl implements PhotoMapRepository {

    private EventBus eventBus;
    private FirebaseAPI firebase;

    public PhotoMapRepositoryImpl(FirebaseAPI firebase, EventBus eventBus) {
        this.firebase = firebase;
        this.eventBus = eventBus;
    }

    @Override
    public void subscribe() {
        firebase.subscribe(new FirebaseEventListenerCallback() {
            @Override public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);
                photo.setId(dataSnapshot.getKey());

                String email = firebase.getAuthEmail();

                boolean publishedByMy = !TextUtils.isEmpty(email) && !TextUtils.isEmpty(photo.getEmail()) && photo.getEmail().equals(email);
                photo.setPublishedByMe(publishedByMy);
                post(PhotoMapEvent.READ_EVENT, photo);
            }

            @Override public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {
                Photo photo = dataSnapshot.getValue(Photo.class);
                photo.setId(dataSnapshot.getKey());

                post(PhotoMapEvent.DELETE_EVENT, photo);
            }

            @Override public void onCancelled(DatabaseError error) {
                post(error.getMessage());
            }
        });
    }

    @Override
    public void unsubscribe() {
        firebase.unsubscribe();
    }

    private void post(int type, Photo photo){
        post(type, photo, null);
    }

    private void post(String error){
        post(0, null, error);
    }

    private void post(int type, Photo photo, String error){
        PhotoMapEvent event = new PhotoMapEvent();
        event.setType(type);
        event.setError(error);
        event.setPhoto(photo);
        eventBus.post(event);
    }

}
