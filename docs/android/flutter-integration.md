<!-- Google tag (gtag.js) -->
<script async src="https://www.googletagmanager.com/gtag/js?id=G-B89K3ZN1LX"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-B89K3ZN1LX');
</script>


# Flutter Integration Guide for Hawcx

This guide will walk you through the process of integrating Hawcx into your Flutter application.

## Prerequisites

- Flutter development environment set up
- Basic knowledge of Flutter and Android development

## Integration Steps

1. **Add Hawcx to your Android project**

   In your flutter project, navigate to the `android/app` directory and create a `libs` folder if it doesn't exist. Place the Hawcx AAR file in this folder.

2. **Update build.gradle**

   In `android/app/build.gradle`, add the following:

   ```gradle
    defaultConfig {
        // TODO: Specify your own unique Application ID (https://developer.android.com/studio/build/application-id.html).
        applicationId = "com.example.flutter_hawcx"
        // You can update the following values to match your application needs.
        // For more information, see: https://flutter.dev/to/review-gradle-config.
        minSdk = 26 // Hawcx require API 26
        targetSdk = flutter.targetSdkVersion
        versionCode = flutter.versionCode
        versionName = flutter.versionName
    }

    dependencies {
        // Import the aar file from the libs directory
        implementation fileTree(dir: 'libs', include: ['*.aar'])

        // Add the dependencies that Hawcx requires
        implementation 'com.squareup.okhttp3:okhttp:5.0.0-alpha.11'
        implementation 'com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11'
        implementation 'com.squareup.retrofit2:retrofit:2.10.0'
        implementation 'com.squareup.retrofit2:converter-gson:2.10.0'
        implementation "androidx.biometric:biometric-ktx:1.2.0-alpha05"
        implementation 'androidx.security:security-crypto:1.1.0-alpha03'
        implementation 'com.google.android.material:material:1.4.0'
        implementation 'com.google.android.gms:play-services-location:21.2.0'
        implementation 'androidx.fragment:fragment-ktx:1.3.6'
        implementation 'androidx.appcompat:appcompat:1.3.0'
        implementation 'androidx.core:core-ktx:1.6.0'
    }
   ```

3. **Create a Native Module** 

   Create a new kotlin file named `HawcxModule.kt` in `android\app\src\main\java\com\hawcxapp\`:
   (following are sample methods, your implementations can change. Closely watch for all overrides)

   ```java
    package com.example.flutter_hawcx

    import android.content.Context
    import androidx.biometric.BiometricPrompt
    import androidx.core.content.ContextCompat
    import android.os.Handler
    import android.os.Looper
    import io.flutter.plugin.common.MethodCall
    import io.flutter.plugin.common.MethodChannel
    import com.hawcx.HawcxInitializer
    import com.hawcx.auth.SignIn
    import com.hawcx.auth.SignUp
    import com.hawcx.auth.Restore
    import com.hawcx.utils.AuthErrorHandler
    import com.hawcx.utils.AuthErrorHandler.SignInErrorCode

    class HawcxModule(private val context: Context) : MethodChannel.MethodCallHandler {

        private val apiKey = "YOUR_API_KEY"
        private var signUp: SignUp? = null
        private var signIn: SignIn? = null
        private var restore: Restore? = null

        init {
            HawcxInitializer.getInstance().init(context, apiKey)
            signUp = SignUp(context, apiKey)
            signIn = SignIn(context, apiKey)
            restore = Restore(context, apiKey)
        }

        override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
            when (call.method) {
                "signUp" -> {
                    val username = call.argument<String>("username")
                    if (username != null) {
                        signUp(username, result)
                    } else {
                        result.error("ERROR", "Username is required", null)
                    }
                }
                "signIn" -> {
                    val username = call.argument<String>("username")
                    if (username != null) {
                        signIn(username, result)
                    } else {
                        result.error("ERROR", "Username is required", null)
                    }
                }
                "handleVerifyOTP" -> {
                    val username = call.argument<String>("username")
                    val otp = call.argument<String>("otp")
                    if (username != null && otp != null) {
                        handleVerifyOTP(username, otp, result)
                    } else {
                        result.error("ERROR", "Username and OTP are required", null)
                    }
                }
                "getLastUser" -> {
                    getLastUser(result)
                }
                "checkLastUser" -> {
                    checkLastUser(result)
                }
                "initiateBiometricLogin" -> {
                    initiateBiometricLogin(result, Runnable { })
                }
                else -> {
                    result.notImplemented()
                }
            }
        }

        // Public method to handle signUp
        public fun signUp(username: String, result: MethodChannel.Result) {
            signUp?.signUp(username, object : SignUp.SignUpCallback {
                override fun showError(errorCode: String) {
                    result.error("SIGNUP_ERROR", "SignUp failed: $errorCode", null)
                }

                override fun showError(errorCode: AuthErrorHandler.SignUpErrorCode, errorMessage: String) {
                    result.error("SIGNUP_ERROR", "SignUp failed: $errorMessage", null)
                }

                override fun onGenerateOTPSuccess() {
                    result.success("OTP generated successfully")
                }

                override fun onSuccessfulSignup() {
                    result.success("Sign up successful")
                }
            })
        }

        // Public method to handle signIn
        public fun signIn(username: String, result: MethodChannel.Result) {
            try{
            signIn?.signIn(username, object : SignIn.SignInCallback {
                override fun showEmailSignInScreen() {
                    result.error("EMAIL_SIGN_IN_REQUIRED", "Please sign in with your email.", null)
                }

                override fun onSuccessfulLogin(username: String) {
                    result.success("Login successful")
                }

                override fun showError(errorCode: AuthErrorHandler.SignInErrorCode, message: String) {
                    result.error("SIGNIN_ERROR", "SignIn failed: $message", null)
                }

                override fun showError(message: String) {
                    result.error("SIGNIN_ERROR", "SignIn failed: $message", null)
                }

                override fun initiateBiometricLogin(callback: Runnable) {
                    initiateBiometricLogin(result, callback)
                }
            })
            } catch (e: Exception) {
                result.error("SIGNUP_ERROR", e.message, null)
            }
        }

        public fun handleVerifyOTP(username: String, otp: String, result: MethodChannel.Result) {
            signUp?.handleVerifyOTP(username, otp, object : SignUp.SignUpCallback {
                override fun showError(errorCode: String) {
                    result.error("VERIFY_OTP_ERROR", "OTP verification failed: $errorCode", null)
                }

                override fun showError(errorCode: AuthErrorHandler.SignUpErrorCode, errorMessage: String) {
                    result.error("VERIFY_OTP_ERROR", "OTP verification failed: $errorMessage", null)
                }

                override fun onGenerateOTPSuccess() {
                    // Not used for OTP verification
                }

                override fun onSuccessfulSignup() {
                    result.success("OTP verified and signup complete")
                }
            })
        }

        public fun getLastUser(result: MethodChannel.Result) {
            try {
                val lastUser = signIn?.getLastUser() ?: ""
                result.success(lastUser)
            } catch (e: Exception) {
                result.error("GET_LAST_USER_ERROR", "Failed to get last user: ${e.message}", null)
            }
        }

        public fun checkLastUser(result: MethodChannel.Result) {
            signIn?.checkLastUser(object : SignIn.SignInCallback {
                override fun showEmailSignInScreen() {
                    result.success("SHOW_EMAIL_SIGN_IN_SCREEN")
                }

                override fun onSuccessfulLogin(username: String) {
                    result.success("Login successful for user: $username")
                }

                override fun showError(errorCode: AuthErrorHandler.SignInErrorCode, message: String) {
                    result.error("CHECK_LAST_USER_ERROR", "Check last user failed: $message", null)
                }

                override fun showError(message: String) {
                    result.error("CHECK_LAST_USER_ERROR", "Check last user failed: $message", null)
                }

                override fun initiateBiometricLogin(callback: Runnable) {
                    initiateBiometricLogin(result, callback)
                }
            })
        }

        public fun initiateBiometricLogin(result: MethodChannel.Result, callback: Runnable) {
            val currentActivity = (context as? androidx.fragment.app.FragmentActivity)

            if (currentActivity == null) {
                result.error("ACTIVITY_ERROR", "Current activity is not available", null)
                return
            }

            val executor = ContextCompat.getMainExecutor(context)
            val biometricPrompt = BiometricPrompt(
                currentActivity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errorMessage: CharSequence) {
                        result.error("BIOMETRIC_AUTH_ERROR", "Authentication error: $errorMessage", null)
                    }

                    override fun onAuthenticationSucceeded(authResult: BiometricPrompt.AuthenticationResult) {
                        callback.run()
                    }

                    override fun onAuthenticationFailed() {
                        result.error("BIOMETRIC_AUTH_FAILED", "Authentication failed", null)
                    }
                })

            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Login")
                .setDescription("Use your fingerprint or face to log in")
                .setNegativeButtonText("Cancel")
                .build()

            biometricPrompt.authenticate(promptInfo)
        }
            public fun generateOtpForAccountRestore(email: String, result: MethodChannel.Result) {
            restore?.generateOtp(email, object : Restore.OnSuccessListener {
                override fun onSuccess(message: String) {
                    result.success(message)
                }
            }, object : Restore.OnErrorListener {
                override fun onError(error: String) {
                    result.error("GENERATE_OTP_ERROR", error, null)
                }
            })
        }

        public fun verifyOtpForAccountRestore(email: String, otp: String, result: MethodChannel.Result) {
            restore?.verifyOtp(email, otp, object : Restore.OnSuccessListener {
                override fun onSuccess(message: String) {
                    result.success(message)
                }
            }, object : Restore.OnErrorListener {
                override fun onError(error: String) {
                    result.error("VERIFY_OTP_ERROR", error, null)
                }
            })
        }
        // Add other methods for different HawcxAuth features
    }

   ```

4. **Use module in MainActivity.kt**

   In `MainActivity.kt`, implement the channel calls to communicate with dart and use FlutterFragmentActivity:
   (You can change the implementation based on your need)

   ```java
    package com.example.flutter_hawcx

    import android.os.Bundle
    import io.flutter.embedding.android.FlutterFragmentActivity
    import io.flutter.plugin.common.MethodChannel
    import io.flutter.embedding.engine.FlutterEngine
    import io.flutter.plugins.GeneratedPluginRegistrant
    import com.example.flutter_hawcx.HawcxModule

    class MainActivity: FlutterFragmentActivity() {
        private val CHANNEL = "com.example.flutter_hawcx/hawcx"
        private lateinit var hawcxModule: HawcxModule

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // Initialize HawcxModule
            hawcxModule = HawcxModule(this)
        }

        // Ensure flutterEngine is initialized to avoid nullability issues
        override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
            super.configureFlutterEngine(flutterEngine)
            GeneratedPluginRegistrant.registerWith(flutterEngine)

            // Set up the method channel to communicate with Flutter
            MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
                when (call.method) {
                    // Sign-up functionality
                    "signUp" -> {
                        val username = call.argument<String>("username")
                        if (username != null) {
                            hawcxModule.signUp(username, result)
                        } else {
                            result.error("ERROR", "Username is required", null)
                        }
                    }

                    // Sign-in functionality
                    "signIn" -> {
                        val username = call.argument<String>("username")
                        if (username != null) {
                            hawcxModule.signIn(username, result)
                        } else {
                            result.error("ERROR", "Username is required", null)
                        }
                    }

                    // OTP Verification functionality
                    "handleVerifyOTP" -> {
                        val username = call.argument<String>("username")
                        val otp = call.argument<String>("otp")
                        if (username != null && otp != null) {
                            hawcxModule.handleVerifyOTP(username, otp, result)
                        } else {
                            result.error("ERROR", "Username and OTP are required", null)
                        }
                    }

                    // Get last signed-in user
                    "getLastUser" -> {
                        hawcxModule.getLastUser(result)
                    }

                    // Check last signed-in user
                    "checkLastUser" -> {
                        hawcxModule.checkLastUser(result)
                    }

                    // Initiate biometric login
                    "initiateBiometricLogin" -> {
                        hawcxModule.initiateBiometricLogin(result, Runnable { })
                    }

                    // Generate OTP for account restore
                    "generateOtpForAccountRestore" -> {
                        val email = call.argument<String>("email")
                        if (email != null) {
                            hawcxModule.generateOtpForAccountRestore(email, result)
                        } else {
                            result.error("ERROR", "Email is required", null)
                        }
                    }

                    // Verify OTP for account restore
                    "verifyOtpForAccountRestore" -> {
                        val email = call.argument<String>("email")
                        val otp = call.argument<String>("otp")
                        if (email != null && otp != null) {
                            hawcxModule.verifyOtpForAccountRestore(email, otp, result)
                        } else {
                            result.error("ERROR", "Email and OTP are required", null)
                        }
                    }

                    // Default handler for unknown method calls
                    else -> {
                        result.notImplemented()
                    }
                }
            }
        }
    }

   ```
5. **Add requirements in `android\app\src\main\AndroidManifest.xml`**

   You need to add biometric access to the to the `AndroidManifest.xml` as following:
   (you can copy the following xml config or check your config for xmlns:tools, permission, feature, android:supportsRt and tools:replace)

   ```xml
    <manifest xmlns:android="http://schemas.android.com/apk/res/android" xmlns:tools="http://schemas.android.com/tools">

        <!-- Permissions required for biometric authentication -->
        <uses-permission android:name="android.permission.USE_BIOMETRIC" />
        <uses-permission android:name="android.permission.USE_FINGERPRINT" />
        <uses-permission android:name="android.permission.INTERNET" />
        <uses-permission android:name="android.permission.CAMERA" />

            <!-- Features related to biometrics -->
        <uses-feature
            android:name="android.hardware.fingerprint"
            android:required="false" />
        <uses-feature
            android:name="android.hardware.biometrics"
            android:required="false" />

        <application
            android:label="flutter_hawcx"
            android:name="${applicationName}"
            android:icon="@mipmap/ic_launcher"
            android:enableOnBackInvokedCallback="true"
            tools:replace="android:label">
            <activity
                android:name=".MainActivity"
                android:exported="true"
                android:launchMode="singleTop"
                android:taskAffinity=""
                android:theme="@style/LaunchTheme"
                android:configChanges="orientation|keyboardHidden|keyboard|screenSize|smallestScreenSize|locale|layoutDirection|fontScale|screenLayout|density|uiMode"
                android:hardwareAccelerated="true"
                android:windowSoftInputMode="adjustResize">
                <!-- Specifies an Android theme to apply to this Activity as soon as
                    the Android process has started. This theme is visible to the user
                    while the Flutter UI initializes. After that, this theme continues
                    to determine the Window background behind the Flutter UI. -->
                <meta-data
                android:name="io.flutter.embedding.android.NormalTheme"
                android:resource="@style/NormalTheme"
                />
                <intent-filter>
                    <action android:name="android.intent.action.MAIN"/>
                    <category android:name="android.intent.category.LAUNCHER"/>
                </intent-filter>
            </activity>
            <!-- Don't delete the meta-data below.
                This is used by the Flutter tool to generate GeneratedPluginRegistrant.java -->
            <meta-data
                android:name="flutterEmbedding"
                android:value="2" />
        </application>
        <!-- Required to query activities that can process text, see:
            https://developer.android.com/training/package-visibility and
            https://developer.android.com/reference/android/content/Intent#ACTION_PROCESS_TEXT.

            In particular, this is used by the Flutter engine in io.flutter.plugin.text.ProcessTextPlugin. -->
        <queries>
            <intent>
                <action android:name="android.intent.action.PROCESS_TEXT"/>
                <data android:mimeType="text/plain"/>
            </intent>
        </queries>
    </manifest>
   ```
6. **Add dependencies**

   Add local_auth in `pubspec.yaml` 

   ```gradle
   dependencies:
    flutter:
        sdk: flutter
    local_auth: ^2.3.0
   ```
7. **Use HawcxAuth in Flutter**

   Now you can use HawcxAuth in your Flutter widgets:

   ```javascript
    import 'package:flutter/material.dart';
    import 'package:flutter/services.dart'; // For Native MethodChannel calls
    import 'package:email_validator/email_validator.dart'; // Optional email validation package

    class SignupScreen extends StatefulWidget {
    @override
    _SignupScreenState createState() => _SignupScreenState();
    }

    class _SignupScreenState extends State<SignupScreen> {
    static const platform = MethodChannel(
        'com.example.flutter_hawcx/hawcx'); // Similar to NativeModules in React Native
    final TextEditingController _emailController = TextEditingController();
    bool _loading = false; // Loading state to manage the signup process

    @override
    void initState() {
        super.initState();
        checkLastUser(); // Similar to useEffect in React Native
    }

    // Email validation
    bool validateEmail(String email) {
        return EmailValidator.validate(email);
    }

    // Check if a user exists and handle biometric login
    Future<void> checkLastUser() async {
        setState(() {
        _loading = true; // Show loading indicator
        });

        try {
        final String result = await platform.invokeMethod('checkLastUser');
        print(result);
        if (result.contains("Login successful for user")) {
            setState(() {
            _loading = false; // Hide loading indicator
            });
            Navigator.pushReplacementNamed(context, '/home');
        } else if (result == 'SHOW_EMAIL_SIGN_IN_SCREEN') {
            setState(() {
            _loading = false; // Hide loading indicator
            });
            Navigator.pushReplacementNamed(context, '/signin');
        }
        } on PlatformException catch (e) {
        print("Error checking last user: ${e.message}");
        setState(() {
            _loading = false; // Hide loading indicator
        });
        }
    }

    // Handle signup process
    void handleSignup() async {
        String email = _emailController.text.trim();
        if (email.isNotEmpty && validateEmail(email)) {
        setState(() {
            _loading = true; // Show loading state
        });
        try {
            await platform.invokeMethod('signUp', {'username': email});
            ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(content: Text('OTP sent for verification!')));
            Navigator.pushNamed(context, '/verification', arguments: email);
        } on PlatformException catch (e) {
            // Check if the error message contains 'User already exists'
            if (e.message != null && e.message!.contains('User already exists')) {
            ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(content: Text('User already exists. Please sign in.')));
            Navigator.pushNamed(context, '/signin');
            } else {
            ScaffoldMessenger.of(context).showSnackBar(
                SnackBar(content: Text('Sign up failed: ${e.message}')));
            }
        } catch (e) {
            ScaffoldMessenger.of(context)
                .showSnackBar(SnackBar(content: Text('Sign up failed')));
        } finally {
            setState(() {
            _loading = false; // Hide loading state
            });
        }
        } else if (email.isEmpty) {
        ScaffoldMessenger.of(context)
            .showSnackBar(SnackBar(content: Text('Please enter your email')));
        } else {
        ScaffoldMessenger.of(context)
            .showSnackBar(SnackBar(content: Text('Please enter a valid email')));
        }
    }

    @override
    Widget build(BuildContext context) {
        return Scaffold(
        appBar: AppBar(title: Text('Sign Up')),
        body: Stack(
            children: [
            Padding(
                padding: EdgeInsets.all(16),
                child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                    Text(
                    'Enter your Email to Sign Up:',
                    style: TextStyle(fontSize: 18),
                    ),
                    SizedBox(height: 12),
                    TextField(
                    controller: _emailController,
                    decoration: InputDecoration(
                        labelText: 'Email',
                        border: OutlineInputBorder(),
                    ),
                    keyboardType: TextInputType.emailAddress,
                    ),
                    SizedBox(height: 12),
                    ElevatedButton(
                    onPressed: _loading ? null : handleSignup,
                    child: Text(_loading ? 'Signing Up...' : 'Sign Up'),
                    ),
                    SizedBox(height: 12),
                    TextButton(
                    onPressed: () => Navigator.pushNamed(context, '/signin'),
                    child: Text('Already have an account? Sign In'),
                    ),
                ],
                ),
            ),
            // Show a circular progress indicator when _loading is true
            if (_loading)
                Center(
                child: CircularProgressIndicator(),
                ),
            ],
        ),
        );
    }
    }

   ```

## Best Practices

- Always handle errors and exceptions from native methods.
- Use async/await for cleaner code when calling native methods.
- Implement proper error handling and user feedback in your React Native UI.

