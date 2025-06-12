package itz.next_step.android.ui.main.search;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import eu.davidea.flexibleadapter.databinding.BR;
import itz.next_step.android.R;
import itz.next_step.android.data.model.api.response.company.CompanyResponse;
import itz.next_step.android.data.model.api.response.post.PostClientListResponse;
import itz.next_step.android.databinding.ActivitySearchBinding;
import itz.next_step.android.di.component.ActivityComponent;
import itz.next_step.android.ui.base.activity.BaseActivity;
import itz.next_step.android.ui.main.home.PostsAdapter;

public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchViewModel> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);

        setupBtnSearch();
        viewBinding.toolbar.setNavigationOnClickListener(v -> finish());
        setupBtnRecycleView();

    }
    void setupBtnRecycleView(){
        SearchAdapter searchAdapter = new SearchAdapter(viewModel);
        viewBinding.rcvSearch.setLayoutManager(new LinearLayoutManager(this));
        viewBinding.rcvSearch.setAdapter(searchAdapter);

        viewModel.fetchPostList();
        viewModel.getFilteredPostList().observe(this, new Observer<List<PostClientListResponse<CompanyResponse>>>() {
            @Override
            public void onChanged(List<PostClientListResponse<CompanyResponse>> postClientListResponses) {
                searchAdapter.setData(postClientListResponses);

            }
        });

        viewModel.getPostList().observe(this, postClientListResponses -> {

            if(postClientListResponses != null && !postClientListResponses.isEmpty()){
                viewModel.searchJob("");
            }
        });
    }
    void setupBtnSearch(){
        customBtnSearch();
        viewBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private final Handler handler = new Handler();
            private Runnable searchRunnable;
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchJob(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable);
                }

                searchRunnable = () -> viewModel.searchJob(newText);

                handler.postDelayed(searchRunnable, 500);

                return true;
            }
        });
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v != null) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void customBtnSearch(){
        View searchPlate = viewBinding.searchView.findViewById(androidx.appcompat.R.id.search_plate);
        if (searchPlate != null) {
            searchPlate.setBackground(null);
        }
        ImageView searchIcon = viewBinding.searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        searchIcon.setColorFilter(ContextCompat.getColor(this, R.color.bg_btn));

        viewBinding.searchView.requestFocus();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    public void performDependencyInjection(ActivityComponent buildComponent) {
        buildComponent.inject(this);
    }
}
