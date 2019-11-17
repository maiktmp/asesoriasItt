package com.pitt.asesoriasitt.interactors;

import com.pitt.asesoriasitt.api.APIAsesorias;
import com.pitt.asesoriasitt.api.GenericResponse;
import com.pitt.asesoriasitt.db.models.Advisory;
import com.pitt.asesoriasitt.db.models.Rol;
import com.pitt.asesoriasitt.db.models.User;
import com.pitt.asesoriasitt.utils.callbacks.CBGeneric;

import java.util.ArrayList;

public class UserInteractor {

    private APIAsesorias api;

    private static UserInteractor instance;

    private UserInteractor() {
        api = APIAsesorias.getInstance();
    }

    public static UserInteractor getInstance() {
        if (instance == null) {
            instance = new UserInteractor();
        }
        return instance;
    }

    public void getAllUserByRol(CBGeneric<ArrayList<User>> cb, long rol) {
        api.getAllUserType(rol, (success, data) -> {
            if (!success) {
                data = new ArrayList<User>();
            }
            cb.onResult(data);
        });
    }

    public void login(String username, String password, CBGeneric<GenericResponse<User>> cb) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        api.login(user, ((success, result) -> {
            if (!success) {
                cb.onResult(null);
                return;
            }
            cb.onResult(result);
        }));
    }

    public void getUserDetail(long userId, CBGeneric<User> cb) {
        api.getUserDetail(userId, ((success, result) -> {
            if (!success) {
                cb.onResult(null);
                return;
            }
            cb.onResult(result);
        }));
    }

    public void createAdvisory(long userId, Advisory advisory, CBGeneric<GenericResponse> cb) {
        api.createAdvisory(userId, advisory, ((success, result) -> {
            if (!success) {
                cb.onResult(null);
                return;
            }
            cb.onResult(result);
        }));
    }

    public void updateUser(User user, CBGeneric<GenericResponse> cb) {
        api.updateUser(user, ((success, result) -> {
            if (!success) {
                cb.onResult(null);
                return;
            }
            cb.onResult(result);
        }));
    }

}
