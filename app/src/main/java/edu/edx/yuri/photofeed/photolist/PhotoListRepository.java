package edu.edx.yuri.photofeed.photolist;

import edu.edx.yuri.photofeed.entities.Photo;

/**
 * Created by yuri_ on 04/01/2018.
 */

public interface PhotoListRepository {

    void subscribe();
    void unSubscribe();
    void remove(Photo photo);

}
