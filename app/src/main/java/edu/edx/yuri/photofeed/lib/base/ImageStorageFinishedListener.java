package edu.edx.yuri.photofeed.lib.base;

/**
 * Created by yuri_ on 28/12/2017.
 */

public interface ImageStorageFinishedListener {
    void onSuccess();
    void onError(String error);
}
