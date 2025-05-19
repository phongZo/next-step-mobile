package itz.next_step.android.di.component;

import itz.next_step.android.di.module.ActivityModule;
import itz.next_step.android.di.scope.ActivityScope;
import itz.next_step.android.ui.main.MainActivity;

import dagger.Component;

@ActivityScope
@Component(modules = {ActivityModule.class}, dependencies = AppComponent.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
}

