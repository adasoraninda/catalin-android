package com.codetron.countries.di.component

import com.codetron.countries.di.module.NetworkModule
import com.codetron.countries.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

}