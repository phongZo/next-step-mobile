package itz.next_step.android.ui.main.account;

import eu.davidea.flexibleadapter.databinding.BR;
import itz.next_step.android.R;
import itz.next_step.android.databinding.FragmentAccountUnLoginBinding;
import itz.next_step.android.di.component.FragmentComponent;
import itz.next_step.android.ui.base.fragment.BaseFragment;
import itz.next_step.android.ui.main.MainActivity;

public class AccountUnLoginFragment extends BaseFragment<FragmentAccountUnLoginBinding, AccountUnLoginViewModel> {
    @Override
    protected void performDataBinding() {
        binding.setF(this);
        binding.setVm(viewModel);
    }

    public void onLoginClick() {
        ((MainActivity) requireActivity()).navigateToLogin();
    }
    @Override
    public int getBindingVariable() {
        return BR.vm;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account_un_login;
    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }
}
