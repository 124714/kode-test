package com.antoan.kodetest.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle
): ViewModel() {

  val userId: StateFlow<String?> = savedStateHandle.getStateFlow("userId", null)
}