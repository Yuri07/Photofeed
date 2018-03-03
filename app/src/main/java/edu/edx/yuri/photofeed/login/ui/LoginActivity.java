package edu.edx.yuri.photofeed.login.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.edx.yuri.photofeed.PhotoFeedApp;
import edu.edx.yuri.photofeed.R;
import edu.edx.yuri.photofeed.login.LoginPresenter;
import edu.edx.yuri.photofeed.login.di.LoginComponent;
import edu.edx.yuri.photofeed.main.ui.MainActivity;

public class LoginActivity extends AppCompatActivity implements LoginView{

    @BindView(R.id.editTxtEmail)
    EditText editTxtEmail;
    @BindView(R.id.editTxtPassword)
    EditText editTxtPassword;
    @BindView(R.id.btnSignin)
    Button btnSignin;
    @BindView(R.id.btnSignup)
    Button btnSignup;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.layoutMainContainer)
    RelativeLayout layoutMainContainer;

    @Inject
    LoginPresenter presenter;

    @Inject
    SharedPreferences sharedPreferences;

    private PhotoFeedApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        app = (PhotoFeedApp) getApplication();

        setupInjection();
        presenter.onCreate();
        presenter.login(null,null);

    }

    private void setupInjection(){
        LoginComponent loginComponent = app.getLoginComponent(this);
        loginComponent.inject(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    /*@OnClick({R.id.btnSignin, R.id.btnSignup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSignin:
                presenter.login(editTxtEmail.getText().toString(),
                        editTxtPassword.getText().toString());
                break;
            case R.id.btnSignup:
                presenter.registerNewUser(editTxtEmail.getText().toString(),
                        editTxtPassword.getText().toString());
                break;
        }
    }*/

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    @OnClick(R.id.btnSignup)
    public void handleSignUp() {
        presenter.registerNewUser(editTxtEmail.getText().toString(),
                editTxtPassword.getText().toString());
    }

    @Override
    @OnClick(R.id.btnSignin)
    public void handleSignIn() {
        presenter.login(editTxtEmail.getText().toString(),
                editTxtPassword.getText().toString());
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(layoutMainContainer, R.string.login_notice_message_useradded, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToMainScreen() {
        //startActivity(new Intent(this, MainActivity.class));
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void setUserEmail(String email) {
        if (email != null) {
            String emailKey = app.getEmailKey();
            sharedPreferences.edit().putString(emailKey, email).apply();//.commit();//commit() e o que tem no codigo original lesson4.edx
            //sharedPreferences.edit().put
        }
    }

    @Override
    public void loginError(String error) {
        editTxtPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signin), error);
        editTxtPassword.setError(msgError);
    }

    @Override
    public void newUserError(String error) {
        editTxtPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signup), error);
        editTxtPassword.setError(msgError);
    }

    private void setInputs(boolean enabled){
        btnSignin.setEnabled(enabled);
        btnSignup.setEnabled(enabled);
        editTxtEmail.setEnabled(enabled);
        editTxtPassword.setEnabled(enabled);
    }

}
