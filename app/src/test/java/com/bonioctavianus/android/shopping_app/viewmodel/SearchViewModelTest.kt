package com.bonioctavianus.android.shopping_app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.search.SearchIntent
import com.bonioctavianus.android.shopping_app.ui.search.SearchInteractor
import com.bonioctavianus.android.shopping_app.ui.search.SearchViewModel
import com.bonioctavianus.android.shopping_app.ui.search.SearchViewState
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
class SearchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mStateObserver: Observer<SearchViewState> = mockk(relaxed = true)
    private val mEventObserver: Observer<SearchViewState> = mockk(relaxed = true)
    private val mInteractor: SearchInteractor = mockk()
    private val mViewModel: SearchViewModel = SearchViewModel(mInteractor)

    @Before
    fun setup() {
        mViewModel.state().observeForever(mStateObserver)
        mViewModel.event()?.observeForever(mEventObserver)
    }

    @Test
    fun `bindIntent() for SearchItem intent - should return Empty state`() {
        val query = "Nintendo"

        val intent: Observable<SearchIntent> =
            Observable.just(
                SearchIntent.SearchItem(query)
            )

        val composer = ObservableTransformer<SearchIntent, SearchViewState> {
            Observable.just(
                SearchViewState.SearchItem.InFlight,
                SearchViewState.SearchItem.Empty
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(SearchViewState.SearchItem.InFlight)
            mStateObserver.onChanged(SearchViewState.SearchItem.Empty)
        }

        verify(exactly = 0) {
            mEventObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for SearchItem intent - should return Loaded state`() {
        val query = "Nintendo"
        val items = mockk<List<Item>>()

        val intent: Observable<SearchIntent> =
            Observable.just(
                SearchIntent.SearchItem(query)
            )

        val composer = ObservableTransformer<SearchIntent, SearchViewState> {
            Observable.just(
                SearchViewState.SearchItem.InFlight,
                SearchViewState.SearchItem.Loaded(items)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(SearchViewState.SearchItem.InFlight)
            mStateObserver.onChanged(SearchViewState.SearchItem.Loaded(items))
        }

        verify(exactly = 0) {
            mEventObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for SearchItem intent when throwing exception - should return Error state`() {
        val query = "Nintendo"
        val exception = mockk<RuntimeException>()

        val intent: Observable<SearchIntent> =
            Observable.just(
                SearchIntent.SearchItem(query)
            )

        val composer = ObservableTransformer<SearchIntent, SearchViewState> {
            Observable.just(
                SearchViewState.SearchItem.InFlight,
                SearchViewState.SearchItem.Error(exception)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(SearchViewState.SearchItem.InFlight)
            mStateObserver.onChanged(SearchViewState.SearchItem.Error(exception))
        }

        verify(exactly = 0) {
            mEventObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for SelectItem intent - should return ItemSelected state`() {
        val item = mockk<Item>()

        val intent: Observable<SearchIntent> =
            Observable.just(
                SearchIntent.SelectItem(item)
            )

        val composer = ObservableTransformer<SearchIntent, SearchViewState> {
            Observable.just(
                SearchViewState.ItemSelected(item)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mEventObserver.onChanged(SearchViewState.ItemSelected(item))
        }

        verify(exactly = 0) {
            mStateObserver.onChanged(any())
        }
    }
}