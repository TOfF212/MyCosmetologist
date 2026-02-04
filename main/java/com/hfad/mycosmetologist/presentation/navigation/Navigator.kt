package com.hfad.mycosmetologist.presentation.navigation

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Navigator (){

    val backStack: SnapshotStateList<AppScreen> = mutableStateListOf<AppScreen>()

    fun goTo(screen: AppScreen) {
        backStack.add(screen)
        Log.d("Navigator", "Navigate go to: $screen")

    }


    fun setRoot(screen: AppScreen) {
        if (backStack.isEmpty()) {
            backStack.add(screen)
        }
        Log.d("Navigator", "Navigate set Root: ${backStack.get(backStack.lastIndex)}")

    }

    fun goBack() {
        if (backStack.size>1){
            backStack.removeLast()
        }
        Log.d("Navigator", "Navigate go back to: ${backStack.get(backStack.lastIndex)}")

    }

    fun replaceRoot(screen: AppScreen){

        backStack.clear()
        backStack.add(screen)
        Log.d("Navigator", "Navigate replace with: $screen")

    }
}