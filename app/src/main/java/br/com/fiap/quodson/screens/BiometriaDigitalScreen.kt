package br.com.fiap.quodson.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.quodson.R
import br.com.fiap.quodson.ui.theme.QuodsonTheme
import br.com.fiap.quodson.ui.theme.quickSandSemibold

@Composable
fun BiometriaDigitalScreen() {
    QuodsonTheme {
        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black)
                    .padding(top = 54.dp)
                    .padding(bottom = 27.dp)
                    .padding(horizontal = 36.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.biometria_digital),
                    tint = Color.White,
                    contentDescription = "Biometria Digital"
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Quod",
                        modifier = Modifier.padding(end = 12.dp),
                        color = Color.White
                    )

                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Usuário",
                        Modifier
                            .size(48.dp)
                    )

                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(id = R.color.cor_de_fundo))
                    .verticalScroll(rememberScrollState())
            ) {


                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 64.dp)
                ) {
                    Text(
                        "Biometria Digital",
                        fontFamily = quickSandSemibold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(24.dp)
                            .align(Alignment.Start),
                        color = Color.Black

                    )
                    HorizontalDivider(
                        modifier = Modifier
                            .padding(bottom = 64.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.biometria_digital),
                        contentDescription = "Biometria Digital",
                        modifier = Modifier.size(80.dp),
                        tint = Color.Black
                    )
                    Text(
                        "Autenticar-se",
                        fontFamily = quickSandSemibold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(24.dp)
                            .align(Alignment.CenterHorizontally),
                        color = Color.Black

                    )
                }
            }

        }
    }

}

@Preview
@Composable
fun BiometriaDigitalScreenPreview() {
    BiometriaDigitalScreen()
}