package edu.edx.yuri.photofeed.domain;

import com.google.firebase.database.DatabaseError;

/**
 * Created by yuri_ on 28/12/2017.
 */

public interface FirebaseActionListenerCallback {

    void onSuccess();//foto removida com sucesso(metodo ou nome da classe poderia ter um nome melhor?mas não é usado só pra isso por isso tem esse nome generico, serve pra qualquer ação nos dados do firebase).
    void onError(DatabaseError error);

}
