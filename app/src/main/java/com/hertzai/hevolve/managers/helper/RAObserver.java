package com.hertzai.hevolve.managers.helper;


import com.hertzai.hevolve.helpers.HevolveLog;
import com.hertzai.hevolve.models.retrofit.BaseApiResponse;
import com.hertzai.hevolve.models.retrofit.UploadUrlApiResponse;
import com.hertzai.hevolve.options.LOADER_STATUS;
import com.hertzai.hevolve.options.LoaderStatus;
import com.hertzai.hevolve.viewModel.HevolveBaseViewModel;

import io.reactivex.Observer;

import static com.hertzai.hevolve.options.LOADER_STATUS.LOADING;


public abstract class RAObserver<O extends BaseApiResponse> implements Observer<O> {

    private final String TAG = this.getClass().getSimpleName();
    private boolean bypassFailure = false;
    private HevolveBaseViewModel baseViewModel;

    private UploadUrlApiResponse uploadUrlApiResponse;

    public RAObserver(HevolveBaseViewModel baseViewModel) {
        this.baseViewModel = baseViewModel;
    }

    public RAObserver(HevolveBaseViewModel baseViewModel, boolean bypassFailure) {
        this.bypassFailure = bypassFailure;
        this.baseViewModel = baseViewModel;
    }


    @Override
    public void onNext(O response) {
        HevolveLog.i(TAG, "on Next called with - " + response.toString());

        LoaderStatus loaderStatus = new LoaderStatus(LOADER_STATUS.NONE);

        if (bypassFailure) {
            onSuccess(response);
            loaderStatus.setLoader(LOADER_STATUS.SUCCESS);
        } else if (response.isSuccess()) {
            if (response.equals(uploadUrlApiResponse)){
                loaderStatus.setLoader(LOADING);
            }else
                loaderStatus.setLoader(LOADER_STATUS.SUCCESS);
            onSuccess(response);
        } else {
//            baseViewModel.getErrorModelLiveData().postValue(response.getErrorModel());
            baseViewModel.checkAndDisplayError(response);
//            baseViewModel.checkResponseError();
            loaderStatus.setLoader(LOADER_STATUS.FAILED);
        }
        baseViewModel.getLoadingLiveData().postValue(loaderStatus);
    }


    @Override
    public void onError(Throwable e) {

        baseViewModel.getLoadingLiveData().postValue(new LoaderStatus(LOADER_STATUS.FAILED));
        HevolveLog.e(TAG, "on Error called with - " + e.getMessage());
        baseViewModel.checkResponseError(e);
    }

    @Override
    public void onComplete() {
        HevolveLog.d(TAG, "on complete called!");
    }

    public abstract void onSuccess(O o);

}
