package itz.next_step.android.ui.main.postDetail;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

import androidx.databinding.DataBindingUtil;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

import eu.davidea.flexibleadapter.databinding.BR;
import itz.next_step.android.R;
import itz.next_step.android.databinding.ActivityPostDetailBinding;
import itz.next_step.android.databinding.LayoutPostDetailFullBinding;
import itz.next_step.android.di.component.ActivityComponent;
import itz.next_step.android.ui.base.activity.BaseActivity;

public class PostDetailActivity extends BaseActivity<ActivityPostDetailBinding, PostDetailViewModel> {
    LayoutPostDetailFullBinding contentBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding.setA(this);
        viewBinding.setVm(viewModel);
        viewBinding.loadingLayout.setVisibility(View.VISIBLE);
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
            contentBinding.postDetailLayout.logo.setImageBitmap(bitmap);
        });
        viewModel.getPostDetail().observe(this, postDetail ->{

            if (contentBinding == null) {
                View inflatedView = Objects.requireNonNull(viewBinding.viewStubContent.getViewStub()).inflate();
                contentBinding = DataBindingUtil.bind(inflatedView);
                contentBinding.setVm(viewModel);

                // ✅ Setup UI sau khi binding
                contentBinding.btnBack.setOnClickListener(v -> finish());
                contentBinding.btnBackCollapsed.setOnClickListener(v -> finish());
                setupToolbar();
                customTabLayout();
            }
            contentBinding.postDetailLayout.tvJobPosition.setText(postDetail.getName());
            contentBinding.tvJobPositionCollapsed.setText(postDetail.getName());
            contentBinding.postDetailLayout.tvCompanyName.setText(postDetail.getCompany().getName());

            String salaryText = (postDetail.getMinSalary() / 1000000) + " - " + (postDetail.getMaxSalary()/1000000) + " triệu";
            contentBinding.postDetailLayout.tvSalary.setText(salaryText);

            String exp = (postDetail.getExperience())+ " năm";
            contentBinding.postDetailLayout.tvExp.setText(exp);

            viewModel.loadLogo(postDetail.getCompany().getLogo());
            viewBinding.loadingLayout.setVisibility(View.GONE);

        });
    }

    private void setupToolbar() {
        contentBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShown = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }

                if (scrollRange + verticalOffset == 0) {
                    contentBinding.toolbarCollapse.setVisibility(View.VISIBLE);
                    isShown = true;
                } else if (isShown) {
                    contentBinding.toolbarCollapse.setVisibility(View.GONE);
                    isShown = false;
                }
            }
        });
    }

    private void customTabLayout() {
        PostDetailViewPagerAdapter postDetailViewPagerAdapter = new PostDetailViewPagerAdapter(this);
        contentBinding.viewPager.setAdapter(postDetailViewPagerAdapter);

        TabLayout tabLayout = contentBinding.tabLayout;
        ViewPager2 viewPager = contentBinding.viewPager;
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
