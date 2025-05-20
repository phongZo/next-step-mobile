package itz.next_step.android.ui.main.notification;

import eu.davidea.flexibleadapter.databinding.BR;
import itz.next_step.android.R;
import itz.next_step.android.databinding.FragmentNotificationBinding;
import itz.next_step.android.di.component.FragmentComponent;
import itz.next_step.android.ui.base.fragment.BaseFragment;

public class NotificationFragment extends BaseFragment<FragmentNotificationBinding, NotificationViewModel> {
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
        return R.layout.fragment_notification;
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}
