package itz.next_step.android.ui.main.account;

import itz.next_step.android.MVVMApplication;
import itz.next_step.android.data.Repository;
import itz.next_step.android.ui.base.fragment.BaseFragmentViewModel;

public class AccountViewModel extends BaseFragmentViewModel {
    public AccountViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
    public void logout() {
        showLoading();
        repository.getSharedPreferences().clearAuthData();
    }
}
