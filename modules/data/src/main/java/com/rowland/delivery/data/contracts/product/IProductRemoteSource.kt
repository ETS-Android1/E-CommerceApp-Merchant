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

package com.rowland.delivery.data.contracts.product

import com.rowland.delivery.data.model.product.ProductPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

interface IProductRemoteSource {
    fun createProduct(productPojo: ProductPojo, productCategory: String, userUid: String): Single<ProductPojo>
    fun updateProduct(productUpdateFields: HashMap<String, Any>, productUid: String): Completable
    fun deleteProduct(productUid: String): Completable
    fun loadProducts(userUid: String, productCategory: String): Flowable<List<ProductPojo>>
}
