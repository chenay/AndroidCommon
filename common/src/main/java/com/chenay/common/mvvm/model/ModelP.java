//package com.chenay.common.mvvm.model;
//
//import android.arch.lifecycle.ViewModelProviders;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//
//import java.util.Map;
//import java.util.function.Consumer;
//
//import io.reactivex.Observable;
//import io.reactivex.ObservableEmitter;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.disposables.CompositeDisposable;
//
//import static io.reactivex.internal.util.NotificationLite.disposable;
//
//public abstract class ModelP<T, R> implements IBaseModelP<T> {
//    //判断是否要显示和隐藏加载进度
//    public static final int IS_NOT_NEED_SHOW_DIALOG = -1;
//    //加载更多数据
//    public static final int IS_LOADING_MORE_DATA = 2;
//
//    private CompositeDisposable mDisposable;
//    private R mRequest;
//
//    private BaseViewModel<T> mBaseViewModel;
//    private Map<String, Object> map;
//    private String url;
//    private int flag;
//
//
//    public ModelP(Fragment fragment, Class<? extends BaseViewModel<T>> clazz) {
//
//        mBaseViewModel = ViewModelProviders.of(fragment).get(clazz);
//        registerObserver(mBaseViewModel, fragment, null);
//
//        NetWorkManager.getInstance().getRequestManagerRetriever().get(fragment.getActivity(), this);
//        mRequest = NetWorkManager.getRetrofit().create(getClazz());
//    }
//
//    public ModelP(FragmentActivity activity, Class<? extends BaseViewModel<T>> clazz) {
//
//        mBaseViewModel = ViewModelProviders.of(activity).get(clazz);
//        registerObserver(mBaseViewModel, null, activity);
//
//        NetWorkManager.getInstance().getRequestManagerRetriever().get(activity, this);
//        mRequest = NetWorkManager.getRetrofit().create(getClazz());
//    }
//
//
//    private void sendRequestToServer(R request, Observable<T> netObservable, int flag) {
//        Observable<T> cacheObservable = Observable.create(new ObservableOnSubscribe<T>() {
//            @Override
//            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
//
//                handlerFirstObservable(emitter, request);
//            }
//        });
//
//        Observable<T> concat = Observable.concat(cacheObservable, netObservable);
//
//
//        disposable(concat.retryWhen(new RetryWithDelay(5, 1000))
//                .compose(ResultTransformer.handleResult())
//                .compose(SchedulerProvider.getInstance().applySchedulers())
//                .subscribe(new Consumer<T>() {
//                    @Override
//                    public void accept(T t) throws Exception {
//                        if (flag == ModelP.IS_LOADING_MORE_DATA) {
//                            accessMoreSuccess(t, flag);
//                        } else {
//                            accessSucceed(t, flag);
//                        }
//                        hanlerDataRequestSuccess(t);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        if (throwable instanceof ApiException) {
//                            ApiException exception = (ApiException) throwable;
//                            accessError(exception.getCode(), (exception).getDisplayMessage(), flag);
//                        }
//                    }
//                }));
//    }
//
//
//}