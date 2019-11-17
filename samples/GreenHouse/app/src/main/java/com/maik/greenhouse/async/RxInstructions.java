package com.maik.greenhouse.async;

import android.annotation.SuppressLint;
import android.util.Log;
import com.maik.greenhouse.callbacks.CBDone;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class RxInstructions {


    private static String TAG = "RxInstructions";

    @SuppressLint("CheckResult")
    public static void completable(Action roomInstruction, CBDone cb) {
        Completable
                .fromAction(roomInstruction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cb::done, throwable -> Log.e(
                        TAG,
                        "Error: " + throwable.getMessage()
                ));

    }

    public static Single single(final Callable roomInstruction) {
        return Single
                .fromCallable(roomInstruction)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
