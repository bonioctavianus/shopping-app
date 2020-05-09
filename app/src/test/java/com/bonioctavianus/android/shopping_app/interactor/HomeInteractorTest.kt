package com.bonioctavianus.android.shopping_app.interactor

import com.bonioctavianus.android.shopping_app.model.Category
import com.bonioctavianus.android.shopping_app.model.Home
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.home.HomeIntent
import com.bonioctavianus.android.shopping_app.ui.home.HomeInteractor
import com.bonioctavianus.android.shopping_app.ui.home.HomeViewState
import com.bonioctavianus.android.shopping_app.usecase.GetHomeItem
import com.bonioctavianus.android.shopping_app.usecase.Result
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeInteractorTest {

    private val mGetHomeItem: GetHomeItem = mockk()
    private val mInteractor: HomeInteractor = HomeInteractor(mGetHomeItem)

    @Test
    fun `composing GetItems intent when there is no items - should return empty state`() {
        val expected = Home(
            categories = emptyList(),
            items = emptyList()
        )

        every { mGetHomeItem.getItems() } returns
                Observable.just(
                    Result.InFlight,
                    Result.Success(expected)
                )

        Observable.just(
            HomeIntent.GetItems
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                HomeViewState.GetHomeItem.InFlight,
                HomeViewState.GetHomeItem.Empty
            )
            .dispose()

        verify {
            mGetHomeItem.getItems()
        }
    }

    @Test
    fun `composing GetItems intent when there is items - should return loaded state`() {
        val expected = Home(
            categories = listOf(
                Category(
                    id = 1,
                    name = "Category",
                    imageUrl = ""
                )
            ),
            items = listOf(
                Item(
                    id = 1,
                    title = "Item",
                    description = "",
                    price = "$400",
                    isFavorite = false,
                    imageUrl = ""
                )
            )
        )

        every { mGetHomeItem.getItems() } returns
                Observable.just(
                    Result.InFlight,
                    Result.Success(expected)
                )

        Observable.just(
            HomeIntent.GetItems
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                HomeViewState.GetHomeItem.InFlight,
                HomeViewState.GetHomeItem.Loaded(expected.categories, expected.items)
            )
            .dispose()

        verify {
            mGetHomeItem.getItems()
        }
    }

    @Test
    fun `composing GetItems intent when throwing exception - should return error state`() {
        val exception = mockk<RuntimeException>()

        every { mGetHomeItem.getItems() } returns
                Observable.just(
                    Result.InFlight,
                    Result.Error(exception)
                )

        Observable.just(
            HomeIntent.GetItems
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                HomeViewState.GetHomeItem.InFlight,
                HomeViewState.GetHomeItem.Error(exception)
            )
            .dispose()

        verify {
            mGetHomeItem.getItems()
        }
    }

    @Test
    fun `composing SelectMenuSearch intent - should return MenuSearchSelected state`() {
        Observable.just(
            HomeIntent.SelectMenuSearch
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                HomeViewState.MenuSearchSelected
            )
            .dispose()
    }

    @Test
    fun `composing SelectItem intent - should return ItemSelected state`() {
        val item = mockk<Item>()

        Observable.just(
            HomeIntent.SelectItem(item)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                HomeViewState.ItemSelected(item)
            )
            .dispose()
    }
}