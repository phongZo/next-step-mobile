package itz.next_step.android.ui.main.postDetail;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import itz.next_step.android.MVVMApplication;
import itz.next_step.android.data.Repository;
import itz.next_step.android.data.model.api.ResponseListObj;
import itz.next_step.android.data.model.api.response.company.CompanyResponse;
import itz.next_step.android.data.model.api.response.post.PostClientListResponse;
import itz.next_step.android.ui.base.activity.BaseViewModel;
import itz.next_step.android.utils.ImageUtils;
import itz.next_step.android.utils.NetworkUtils;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class PostDetailViewModel extends BaseViewModel {
    MutableLiveData<Bitmap> logoLiveData = new MutableLiveData<>();
    private final MutableLiveData<PostClientListResponse<CompanyResponse>> postDetail = new MutableLiveData<>();
    public LiveData<PostClientListResponse<CompanyResponse>> getPostDetail() {
        return postDetail;
    }

    public PostDetailViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }

    public void fetchPostDetail(Long postId){
        showLoading();
        compositeDisposable.add(repository.getApiService().getPostById(postId)
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
                            if (response != null && response.getData() != null) {
                                postDetail.setValue(response.getData());
                            }
                            hideLoading();
                        }, throwable -> {
                            hideLoading();
                            Timber.e(throwable);
                        }));
    }
    public void loadLogo(String url){
        compositeDisposable.add(repository.getApiService().loadImage(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody response) throws Exception {
                        InputStream inputStream = response.byteStream();
                        Bitmap bitmap = ImageUtils.getBitmap(inputStream);
                        if (bitmap != null) {
                            logoLiveData.setValue(bitmap);
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
}
