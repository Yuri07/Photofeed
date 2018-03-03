package edu.edx.yuri.photofeed.domain.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.edx.yuri.photofeed.PhotoFeedAppModule;

/**
 * Created by yuri_ on 28/12/2017.
 */

@Singleton
@Component(modules = {DomainModule.class, PhotoFeedAppModule.class})
public interface DomainComponent {
}
