package itz.next_step.android.ui.main.account;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;

import eu.davidea.flexibleadapter.databinding.BR;
import itz.next_step.android.R;
import itz.next_step.android.databinding.FragmentAccountBinding;
import itz.next_step.android.di.component.FragmentComponent;
import itz.next_step.android.ui.base.fragment.BaseFragment;
import itz.next_step.android.ui.main.MainActivity;
import itz.next_step.android.utils.DialogConfirmUtils;

public class AccountFragment extends BaseFragment<FragmentAccountBinding, AccountViewModel> {
    private int originalWidth = 0;
    private int originalHeight = 0;


    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        viewModel.avatarLiveData.observe(getViewLifecycleOwner(), bitmap -> {
            if (bitmap != null) {
                binding.ivAvatar.setImageBitmap(bitmap);
            }
        });
        setScroll();

    }

    private void setScroll() {
        AppBarLayout appBarLayout = binding.appbar;
        View avatarContainer = binding.avatarContainer;
        TextView userCode = binding.code;
        View header = binding.header;
        avatarContainer.post(new Runnable() {
            @Override
            public void run() {
                originalWidth = avatarContainer.getWidth();
                originalHeight = avatarContainer.getHeight();
            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isCollapsed = false;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int totalScrollRange = appBarLayout.getTotalScrollRange();
                if (totalScrollRange == 0) return;
                float progress = Math.abs(verticalOffset * 1.0f / totalScrollRange);
                if (originalWidth == 0 || originalHeight == 0) return;

                float scale = 1f - progress * 0.7f;

                ViewGroup.LayoutParams params = avatarContainer.getLayoutParams();
                params.width = (int) (originalWidth * scale);
                params.height = (int) (originalHeight * scale);
                avatarContainer.setLayoutParams(params);
                if ((1f - progress * 2f) <= 0f) {
                    userCode.setVisibility(View.GONE);
                } else {
                    userCode.setVisibility(View.VISIBLE);
                    userCode.setAlpha(1f - progress * 2f);
                }

                if (progress >= 1f && !isCollapsed) {
                    appBarLayout.setBackgroundResource(R.color.bg_app);
                    isCollapsed = true;
                } else if (progress < 1f && isCollapsed) {
                    appBarLayout.setBackgroundResource(R.drawable.bg_profile);
                    isCollapsed = false;
                }

                int originalMargin = getResources().getDimensionPixelSize(R.dimen._12sdp);
                int newMargin = (int) (originalMargin * (1 - progress));

                // Cập nhật margin
                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) header.getLayoutParams();
                lp.setMargins(newMargin, lp.topMargin, newMargin, lp.bottomMargin);
                header.setLayoutParams(lp);
            }
        });
    }
    public void onLogoutClick() {
        DialogConfirmUtils.showConfirmDialog(
                getContext(),
                "Bạn chắc chắn muốn đăng xuất?",
                "Xác nhận",
                "Hủy",
                () -> {
                    viewModel.logout();
                    if (getActivity() instanceof MainActivity) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.handleFragment("HOME");
                        mainActivity.setBottomNavSelected(R.id.home);
                        viewModel.hideLoading();
                    }
                }
        );
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account;
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}
