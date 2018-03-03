package edu.edx.yuri.photofeed.photomap;

import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import edu.edx.yuri.photofeed.lib.base.EventBus;
import edu.edx.yuri.photofeed.photolist.events.PhotoListEvent;
import edu.edx.yuri.photofeed.photomap.events.PhotoMapEvent;
import edu.edx.yuri.photofeed.photomap.ui.PhotoMapView;

/**
 * Created by yuri_ on 04/01/2018.
 */

public class PhotoMapPresenterImpl implements PhotoMapPresenter {

    EventBus eventBus;
    PhotoMapView view;
    PhotoMapInteractor interactor;

    public PhotoMapPresenterImpl(EventBus eventBus, PhotoMapView view, PhotoMapInteractor interactor) {
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
        interactor.subscribe();
    }

    @Override
    public void unsubscribe() {
        interactor.unsubscribe();
    }

    @Override
    @Subscribe
    public void onEventMainThread(PhotoMapEvent event) {
        if (this.view != null) {
            String error = event.getError();
            if (error != null) {
                view.onPhotosError(error);
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
