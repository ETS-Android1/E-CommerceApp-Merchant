/*
 * Copyright (c) Otieno Rowland,  2021. All rights reserved.
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

package com.rowland.delivery.data.contracts.category

import com.rowland.delivery.data.model.category.CategoryPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface ICategoryDataStore {
    fun clearFromCache(): Completable
    fun saveToCache(categories: List<CategoryPojo>): Completable
    fun createCategory(category: CategoryPojo, userUid: String): Single<CategoryPojo>
    fun updateCategory(CategoryUpdateFields: HashMap<String, Any>, CategoryUid: String): Completable
    fun deleteCategory(CategoryUid: String, userUid: String): Completable
    fun loadCategories(userUid: String): Flowable<List<CategoryPojo>>
    fun isCached(): Single<Boolean>
}
