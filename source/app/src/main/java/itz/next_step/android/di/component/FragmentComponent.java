package itz.next_step.android.di.component;


import itz.next_step.android.di.module.FragmentModule;
import itz.next_step.android.di.scope.FragmentScope;

import dagger.Component;
import itz.next_step.android.ui.main.account.AccountFragment;
import itz.next_step.android.ui.main.account.AccountUnLoginFragment;
import itz.next_step.android.ui.main.comment.TopCommentFragment;
import itz.next_step.android.ui.main.cv.CvProfileFragment;
import itz.next_step.android.ui.main.home.HomeFragment;
import itz.next_step.android.ui.main.notification.NotificationFragment;

@FragmentScope
@Component(modules = {FragmentModule.class},dependencies = AppComponent.class)
public interface FragmentComponent {
    void inject(HomeFragment fragment);
    void inject(CvProfileFragment fragment);
    void inject(NotificationFragment fragment);
    void inject(TopCommentFragment fragment);
    void inject(AccountFragment fragment);
    void inject(AccountUnLoginFragment fragment);
}
