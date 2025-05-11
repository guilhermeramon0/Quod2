package br.com.fiap.quodson.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import br.com.fiap.quodson.ui.theme.QuodsonTheme
import br.com.fiap.quodson.ui.theme.quickSandSemibold
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.fiap.quodson.R
import br.com.fiap.quodson.model.Cidade
import br.com.fiap.quodson.model.Estado
import br.com.fiap.quodson.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CadastroScreen(navController: NavController) {
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.quodson),
                contentDescription = "Logo Quodson",
                modifier = Modifier
                    .padding(vertical = 48.dp)
                    .fillMaxWidth(0.5f)
            )
            ArrowBack(navController)
            FormBox("Digite seu CPF/CNPJ", "CPF/CNPJ")
            FormBox("Digite seu nome", "Nome")
            FormBox(
                "Digite sua senha", "Senha",
                painterResource(id = R.drawable.eye),
                "Olho"
            )

            DropdownCidades(dropdownEstados())

            Button(
                onClick = {
                    navController.navigate("menu")
                },
                shape = RoundedCornerShape(20),
                modifier = Modifier
                    .padding(top = 48.dp)
                    .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.cor_primaria))
            ) {
                Text(
                    "Continuar",
                    Modifier
                        .padding(vertical = 6.dp),
                    fontFamily = quickSandSemibold
                )
            }
            // para não ficar colado na parte inferior
            Spacer(modifier = Modifier.height(100.dp))
        }

    }
}

@Composable
fun ArrowBack(navController: NavController) {
    Icon(
        imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar",
        tint = Color.White,
        modifier = Modifier
            .padding(end = 320.dp)
            .padding(bottom = 24.dp)
            .clickable {
                navController.popBackStack()
            }
    )
}

@Composable
fun CustomOutlinedTextField(
    placeholder: String,
    label: String,
    icon: Any? = null,
    iconDescription: String? = null
){

    var texto by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = colorResource(id = R.color.preto_transparente),
            unfocusedTextColor = Color.White,
            focusedTextColor = Color.White,
            focusedLabelColor = colorResource(id = R.color.cor_primaria),
            unfocusedLabelColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            focusedTrailingIconColor = Color.White,
            unfocusedTrailingIconColor = Color.White
        ),
        value = texto,
        onValueChange = { novoTexto -> texto = novoTexto },
        singleLine = true,
        placeholder = { Text(placeholder, color = Color.White) },
        label = { Text(label) },

        trailingIcon = {
            when (icon) {
                is ImageVector -> {
                    Icon(
                        imageVector = icon,
                        contentDescription = iconDescription,
                        tint = Color.White
                    )
                }

                is Painter -> {
                    Icon(
                        painter = icon,
                        contentDescription = iconDescription,
                        tint = Color.White
                    )
                }
            }
        },
        visualTransformation = if (label == "Senha") PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if (label == "CPF/CNPJ") KeyboardOptions(keyboardType = KeyboardType.Number) else KeyboardOptions.Default
    )
}

@Composable
fun FormBox(
    placeholder: String,
    label: String,
    icon: Any? = null,
    iconDescription: String? = null
) {

    Column(
        modifier = Modifier
            .padding(top = 32.dp, start = 64.dp, end = 64.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomOutlinedTextField(placeholder, label, icon, iconDescription)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dropdownEstados(): Estado {

    var listaEstadoState by remember { mutableStateOf(listOf<Estado>()) }

    val call = RetrofitFactory().getLocalidadeService().getEstados()

    call.enqueue(object : Callback<List<Estado>> {
        override fun onResponse(call: Call<List<Estado>>, response: Response<List<Estado>>) {
            listaEstadoState = response.body()!!
        }

        override fun onFailure(call: Call<List<Estado>>, t: Throwable) {
            Log.i("Quodson", "onResponse: ${t.message}")
        }

    })

    val list = listaEstadoState.sortedBy { it.nome }
    var isExpanded by remember { mutableStateOf(false) }
    var estadoSelecionado by remember { mutableStateOf(Estado()) }

    Column(
        modifier = Modifier
            .padding(top = 32.dp, start = 64.dp, end = 64.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }

    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = !isExpanded }) {

        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(id = R.color.preto_transparente),
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTrailingIconColor = Color.White,
                unfocusedTrailingIconColor = Color.White
            ),
            modifier = Modifier.menuAnchor(),
            value = estadoSelecionado.nome,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(
                expanded = isExpanded) },
        )

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            list.forEachIndexed { index, estado ->
                DropdownMenuItem(
                    text = { Text(estado.nome) },
                    onClick = {
                        estadoSelecionado = list[index]
                        isExpanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
    return estadoSelecionado
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownCidades(estadoSelecionado: Estado) {

    var listaCidadeState by remember { mutableStateOf(listOf<Cidade>()) }

    val call = RetrofitFactory().getLocalidadeService().getCidadesByEstado(estadoSelecionado.id)

    call.enqueue(object : Callback<List<Cidade>> {
        override fun onResponse(call: Call<List<Cidade>>, response: Response<List<Cidade>>) {
            listaCidadeState = response.body()!!
        }

        override fun onFailure(call: Call<List<Cidade>>, t: Throwable) {
            Log.i("Quodson", "onResponse: ${t.message}")
        }

    })

    val list = listaCidadeState.sortedBy { it.nome }
    var isExpanded by remember { mutableStateOf(false) }
    var cidadeSelecionada by remember { mutableStateOf(Cidade()) }

    Column(
        modifier = Modifier
            .padding(top = 32.dp, start = 64.dp, end = 64.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }

    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = !isExpanded }) {

        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorResource(id = R.color.preto_transparente),
                unfocusedTextColor = Color.White,
                focusedTextColor = Color.White,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedTrailingIconColor = Color.White,
                unfocusedTrailingIconColor = Color.White
            ),
            modifier = Modifier.menuAnchor(),
            value = cidadeSelecionada.nome,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
        )

        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            list.forEachIndexed { index, cidade ->
                DropdownMenuItem(
                    text = { Text(cidade.nome) },
                    onClick = {
                        cidadeSelecionada = list[index]
                        isExpanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Preview
@Composable
fun CadastroScreenPreview() {
    CadastroScreen(rememberNavController())
}