package com.tec.ine.utils;


public interface CBSuccess<T> {
    void onResponse(boolean success, T result);
}
