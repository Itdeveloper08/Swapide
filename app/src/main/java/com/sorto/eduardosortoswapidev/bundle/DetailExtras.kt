/*
 * Copyright (C) 2018 Radley Marx
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

package com.sorto.eduardosortoswapidev.bundle

import android.content.Context
import android.content.Intent

import com.sorto.eduardosortoswapidev.view.DetailActivity
import com.sorto.eduardosortoswapidev.models.SWModel


object DetailExtras {

    const val MODEL = "MODEL"


    fun getIntent(context: Context, item: SWModel): Intent {

        val intent = Intent(context, DetailActivity::class.java)
        intent.action = Intent.ACTION_VIEW
        intent.putExtra(MODEL, item)

        return intent
    }


    fun hasAll(intent: Intent, vararg extras: String): Boolean {
        for (extra in extras) {
            if (!intent.hasExtra(extra)) {
                return false
            }
        }
        return true
    }
}
