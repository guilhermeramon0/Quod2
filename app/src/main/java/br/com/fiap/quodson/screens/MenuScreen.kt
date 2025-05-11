package br.com.fiap.quodson.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.quodson.R
import br.com.fiap.quodson.ui.theme.QuodsonTheme

@Composable
fun MenuScreen(navController: NavController) {
    QuodsonTheme {

        Box(
            modifier = Modifier
                .fillMaxSize()
        )
        {
            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = "Background",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )
        }
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
            ArrowBack(navController)
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "O que você quer fazer?",
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 48.dp)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(colorResource(id = R.color.preto_transparente))
                )
                {
                        IconeComDescricao(
                            icone = R.drawable.documento,
                            descricao = "Análise de Documento",
                            navController = navController,
                            navigate = "documento"
                        )
                    Row {
                        IconeComDescricao(
                            icone = R.drawable.biometria_digital,
                            descricao = "Biometria Digital",
                            navController = navController,
                            navigate = "biometria_digital"
                        )
                        IconeComDescricao(
                            icone = R.drawable.biometria_facial,
                            descricao = "Biometria Facial",
                            navController = navController,
                            navigate = "biometria_facial"
                        )
                    }
                }
                }
            }
    }
}

@Composable
fun IconeComDescricao(icone: Int, descricao: String, navController: NavController, navigate: String) {
Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.padding(all = 20.dp)
        .clickable {
        navController.navigate(navigate)
    }
) {
    Image(
        painter = painterResource(id = icone),
        contentDescription = descricao,
        contentScale = ContentScale.FillBounds,
        modifier = Modifier.padding(bottom = 10.dp)
    )
    Text(
        text = descricao,
        color = Color.White,
        modifier = Modifier.width(96.dp),
        textAlign = TextAlign.Center
    )
}
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen(rememberNavController())
}