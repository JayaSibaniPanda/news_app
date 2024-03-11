package com.example.newsapplication.feature

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newsapplication.Constant.CATEGORY_SCREEN
import com.example.newsapplication.Constant.MAIN_SCREEN
import com.example.newsapplication.R
import com.example.newsapplication.model.sharedpreference.PreferenceManager
import com.example.newsapplication.ui.view.TopBar
import java.util.Locale

@SuppressLint("RememberReturnType")
@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    val pref =  remember { PreferenceManager(context) }
    
    Column {
        TopBar(heading = "Country List")
        CountryList(navController = navController, preferenceManager = pref)
    }
}

@Composable
fun CountryListItem(countryText: String, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = { onItemClick(countryText) })
            .background(colorResource(id = R.color.white))
            .height(57.dp)
            .fillMaxWidth()
            .padding(PaddingValues(8.dp, 16.dp))
    ) {
        Text(text = countryText, fontSize = 18.sp, color = Color.DarkGray)
    }
}

@Composable
fun CountryList(navController: NavController, preferenceManager: PreferenceManager) {
    val countries = getListOfCountries()
    val countryList = countries.first
    val countryCode = countries.second
    LazyColumn(modifier = Modifier.fillMaxWidth()
                .padding(10.dp)) {
        itemsIndexed(countryList) {index, filteredCountry ->
            CountryListItem(
                countryText = filteredCountry,
                onItemClick = {
                    preferenceManager.saveData("country", countryCode[index])
                    navController.navigate(CATEGORY_SCREEN) {
                        popUpTo(MAIN_SCREEN) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

fun getListOfCountries(): Pair<ArrayList<String>, Array<String>> {
    val isoCountryCodes = Locale.getISOCountries()
    val countryListWithEmojis = ArrayList<String>()
    for (countryCode in isoCountryCodes) {
        val locale = Locale("", countryCode)
        val countryName = locale.displayCountry
        val flagOffset = 0x1F1E6
        val asciiOffset = 0x41
        val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
        val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset
        val flag =
            (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
        countryListWithEmojis.add("$countryName $flag")
    }
    return Pair(countryListWithEmojis,isoCountryCodes)
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    MainScreen(navController = navController)
}

@Preview(showBackground = true)
@Composable
fun CountryListPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val pref = remember { PreferenceManager(context) }
    CountryList(navController = navController, pref)
}


@Preview(showBackground = true)
@Composable
fun CountryListItemPreview() {
    CountryListItem(countryText = "United States 🇺🇸", onItemClick = { })
}
