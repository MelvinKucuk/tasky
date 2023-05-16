package com.example.tasky.login.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tasky.R
import com.example.tasky.ui.theme.Black
import com.example.tasky.ui.theme.LightGray
import com.example.tasky.ui.theme.TaskyTheme

@Composable
fun LoginScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Black,

        ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(top = 46.dp),
                text = stringResource(R.string.login_greetings),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.size(size = 46.dp))

            Scaffold(
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 25.dp,
                            topEnd = 25.dp
                        )
                    ),
                containerColor = Color.White
            ) { padding ->
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.size(50.dp))
                    TaskyTextField()
                    Spacer(modifier = Modifier.size(16.dp))
                    TaskyTextField()
                    Spacer(modifier = Modifier.size(25.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .padding(horizontal = 16.dp),
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Black,
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = stringResource(R.string.login_main_button),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.padding(bottom = 60.dp),
                        text = stringResource(R.string.login_sign_up),
                        color = LightGray,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TaskyTextField(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf(TextFieldValue()) }

    TextField(
        value = text,
        onValueChange = {
            text = it
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    TaskyTheme {
        LoginScreen()
    }
}