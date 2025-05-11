package br.com.fiap.quodson.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import br.com.fiap.quodson.R
import br.com.fiap.quodson.ui.theme.QuodsonTheme
import br.com.fiap.quodson.ui.theme.quickSandBold
import br.com.fiap.quodson.ui.theme.quickSandSemibold
import kotlinx.coroutines.delay

@Composable
fun DocumentoScreen() {
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
                    painter = painterResource(id = R.drawable.documento),
                    tint = Color.White,
                    contentDescription = "Análise de Documento"
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
                        "Analisar Documento",
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
                        Image(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = "Câmera",
                            modifier = Modifier.size(76.dp)
//                                .clickable {
//                                    startCamera()
//                                }
                                ,
                            contentScale = ContentScale.Fit
                        )
                    }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    HorizontalDivider()
                    Text(
                        "Documentos do Cliente",
                        fontFamily = quickSandSemibold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(24.dp)
                            .align(Alignment.Start),
                        color = Color.Black
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(alignment = Alignment.CenterHorizontally)
                            .padding(horizontal = 32.dp)
                    ) {
                        CardMaker("CPF", colorResource(id = R.color.cor_primaria), 0f)
                        CardMaker("CNH", colorResource(id = R.color.cor_secundaria), 66f)
                        CardMaker("RG", colorResource(id = R.color.cor_terciaria), 132f)
                    }

                }

                Spacer(modifier = Modifier.height(100.dp))

            }

        }
    }

}

@SuppressLint("UseOfNonLambdaOffsetOverload")
@Composable
fun CardMaker(name: String, color: Color, cardOffset: Float) {
    var cardPosition by remember { mutableFloatStateOf(cardOffset) }


    val screenWidth = LocalConfiguration.current.screenWidthDp
    var height by remember { mutableStateOf(180.dp) }
    var width by remember { mutableStateOf(196.dp) }
    var zIndex by remember { mutableFloatStateOf(0f) }
    var isExpanded by remember { mutableStateOf(false) }

    val draggableState = rememberDraggableState { delta ->
        cardPosition = (cardPosition + delta).coerceIn(-32f, (screenWidth - width.value - 32))
    }

    Card(

        modifier = Modifier
            .height(height)
            .width(width)
            .offset(x = cardPosition.dp)
            .clickable {
                if (height == 180.dp) {
                    height = 420.dp
                    width = 360.dp
                    zIndex = 2f
                    cardPosition = screenWidth / 2 - width.value / 2 - 25
                    isExpanded = true

                } else {
                    height = 180.dp
                    width = 196.dp
                    zIndex = 0f
                    isExpanded = false
                }
            }
            .zIndex(zIndex)
            .then(
                if (height == 180.dp) { // draggable
                    Modifier.draggable(
                        state = draggableState,
                        orientation = Orientation.Horizontal
                    )
                } else {
                    Modifier // not draggable
                }
            ),
        colors = CardDefaults
            .cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(36.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                name,
                fontFamily = quickSandSemibold,
                fontSize = 24.sp,
                color = Color.White,
                modifier = Modifier.padding(12.dp)
            )
        }
        ValidaDocumento(isExpanded)
    }
}

@Composable
fun ValidaDocumento(isExpanded: Boolean) {

    var texto by remember { mutableStateOf("Analisando...") }
    val screenWidth = LocalConfiguration.current.screenWidthDp
    var isAnalisando by remember { mutableStateOf(true) }

    val sucesso by remember {mutableStateOf(sucesso()) }

    if (isExpanded) {

        Box(
            modifier = Modifier
                .padding(top = 24.dp)
                .width(screenWidth.dp)
                .height(42.dp)
                .background(
                    color = Color.Black
                )
                .padding(horizontal = 12.dp)
                .zIndex(4f)
        ) {
            Text(
                texto,
                fontFamily = quickSandBold,
                fontSize = 24.sp,
                color = if (isAnalisando) {
                    colorResource(R.color.cor_primaria) }
                else {
                    if (sucesso) {
                        colorResource(R.color.cor_secundaria)
                    } else {
                        colorResource(R.color.cor_terciaria)
                    }
                }
                    ,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        LaunchedEffect(true) {
            delay(2000L)
            texto = if (sucesso) {"Documento válido!"} else {"Documento inválido!"}
            isAnalisando = false
        }

    }
    else {
        texto = "Analisando..."
        isAnalisando = true
    }
}

private fun sucesso(): Boolean {
    val numeroAleatorio = (0..1).random()
    return numeroAleatorio == 1
}



@Preview
@Composable
fun DocumentoScreenPreview() {
    DocumentoScreen()
}