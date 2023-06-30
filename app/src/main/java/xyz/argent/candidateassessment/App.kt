package xyz.argent.candidateassessment

import android.app.Application
import xyz.argent.candidateassessment.di.Dependencies

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