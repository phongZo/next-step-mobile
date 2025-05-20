package itz.next_step.android.ui.main.home;

import itz.next_step.android.MVVMApplication;
import itz.next_step.android.data.Repository;
import itz.next_step.android.ui.base.fragment.BaseFragmentViewModel;

public class HomeViewModel extends BaseFragmentViewModel {
    public HomeViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
