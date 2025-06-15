package itz.next_step.android.ui.main.postDetail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import eu.davidea.flexibleadapter.databinding.BR;
import itz.next_step.android.R;
import itz.next_step.android.databinding.ActivityPostDetailBinding;
import itz.next_step.android.di.component.ActivityComponent;
import itz.next_step.android.ui.base.activity.BaseActivity;

public class PostDetailActivity extends BaseActivity<ActivityPostDetailBinding, PostDetailViewModel> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);

        viewBinding.btnBack.setOnClickListener(v-> {finish();});
        customTabLayout();

        setupToolbar();
        viewBinding.btnBackCollapsed.setOnClickListener(v-> {finish();});
        loadData();

    }

    private void loadData() {
        Long postId = getIntent().getLongExtra("post_id", -1);

        if (postId != -1) {
            viewModel.fetchPostDetail(postId);
        } else {
            finish();
        }
        viewModel.logoLiveData.observe(this, bitmap -> {
            viewBinding.logo.setImageBitmap(bitmap);
        });
        viewModel.getPostDetail().observe(this, postDetail ->{
            viewBinding.tvJobPosition.setText(postDetail.getName());
            viewBinding.tvJobPositionCollapsed.setText(postDetail.getName());
            viewBinding.tvCompanyName.setText(postDetail.getCompany().getName());

            String salaryText = (postDetail.getMinSalary() / 1000000) + " - " + (postDetail.getMaxSalary()/1000000) + " triệu";
            viewBinding.tvSalary.setText(salaryText);

            String exp = (postDetail.getExperience())+ " năm";
            viewBinding.tvExp.setText(exp);

            viewModel.loadLogo(postDetail.getCompany().getLogo());

        });
    }

    private void setupToolbar() {
        viewBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShown = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (scrollRange + verticalOffset == 0) {
                    viewBinding.toolbarCollapse.setVisibility(View.VISIBLE);
                    isShown = true;
                } else if (isShown) {
                    viewBinding.toolbarCollapse.setVisibility(View.GONE);
                    isShown = false;
                }
            }
        });
    }

    private void customTabLayout() {
        PostDetailViewPagerAdapter postDetailViewPagerAdapter = new PostDetailViewPagerAdapter(this);
        viewBinding.viewPager.setAdapter(postDetailViewPagerAdapter);

        TabLayout tabLayout = viewBinding.tabLayout;
        ViewPager2 viewPager = viewBinding.viewPager;
        viewPager.setUserInputEnabled(false);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Thông tin");
            } else if (position == 1){
                tab.setText("Công ty");
            }else {
                tab.setText("Mức độ cạnh tranh");
            }
        }).attach();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_post_detail;
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
