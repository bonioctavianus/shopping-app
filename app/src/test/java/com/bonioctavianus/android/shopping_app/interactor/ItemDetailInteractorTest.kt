package com.bonioctavianus.android.shopping_app.interactor

import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.detail.ItemDetailIntent
import com.bonioctavianus.android.shopping_app.ui.detail.ItemDetailInteractor
import com.bonioctavianus.android.shopping_app.ui.detail.ItemDetailViewState
import com.bonioctavianus.android.shopping_app.usecase.PurchaseItem
import com.bonioctavianus.android.shopping_app.usecase.Result
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Observable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ItemDetailInteractorTest {

    private val mPurchaseItem: PurchaseItem = mockk()
    private val mInteractor: ItemDetailInteractor = ItemDetailInteractor(mPurchaseItem)

    @Test
    fun `composing DisplayItem intent - should return ItemDisplayed state`() {
        val item = mockk<Item>()

        Observable.just(
            ItemDetailIntent.DisplayItem(item)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                ItemDetailViewState.ItemDisplayed(item)
            )
            .dispose()
    }

    @Test
    fun `composing ShareItem intent - should return ItemShared state`() {
        val item = Item(
            id = 1,
            title = "Nintendo",
            description = "",
            price = "$400",
            isFavorite = false,
            imageUrl = ""
        )
        val expected = "Let\'s share ${item.title} - ${item.price}"

        Observable.just(
            ItemDetailIntent.ShareItem(item)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                ItemDetailViewState.ItemShared(expected)
            )
            .dispose()
    }

    @Test
    fun `composing UpdateFavoriteStatus intent - should return ItemDisplayed state`() {
        val item = Item(
            id = 1,
            title = "Nintendo",
            description = "",
            price = "$400",
            isFavorite = false,
            imageUrl = ""
        )

        Observable.just(
            ItemDetailIntent.UpdateFavoriteStatus(item)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                ItemDetailViewState.ItemDisplayed(item.copy(isFavorite = true))
            )
            .dispose()
    }

    @Test
    fun `composing PurchaseItem intent - should return Success state`() {
        val item = mockk<Item>()
        val message = "Purchase Success"

        every { mPurchaseItem.purchase(item) } returns
                Observable.just(
                    Result.Success(Unit)
                )

        Observable.just(
            ItemDetailIntent.PurchaseItem(item)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                ItemDetailViewState.PurchaseItem.Success(message)
            )
            .dispose()
    }

    @Test
    fun `composing PurchaseItem intent - should return Error state`() {
        val item = mockk<Item>()
        val exception = mockk<RuntimeException>()

        every { mPurchaseItem.purchase(item) } returns
                Observable.just(
                    Result.Error(exception)
                )

        Observable.just(
            ItemDetailIntent.PurchaseItem(item)
        )
            .compose(mInteractor.compose())
            .test()
            .assertResult(
                ItemDetailViewState.PurchaseItem.Error(exception)
            )
            .dispose()
    }
}