package org.example.androidstudiopluginsample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import org.jetbrains.jewel.bridge.addComposeTab
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

@Composable
private fun MyToolWindowContent() {
    var input1 by remember { mutableStateOf("") }
    var input2 by remember { mutableStateOf("") }

    val result = remember(input1, input2) {
        val num1 = input1.toDoubleOrNull()
        val num2 = input2.toDoubleOrNull()
        when {
            num1 == null && input1.isNotEmpty() -> "エラー: 最初の値が無効です"
            num2 == null && input2.isNotEmpty() -> "エラー: 2番目の値が無効です"
            num1 != null && num2 != null -> {
                val sum = num1 + num2
                val display = if (sum == sum.toLong().toDouble()) sum.toLong().toString() else sum.toString()
                "結果: $display"
            }
            else -> "数値を入力してください"
        }
    }

    Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("足し算")

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                value = input1,
                onValueChange = { input1 = it },
                modifier = Modifier.width(120.dp),
                placeholder = { Text("数値1") },
            )
            Text("+")
            TextField(
                value = input2,
                onValueChange = { input2 = it },
                modifier = Modifier.width(120.dp),
                placeholder = { Text("数値2") },
            )
        }

        Text(result)

        OutlinedButton(
            onClick = {
                input1 = ""
                input2 = ""
            },
        ) { Text("クリア") }
    }
}
