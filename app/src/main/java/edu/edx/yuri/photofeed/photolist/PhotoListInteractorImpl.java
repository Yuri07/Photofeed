package edu.edx.yuri.photofeed.photolist;

import edu.edx.yuri.photofeed.entities.Photo;

/**
 * Created by yuri_ on 04/01/2018.
 */

public class PhotoListInteractorImpl implements PhotoListInteractor {

    PhotoListRepository repository;

    public PhotoListInteractorImpl(PhotoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribe() {
        repository.subscribe();
    }

    @Override
    public void unsubscribe() {
        repository.unSubscribe();
    }

    @Override
    public void removePhoto(Photo photo) {
        repository.remove(photo);
    }
}
