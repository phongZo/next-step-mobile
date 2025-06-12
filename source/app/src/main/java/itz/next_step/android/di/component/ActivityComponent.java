package itz.next_step.android.di.component;

import itz.next_step.android.di.module.ActivityModule;
import itz.next_step.android.di.scope.ActivityScope;
import itz.next_step.android.ui.main.MainActivity;

import dagger.Component;
import itz.next_step.android.ui.main.login.LoginActivity;
import itz.next_step.android.ui.main.login.SignUpActivity;
import itz.next_step.android.ui.main.search.SearchActivity;

@ActivityScope
@Component(modules = {ActivityModule.class}, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
    void inject(LoginActivity activity);
    void inject(SignUpActivity activity);
    void inject(SearchActivity activity);
}

