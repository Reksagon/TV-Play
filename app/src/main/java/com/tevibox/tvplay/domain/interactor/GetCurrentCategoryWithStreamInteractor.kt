package com.tevibox.tvplay.domain.interactor

import android.content.Context
import com.javavirys.core.interactor.InteractorInterface
import com.tevibox.tvplay.R
import com.tevibox.tvplay.core.entity.Category
import com.tevibox.tvplay.core.entity.CategoryWithStream
import com.tevibox.tvplay.core.exception.StreamNotFoundException
import kotlinx.coroutines.flow.*
import timber.log.Timber

class GetCurrentCategoryWithStreamInteractor(
    private val context: Context,
    private val getCurrentCategoryInteractor: GetCurrentCategoryInteractor,
    private val getCurrentStreamInteractor: GetCurrentStreamInteractor
) : InteractorInterface<Unit, Flow<CategoryWithStream>> {

    override suspend fun invoke(param: Unit) = getCurrentCategoryInteractor(Unit).flatMapConcat {
        getCategoryWithStreamFlow(it)
    }

    private suspend fun getCategoryWithStreamFlow(category: Category): Flow<CategoryWithStream> {
        return getCurrentStreamInteractor(category.name)
            .map {
                CategoryWithStream(category, it)
            }.catch { e ->
                if (e is StreamNotFoundException) {
                    Timber.d("getCategoryWithStreamFlow: StreamNotFoundException")
                    emitAll(
                        getCurrentStreamInteractor("").map {
                            CategoryWithStream(Category(0, context.getString(R.string.all)), it)
                        }
                    )
                } else {
                    throw e
                }
            }
    }
}