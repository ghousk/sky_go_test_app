//*********************************************************************
// Created by Ghous Khan on 2020-02-19.
// Innovative Quest Ltd
// Copyright (C) Innovative Quest Ltd All Rights Reserved
// Any copying or reproduction of this software in strictly prohibited.
//*********************************************************************


package com.innovativequest.sky_go_test_app.ui.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.innovativequest.sky_go_test_app.repository.DataListItemsRepository
import com.innovativequest.sky_go_test_app.testing.OpenForTesting
import com.innovativequest.sky_go_test_app.util.AbsentLiveData
import com.innovativequest.sky_go_test_app.model.DataListItemResponse
import com.innovativequest.sky_go_test_app.model.Resource
import javax.inject.Inject

@OpenForTesting
class ItemDetailViewModel @Inject constructor(repository: DataListItemsRepository) : ViewModel() {
    private val _repoId: MutableLiveData<RepoId> = MutableLiveData()
    val repoId: LiveData<RepoId>
        get() = _repoId
    val dataListItemResponseById: LiveData<Resource<DataListItemResponse>> = Transformations
        .switchMap(_repoId) { input ->
            input.ifExists { movieId ->
                repository.loadDataListItemResponseById(movieId)
            }
        }

    val dataListItems: LiveData<Resource<DataListItemResponse>> = repository.loadDataListItemResponses()

    fun retry() {
        val id = _repoId.value?.id
        if (id != null) {
            _repoId.value = RepoId(id)
        }
    }

    fun setId(id: String) {
        val update = RepoId(id)
        if (_repoId.value == update) {
            return
        }
        _repoId.value = update
    }

    data class RepoId(val id: String) {
        fun <T> ifExists(f: (String) -> LiveData<T>): LiveData<T> {
            return if (id.isBlank()) {
                AbsentLiveData.create()
            } else {
                f(id)
            }
        }
    }
}
