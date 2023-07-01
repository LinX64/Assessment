package xyz.argent.candidateassessment

import android.app.Application
import xyz.argent.candidateassessment.di.Dependencies

class App : Application() {

    lateinit var dependencies : Dependencies

    override fun onCreate() {
        super.onCreate()
        dependencies = Dependencies()
    }
}