package itz.next_step.android.ui.main.postDetail;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import eu.davidea.flexibleadapter.databinding.BR;
import itz.next_step.android.R;
import itz.next_step.android.databinding.FragmentCompanyBinding;
import itz.next_step.android.di.component.FragmentComponent;
import itz.next_step.android.ui.base.fragment.BaseFragment;


public class CompanyFragment extends BaseFragment<FragmentCompanyBinding, CompanyViewModel> {
    private PostDetailViewModel activityViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(CompanyViewModel.class);

        activityViewModel = new ViewModelProvider(requireActivity()).get(PostDetailViewModel.class);

        activityViewModel.getPostDetail().observe(getViewLifecycleOwner(), postDetail -> {
            if (postDetail != null) {
                binding.descriptionCompany.setText(postDetail.getCompany().getDescription());
                binding.addressCompany.setText(postDetail.getCompany().getAddress());
                binding.websiteCompany.setText(postDetail.getCompany().getWebsiteUrl());
            }
        });
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_company;
    }

    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}