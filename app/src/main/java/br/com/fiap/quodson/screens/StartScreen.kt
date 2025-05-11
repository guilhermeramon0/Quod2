package br.com.fiap.quodson.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.quodson.ui.theme.quickSandSemibold
import br.com.fiap.quodson.R
import br.com.fiap.quodson.ui.theme.QuodsonTheme

@Composable
fun StartScreen(navController: NavController) {
    QuodsonTheme {

        Box(
            modifier = Modifier
                .fillMaxSize()
        )
        {            Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "Background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )}
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.quodson),
                contentDescription = "Logo Quodson",
                modifier = Modifier
                    .padding(vertical = 48.dp)
                    .fillMaxWidth(0.5f)
            )
            Button(
                onClick = {
                    navController.navigate("menu")
                },
                shape = RoundedCornerShape(20),
                modifier = Modifier
                    .padding(top = 64.dp)
                    .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.cor_primaria))
            ) {
                Text(
                    "Entrar",
                    modifier = Modifier
                        .padding(vertical = 6.dp),
                    fontFamily = quickSandSemibold
                )
            }
            Button(
                onClick = {
                    navController.navigate("cadastro")
                },
                shape = RoundedCornerShape(20),
                modifier = Modifier
                    .padding(top = 64.dp)
                    .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.cor_primaria))
            ) {
                Text(
                    "Criar conta",
                    modifier = Modifier
                        .padding(vertical = 6.dp),
                    fontFamily = quickSandSemibold
                )
            }
        }

    }
}

@Preview
@Composable
fun StartScreenPreview() {
    StartScreen(rememberNavController())
}