package com.iafsd.killyourhabit.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
/**
 * Primary  – color displayed most frequently across your app’s screens and components;
 * Primary Variant – color is used to distinguish elements using primary colors, such as top app bar and the system bar.
 * Secondary – color provides more ways to accent and distinguish your product. Having a secondary color is optional, and should be applied sparingly to accent select parts of your UI;
 * SecondaryVariant – color is used to distinguish elements using secondary colours;
 * Background – color appears behind scrollable content;
 * Surface – color uses on surfaces of components, like cards and menus;
 * Error – color used for indicating an error.
 * OnPrimary – color of text and icons displayed on top of the primary color.
 * OnSecondary – color of text and icons displayed on top of the secondary color;
 * OnBackground – color of text and icons displayed on top of the background color;
 * OnSurface – color of text and icons displayed on top of the surface color;
 * OnError – color of text and icons displayed on top of the error color.
 * IsLight – Whether these colors are considered as a “light” or “dark” set of colors.
 * */
private val DarkColorPalette = darkColors(
    //main background color
    primary = PrimaryBlue700,
    primaryVariant = PrimaryBlue600,
    //used for text color
    secondary = textColorDark,
    //background of sudoku board
    surface = SecondaryOrange,
    //grid lines of sudoku board
    onPrimary = SecondaryOrange,
    onSurface = SecondaryOrange,
)

private val LightColorPalette = lightColors(
    primary = PrimaryBlue700,
    primaryVariant = PrimaryBlue600,
    secondary =  SecondaryOrange,
    onPrimary = SecondaryOrange,
   /* //background of body board
    surface = SecondaryOrange,
    //grid lines of sudoku board
    onPrimary = PrimaryBlue600,
    onSurface = SecondaryOrange*/

  /*  background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,*/

)

@Composable
fun KillYourHabitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}