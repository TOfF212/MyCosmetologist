package com.hfad.mycosmetologist.presentation.navigation

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import dagger.hilt.android.scopes.ActivityRetainedScoped

@ActivityRetainedScoped
class Navigator {
    val backStack: SnapshotStateList<AppScreen> = mutableStateListOf()

    fun goTo(screen: AppScreen) {
        backStack.add(screen)
        Log.d("Navigator", "Navigate go to: $screen")
    }

    fun replaceScreen(screen: AppScreen) {
        backStack.removeLast()
        backStack.add(screen)
        Log.d("Navigator", "Navigate replace screen with: $screen")
    }

    fun setRoot(screen: AppScreen) {
        if (backStack.isEmpty()) {
            backStack.add(screen)
        }
        Log.d("Navigator", "Navigate set Root: ${backStack.get(backStack.lastIndex)}")
    }

    fun goBack() {
        if (backStack.size > 0) {
            backStack.removeLast()
        }
        Log.d("Navigator", "Navigate go back to: ${backStack.get(backStack.lastIndex)}")
    }

    fun replaceRoot(screen: AppScreen) {
        backStack.clear()
        backStack.add(screen)
        Log.d("Navigator", "Navigate replace with: $screen")
    }
}
