package itz.next_step.android.ui.main.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import itz.next_step.android.BR;
import itz.next_step.android.R;
import itz.next_step.android.data.model.api.response.company.CompanyResponse;
import itz.next_step.android.data.model.api.response.post.PostClientListResponse;
import itz.next_step.android.databinding.FragmentHomeBinding;
import itz.next_step.android.di.component.FragmentComponent;
import itz.next_step.android.ui.base.fragment.BaseFragment;
import itz.next_step.android.ui.main.postDetail.PostDetailActivity;
import itz.next_step.android.ui.main.search.SearchActivity;

public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        performDataBinding();

        customBtnSearch();
        binding.searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                Intent intent = new Intent(requireContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        loadJobs();

        return binding.getRoot();
    }

    private void loadJobs() {
        viewModel.fetchPostList();
        viewModel.getPostList().observe(getViewLifecycleOwner(), postClientListResponses -> {

            if(postClientListResponses != null){
                viewModel.searchJob("");
            }
        });
        viewModel.getFilteredPostList().observe(getViewLifecycleOwner(), filteredList -> {
            if (filteredList == null || filteredList.isEmpty()) return;

            List<List<PostClientListResponse<CompanyResponse>>> pages = new ArrayList<>();
            for (int i = 0; i < filteredList.size(); i += 4) {
                pages.add(filteredList.subList(i, Math.min(i + 4, filteredList.size())));
            }

            PostsPagerAdapter postsPagerAdapter = new PostsPagerAdapter(pages, viewModel, postId -> {
                showProgressbar("Đang tải chi tiết...");

                Intent intent = new Intent(requireContext(), PostDetailActivity.class);
                intent.putExtra("post_id", postId);
                startActivity(intent);
            });
            binding.viewPager.setAdapter(postsPagerAdapter);

            setupIndicator(pages.size());
            setCurrentIndicator(0);

            binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    setCurrentIndicator(position);
                }
            });
        });
    }

    private void customBtnSearch() {
        View searchPlate = binding.searchView.findViewById(androidx.appcompat.R.id.search_plate);
        if (searchPlate != null) {
            searchPlate.setBackground(null);
        }
        ImageView searchIcon = binding.searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        searchIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.bg_btn));

    }

    private void setupIndicator(int count) {
        binding.indicatorLayout.removeAllViews();
        for (int i = 0; i < count; i++) {
            View dot = new View(requireContext());
            int size = (int) getResources().getDimension(R.dimen._8sdp);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            dot.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.indicator_unactive));
            binding.indicatorLayout.addView(dot);
        }
    }


    private void setCurrentIndicator(int index) {
        int childCount = binding.indicatorLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View dot = binding.indicatorLayout.getChildAt(i);
            int drawableId = (i == index)
                    ? R.drawable.indicator_active
                    : R.drawable.indicator_unactive;
            dot.setBackground(ContextCompat.getDrawable(requireContext(), drawableId));
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        hideProgress();
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);

    }
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }
    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}
