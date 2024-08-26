package com.antoan.kodetest.main.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import com.antoan.kodetest.common.presentation.Event
import com.antoan.kodetest.common.presentation.model.UIEmployee
import com.antoan.kodetest.main.domain.model.SortParameter

data class MainUiState(
  val isLoading: Boolean = true,
  val isSearchMode: Boolean = false,
  val noSearchResult: Boolean = false,
  val searchQuery: String = "",
  val employees: List<UIEmployee> = emptyList(),
  val order: SortParameter = SortParameter.ALPHABET,
  val failure: Event<Throwable>? = null,
  val dividerIndex: Int = NOT_DIVIDER_INDEX
) {
  companion object {
    const val NOT_DIVIDER_INDEX = -1
  }
}
