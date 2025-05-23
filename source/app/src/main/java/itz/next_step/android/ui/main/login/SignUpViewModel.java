package itz.next_step.android.ui.main.login;

import android.content.Intent;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import itz.next_step.android.MVVMApplication;
import itz.next_step.android.R;
import itz.next_step.android.data.Repository;
import itz.next_step.android.data.model.api.request.candidate.CandidateSignUpRequest;
import itz.next_step.android.ui.base.activity.BaseViewModel;
import itz.next_step.android.utils.NetworkUtils;
import retrofit2.HttpException;
import timber.log.Timber;

public class SignUpViewModel extends BaseViewModel {
    public SignUpViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void signUpCandidate(CandidateSignUpRequest request) {
        showLoading();
        compositeDisposable.add(repository.getApiService().signUpCandidate(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(throwable ->
                        throwable.flatMap((Function<Throwable, ObservableSource<?>>) throwable1 -> {
                            if (NetworkUtils.checkNetworkError(throwable1)) {
                                hideLoading();
                                return application.showDialogNoInternetAccess();
                            } else {
                                return Observable.error(throwable1);
                            }
                        })
                )
                .subscribe(
                        response -> {
                            hideLoading();
                            application.getCurrentActivity().finish();
                        }, throwable -> {
                            hideLoading();
                            Timber.e(throwable);
                            if (throwable instanceof HttpException && ((HttpException) throwable).code() == 400) {
                                HttpException httpException = (HttpException) throwable;
                                if (httpException.code() == 400) {
                                }
                            }
                            showNormalMessage(getApplication().getString(R.string.signup_un_success));
                        }));
    }
}
