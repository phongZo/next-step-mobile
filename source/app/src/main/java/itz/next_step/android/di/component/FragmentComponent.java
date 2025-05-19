package itz.next_step.android.di.component;


import itz.next_step.android.di.module.FragmentModule;
import itz.next_step.android.di.scope.FragmentScope;

import dagger.Component;

@FragmentScope
@Component(modules = {FragmentModule.class},dependencies = AppComponent.class)
public interface FragmentComponent {

}
