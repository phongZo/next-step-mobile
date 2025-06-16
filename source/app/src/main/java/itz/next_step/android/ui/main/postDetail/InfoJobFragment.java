package itz.next_step.android.ui.main.postDetail;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import eu.davidea.flexibleadapter.databinding.BR;
import itz.next_step.android.R;
import itz.next_step.android.databinding.FragmentInfoJobBinding;
import itz.next_step.android.di.component.FragmentComponent;
import itz.next_step.android.ui.base.fragment.BaseFragment;


public class InfoJobFragment extends BaseFragment<FragmentInfoJobBinding,InfoJobViewModel> {
    private PostDetailViewModel activityViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(InfoJobViewModel.class);

        activityViewModel = new ViewModelProvider(requireActivity()).get(PostDetailViewModel.class);

        activityViewModel.getPostDetail().observe(getViewLifecycleOwner(), postDetail -> {
            if (postDetail != null) {
                binding.descriptionJob.setText(postDetail.getDescription());
                binding.addressJob.setText(postDetail.getCompany().getAddress());
            }
        });
    }

    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_info_job;
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