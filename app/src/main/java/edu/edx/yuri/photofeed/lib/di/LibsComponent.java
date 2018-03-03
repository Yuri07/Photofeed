package edu.edx.yuri.photofeed.lib.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.edx.yuri.photofeed.PhotoFeedAppModule;

/**
 * Created by yuri_ on 28/12/2017.
 */

@Singleton
@Component(modules = {LibsModule.class, PhotoFeedAppModule.class})
public interface LibsComponent {
}
