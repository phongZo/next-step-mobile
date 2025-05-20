package itz.next_step.android.ui.main.notification;

import itz.next_step.android.MVVMApplication;
import itz.next_step.android.data.Repository;
import itz.next_step.android.ui.base.fragment.BaseFragmentViewModel;

public class NotificationViewModel extends BaseFragmentViewModel {
    public NotificationViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
