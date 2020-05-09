package com.bonioctavianus.android.shopping_app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.detail.ItemDetailIntent
import com.bonioctavianus.android.shopping_app.ui.detail.ItemDetailInteractor
import com.bonioctavianus.android.shopping_app.ui.detail.ItemDetailViewModel
import com.bonioctavianus.android.shopping_app.ui.detail.ItemDetailViewState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ItemDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mStateObserver: Observer<ItemDetailViewState> = mockk(relaxed = true)
    private val mEventObserver: Observer<ItemDetailViewState> = mockk(relaxed = true)
    private val mInteractor: ItemDetailInteractor = mockk()
    private val mViewModel: ItemDetailViewModel = ItemDetailViewModel(mInteractor)

    @Before
    fun setup() {
        mViewModel.state().observeForever(mStateObserver)
        mViewModel.event()?.observeForever(mEventObserver)
    }

    @Test
    fun `bindIntent() for DisplayItem intent - should return ItemDisplayed state`() {
        val item = mockk<Item>()

        val intent: Observable<ItemDetailIntent> =
            Observable.just(
                ItemDetailIntent.DisplayItem(item)
            )

        val composer = ObservableTransformer<ItemDetailIntent, ItemDetailViewState> {
            Observable.just(
                ItemDetailViewState.ItemDisplayed(item)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(ItemDetailViewState.ItemDisplayed(item))
        }

        verify(exactly = 0) {
            mEventObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for ShareItem intent - should return ItemShared state`() {
        val item = Item(
            id = 1,
            title = "Nintendo",
            description = "",
            price = "$400",
            isFavorite = false,
            imageUrl = ""
        )
        val text = "Let\'s share ${item.title} - ${item.price}"

        val intent: Observable<ItemDetailIntent> =
            Observable.just(
                ItemDetailIntent.ShareItem(item)
            )

        val composer = ObservableTransformer<ItemDetailIntent, ItemDetailViewState> {
            Observable.just(
                ItemDetailViewState.ItemShared(text)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mEventObserver.onChanged(ItemDetailViewState.ItemShared(text))
        }

        verify(exactly = 0) {
            mStateObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for UpdateFavoriteStatus intent - should return ItemDisplayed state`() {
        val item = Item(
            id = 1,
            title = "Nintendo",
            description = "",
            price = "$400",
            isFavorite = false,
            imageUrl = ""
        )

        val updatedItem = item.copy(isFavorite = true)

        val intent: Observable<ItemDetailIntent> =
            Observable.just(
                ItemDetailIntent.UpdateFavoriteStatus(item)
            )

        val composer = ObservableTransformer<ItemDetailIntent, ItemDetailViewState> {
            Observable.just(
                ItemDetailViewState.ItemDisplayed(updatedItem)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(ItemDetailViewState.ItemDisplayed(updatedItem))
        }

        verify(exactly = 0) {
            mEventObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for PurchaseItem intent - should return Success state`() {
        val item = mockk<Item>()
        val message = "Purchase Success"

        val intent: Observable<ItemDetailIntent> =
            Observable.just(
                ItemDetailIntent.PurchaseItem(item)
            )

        val composer = ObservableTransformer<ItemDetailIntent, ItemDetailViewState> {
            Observable.just(
                ItemDetailViewState.PurchaseItem.Success(message)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mEventObserver.onChanged(ItemDetailViewState.PurchaseItem.Success(message))
        }

        verify(exactly = 0) {
            mStateObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for PurchaseItem intent when throwing exception - should return Error state`() {
        val item = mockk<Item>()
        val exception = mockk<RuntimeException>()

        val intent: Observable<ItemDetailIntent> =
            Observable.just(
                ItemDetailIntent.PurchaseItem(item)
            )

        val composer = ObservableTransformer<ItemDetailIntent, ItemDetailViewState> {
            Observable.just(
                ItemDetailViewState.PurchaseItem.Error(exception)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mEventObserver.onChanged(ItemDetailViewState.PurchaseItem.Error(exception))
        }

        verify(exactly = 0) {
            mStateObserver.onChanged(any())
        }
    }
}