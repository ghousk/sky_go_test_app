//*********************************************************************
// Created by Ghous Khan on 2020-02-19.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.sky_go_test_app.ui.datalistitems

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.core.widget.addTextChangedListener
import androidx.databinding.Bindable
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.innovativequest.sky_go_test_app.AppExecutors
import com.innovativequest.sky_go_test_app.R
import com.innovativequest.sky_go_test_app.binding.FragmentDataBindingComponent
import com.innovativequest.sky_go_test_app.databinding.DataListItemsFragmentBinding
import com.innovativequest.sky_go_test_app.di.Injectable
import com.innovativequest.sky_go_test_app.testing.OpenForTesting
import com.innovativequest.sky_go_test_app.ui.common.DataListItemsAdapter
import com.innovativequest.sky_go_test_app.ui.common.RetryCallback
import com.innovativequest.sky_go_test_app.util.autoCleared
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.innovativequest.sky_go_test_app.model.DataListItem
import javax.inject.Inject

@OpenForTesting
class DataListItemsFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var appExecutors: AppExecutors

    var mItemsList: ArrayList<DataListItem> = ArrayList()

    var binding by autoCleared<DataListItemsFragmentBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val dataListItemsViewModel: DataListItemsViewModel by viewModels {
        viewModelFactory
    }
    private var adapter by autoCleared<DataListItemsAdapter>()
    private var handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<DataListItemsFragmentBinding>(
            inflater,
            R.layout.data_list_items_fragment,
            container,
            false,
            dataBindingComponent
        )
        dataBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                dataListItemsViewModel.retry()
            }
        }
        binding = dataBinding
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)
        // When the image is loaded, set the image request listener to start the transaction
        binding.imageRequestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                startPostponedEnterTransition()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                startPostponedEnterTransition()
                return false
            }
        }

        binding.textChangeListener  = object: TextWatcher{
            override fun afterTextChanged(editableString: Editable?) {
                adapter.submitList(mItemsList.filter {
                        item -> (item.title != null && item.genre != null) &&
                                      ( item.title.contains(editableString.toString(), true) ||
                                        item.genre.contains(editableString.toString(), true) )
                })
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Do Nothing
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Do Nothing
            }

        }

        // Animation Watchdog - Make sure we don't wait longer than a second for the Glide image
        handler.postDelayed(1000) {
            startPostponedEnterTransition()
        }
        postponeEnterTransition()
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.lifecycleOwner = viewLifecycleOwner
        val rvAdapter = DataListItemsAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors
        ) { dataListItem ->
            navController().navigate(DataListItemsFragmentDirections.showRepo(dataListItem.id.toString()))
        }
        binding.repoList.layoutManager = GridLayoutManager(context, 3)
        binding.repoList.adapter = rvAdapter
        this.adapter = rvAdapter

        initRepoList()
    }

    private fun initRepoList() {
        dataListItemsViewModel.dataListItems.observe(viewLifecycleOwner, Observer {
            it?.data?.items?.let {items ->
                mItemsList = ArrayList(items)
                adapter.submitList(mItemsList)
            }
        })
    }

    /**
     * Created to be able to override in tests
     */
    fun navController() = findNavController()
}
