package itz.next_step.android.ui.main.home;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import itz.next_step.android.MVVMApplication;
import itz.next_step.android.data.Repository;
import itz.next_step.android.data.model.api.ResponseListObj;
import itz.next_step.android.data.model.api.response.company.CompanyResponse;
import itz.next_step.android.data.model.api.response.post.PostClientListResponse;
import itz.next_step.android.ui.base.fragment.BaseFragmentViewModel;
import itz.next_step.android.utils.ImageUtils;
import itz.next_step.android.utils.NetworkUtils;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class HomeViewModel extends BaseFragmentViewModel {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<List<PostClientListResponse<CompanyResponse>>> postsLiveData = new MutableLiveData<>();
    public LiveData<List<PostClientListResponse<CompanyResponse>>> getPostList() {
        return postsLiveData;
    }

    private final MutableLiveData<List<PostClientListResponse<CompanyResponse>>> filteredPostList = new MutableLiveData<>();
    public LiveData<List<PostClientListResponse<CompanyResponse>>> getFilteredPostList() {
        return filteredPostList;
    }
    public HomeViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
        fetchPostList();
    }

    public void fetchPostList(){
        showLoading();
        compositeDisposable.add(repository.getApiService().getPost()
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
                                ResponseListObj<PostClientListResponse<CompanyResponse>> responseListObj = response.getData();

                                List<PostClientListResponse<CompanyResponse>> posts = responseListObj.getContent();
                                postsLiveData.setValue(posts);
                            }
                            hideLoading();
                        }, throwable -> {
                            hideLoading();
                            Timber.e(throwable);
                        }));
    }

    public void searchJob(String query) {
        compositeDisposable.add(
            io.reactivex.rxjava3.core.Single.fromCallable(() -> {
                List<PostClientListResponse<CompanyResponse>> fullList = postsLiveData.getValue();
                if (fullList == null) return new ArrayList<PostClientListResponse<CompanyResponse>>();

                List<PostClientListResponse<CompanyResponse>> filteredList = new ArrayList<>();
                if (query != null && !query.trim().isEmpty()) {
                    String lowerQuery = query.toLowerCase();
                    for (PostClientListResponse<CompanyResponse> post : fullList) {
                        boolean matchCompany = post.getCompany().getName() != null && post.getCompany().getName().toLowerCase().contains(lowerQuery);
                        boolean matchJob = post.getName() != null && post.getName().toLowerCase().contains(lowerQuery);

                        if (matchCompany || matchJob) {
                            filteredList.add(post);
                        }
                    }
                } else {
                    filteredList.addAll(fullList);
                }
                return filteredList;
            })
            .subscribeOn(Schedulers.computation()) // chạy ở thread khác
            .observeOn(AndroidSchedulers.mainThread()) // trả kết quả về UI
            .subscribe(
                    filtered -> filteredPostList.setValue(filtered),
                    error -> Log.e("searchJob", "Lỗi khi lọc công việc: " + error.getMessage())
            )
        );
    }

    public void loadLogo(String url, MutableLiveData<Bitmap> logo){
        compositeDisposable.add(repository.getApiService().loadImage(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody response) throws Exception {
                        InputStream inputStream = response.byteStream();
                        Bitmap bitmap = ImageUtils.getBitmap(inputStream);
                        if (bitmap != null) {
                            logo.setValue(bitmap);
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
