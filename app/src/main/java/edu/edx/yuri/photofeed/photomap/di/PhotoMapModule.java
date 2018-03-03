package edu.edx.yuri.photofeed.photomap.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.edx.yuri.photofeed.domain.FirebaseAPI;
import edu.edx.yuri.photofeed.lib.base.EventBus;
import edu.edx.yuri.photofeed.photomap.PhotoMapInteractor;
import edu.edx.yuri.photofeed.photomap.PhotoMapInteractorImpl;
import edu.edx.yuri.photofeed.photomap.PhotoMapPresenter;
import edu.edx.yuri.photofeed.photomap.PhotoMapPresenterImpl;
import edu.edx.yuri.photofeed.photomap.PhotoMapRepository;
import edu.edx.yuri.photofeed.photomap.PhotoMapRepositoryImpl;
import edu.edx.yuri.photofeed.photomap.ui.PhotoMapView;

/**
 * Created by yuri_ on 04/01/2018.
 */

@Module
public class PhotoMapModule {

    PhotoMapView view;

    public PhotoMapModule(PhotoMapView view) {
        this.view = view;
    }

    @Provides @Singleton
    PhotoMapView providesPhotoContentView() {
        return this.view;
    }

    @Provides @Singleton
    PhotoMapPresenter providesPhotoContentPresenter(EventBus eventBus, PhotoMapView view, PhotoMapInteractor listInteractor) {
        return new PhotoMapPresenterImpl(eventBus, view, listInteractor);
    }

    @Provides @Singleton
    PhotoMapInteractor providesPhotoContentInteractor(PhotoMapRepository repository) {
        return new PhotoMapInteractorImpl(repository);
    }

    @Provides
    @Singleton
    PhotoMapRepository providesPhotoContentRepository(FirebaseAPI firebase, EventBus eventBus) {
        return new PhotoMapRepositoryImpl(firebase, eventBus);
    }

}
