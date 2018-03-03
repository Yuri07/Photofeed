package edu.edx.yuri.photofeed.photomap;

import edu.edx.yuri.photofeed.photomap.events.PhotoMapEvent;

/**
 * Created by yuri_ on 04/01/2018.
 */

public interface PhotoMapPresenter {

    void onCreate();
    void onDestroy();

    void subscribe();
    void unsubscribe();

    void onEventMainThread(PhotoMapEvent event);

}
