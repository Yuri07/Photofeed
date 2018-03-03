package edu.edx.yuri.photofeed.main;

/**
 * Created by yuri_ on 02/01/2018.
 */

public class SessionInteractorImpl implements SessionInteractor {

    MainRepository repository;

    public SessionInteractorImpl(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void logout() {
        repository.logout();
    }
}
