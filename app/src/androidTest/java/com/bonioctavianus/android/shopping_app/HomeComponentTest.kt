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
import com.bonioctavianus.android.shopping_app.model.Category
import com.bonioctavianus.android.shopping_app.model.Item
import com.bonioctavianus.android.shopping_app.ui.home.HomeFragment
import com.bonioctavianus.android.shopping_app.ui.home.HomeViewState
import kotlinx.android.synthetic.main.fragment_home.*
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class HomeComponentTest {

    @Test
    fun testRenderForInFlightState() {
        val mockNavController = Mockito.mock(NavController::class.java)

        val scenario =
            launchFragmentInContainer<HomeFragment>(factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return HomeFragment().also { fragment ->
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
            val state = HomeViewState.GetHomeItem.InFlight

            fragment.component_home.renderState(state)
        }

        Espresso.onView(ViewMatchers.withId(R.id.view_in_flight))
            .check(
                ViewAssertions.matches(
                    ViewMatchers.isDisplayed()
                )
            )

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

        Espresso.onView(ViewMatchers.withId(R.id.view_content))
            .check(
                ViewAssertions.matches(
                    not(ViewMatchers.isDisplayed())
                )
            )
    }

    @Test
    fun testRenderForEmptyState() {
        val mockNavController = Mockito.mock(NavController::class.java)

        val scenario =
            launchFragmentInContainer<HomeFragment>(factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return HomeFragment().also { fragment ->
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
            val state = HomeViewState.GetHomeItem.Empty

            fragment.component_home.renderState(state)
        }

        Espresso.onView(ViewMatchers.withId(R.id.view_in_flight))
            .check(
                ViewAssertions.matches(
                    not(ViewMatchers.isDisplayed())
                )
            )

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

        Espresso.onView(ViewMatchers.withId(R.id.view_content))
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
            launchFragmentInContainer<HomeFragment>(factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return HomeFragment().also { fragment ->
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
            val categories = emptyList<Category>()
            val items = emptyList<Item>()
            val state = HomeViewState.GetHomeItem.Loaded(categories, items)

            fragment.component_home.renderState(state)
        }

        Espresso.onView(ViewMatchers.withId(R.id.view_in_flight))
            .check(
                ViewAssertions.matches(
                    not(ViewMatchers.isDisplayed())
                )
            )

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

        Espresso.onView(ViewMatchers.withId(R.id.view_content))
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
            launchFragmentInContainer<HomeFragment>(factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return HomeFragment().also { fragment ->
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
            val state = HomeViewState.GetHomeItem.Error(exception)

            fragment.component_home.renderState(state)
        }

        Espresso.onView(ViewMatchers.withId(R.id.view_in_flight))
            .check(
                ViewAssertions.matches(
                    not(ViewMatchers.isDisplayed())
                )
            )

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

        Espresso.onView(ViewMatchers.withId(R.id.view_content))
            .check(
                ViewAssertions.matches(
                    not(ViewMatchers.isDisplayed())
                )
            )
    }
}