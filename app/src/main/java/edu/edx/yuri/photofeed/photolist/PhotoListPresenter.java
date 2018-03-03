package edu.edx.yuri.photofeed.photolist;

import edu.edx.yuri.photofeed.entities.Photo;
import edu.edx.yuri.photofeed.photolist.events.PhotoListEvent;

/**
 * Created by yuri_ on 04/01/2018.
 */

public interface PhotoListPresenter {

    void onCreate();
    void onDestroy();

    void subscribe();
    void unsubscribe();

    void removePhoto(Photo photo);
    void onEventMainThread(PhotoListEvent event);

}
