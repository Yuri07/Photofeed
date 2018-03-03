package edu.edx.yuri.photofeed.login;

/**
 * Created by yuri_ on 27/12/2017.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private LoginRepository loginRepository;

    public LoginInteractorImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public void execute(String email, String password) {
        loginRepository.signIn(email, password);
    }
}
