package edu.edx.yuri.photofeed.main;

import android.location.Location;

/**
 * Created by yuri_ on 02/01/2018.
 */

public interface MainRepository {

    void logout();
    void uploadPhoto(Location location, String path);

}
