package com.bonioctavianus.android.shopping_app.interactor

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.profile.ProfileIntent
import com.bonioctavianus.android.shopping_app.ui.profile.ProfileInteractor
import com.bonioctavianus.android.shopping_app.ui.profile.ProfileViewState
import com.bonioctavianus.android.shopping_app.usecase.DoSignOut
import com.bonioctavianus.android.shopping_app.usecase.GetPurchasedItem
import com.bonioctavianus.android.shopping_app.usecase.Result
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ProfileInteractorTest {

    private val mGetPurchasedItem: GetPurchasedItem = mockk()
    private val mDoSignOut: DoSignOut = mockk()
    private val mInteractor: ProfileInteractor = ProfileInteractor(mGetPurchasedItem, mDoSignOut)

    @Test
    fun `composing GetPurchasedItems intent - should return Empty state`() {
        val items = emptyList<Item>()

        every { mGetPurchasedItem.getItems() } returns
                Observable.just(
                    Result.InFlight,
                    Result.Success(items)
                )

        Observable.just(
            ProfileIntent.GetPurchasedItems
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                ProfileViewState.GetPurchasedItems.InFlight,
                ProfileViewState.GetPurchasedItems.Empty
            )
            .dispose()

        verify {
            mGetPurchasedItem.getItems()
        }
    }

    @Test
    fun `composing GetPurchasedItems intent - should return Loaded state`() {
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

        every { mGetPurchasedItem.getItems() } returns
                Observable.just(
                    Result.InFlight,
                    Result.Success(items)
                )

        Observable.just(
            ProfileIntent.GetPurchasedItems
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                ProfileViewState.GetPurchasedItems.InFlight,
                ProfileViewState.GetPurchasedItems.Loaded(items)
            )
            .dispose()

        verify {
            mGetPurchasedItem.getItems()
        }
    }

    @Test
    fun `composing GetPurchasedItems intent when throwing exception - should return Error state`() {
        val exception = mockk<RuntimeException>()

        every { mGetPurchasedItem.getItems() } returns
                Observable.just(
                    Result.InFlight,
                    Result.Error(exception)
                )

        Observable.just(
            ProfileIntent.GetPurchasedItems
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                ProfileViewState.GetPurchasedItems.InFlight,
                ProfileViewState.GetPurchasedItems.Error(exception)
            )
            .dispose()

        verify {
            mGetPurchasedItem.getItems()
        }
    }

    @Test
    fun `composing SelectItem intent - should return ItemSelected state`() {
        val item = mockk<Item>()

        Observable.just(
            ProfileIntent.SelectItem(item)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                ProfileViewState.ItemSelected(item)
            )
            .dispose()
    }

    @Test
    fun `composing SelectMenuSignOut intent - should return Success state`() {
        every { mDoSignOut.signOut() } returns
                Observable.just(
                    Result.Success(Unit)
                )

        Observable.just(
            ProfileIntent.SelectMenuSignOut
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                ProfileViewState.SignOut.Success
            )
            .dispose()

        verify {
            mDoSignOut.signOut()
        }
    }

    @Test
    fun `composing SelectMenuSignOut intent when throwing exception - should return Error state`() {
        val exception = mockk<RuntimeException>()

        every { mDoSignOut.signOut() } returns
                Observable.just(
                    Result.Error(exception)
                )

        Observable.just(
            ProfileIntent.SelectMenuSignOut
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                ProfileViewState.SignOut.Error(exception)
            )
            .dispose()

        verify {
            mDoSignOut.signOut()
        }
    }
}