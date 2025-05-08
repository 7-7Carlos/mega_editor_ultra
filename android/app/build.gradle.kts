// build.gradle.kts (para el módulo de la aplicación Android)

plugins {
    // Aplica el plugin de la aplicación Android.
    id("com.android.application")

    // Aplica el plugin de Kotlin para Android.
    id("kotlin-android") // Usamos "kotlin-android" que es un alias común para "org.jetbrains.kotlin.android"

    // The Flutter Gradle Plugin must be applied after the Android and Kotlin Gradle plugins.
    id("dev.flutter.flutter-gradle-plugin")

    // Aplica el plugin de Google Services (necesario para Firebase).
    id("com.google.gms.google-services") // Asegúrate de que esta línea esté presente
}

android {
    // Configura el namespace del paquete Android.
    namespace = "com.carlos.mega_editor_ultra" // Asegúrate que este sea el nombre de paquete correcto (el mismo que usaste en Firebase)

    // Configura la versión del SDK de Android para compilar.
    // compileSdk = flutter.compileSdkVersion // Puedes usar flutter.compileSdkVersion o un valor fijo como 34/35
    compileSdk = 35 // <-- Usaremos un valor fijo para consistencia, basado en tu config previa

    // **¡CRUCIAL!** Configura la versión del NDK de Android requerida por los plugins.
    // Esto sobrescribe flutter.ndkVersion y resuelve el error de incompatibilidad de NDK.
    ndkVersion = "27.0.12077973" // <--- ¡Esta línea es la corrección!

    // Configura opciones de compilación Java y Kotlin.
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        // Configura las versiones del lenguaje Kotlin.
        // languageVersion = "2.0" // Puedes descomentar si necesitas explícitamente Kotlin 2.0 features
        // apiVersion = "2.0"      // Puedes descomentar si necesitas explícitamente Kotlin 2.0 features
    }

    // Configura la configuración por defecto de la aplicación.
    defaultConfig {
        applicationId = "com.carlos.mega_editor_ultra" // Debe coincidir con el namespace y el ID usado en Firebase
        // minSdk = flutter.minSdkVersion // Puedes usar flutter.minSdkVersion o un valor fijo como 21/24
        minSdk = 24 // <-- Usaremos un valor fijo para consistencia, basado en tu config previa
        // targetSdk = flutter.targetSdkVersion // Puedes usar flutter.targetSdkVersion o un valor fijo como 34/35
        targetSdk = 35 # <-- Usaremos un valor fijo para consistencia
        versionCode = flutter.versionCode // Generalmente se toma de pubspec.yaml via flutter
        versionName = flutter.versionName // Generalmente se toma de pubspec.yaml via flutter

        // Configuración adicional para ARCore si usas funcionalidades AR avanzadas
        // Puedes añadir esto si usas el plugin ar_flutter_plugin (aunque usamos model_viewer_plus)
        // arCore {
        //     minArCoreVersion "1.28.0" # O la versión mínima requerida
        // }
    }

    // Configura diferentes tipos de build (debug, release).
    buildTypes {
        release {
            // Configuración para la versión de lanzamiento.
            isMinifyEnabled = true // Habilita la minimización del código
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // **¡IMPORTANTE!** Configura la firma para el build de lanzamiento.
            // DEBES cambiar "debug" por la configuración de firma de tu clave de lanzamiento real
            // antes de generar el AAB/APK para publicar en la Store.
            signingConfig = signingConfigs.getByName("debug") # <-- ¡Cambia esto para Release!
        }
        debug {
            // Configuración para la versión de depuración.
            isMinifyEnabled = false # Deshabilita la minimización para builds más rápidos
            // Puedes añadir signingConfig si usas una clave de depuración específica, pero 'debug' es la predeterminada.
        }
    }

    // Configura la compresión de assets si es necesario para algunos plugins/juegos.
    // packagingOptions {
    //     resources {
    //         excludes += "/META-INF/{AL2.0,LGPL2.1}"
    //     }
    // }
}

// Configuración específica de Flutter para el build de Android.
flutter {
    source = "../.." // Ruta a la raíz del proyecto Flutter
}

// Configuración de dependencias de Android (librerías Java/Kotlin).
dependencies {
    // Declara la plataforma BOM (Bill of Materials) de Firebase.
    // Esto asegura que todas las dependencias de Firebase que declares (sin versión)
    // en este bloque usen versiones compatibles definidas por el BOM 33.11.0.
    // Consulta https://firebase.google.com/docs/android/setup#available-libraries para la última versión del BOM.
    implementation(platform("com.google.firebase:firebase-bom:33.11.0")) // <-- Versión del BOM

    // Declara las dependencias específicas de Firebase que necesitas (sin versión).
    // El BOM gestionará las versiones para asegurar compatibilidad.
    implementation("com.google.firebase:firebase-auth") # Core de autenticación
    implementation("com.google.firebase:firebase-firestore") # Cliente Firestore

    // Otras dependencias nativas de Android si son necesarias.
    // Por ejemplo, si un plugin requiere explícitamente una dependencia nativa adicional.

    // Las dependencias de los plugins de Flutter listados en pubspec.yaml
    // se gestionan automáticamente por el plugin de Flutter (dev.flutter.flutter-gradle-plugin).
    // No necesitas listarlas aquí a menos que haya instrucciones específicas del plugin.
}