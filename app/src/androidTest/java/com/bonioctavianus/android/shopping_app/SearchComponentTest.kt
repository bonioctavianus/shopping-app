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
import com.bonioctavianus.android.shopping_app.ui.search.SearchFragment
import com.bonioctavianus.android.shopping_app.ui.search.SearchViewState
import kotlinx.android.synthetic.main.fragment_search.*
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class SearchComponentTest {

    @Test
    fun testRenderForEmptyState() {
        val mockNavController = Mockito.mock(NavController::class.java)

        val scenario =
            launchFragmentInContainer<SearchFragment>(factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return SearchFragment().also { fragment ->
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
            val state = SearchViewState.SearchItem.Empty

            fragment.component_search.renderState(state)
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
            launchFragmentInContainer<SearchFragment>(factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return SearchFragment().also { fragment ->
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
            val state = SearchViewState.SearchItem.Loaded(items)

            fragment.component_search.renderState(state)
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
            launchFragmentInContainer<SearchFragment>(factory = object : FragmentFactory() {
                override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                    return SearchFragment().also { fragment ->
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
            val state = SearchViewState.SearchItem.Error(exception)

            fragment.component_search.renderState(state)
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