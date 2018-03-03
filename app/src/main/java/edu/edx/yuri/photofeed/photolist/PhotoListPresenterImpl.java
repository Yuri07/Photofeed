package edu.edx.yuri.photofeed.photolist;

import org.greenrobot.eventbus.Subscribe;

import edu.edx.yuri.photofeed.entities.Photo;
import edu.edx.yuri.photofeed.lib.base.EventBus;
import edu.edx.yuri.photofeed.photolist.events.PhotoListEvent;
import edu.edx.yuri.photofeed.photolist.ui.PhotoListView;

/**
 * Created by yuri_ on 04/01/2018.
 */

public class PhotoListPresenterImpl implements PhotoListPresenter {

    EventBus eventBus;
    PhotoListView view;
    PhotoListInteractor interactor;
    private final static String EMPTY_LIST = "Empty list";

    public PhotoListPresenterImpl(EventBus eventBus, PhotoListView view, PhotoListInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        this.view = null;
        eventBus.unregister(this);
    }

    @Override
    public void subscribe() {
        if (view != null){
            view.hideList();
            view.showProgress();
        }
        interactor.subscribe();
    }

    @Override
    public void unsubscribe() {
        interactor.unsubscribe();
    }

    @Override
    public void removePhoto(Photo photo) {
        interactor.removePhoto(photo);
    }

    @Override
    @Subscribe
    public void onEventMainThread(PhotoListEvent event) {
        if (this.view != null) {
            if (view != null){
                view.hideProgress();
                view.showList();
            }
            String error = event.getError();
            if (error != null) {
                if (error.isEmpty()) {
                    view.onPhotosError(EMPTY_LIST);
                } else {
                    view.onPhotosError(error);
                }
            } else {
                if (event.getType() == PhotoListEvent.READ_EVENT) {
                    view.addPhoto(event.getPhoto());
                } else if (event.getType() == PhotoListEvent.DELETE_EVENT) {
                    view.removePhoto(event.getPhoto());
                }
            }
        }
    }
}
