package com.codetron.countries

import android.app.Application
import com.codetron.countries.di.component.AppComponent
import com.codetron.countries.di.component.DaggerAppComponent

class CountriesApp : Application() {

    val appComponent: AppComponent by lazy { DaggerAppComponent.create() }

}