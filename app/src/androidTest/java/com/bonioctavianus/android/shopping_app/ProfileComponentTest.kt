package com.bonioctavianus.android.shopping_app

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.profile.ProfileFragment
import com.bonioctavianus.android.shopping_app.ui.profile.ProfileViewState
import kotlinx.android.synthetic.main.fragment_profile.*
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class ProfileComponentTest {

    @Test
    fun testRenderForEmptyState() {
        val mockNavController = Mockito.mock(NavController::class.java)

        val scenario =
            launchFragmentInContainer<ProfileFragment>(factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return ProfileFragment().also { fragment ->
                        fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                            if (viewLifecycleOwner != null) {
                                Navigation.setViewNavController(
                                    fragment.requireView(),
                                    mockNavController
                                )
                            }
                        }
                    }
                }
            }, themeResId = R.style.AppTheme)

        scenario.onFragment { fragment ->
            val state = ProfileViewState.GetPurchasedItems.Empty

            fragment.component_profile.renderState(state)
        }

        Espresso.onView(ViewMatchers.withId(R.id.view_empty))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            )

        Espresso.onView(ViewMatchers.withId(R.id.view_error))
            .check(
                ViewAssertions.matches(
                    not(ViewMatchers.isDisplayed())
                )
            )

        Espresso.onView(ViewMatchers.withId(R.id.list_item))
            .check(
                ViewAssertions.matches(
                    not(ViewMatchers.isDisplayed())
                )
            )
    }

    @Test
    fun testRenderForLoadedState() {
        val mockNavController = Mockito.mock(NavController::class.java)

        val scenario =
            launchFragmentInContainer<ProfileFragment>(factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return ProfileFragment().also { fragment ->
                        fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                            if (viewLifecycleOwner != null) {
                                Navigation.setViewNavController(
                                    fragment.requireView(),
                                    mockNavController
                                )
                            }
                        }
                    }
                }
            }, themeResId = R.style.AppTheme)

        scenario.onFragment { fragment ->
            val items = emptyList<Item>()
            val state = ProfileViewState.GetPurchasedItems.Loaded(items)

            fragment.component_profile.renderState(state)
        }

        Espresso.onView(ViewMatchers.withId(R.id.view_empty))
            .check(
                ViewAssertions.matches(
                    not(ViewMatchers.isDisplayed())
                )
            )

        Espresso.onView(ViewMatchers.withId(R.id.view_error))
            .check(
                ViewAssertions.matches(
                    not(ViewMatchers.isDisplayed())
                )
            )

        Espresso.onView(ViewMatchers.withId(R.id.list_item))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            )
    }

    @Test
    fun testRenderForErrorState() {
        val mockNavController = Mockito.mock(NavController::class.java)

        val scenario =
            launchFragmentInContainer<ProfileFragment>(factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return ProfileFragment().also { fragment ->
                        fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                            if (viewLifecycleOwner != null) {
                                Navigation.setViewNavController(
                                    fragment.requireView(),
                                    mockNavController
                                )
                            }
                        }
                    }
                }
            }, themeResId = R.style.AppTheme)

        scenario.onFragment { fragment ->
            val exception = RuntimeException("Booom!")
            val state = ProfileViewState.GetPurchasedItems.Error(exception)

            fragment.component_profile.renderState(state)
        }

        Espresso.onView(ViewMatchers.withId(R.id.view_empty))
            .check(
                ViewAssertions.matches(
                    not(ViewMatchers.isDisplayed())
                )
            )

        Espresso.onView(ViewMatchers.withId(R.id.view_error))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            )

        Espresso.onView(ViewMatchers.withId(R.id.list_item))
            .check(
                ViewAssertions.matches(
                    not(ViewMatchers.isDisplayed())
                )
            )
    }
}