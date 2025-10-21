package com.example.a1_homework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a1_homework.ui.theme._1_homeworkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _1_homeworkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserForm()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserForm() {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var isMale by remember { mutableStateOf(true) }
    var subscribeToNews by remember { mutableStateOf(false) }
    var showSummary by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TextField(
            value = name,
            onValueChange = {
                name = it
                isError = it.isEmpty()
            },
            label = { Text(stringResource(R.string.name_label)) },
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                if (isError) {
                    Text(stringResource(R.string.name_error))
                }
            }
        )

        TextField(
            value = age,
            onValueChange = { if (it.all { char -> char.isDigit() }) age = it },
            label = { Text(stringResource(R.string.age_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.gender_label))
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = isMale,
                    onClick = { isMale = true }
                )
                Text(stringResource(R.string.male))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = !isMale,
                    onClick = { isMale = false }
                )
                Text(stringResource(R.string.female))
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = subscribeToNews,
                onCheckedChange = { subscribeToNews = it }
            )
            Text(stringResource(R.string.subscribe_news))
        }

        Button(
            onClick = { showSummary = true },
            enabled = name.isNotEmpty() && age.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.submit_button))
        }

        if (showSummary) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("${stringResource(R.string.summary_name)}: $name")
                    Text("${stringResource(R.string.summary_age)}: $age")
                    Text("${stringResource(R.string.summary_gender)}: ${if (isMale) stringResource(R.string.male) else stringResource(R.string.female)}")
                    Text("${stringResource(R.string.summary_subscription)}: ${if (subscribeToNews) stringResource(R.string.yes) else stringResource(R.string.no)}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserFormPreview() {
    _1_homeworkTheme {
        UserForm()
    }
}

@Preview(showBackground = true)
@Composable
fun UserFormDarkPreview() {
    _1_homeworkTheme(darkTheme = true) {
        UserForm()
    }
}