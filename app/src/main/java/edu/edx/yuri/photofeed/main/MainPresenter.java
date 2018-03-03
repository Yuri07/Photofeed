package edu.edx.yuri.photofeed.main;

import android.location.Location;

import edu.edx.yuri.photofeed.main.events.MainEvent;

/**
 * Created by yuri_ on 02/01/2018.
 */

public interface MainPresenter {

    void onCreate();
    void onDestroy();

    void logout();
    void uploadPhoto(Location location, String path);
    void onEventMainThread(MainEvent event);

}
