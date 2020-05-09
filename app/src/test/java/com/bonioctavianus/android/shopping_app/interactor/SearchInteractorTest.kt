package com.bonioctavianus.android.shopping_app.interactor

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.search.SearchIntent
import com.bonioctavianus.android.shopping_app.ui.search.SearchInteractor
import com.bonioctavianus.android.shopping_app.ui.search.SearchViewState
import com.bonioctavianus.android.shopping_app.usecase.Result
import com.bonioctavianus.android.shopping_app.usecase.SearchItem
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchInteractorTest {

    private val mSearchItem: SearchItem = mockk()
    private val mInteractor: SearchInteractor = SearchInteractor(mSearchItem)

    @Test
    fun `composing SearchItem intent - should return Empty state`() {
        val query = "Nintendo"
        val items = emptyList<Item>()

        every { mSearchItem.search(query) } returns
                Observable.just(
                    Result.Success(items)
                )

        Observable.just(
            SearchIntent.SearchItem(query)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                SearchViewState.SearchItem.Empty
            )
            .dispose()

        verify {
            mSearchItem.search(query)
        }
    }

    @Test
    fun `composing SearchItem intent - should return Loaded state`() {
        val query = "Nintendo"
        val items = listOf(
            Item(
                id = 1,
                title = "Nintendo",
                description = "",
                price = "$400",
                isFavorite = false,
                imageUrl = ""
            )
        )

        every { mSearchItem.search(query) } returns
                Observable.just(
                    Result.Success(items)
                )

        Observable.just(
            SearchIntent.SearchItem(query)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                SearchViewState.SearchItem.Loaded(items)
            )
            .dispose()

        verify {
            mSearchItem.search(query)
        }
    }

    @Test
    fun `composing SearchItem intent when throwing exception - should return Error state`() {
        val query = "Nintendo"
        val exception = mockk<RuntimeException>()

        every { mSearchItem.search(query) } returns
                Observable.just(
                    Result.Error(exception)
                )

        Observable.just(
            SearchIntent.SearchItem(query)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                SearchViewState.SearchItem.Error(exception)
            )
            .dispose()

        verify {
            mSearchItem.search(query)
        }
    }

    @Test
    fun `composing SelectItem intent - should return ItemSelected state`() {
        val item = mockk<Item>()

        Observable.just(
            SearchIntent.SelectItem(item)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                SearchViewState.ItemSelected(item)
            )
            .dispose()
    }
}