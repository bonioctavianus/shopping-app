package com.bonioctavianus.android.shopping_app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bonioctavianus.android.shopping_app.model.Category
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.home.HomeIntent
import com.bonioctavianus.android.shopping_app.ui.home.HomeInteractor
import com.bonioctavianus.android.shopping_app.ui.home.HomeViewModel
import com.bonioctavianus.android.shopping_app.ui.home.HomeViewState
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
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mStateObserver: Observer<HomeViewState> = mockk(relaxed = true)
    private val mEventObserver: Observer<HomeViewState> = mockk(relaxed = true)
    private val mInteractor: HomeInteractor = mockk()
    private val mViewModel: HomeViewModel = HomeViewModel(mInteractor)

    @Before
    fun setup() {
        mViewModel.state().observeForever(mStateObserver)
        mViewModel.event()?.observeForever(mEventObserver)
    }

    @Test
    fun `bindIntent() for GetItems intent - should return Empty state`() {
        val intent: Observable<HomeIntent> =
            Observable.just(
                HomeIntent.GetItems
            )

        val composer = ObservableTransformer<HomeIntent, HomeViewState> {
            Observable.just(
                HomeViewState.GetHomeItem.InFlight,
                HomeViewState.GetHomeItem.Empty
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(HomeViewState.GetHomeItem.InFlight)
            mStateObserver.onChanged(HomeViewState.GetHomeItem.Empty)
        }

        verify(exactly = 0) {
            mEventObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for GetItems intent - should return Loaded state`() {
        val categories = mockk<List<Category>>()
        val items = mockk<List<Item>>()

        val intent: Observable<HomeIntent> =
            Observable.just(
                HomeIntent.GetItems
            )

        val composer = ObservableTransformer<HomeIntent, HomeViewState> {
            Observable.just(
                HomeViewState.GetHomeItem.InFlight,
                HomeViewState.GetHomeItem.Loaded(categories, items)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(HomeViewState.GetHomeItem.InFlight)
            mStateObserver.onChanged(HomeViewState.GetHomeItem.Loaded(categories, items))
        }

        verify(exactly = 0) {
            mEventObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for GetItems intent when throwing exception - should return Error state`() {
        val exception = mockk<RuntimeException>()

        val intent: Observable<HomeIntent> =
            Observable.just(
                HomeIntent.GetItems
            )

        val composer = ObservableTransformer<HomeIntent, HomeViewState> {
            Observable.just(
                HomeViewState.GetHomeItem.InFlight,
                HomeViewState.GetHomeItem.Error(exception)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(HomeViewState.GetHomeItem.InFlight)
            mStateObserver.onChanged(HomeViewState.GetHomeItem.Error(exception))
        }

        verify(exactly = 0) {
            mEventObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for SelectMenuSearch intent when throwing exception - should return MenuSearchSelected state`() {
        val intent: Observable<HomeIntent> =
            Observable.just(
                HomeIntent.SelectMenuSearch
            )

        val composer = ObservableTransformer<HomeIntent, HomeViewState> {
            Observable.just(
                HomeViewState.MenuSearchSelected
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mEventObserver.onChanged(HomeViewState.MenuSearchSelected)
        }

        verify(exactly = 0) {
            mStateObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for SelectItem intent - should return ItemSelected state`() {
        val item = mockk<Item>()

        val intent: Observable<HomeIntent> =
            Observable.just(
                HomeIntent.SelectItem(item)
            )

        val composer = ObservableTransformer<HomeIntent, HomeViewState> {
            Observable.just(
                HomeViewState.ItemSelected(item)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mEventObserver.onChanged(HomeViewState.ItemSelected(item))
        }

        verify(exactly = 0) {
            mStateObserver.onChanged(any())
        }
    }
}