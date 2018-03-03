package edu.edx.yuri.photofeed.login.di;

import javax.inject.Singleton;

import dagger.Component;
import edu.edx.yuri.photofeed.PhotoFeedAppModule;
import edu.edx.yuri.photofeed.domain.di.DomainModule;
import edu.edx.yuri.photofeed.lib.di.LibsModule;
import edu.edx.yuri.photofeed.login.ui.LoginActivity;

/**
 * Created by yuri_ on 27/12/2017.
 */

@Singleton
@Component(modules = {LoginModule.class, DomainModule.class, LibsModule.class, PhotoFeedAppModule.class})
public interface LoginComponent {
    void inject(LoginActivity activity);
}
