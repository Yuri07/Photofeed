package edu.edx.yuri.photofeed.login;

/**
 * Created by yuri_ on 27/12/2017.
 */

public class SignupInteractorImpl implements SignupInteractor {

    private LoginRepository loginRepository;

    public SignupInteractorImpl(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public void execute(String email, String password) {
        loginRepository.signUp(email, password);
    }
}
