package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text as Text1

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConverterApp() {
    var inputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("mm") }
    var outputUnit by remember { mutableStateOf("cm") }
    val units = listOf("mm", "cm", "dm", "m", "dam", "hm", "km")
    var result by remember { mutableStateOf("") }

    fun convert(value: Double, from: String, to: String): Double {
        val meterValue = when (from) {
            "mm" -> value / 1000
            "cm" -> value / 100
            "dm" -> value / 10
            "m" -> value
            "dam" -> value * 10
            "hm" -> value * 100
            "km" -> value * 1000
            else -> value
        }
        return when (to) {
            "mm" -> meterValue * 1000
            "cm" -> meterValue * 100
            "dm" -> meterValue * 10
            "m" -> meterValue
            "dam" -> meterValue / 10
            "hm" -> meterValue / 100
            "km" -> meterValue / 1000
            else -> meterValue
        }
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text1(text = "Convertisseur d'unités de mesure", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = inputValue,
            onValueChange = { inputValue = it },
            label = { Text1("Valeur à convertir") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            UnitDropdownMenu(
                units = units,
                selectedUnit = inputUnit,
                onUnitSelected = { inputUnit = it }
            )

            UnitDropdownMenu(
                units = units,
                selectedUnit = outputUnit,
                onUnitSelected = { outputUnit = it }
            )
        }

        Button(onClick = {
            val value = inputValue.toDoubleOrNull()
            if (value != null) {
                result = convert(value, inputUnit, outputUnit).toString()
            } else {
                result = "Invalid input"
            }
        }) {
            Text1("Convertir")
        }

        Text1(text = "Résultat: $result", fontSize = 20.sp)
    }
}

@Composable
fun UnitDropdownMenu(units: List<String>, selectedUnit: String, onUnitSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize()) {
        TextButton(onClick = { expanded = !expanded }) {
            Text1(text = selectedUnit)
            //Icon(imageVector = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            units.forEach { unit ->
                DropdownMenuItem(onClick = {
                    onUnitSelected(unit)
                    expanded = false
                }) {
                    //Text1(text = unit)
                }
            }
        }
    }
}

fun DropdownMenuItem(onClick: () -> Unit, interactionSource: () -> Unit) {
    TODO("Not yet implemented")
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UnitConverterPreview() {
    UnitConverterApp()
}