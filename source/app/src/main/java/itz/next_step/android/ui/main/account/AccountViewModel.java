package itz.next_step.android.ui.main.account;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.io.InputStream;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import itz.next_step.android.MVVMApplication;
import itz.next_step.android.data.Repository;
import itz.next_step.android.ui.base.fragment.BaseFragmentViewModel;
import itz.next_step.android.utils.ImageUtils;
import itz.next_step.android.utils.NetworkUtils;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import timber.log.Timber;

public class AccountViewModel extends BaseFragmentViewModel {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    MutableLiveData<Bitmap> avatarLiveData = new MutableLiveData<>();

    public final MutableLiveData<String> name = new MutableLiveData<>("");
    public final MutableLiveData<String> code = new MutableLiveData<>("");

    public AccountViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
        fetchProfile();
    }

    public void fetchProfile() {
        showLoading();
        compositeDisposable.add(repository.getApiService().getCandidateProfile()
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
                            name.setValue(response.getData().getAccount().getFullName());
                            code.setValue(response.getData().getCode());
                            loadAvatar(response.getData().getAccount().getAvatar());
                        }, throwable -> {
                            hideLoading();
                            Timber.e(throwable);
                            if (throwable instanceof HttpException && ((HttpException) throwable).code() == 400) {
                                HttpException httpException = (HttpException) throwable;
                                if (httpException.code() == 400) {
                                }
                            }
                        }));
    }

    public void loadAvatar(String url) {
        compositeDisposable.add(repository.getApiService().loadImage(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody response) throws Exception {
                        InputStream inputStream = response.byteStream();
                        Bitmap bitmap = ImageUtils.getBitmap(inputStream);
                        if (bitmap != null) {
                            avatarLiveData.setValue(bitmap);
                        } else {
                            Log.e("ProfileViewModel", "Lỗi: Bitmap rỗng");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("ProfileViewModel", "Lỗi khi tải ảnh: " + throwable.getMessage());
                    }
                })
        );
    }
    public void logout() {
        showLoading();
        repository.getSharedPreferences().clearAuthData();
    }
}
