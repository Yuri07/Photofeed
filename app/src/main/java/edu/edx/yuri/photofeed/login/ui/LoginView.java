package edu.edx.yuri.photofeed.login.ui;

/**
 * Created by yuri_ on 14/12/2017.
 */

public interface LoginView {

    void enableInputs();
    void disableInputs();
    void showProgress();
    void hideProgress();

    void handleSignUp();
    void handleSignIn();

    void newUserSuccess();
    void navigateToMainScreen();
    void setUserEmail(String email);

    void loginError(String error);
    void newUserError(String error);

}
