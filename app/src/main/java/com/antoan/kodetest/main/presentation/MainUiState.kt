package com.antoan.kodetest.main.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import com.antoan.kodetest.common.presentation.Event
import com.antoan.kodetest.common.presentation.model.UIEmployee
import com.antoan.kodetest.main.domain.model.SortParameter

data class MainUiState(
  val isLoading: Boolean = true,
  val employees: List<UIEmployee> = emptyList(),
  val order: SortParameter = SortParameter.ALPHABET,
//  val currentDepartment: String = "ALL",
  val failure: Event<Throwable>? = null,
  val dividerIndex: Int = 0
)
