package edu.edx.yuri.photofeed.photomap.ui;

import edu.edx.yuri.photofeed.entities.Photo;

/**
 * Created by yuri_ on 03/01/2018.
 */

public interface PhotoMapView {

    void addPhoto(Photo photo);
    void removePhoto(Photo photo);
    void onPhotosError(String error);

}
