package com.omkar.mhatre.popularmovies.AsynLoader;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AsyncLoader {

    public static void load(final SubscriptionBlock subscriptionBlock, final OnCompletionBlock completionBlock){

        Observable.just(subscriptionBlock)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SubscriptionBlock>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        try {
                            subscriptionBlock.subscribe();
                        } catch (Exception e) {
                            completionBlock.onComplete(e);
                        }
                        completionBlock.onComplete(null);
                    }

                    @Override
                    public void onNext(SubscriptionBlock subscriptionBlock) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        completionBlock.onComplete(new Exception(e));
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }


    public static void load(SubscriptionBlock subscriptionBlock) {

        Observable.just(subscriptionBlock)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SubscriptionBlock>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        try {
                            subscriptionBlock.subscribe();
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void onNext(SubscriptionBlock subscriptionBlock) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}

