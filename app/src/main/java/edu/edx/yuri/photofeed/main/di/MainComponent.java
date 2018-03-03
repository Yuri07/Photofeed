package edu.edx.yuri.photofeed.main.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.edx.yuri.photofeed.PhotoFeedApp;
import edu.edx.yuri.photofeed.PhotoFeedAppModule;
import edu.edx.yuri.photofeed.domain.di.DomainModule;
import edu.edx.yuri.photofeed.lib.di.LibsModule;
import edu.edx.yuri.photofeed.main.ui.MainActivity;

/**
 * Created by yuri_ on 03/01/2018.
 */
@Singleton
@Component(modules = {MainModule.class, DomainModule.class, LibsModule.class, PhotoFeedAppModule.class})
public interface MainComponent {

    void inject(MainActivity activity);

}
