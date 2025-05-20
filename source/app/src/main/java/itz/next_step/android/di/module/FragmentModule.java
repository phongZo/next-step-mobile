package itz.next_step.android.di.module;

import android.content.Context;

import androidx.core.util.Supplier;
import androidx.lifecycle.ViewModelProvider;

import itz.next_step.android.MVVMApplication;
import itz.next_step.android.ViewModelProviderFactory;
import itz.next_step.android.data.Repository;
import itz.next_step.android.di.scope.FragmentScope;
import itz.next_step.android.ui.base.fragment.BaseFragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import itz.next_step.android.ui.main.account.AccountViewModel;
import itz.next_step.android.ui.main.comment.TopCommentViewModel;
import itz.next_step.android.ui.main.cv.CvProfileViewModel;
import itz.next_step.android.ui.main.home.HomeViewModel;
import itz.next_step.android.ui.main.notification.NotificationViewModel;

@Module
public class FragmentModule {

    private BaseFragment<?, ?> fragment;

    public FragmentModule(BaseFragment<?, ?> fragment) {
        this.fragment = fragment;
    }

    @Named("access_token")
    @Provides
    @FragmentScope
    String provideToken(Repository repository) {
        return repository.getToken();
    }
    @Provides
    @FragmentScope
    HomeViewModel provideHomeViewModel(Repository repository, Context application) {
        Supplier<HomeViewModel> supplier = () -> new HomeViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<HomeViewModel> factory = new ViewModelProviderFactory<>(HomeViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(HomeViewModel.class);
    }

    @Provides
    @FragmentScope
    CvProfileViewModel provideCvProfileViewModel(Repository repository, Context application) {
        Supplier<CvProfileViewModel> supplier = () -> new CvProfileViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<CvProfileViewModel> factory = new ViewModelProviderFactory<>(CvProfileViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(CvProfileViewModel.class);
    }

    @Provides
    @FragmentScope
    NotificationViewModel provideNotificationViewModel(Repository repository, Context application) {
        Supplier<NotificationViewModel> supplier = () -> new NotificationViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<NotificationViewModel> factory = new ViewModelProviderFactory<>(NotificationViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(NotificationViewModel.class);
    }

    @Provides
    @FragmentScope
    TopCommentViewModel provideTopCommentViewModel(Repository repository, Context application) {
        Supplier<TopCommentViewModel> supplier = () -> new TopCommentViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<TopCommentViewModel> factory = new ViewModelProviderFactory<>(TopCommentViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(TopCommentViewModel.class);
    }

    @Provides
    @FragmentScope
    AccountViewModel provideAccountViewModel(Repository repository, Context application) {
        Supplier<AccountViewModel> supplier = () -> new AccountViewModel(repository, (MVVMApplication)application);
        ViewModelProviderFactory<AccountViewModel> factory = new ViewModelProviderFactory<>(AccountViewModel.class, supplier);
        return new ViewModelProvider(fragment, factory).get(AccountViewModel.class);
    }
}
