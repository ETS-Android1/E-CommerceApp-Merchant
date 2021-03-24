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

package com.rowland.delivery.data.contracts.order

import com.rowland.delivery.data.model.order.OrderDataPojo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

interface IOrderCacheSource {
    fun createOrder(order: OrderDataPojo, userUid: String): Single<OrderDataPojo>
    fun updateOrder(orderUpdateFields: HashMap<String, Any>, orderUid: String): Completable
    fun deleteOrder(orderUid: String): Completable
    fun loadOrders(userUid: String): Flowable<List<OrderDataPojo>>
    fun saveToCache(Orders: List<OrderDataPojo>): Completable
    fun clearFromCache(): Completable
    fun isCached(): Single<Boolean>
    fun setLastCacheTime(lastCache: Long)
    fun isExpired(): Boolean
}
