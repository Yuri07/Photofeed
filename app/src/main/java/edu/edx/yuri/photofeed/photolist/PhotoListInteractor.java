package edu.edx.yuri.photofeed.photolist;

import edu.edx.yuri.photofeed.entities.Photo;

/**
 * Created by yuri_ on 04/01/2018.
 */

public interface PhotoListInteractor {

    void subscribe();
    void unsubscribe();
    void removePhoto(Photo photo);

}
