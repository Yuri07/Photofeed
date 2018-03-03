package edu.edx.yuri.photofeed.login.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.edx.yuri.photofeed.domain.FirebaseAPI;
import edu.edx.yuri.photofeed.lib.base.EventBus;
import edu.edx.yuri.photofeed.login.LoginInteractor;
import edu.edx.yuri.photofeed.login.LoginInteractorImpl;
import edu.edx.yuri.photofeed.login.LoginPresenter;
import edu.edx.yuri.photofeed.login.LoginPresenterImpl;
import edu.edx.yuri.photofeed.login.LoginRepository;
import edu.edx.yuri.photofeed.login.LoginRepositoryImpl;
import edu.edx.yuri.photofeed.login.SignupInteractor;
import edu.edx.yuri.photofeed.login.SignupInteractorImpl;
import edu.edx.yuri.photofeed.login.ui.LoginView;

/**
 * Created by yuri_ on 27/12/2017.
 */

@Module
public class LoginModule {

    LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    LoginView providesLoginView(){
        return this.view;
    }

    @Provides
    @Singleton
    LoginPresenter providesLoginPresenter(EventBus eventBus, LoginView loginView, LoginInteractor loginInteractor, SignupInteractor signupInteractor) {
        return new LoginPresenterImpl(eventBus, loginView, loginInteractor, signupInteractor);
    }

    @Provides @Singleton
    LoginInteractor providesLoginInteractor(LoginRepository repository) {
        return new LoginInteractorImpl(repository);
    }

    @Provides @Singleton
    SignupInteractor providesSignupInteractor(LoginRepository repository) {
        return new SignupInteractorImpl(repository);
    }

    @Provides @Singleton
    LoginRepository providesLoginRepository(FirebaseAPI firebase, EventBus eventBus) {
        return new LoginRepositoryImpl(firebase, eventBus);
    }

}
