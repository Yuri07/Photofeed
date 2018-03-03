package edu.edx.yuri.photofeed.main;

import android.location.Location;

/**
 * Created by yuri_ on 02/01/2018.
 */

public class UploadInteractorImpl implements UploadInteractor {

    private MainRepository repository;

    public UploadInteractorImpl(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Location location, String path) {
        repository.uploadPhoto(location, path);
    }
}
