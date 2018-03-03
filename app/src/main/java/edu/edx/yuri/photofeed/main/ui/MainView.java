package edu.edx.yuri.photofeed.main.ui;

/**
 * Created by yuri_ on 02/01/2018.
 */

public interface MainView {

    void onUploadInit();
    void onUploadComplete();
    void onUploadError(String error);

}
