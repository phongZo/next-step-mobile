package itz.next_step.android.ui.main.notification;

import androidx.databinding.ObservableBoolean;

import itz.next_step.android.MVVMApplication;
import itz.next_step.android.data.Repository;
import itz.next_step.android.ui.base.fragment.BaseFragmentViewModel;

public class NotificationViewModel extends BaseFragmentViewModel {
    public ObservableBoolean isLogin = new ObservableBoolean(true);
    public NotificationViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
