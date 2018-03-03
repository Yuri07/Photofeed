package edu.edx.yuri.photofeed.photolist.di;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.edx.yuri.photofeed.domain.FirebaseAPI;
import edu.edx.yuri.photofeed.domain.Util;
import edu.edx.yuri.photofeed.entities.Photo;
import edu.edx.yuri.photofeed.lib.base.EventBus;
import edu.edx.yuri.photofeed.lib.base.ImageLoader;
import edu.edx.yuri.photofeed.photolist.PhotoListInteractor;
import edu.edx.yuri.photofeed.photolist.PhotoListInteractorImpl;
import edu.edx.yuri.photofeed.photolist.PhotoListPresenter;
import edu.edx.yuri.photofeed.photolist.PhotoListPresenterImpl;
import edu.edx.yuri.photofeed.photolist.PhotoListRepository;
import edu.edx.yuri.photofeed.photolist.PhotoListRepositoryImpl;
import edu.edx.yuri.photofeed.photolist.ui.PhotoListView;
import edu.edx.yuri.photofeed.photolist.ui.adapters.OnItemClickListener;
import edu.edx.yuri.photofeed.photolist.ui.adapters.PhotoListAdapter;

/**
 * Created by yuri_ on 04/01/2018.
 */

@Module
public class PhotoListModule {

    PhotoListView view;
    OnItemClickListener onItemClickListener;

    public PhotoListModule(PhotoListView view, OnItemClickListener onItemClickListener) {
        this.view = view;
        this.onItemClickListener = onItemClickListener;
    }

    @Provides
    @Singleton
    PhotoListView providesPhotoContentView() {
        return this.view;
    }

    @Provides @Singleton
    PhotoListPresenter providesPhotoListPresenter(EventBus eventBus, PhotoListView view, PhotoListInteractor listInteractor) {
        return new PhotoListPresenterImpl(eventBus, view, listInteractor);
    }

    @Provides @Singleton
    PhotoListInteractor providesPhotoListInteractor(PhotoListRepository repository) {
        return new PhotoListInteractorImpl(repository);
    }

    @Provides @Singleton
    PhotoListRepository providesPhotoListRepository(FirebaseAPI firebase, EventBus eventBus) {
        return new PhotoListRepositoryImpl(firebase, eventBus);
    }

    @Provides @Singleton
    PhotoListAdapter providesPhotosAdapter(Util utils, List<Photo> photoList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        return new PhotoListAdapter(utils, photoList, imageLoader, onItemClickListener);
    }

    @Provides @Singleton
    OnItemClickListener providesOnItemClickListener() {
        return this.onItemClickListener;
    }

    @Provides @Singleton
    List<Photo> providesPhotosList() {
        return new ArrayList<Photo>();
    }

}
