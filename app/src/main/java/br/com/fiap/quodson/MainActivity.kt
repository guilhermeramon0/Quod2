package br.com.fiap.quodson

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.quodson.databinding.ActivityMainBinding
import br.com.fiap.quodson.screens.BiometriaDigitalScreen
import br.com.fiap.quodson.screens.BiometriaFacialScreen
import br.com.fiap.quodson.screens.CadastroScreen
import br.com.fiap.quodson.screens.DocumentoScreen
import br.com.fiap.quodson.screens.MenuScreen
import br.com.fiap.quodson.screens.StartScreen
import br.com.fiap.quodson.ui.theme.QuodsonTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewBinding: ActivityMainBinding
//    private lateinit var cameraController: LifecycleCameraController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        if (!hasPermissions(baseContext)) {
            activityResultLauncher.launch(REQUIRED_PERMISSIONS)
        }
//        else {
//            startCamera()
//        }

        enableEdgeToEdge()
        setContent {
            QuodsonTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "start",
                    ) {

                        composable(route = "start") {
                            StartScreen(navController)
                        }
                        composable(route = "cadastro") {
                            CadastroScreen(navController)
                        }
                        composable(route = "menu") {
                            MenuScreen(navController)
                        }
                        composable(route = "documento") {
                            DocumentoScreen()
                        }
                        composable(route = "biometria_digital") {
                            BiometriaDigitalScreen()
                        }
                        composable(route = "biometria_facial") {
                            BiometriaFacialScreen()
                        }
                    }

                }
            }
        }
    }

//    private fun startCamera() {
//        val previewView: PreviewView = viewBinding.viewFinder
//        cameraController = LifecycleCameraController(baseContext)
//        cameraController.bindToLifecycle(this)
//        cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//        previewView.controller = cameraController
//    }

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions())
    { permissions ->
        var permissionGranted = true
        permissions.entries.forEach {
            if (it.key in REQUIRED_PERMISSIONS && !it.value) {
                permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(this, "Permissão negada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permissão aceita", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                android.Manifest.permission.CAMERA
            )
//                .apply {
//                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
//                    add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                }
//            }
                .toTypedArray()

    }
        private fun hasPermissions(context: Context) = REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
}
