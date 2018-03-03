package edu.edx.yuri.photofeed.main;

import android.location.Location;

/**
 * Created by yuri_ on 02/01/2018.
 */

public interface UploadInteractor {

    void execute(Location location, String path);

}
