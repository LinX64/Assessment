package xyz.argent.candidateassessment.app

import android.app.Application

class App : Application() {

    companion object {
        lateinit var dependencies: Dependencies
            private set
    }

    override fun onCreate() {
        super.onCreate()
        dependencies = Dependencies(this)
    }
}