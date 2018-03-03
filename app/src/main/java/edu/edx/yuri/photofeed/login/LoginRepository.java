package edu.edx.yuri.photofeed.login;

/**
 * Created by yuri_ on 14/12/2017.
 */

public interface LoginRepository {

    void signUp(final String email, final String password);
    void signIn(String email, String password);

}
