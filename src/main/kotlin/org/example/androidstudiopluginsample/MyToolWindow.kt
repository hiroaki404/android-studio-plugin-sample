package org.example.androidstudiopluginsample

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import org.jetbrains.jewel.bridge.addComposeTab
import org.jetbrains.jewel.foundation.ExperimentalJewelApi
import org.jetbrains.jewel.ui.component.OutlinedButton
import org.jetbrains.jewel.ui.component.Text
import org.jetbrains.jewel.ui.component.TextField

class MyToolWindowFactory : ToolWindowFactory {
    override fun shouldBeAvailable(project: Project) = true

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        toolWindow.addComposeTab("My Tool Window") {
            LaunchedEffect(Unit) {
                // initial data loading
            }

            MyToolWindowContent()
        }
    }
}

@OptIn(ExperimentalJewelApi::class)
@Composable
private fun MyToolWindowContent() {
    val input1 = rememberTextFieldState()
    val input2 = rememberTextFieldState()

    val result by remember {
        derivedStateOf {
            val text1 = input1.text.toString()
            val text2 = input2.text.toString()
            val num1 = text1.toDoubleOrNull()
            val num2 = text2.toDoubleOrNull()
            when {
                num1 == null && text1.isNotEmpty() -> "エラー: 最初の値が無効です"
                num2 == null && text2.isNotEmpty() -> "エラー: 2番目の値が無効です"
                num1 != null && num2 != null -> {
                    val sum = num1 + num2
                    val display = if (sum == sum.toLong().toDouble()) sum.toLong().toString() else sum.toString()
                    "結果: $display"
                }

                else -> "数値を入力してください"
            }
        }
    }

    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("足し算")

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                state = input1,
                modifier = Modifier.width(120.dp),
                placeholder = { Text("数値1") },
            )
            Text("+")
            TextField(
                state = input2,
                modifier = Modifier.width(120.dp),
                placeholder = { Text("数値2") },
            )
        }

        Text(result)

        OutlinedButton(
            onClick = {
                input1.edit { delete(0, length) }
                input2.edit { delete(0, length) }
            },
        ) { Text("クリア") }
    }
}
