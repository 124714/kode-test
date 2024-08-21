package com.antoan.kodetest.main.presentation

import com.antoan.kodetest.common.presentation.model.UIEmployee

data class MainViewState(
  val isLoading: Boolean = true,
  val employees: List<UIEmployee> = emptyList(),
  val failure: Throwable? = null
)
