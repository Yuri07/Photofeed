package edu.edx.yuri.photofeed.login;

import edu.edx.yuri.photofeed.login.events.LoginEvent;

/**
 * Created by yuri_ on 14/12/2017.
 */

public interface LoginPresenter {

    void onCreate();
    void onDestroy();
    void onEventMainThread(LoginEvent event);
    void login(String email, String password);
    void registerNewUser(String email, String password);

}
