package com.koje.framework

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.koje.math.puzzle.R
import java.util.Locale

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        init(this)
    }

    companion object {
        val idReceivers = R.id.receivers
        lateinit var instance: Context

        fun init(value: Application) {
            instance = value
        }

        fun debugging(): Boolean {
            return true
        }

        fun getString(id: Int): String {
            return instance.resources.getString(id)
        }

        fun overrideLocale(language: String) {
            val config = Configuration(instance.resources.configuration)
            config.setLocale(Locale(language))
            instance = instance.createConfigurationContext(config)
        }

    }


}