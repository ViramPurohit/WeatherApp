/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.viram.weather.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.viram.weather.util.TestUtil
import com.viram.weather.util.getOrAwaitValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CityDaoTest : DbTest() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertAndLoad() {

        val city_1 = TestUtil.createUser("Ahmedabad",
           "SG Highway ,Ahmedabad",
            23.0225,
                72.5714)
        val city_2 = TestUtil.createUser("Surat",
            "SVNIT Campus, Athwa, Surat, Gujarat 395007",
            21.1702,
            72.8311)
        val city_3 = TestUtil.createUser("Vadodara",
            "Pratapgunj, Vadodara, Gujarat 390002",
            22.3072,
            73.1812)
        db.runInTransaction {
            db.userCityDao().insert(city_1)
            db.userCityDao().insertCities(arrayListOf(city_2, city_3))
        }


        val list = db.userCityDao().getAllSavedCity().getOrAwaitValue()
        assertThat(list.size, `is`(3))
        val first = list[0]

        assertThat(first.city, `is`("Ahmedabad"))
        assertThat(first.latitude, `is`(23.0225))

        val second = list[1]
        assertThat(second.city, `is`("Surat"))
        assertThat(second.latitude, `is`(21.1702))

        val delete = city_3.city
        db.userCityDao().deleteCity(delete)

    }
}
