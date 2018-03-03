package edu.edx.yuri.photofeed.main.di;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.edx.yuri.photofeed.domain.FirebaseAPI;
import edu.edx.yuri.photofeed.lib.base.EventBus;
import edu.edx.yuri.photofeed.lib.base.ImageStorage;
import edu.edx.yuri.photofeed.main.MainPresenter;
import edu.edx.yuri.photofeed.main.MainPresenterImpl;
import edu.edx.yuri.photofeed.main.MainRepository;
import edu.edx.yuri.photofeed.main.MainRepositoryImpl;
import edu.edx.yuri.photofeed.main.SessionInteractor;
import edu.edx.yuri.photofeed.main.SessionInteractorImpl;
import edu.edx.yuri.photofeed.main.UploadInteractor;
import edu.edx.yuri.photofeed.main.UploadInteractorImpl;
import edu.edx.yuri.photofeed.main.ui.MainView;
import edu.edx.yuri.photofeed.main.ui.adapters.MainSectionsPagerAdapter;

/**
 * Created by yuri_ on 02/01/2018.
 */
@Module
public class MainModule {

    private MainView view;
    private String[] titles;
    private Fragment[] fragments;
    private FragmentManager fragmentManager;

    public MainModule(MainView view, FragmentManager fragmentManager, Fragment[] fragments, String[] titles) {
        this.view = view;
        this.titles = titles;
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
    }

    @Provides
    @Singleton
    MainView providesMainView() {
        return this.view;
    }

    @Provides @Singleton
    MainPresenter providesMainPresenter(MainView view, EventBus eventBus, UploadInteractor uploadInteractor, SessionInteractor sessionInteractor) {
        return new MainPresenterImpl(view, eventBus, uploadInteractor, sessionInteractor);
    }

    @Provides @Singleton
    UploadInteractor providesUploadInteractor(MainRepository repository) {
        return new UploadInteractorImpl(repository);
    }

    @Provides @Singleton
    SessionInteractor providesSessionInteractor(MainRepository repository) {
        return new SessionInteractorImpl(repository);
    }

    @Provides @Singleton
    MainRepository providesMainRepository(EventBus eventBus, FirebaseAPI firebase, ImageStorage imageStorage) {
        return new MainRepositoryImpl(eventBus, firebase, imageStorage);
    }

    @Provides @Singleton
    MainSectionsPagerAdapter providesAdapter(FragmentManager fm, Fragment[] fragments, String[] titles){
        return new MainSectionsPagerAdapter(fm, fragments, titles);
    }

    @Provides @Singleton
    FragmentManager providesAdapterFragmentManager(){
        return this.fragmentManager;
    }

    @Provides @Singleton
    Fragment[] providesFragmentArrayForAdapter(){
        return this.fragments;
    }

    @Provides
    @Singleton
    String[] providesStringArrayForAdapter(){
        return this.titles;
    }

}
