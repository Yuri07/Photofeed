package edu.edx.yuri.photofeed.photomap;

/**
 * Created by yuri_ on 04/01/2018.
 */

public class PhotoMapInteractorImpl implements PhotoMapInteractor {

    PhotoMapRepository repository;

    public PhotoMapInteractorImpl(PhotoMapRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribe() {
        repository.subscribe();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribe();
    }
}
