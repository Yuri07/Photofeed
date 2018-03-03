package edu.edx.yuri.photofeed;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import edu.edx.yuri.photofeed.domain.di.DomainModule;
import edu.edx.yuri.photofeed.lib.di.LibsModule;
import edu.edx.yuri.photofeed.login.di.DaggerLoginComponent;
import edu.edx.yuri.photofeed.login.di.LoginComponent;
import edu.edx.yuri.photofeed.login.di.LoginModule;
import edu.edx.yuri.photofeed.login.ui.LoginView;
import edu.edx.yuri.photofeed.main.di.DaggerMainComponent;
import edu.edx.yuri.photofeed.main.di.MainComponent;
import edu.edx.yuri.photofeed.main.di.MainModule;
import edu.edx.yuri.photofeed.main.ui.MainView;
import edu.edx.yuri.photofeed.photolist.di.DaggerPhotoListComponent;
import edu.edx.yuri.photofeed.photolist.di.PhotoListComponent;
import edu.edx.yuri.photofeed.photolist.di.PhotoListModule;
import edu.edx.yuri.photofeed.photolist.ui.PhotoListView;
import edu.edx.yuri.photofeed.photolist.ui.adapters.OnItemClickListener;
import edu.edx.yuri.photofeed.photomap.di.DaggerPhotoMapComponent;
import edu.edx.yuri.photofeed.photomap.di.PhotoMapComponent;
import edu.edx.yuri.photofeed.photomap.di.PhotoMapModule;
import edu.edx.yuri.photofeed.photomap.ui.PhotoMapView;

/**
 * Created by yuri_ on 13/12/2017.
 */

public class PhotoFeedApp extends Application {

    private final static String EMAIL_KEY = "email";
    private LibsModule libsModule;
    private DomainModule domainModule;
    private PhotoFeedAppModule photoFeedAppModule;

    @Override
    public void onCreate() {
        super.onCreate();
        //initFirebase();
        initModules();
    }

    private void initModules() {
        libsModule = new LibsModule();
        domainModule = new DomainModule();
        photoFeedAppModule = new  PhotoFeedAppModule(this);
    }

    /*private void initFirebase() {
        Firebase.setAndroidContext(this);
    }*/

    public static String getEmailKey() {
        return EMAIL_KEY;
    }

    public LoginComponent getLoginComponent(LoginView view) {
        return DaggerLoginComponent
                .builder()
                .photoFeedAppModule(photoFeedAppModule)
                .domainModule(domainModule)
                .libsModule(libsModule)
                .loginModule(new LoginModule(view))
                .build();
    }

    public MainComponent getMainComponent(MainView view, FragmentManager manager, Fragment[]fragments, String[] titles) {
        return DaggerMainComponent
                .builder()
                .photoFeedAppModule(photoFeedAppModule)
                .domainModule(domainModule)
                .libsModule(libsModule)
                .mainModule(new MainModule(view, manager, fragments, titles))
                .build();
    }

    public PhotoListComponent getPhotoListComponent(Fragment fragment, PhotoListView view, OnItemClickListener onItemClickListener) {
        libsModule.setFragment(fragment);

        return DaggerPhotoListComponent
                .builder()
                .photoFeedAppModule(photoFeedAppModule)
                .domainModule(domainModule)
                .libsModule(libsModule)
                .photoListModule(new PhotoListModule(view, onItemClickListener))
                .build();

    }

    public PhotoMapComponent getPhotoMapComponent(Fragment fragment, PhotoMapView view) {
        libsModule.setFragment(fragment);

        return DaggerPhotoMapComponent
                .builder()
                .photoFeedAppModule(photoFeedAppModule)
                .domainModule(domainModule)
                .libsModule(libsModule)
                .photoMapModule(new PhotoMapModule(view))
                .build();

    }

}
