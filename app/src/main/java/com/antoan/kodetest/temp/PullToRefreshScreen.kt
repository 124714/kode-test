package com.antoan.kodetest.temp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.frankieshao.refreshlayout.RefreshLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshScreen(
  modifier: Modifier = Modifier
) {
  var itemCount by remember { mutableIntStateOf(15) }
  var isRefreshing by remember { mutableStateOf(false) }
  val state = rememberPullToRefreshState()
  val coroutineScope = rememberCoroutineScope()
  val onRefresh: () -> Unit = {
    isRefreshing = true
    coroutineScope.launch {
      delay(1500)
      itemCount += 5
      isRefreshing = false
    }
  }



  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Title") },
        // Provide an accessible alternative to trigger refresh.
        actions = {
          IconButton(onClick = onRefresh) {
            Icon(Icons.Filled.Refresh, "Trigger Refresh")
          }
        }
      )
    }
  ) {
    PullToRefreshBox(
      modifier = Modifier.padding(it),
      state = state,
      isRefreshing = isRefreshing,
      onRefresh = onRefresh,
    ) {
      LazyColumn(Modifier.fillMaxSize()) {
        items(itemCount) { ListItem({ Text(text = "Item ${itemCount - it}") }) }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefreshLayoutWithList() {
  var itemCount by remember { mutableIntStateOf(15) }
  var isRefreshing by remember { mutableStateOf(false) }
  val coroutineScope = rememberCoroutineScope()
  val onRefresh: () -> Unit = {
    isRefreshing = true
    coroutineScope.launch {
      delay(1500)
      itemCount += 5
      isRefreshing = false
    }
  }
  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Title") },
        // Provide an accessible alternative to trigger refresh.
        actions = {
          IconButton(onClick = onRefresh) {
            Icon(Icons.Filled.Refresh, "Trigger Refresh")
          }
        }
      )
    }
  ) {
    RefreshLayout(
      modifier = Modifier.padding(it),
      isRefreshing = isRefreshing,
      onRefresh = { onRefresh() },
    ) {
      LazyColumn(Modifier.fillMaxSize()) {
        items(itemCount) {
          ListItem({ Text(text = "Item ${itemCount - it}") })
        }
      }
    }
  }

}