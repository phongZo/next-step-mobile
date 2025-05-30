package itz.next_step.android.ui.main.comment;

import itz.next_step.android.MVVMApplication;
import itz.next_step.android.data.Repository;
import itz.next_step.android.ui.base.fragment.BaseFragmentViewModel;

public class TopCommentViewModel extends BaseFragmentViewModel {
    public TopCommentViewModel(Repository repository, MVVMApplication application) {
        super(repository, application);
    }
}
