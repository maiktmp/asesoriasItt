package com.maik.greenhouse.callbacks;


public interface CBSuccess<T> {
    void onResponse(boolean success, T result);
}
