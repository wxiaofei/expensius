/*
 * Copyright (C) 2016 Mantas Varnagiris.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.mvcoding.expensius.feature.settings

import android.app.Activity
import com.memoizrlabs.Shank.registerFactory
import com.memoizrlabs.ShankModule
import com.memoizrlabs.shankkotlin.provideSingletonFor
import com.mvcoding.expensius.feature.currency.provideCurrenciesProvider
import com.mvcoding.expensius.provideAppUserService
import com.mvcoding.expensius.provideAppUserWriteService
import com.mvcoding.expensius.provideRxSchedulers
import memoizrlabs.com.shankandroid.withThisScope

class SettingsModule : ShankModule {
    override fun registerFactories() {
        settingsPresenter()
    }

    private fun settingsPresenter() = registerFactory(SettingsPresenter::class.java) { ->
        SettingsPresenter(provideAppUserService(), provideAppUserWriteService(), provideCurrenciesProvider(), provideRxSchedulers())
    }
}

fun Activity.provideSettingsPresenter() = withThisScope.provideSingletonFor<SettingsPresenter>()