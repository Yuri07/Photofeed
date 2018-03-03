package edu.edx.yuri.photofeed.photomap.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.edx.yuri.photofeed.PhotoFeedAppModule;
import edu.edx.yuri.photofeed.domain.di.DomainModule;
import edu.edx.yuri.photofeed.lib.di.LibsModule;
import edu.edx.yuri.photofeed.photomap.ui.PhotoMapFragment;

/**
 * Created by yuri_ on 04/01/2018.
 */

@Singleton
@Component(modules = {PhotoMapModule.class, DomainModule.class, LibsModule.class, PhotoFeedAppModule.class})
public interface PhotoMapComponent {

    void inject(PhotoMapFragment fragment);

}
