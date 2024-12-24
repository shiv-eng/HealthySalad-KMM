
package com.shivangi.healthysalad

import android.app.Application
import di.component.AppComponent

class SaladApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppComponent.Injector.inject(this)
    }
}
