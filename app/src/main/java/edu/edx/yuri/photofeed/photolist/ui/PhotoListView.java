package edu.edx.yuri.photofeed.photolist.ui;

import edu.edx.yuri.photofeed.entities.Photo;

/**
 * Created by yuri_ on 03/01/2018.
 */

public interface PhotoListView {

    void showList();
    void hideList();
    void showProgress();
    void hideProgress();

    void addPhoto(Photo photo);
    void removePhoto(Photo photo);
    void onPhotosError(String error);

}
