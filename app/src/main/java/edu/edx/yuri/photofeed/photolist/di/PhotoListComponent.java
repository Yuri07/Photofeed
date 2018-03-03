package edu.edx.yuri.photofeed.photolist.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.edx.yuri.photofeed.PhotoFeedAppModule;
import edu.edx.yuri.photofeed.domain.di.DomainModule;
import edu.edx.yuri.photofeed.lib.di.LibsModule;
import edu.edx.yuri.photofeed.photolist.ui.PhotoListFragment;

/**
 * Created by yuri_ on 04/01/2018.
 */

@Singleton
@Component(modules = {PhotoListModule.class, DomainModule.class, LibsModule.class, PhotoFeedAppModule.class})
public interface PhotoListComponent {

    void inject(PhotoListFragment fragment);

}
