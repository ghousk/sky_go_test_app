//*********************************************************************
// Created by Ghous Khan on 2020-02-19.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.sky_go_test_app.ui.datalistitems

import androidx.lifecycle.MutableLiveData
import androidx.databinding.DataBindingComponent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.navigation.NavController
import com.innovativequest.sky_go_test_app.R
import com.innovativequest.sky_go_test_app.binding.FragmentBindingAdapters
import com.innovativequest.sky_go_test_app.testing.SingleFragmentActivity
import com.innovativequest.sky_go_test_app.model.DataListItemResponse
import com.innovativequest.sky_go_test_app.model.Resource
import com.innovativequest.sky_go_test_app.util.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class DataListItemsFragmentTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule(SingleFragmentActivity::class.java, true, true)
    @Rule
    @JvmField
    val executorRule = TaskExecutorWithIdlingResourceRule()
    @Rule
    @JvmField
    val countingAppExecutors = CountingAppExecutorsRule()
    @Rule
    @JvmField
    val dataBindingIdlingResourceRule = DataBindingIdlingResourceRule(activityRule)
    private lateinit var preferencesManager: PreferencesManager
    private lateinit var viewModel: DataListItemsViewModel
    private lateinit var mockBindingAdapter: FragmentBindingAdapters
    private val repoListData = MutableLiveData<Resource<DataListItemResponse>>()
    private val testFragment = TestDataListItemsFragment().apply {
    }

    @Before
    fun init() {
        preferencesManager = mock(PreferencesManager::class.java)
        viewModel = mock(DataListItemsViewModel::class.java)
        `when`(viewModel.dataListItems).thenReturn(repoListData)
        mockBindingAdapter = mock(FragmentBindingAdapters::class.java)

        testFragment.appExecutors = countingAppExecutors.appExecutors
        testFragment.viewModelFactory = ViewModelUtil.createFor(viewModel)
        testFragment.dataBindingComponent = object : DataBindingComponent {
            override fun getFragmentBindingAdapters(): FragmentBindingAdapters {
                return mockBindingAdapter
            }
        }
        activityRule.activity.setFragment(testFragment)
        activityRule.runOnUiThread {
            testFragment.binding.repoList.itemAnimator = null
        }
        EspressoTestUtil.disableProgressBarAnimations(activityRule)
    }

    @Test
    fun loadRepos() {
        val repos = setRepos(2)
        repos.items.let {
            for (pos in repos.items.indices) {
                val repo = repos.items[pos]
                onView(listMatcher().atPosition(pos)).apply {
                    check(matches(hasDescendant(withText(repo.title))))
                    check(matches(hasDescendant(withText(repo.genre))))
                }
            }
        }

        setRepos(3).items.let {
            val repo3 = setRepos(3).items[2]
            onView(listMatcher().atPosition(2)).check(
                matches(hasDescendant(withText(repo3.title)))
            )
        }
    }

    @Test
    fun nullRepoList() {
        repoListData.postValue(null)
        onView(listMatcher().atPosition(0)).check(doesNotExist())
    }

    private fun listMatcher() = RecyclerViewMatcher(R.id.repo_list)

    private fun setRepos(count: Int): DataListItemResponse {
        val dataListItemResponse =   TestUtil.createRepos(count, "2017", "Action", 11241,
            "Jumanji: welcome to the jungle",
            "https://image.tmdb.org/t/p/w370_and_h556_bestv2/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg")
        repoListData.postValue(Resource.success(dataListItemResponse))
        return dataListItemResponse
    }

    class TestDataListItemsFragment : DataListItemsFragment() {
        val navController = mock<NavController>()
        override fun navController() = navController
    }
}