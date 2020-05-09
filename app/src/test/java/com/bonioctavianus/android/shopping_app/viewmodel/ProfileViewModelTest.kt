package com.bonioctavianus.android.shopping_app.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.profile.ProfileIntent
import com.bonioctavianus.android.shopping_app.ui.profile.ProfileInteractor
import com.bonioctavianus.android.shopping_app.ui.profile.ProfileViewModel
import com.bonioctavianus.android.shopping_app.ui.profile.ProfileViewState
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
class ProfileViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val mStateObserver: Observer<ProfileViewState> = mockk(relaxed = true)
    private val mEventObserver: Observer<ProfileViewState> = mockk(relaxed = true)
    private val mInteractor: ProfileInteractor = mockk()
    private val mViewModel: ProfileViewModel = ProfileViewModel(mInteractor)

    @Before
    fun setup() {
        mViewModel.state().observeForever(mStateObserver)
        mViewModel.event()?.observeForever(mEventObserver)
    }

    @Test
    fun `bindIntent() for GetPurchasedItems intent - should return Empty state`() {
        val intent: Observable<ProfileIntent> =
            Observable.just(
                ProfileIntent.GetPurchasedItems
            )

        val composer = ObservableTransformer<ProfileIntent, ProfileViewState> {
            Observable.just(
                ProfileViewState.GetPurchasedItems.InFlight,
                ProfileViewState.GetPurchasedItems.Empty
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(ProfileViewState.GetPurchasedItems.InFlight)
            mStateObserver.onChanged(ProfileViewState.GetPurchasedItems.Empty)
        }

        verify(exactly = 0) {
            mEventObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for GetPurchasedItems intent - should return Loaded state`() {
        val items = mockk<List<Item>>()

        val intent: Observable<ProfileIntent> =
            Observable.just(
                ProfileIntent.GetPurchasedItems
            )

        val composer = ObservableTransformer<ProfileIntent, ProfileViewState> {
            Observable.just(
                ProfileViewState.GetPurchasedItems.InFlight,
                ProfileViewState.GetPurchasedItems.Loaded(items)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(ProfileViewState.GetPurchasedItems.InFlight)
            mStateObserver.onChanged(ProfileViewState.GetPurchasedItems.Loaded(items))
        }

        verify(exactly = 0) {
            mEventObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for GetPurchasedItems intent when throwing exception - should return Error state`() {
        val exception = mockk<RuntimeException>()

        val intent: Observable<ProfileIntent> =
            Observable.just(
                ProfileIntent.GetPurchasedItems
            )

        val composer = ObservableTransformer<ProfileIntent, ProfileViewState> {
            Observable.just(
                ProfileViewState.GetPurchasedItems.InFlight,
                ProfileViewState.GetPurchasedItems.Error(exception)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mStateObserver.onChanged(ProfileViewState.GetPurchasedItems.InFlight)
            mStateObserver.onChanged(ProfileViewState.GetPurchasedItems.Error(exception))
        }

        verify(exactly = 0) {
            mEventObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for SelectItem intent when throwing exception - should return ItemSelected state`() {
        val item = mockk<Item>()

        val intent: Observable<ProfileIntent> =
            Observable.just(
                ProfileIntent.SelectItem(item)
            )

        val composer = ObservableTransformer<ProfileIntent, ProfileViewState> {
            Observable.just(
                ProfileViewState.ItemSelected(item)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mEventObserver.onChanged(ProfileViewState.ItemSelected(item))
        }

        verify(exactly = 0) {
            mStateObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for SelectMenuSignOut intent - should return Success state`() {
        val intent: Observable<ProfileIntent> =
            Observable.just(
                ProfileIntent.SelectMenuSignOut
            )

        val composer = ObservableTransformer<ProfileIntent, ProfileViewState> {
            Observable.just(
                ProfileViewState.SignOut.Success
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mEventObserver.onChanged(ProfileViewState.SignOut.Success)
        }

        verify(exactly = 0) {
            mStateObserver.onChanged(any())
        }
    }

    @Test
    fun `bindIntent() for SelectMenuSignOut intent when throwing exception - should return Error state`() {
        val exception = mockk<RuntimeException>()

        val intent: Observable<ProfileIntent> =
            Observable.just(
                ProfileIntent.SelectMenuSignOut
            )

        val composer = ObservableTransformer<ProfileIntent, ProfileViewState> {
            Observable.just(
                ProfileViewState.SignOut.Error(exception)
            )
        }

        every { mInteractor.compose() } returns composer

        mViewModel.bindIntent(intent)

        verify {
            mInteractor.compose()
            mEventObserver.onChanged(ProfileViewState.SignOut.Error(exception))
        }

        verify(exactly = 0) {
            mStateObserver.onChanged(any())
        }
    }
}