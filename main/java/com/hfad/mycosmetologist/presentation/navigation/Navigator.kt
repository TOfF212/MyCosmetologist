package com.hfad.mycosmetologist.presentation.navigation

import androidx.compose.runtime.mutableStateListOf
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Navigator (
    startDestination: AppScreen
){

    val backStack = mutableStateListOf(startDestination)

    fun goTo(screen: AppScreen) {
        backStack.add(screen)
    }

    fun goBack() {
        if (backStack.size>1){
            backStack.removeLast()
        }
    }

    fun replaceRoot(screen: AppScreen){
        backStack.clear()
        backStack.add(screen)
    }
}