package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.ui.theme.MyApplicationTheme

// ------------------------------
// 김건보의 To-Do List 앱
// ------------------------------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    KimGeonboToDoApp() // 실제 앱 UI 호출
                }
            }
        }
    }
}

// ------------------------------
// 메인 To-Do 앱 UI
// ------------------------------
@Composable
fun KimGeonboToDoApp() {
    // 상태 변수 정의
    var text by remember { mutableStateOf("") } // 입력창 텍스트
    var todoList by remember { mutableStateOf(listOf<String>()) } // 할 일 목록 리스트
    var message by remember { mutableStateOf("오늘도 화이팅 💪") } // 응원 문구

    // 랜덤으로 보여줄 응원 문구 리스트
    val messages = listOf(
        "좋아요! 멋져요 💙",
        "건보는 할 수 있어!",
        "할 일을 하나씩 끝내보자 😎",
        "완벽해요 👍",
        "오늘도 화이팅 💪"
    )

    // 전체 화면 구성
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            // 앱 제목
            Text(
                text = "📝 건보의 할 일 관리 앱",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // 입력창 + 추가 버튼
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // 텍스트 입력창
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("할 일을 입력하세요") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        if (text.isNotBlank()) {
                            todoList = todoList + text
                            text = ""
                            message = messages.random() // 랜덤 응원 문구
                        }
                    })
                )

                // 추가 버튼
                Button(
                    onClick = {
                        if (text.isNotBlank()) {
                            todoList = todoList + text
                            text = ""
                            message = messages.random()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("추가")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 응원 문구 출력
            Text(
                text = message,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 할 일 목록 표시
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                todoList.forEachIndexed { index, item ->
                    KimGeonboToDoItem(
                        text = item,
                        onDelete = {
                            todoList = todoList.filterIndexed { i, _ -> i != index }
                        }
                    )
                }
            }
        }

        // 화면 하단 서명
        Text(
            text = "© 2025 Kim Gunbo",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

// ------------------------------
// 개별 할 일 카드 UI
// ------------------------------
@Composable
fun KimGeonboToDoItem(text: String, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text, fontSize = 18.sp)
            TextButton(onClick = onDelete) {
                Text("삭제", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

// ------------------------------
// 미리보기
// ------------------------------
@Preview(showBackground = true)
@Composable
fun PreviewKimGeonboToDoApp() {
    MyApplicationTheme {
        KimGeonboToDoApp()
    }
}
