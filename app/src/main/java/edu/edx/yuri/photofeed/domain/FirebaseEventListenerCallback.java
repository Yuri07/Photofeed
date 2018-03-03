package edu.edx.yuri.photofeed.domain;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by yuri_ on 28/12/2017.
 */

public interface FirebaseEventListenerCallback {

    void onChildAdded(DataSnapshot dataSnapshot);
    void onChildRemoved(DataSnapshot dataSnapshot);
    void onCancelled(DatabaseError error);

}
