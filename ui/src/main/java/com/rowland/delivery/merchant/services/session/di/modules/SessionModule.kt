/*
 * Copyright 2021 Otieno Rowland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rowland.delivery.merchant.services.session.di.modules

import com.rowland.delivery.merchant.services.session.SessionManager
import com.rowland.delivery.merchant.services.sharedpreferences.SharedPreferencesManager
import com.rowland.delivery.merchant.services.sharedpreferences.di.modules.SharedPreferencesModule

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Rowland on 4/30/2018.
 */

@Module(includes = [SharedPreferencesModule::class])
@InstallIn(SingletonComponent::class)
class SessionModule {

    @Provides
    fun providesSessionManager(preferencesManager: SharedPreferencesManager): SessionManager {
        return SessionManager(preferencesManager)
    }
}
