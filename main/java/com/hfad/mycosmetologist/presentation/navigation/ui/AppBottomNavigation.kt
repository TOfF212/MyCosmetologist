package com.hfad.mycosmetologist.presentation.navigation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hfad.mycosmetologist.R
import com.hfad.mycosmetologist.presentation.navigation.AppScreen
import com.hfad.mycosmetologist.presentation.navigation.Navigator

@Composable
fun AppBottomNavigation(navigator: Navigator) {
    val currentScreen = navigator.backStack.last()
    Surface(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(28.dp),
        tonalElevation = 8.dp
    ) {
        NavigationBar {
            NavigationBarItem(
                selected = (currentScreen is AppScreen.Home),
                onClick = { navigator.goTo(AppScreen.Home) },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.outline_family_home_24),
                        contentDescription = "home",
                    )
                },
                label = {
                    Text(
                        text = "Главная",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
                    selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
                    selectedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                    disabledIconColor = MaterialTheme.colorScheme.tertiaryContainer,
                    disabledTextColor = MaterialTheme.colorScheme.onBackground
                )
            )
            NavigationBarItem(
                selected = (currentScreen is AppScreen.Clients),
                onClick = { navigator.goTo(AppScreen.Clients) },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.sharp_add_circle_24),
                        contentDescription = "add",
                    )
                },
                label = {
                    Text(
                        text = "Добавить",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
                    selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
                    selectedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                    disabledIconColor = MaterialTheme.colorScheme.tertiaryContainer,
                    disabledTextColor = MaterialTheme.colorScheme.onBackground
                )
            )
            NavigationBarItem(
                selected = (currentScreen is AppScreen.Profile),
                onClick = { navigator.goTo(AppScreen.Profile) },
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.sharp_account_circle_24),
                        contentDescription = "profile",
                    )
                },
                label = {
                    Text(
                        text = "Профиль",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
                    selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
                    selectedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                    disabledIconColor = MaterialTheme.colorScheme.tertiaryContainer,
                    disabledTextColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}
