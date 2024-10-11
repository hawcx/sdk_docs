<!-- End Cloudflare Web Analytics -->

<script async src="https://www.googletagmanager.com/gtag/js?id=G-B89K3ZN1LX"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-B89K3ZN1LX');
</script>


# React Native Integration Guide for Hawcx

This guide will walk you through the process of integrating Hawcx into your React Native application.

## Prerequisites

- React Native development environment set up
- Basic knowledge of React Native and Android development

## Integration Steps

1. **Add Hawcx to your Android project**

   In your React Native project, navigate to the `android/app` directory and create a `libs` folder if it doesn't exist. Place the Hawcx AAR file in this folder.

2. **Update build.gradle**

   In `android/app/build.gradle`, add the following:

   ```gradle
    dependencies {
        implementation fileTree(dir: 'libs', include: ['*.aar'])

        // Other dependancies on which hawcx.package is dependent
        // you can change versions as per porject's need

        implementation 'com.squareup.okhttp3:okhttp:5.0.0-alpha.11'
        implementation 'com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11'
        implementation 'com.squareup.retrofit2:retrofit:2.10.0'
        implementation 'com.squareup.retrofit2:converter-gson:2.10.0'
        implementation "androidx.biometric:biometric-ktx:1.2.0-alpha05"
        implementation 'androidx.security:security-crypto:1.1.0-alpha03'
    }
   ```

   Now in ``, add the minSdkVersion as 26:
   (Hawcx works with SDK virsion 26 and above)

   ```gradle
   minSdkVersion = 26
   ```

3. **Create a Native Module** 

   Create a new Java file named `HawcxModule.kt` in `android\app\src\main\java\com\hawcxapp\`:

   ```java
    package com.yourproject

    import android.widget.Toast
    import android.content.Context
    import androidx.biometric.BiometricPrompt
    import androidx.core.content.ContextCompat
    import com.facebook.react.bridge.Promise
    import com.facebook.react.bridge.ReactApplicationContext
    import com.facebook.react.bridge.ReactContextBaseJavaModule
    import com.facebook.react.bridge.ReactMethod
    import com.hawcx.HawcxInitializer
    import com.hawcx.auth.SignUp
    import com.hawcx.auth.SignIn
    import com.hawcx.auth.Restore
    import com.hawcx.utils.AuthErrorHandler
    import android.os.Handler
    import android.os.Looper


    class HawcxModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

        // Hardcoded API Key
        private val apiKey = "YOUR_API_KEY"
        private var signUp: SignUp? = null
        private var signIn: SignIn? = null
        private var restore: Restore? = null

        init {
            HawcxInitializer.getInstance().init(reactContext, "apiKey")
            signUp = SignUp(reactContext, apiKey)
            signIn = SignIn(reactContext, apiKey)
            restore = Restore(reactContext, apiKey)
        }
        override fun getName(): String {
            return "HawcxModule"
        }
        
        // Expose the signUp method to React Native
        @ReactMethod
        fun signUp(username: String, promise: Promise) {
            signUp?.signUp(username, object : SignUp.SignUpCallback {
                override fun showError(errorCode: String) {
                    promise.reject("SIGNUP_ERROR", "SignUp failed: $errorCode")
                }

                override fun showError(errorCode: AuthErrorHandler.SignUpErrorCode, errorMessage: String) {
                    promise.reject("SIGNUP_ERROR", "SignUp failed: $errorMessage")
                }

                override fun onGenerateOTPSuccess() {
                    promise.resolve("OTP generated successfully")
                }

                override fun onSuccessfulSignup() {
                    promise.resolve("Sign up successful")
                }
            })
        }

        // Expose the handleVerifyOTP method to React Native
        @ReactMethod
        fun handleVerifyOTP(username: String, otp: String, promise: Promise) {
            signUp?.handleVerifyOTP(username, otp, object : SignUp.SignUpCallback {
                override fun showError(errorCode: String) {
                    promise.reject("VERIFY_OTP_ERROR", "OTP verification failed: $errorCode")
                }

                override fun showError(errorCode: AuthErrorHandler.SignUpErrorCode, errorMessage: String) {
                    promise.reject("VERIFY_OTP_ERROR", "OTP verification failed: $errorMessage")
                }

                override fun onGenerateOTPSuccess() {
                    // Not used for OTP verification
                }

                override fun onSuccessfulSignup() {
                    promise.resolve("OTP verified and signup complete")
                }
            })
        }

        // Expose the signIn method to React Native
        @ReactMethod
        fun signIn(username: String, promise: Promise) {
            signIn?.signIn(username, object : SignIn.SignInCallback {
                override fun showEmailSignInScreen() {
                    promise.reject("EMAIL_SIGN_IN_REQUIRED", "Please sign in with your email.")
                }

                override fun onSuccessfulLogin(username: String) {
                    promise.resolve("Login successful")
                }

                override fun showError(errorCode: AuthErrorHandler.SignInErrorCode, message: String) {
                    promise.reject("SIGNIN_ERROR", "SignIn failed: $message")
                }

                override fun showError(message: String) {
                    promise.reject("SIGNIN_ERROR", "SignIn failed: $message")
                }

                override fun initiateBiometricLogin(callback: Runnable) {
                    initiateBiometricLogin(promise, callback)
                }
            })
        }

        // Expose the getLastUser method to React Native
        @ReactMethod
        fun getLastUser(promise: Promise) {
            try {
                val lastUser = signIn?.getLastUser() ?: ""
                promise.resolve(lastUser)
            } catch (e: Exception) {
                promise.reject("GET_LAST_USER_ERROR", "Failed to get last user: ${e.message}")
            }
        }

        // Expose the checkLastUser method to React Native
        @ReactMethod
        fun checkLastUser(promise: Promise) {
            signIn?.checkLastUser(object : SignIn.SignInCallback {
                override fun showEmailSignInScreen() {
                    promise.resolve("SHOW_EMAIL_SIGN_IN_SCREEN")
                }

                override fun onSuccessfulLogin(username: String) {
                    promise.resolve("Login successful for user: $username")
                }

                override fun showError(errorCode: AuthErrorHandler.SignInErrorCode, message: String) {
                    promise.reject("CHECK_LAST_USER_ERROR", "Check last user failed: $message")
                }

                override fun showError(message: String) {
                    promise.reject("CHECK_LAST_USER_ERROR", "Check last user failed: $message")
                }

                override fun initiateBiometricLogin(callback: Runnable) {
                    initiateBiometricLogin(promise, callback)
                }
            })
        }

        // Method to handle biometric login using React Native Biometrics
        @ReactMethod
        private fun initiateBiometricLogin(promise: Promise, callback: Runnable) {
            val currentActivity = reactApplicationContext.currentActivity

            // Ensure currentActivity is not null
            if (currentActivity == null) {
                promise.reject("ACTIVITY_ERROR", "Current activity is not available")
                return
            }

            // Ensure currentActivity is a FragmentActivity
            if (currentActivity !is androidx.fragment.app.FragmentActivity) {
                promise.reject("ACTIVITY_ERROR", "Current activity is not a FragmentActivity")
                return
            }

            // Run on the main thread to avoid fragment transaction issues
            Handler(Looper.getMainLooper()).post {
                val fragmentActivity = currentActivity as androidx.fragment.app.FragmentActivity
                val executor = ContextCompat.getMainExecutor(reactApplicationContext)

                // Create the BiometricPrompt
                val biometricPrompt = BiometricPrompt(
                    fragmentActivity, 
                    executor,
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationError(errorCode: Int, errorMessage: CharSequence) {
                            super.onAuthenticationError(errorCode, errorMessage)
                            promise.reject("BIOMETRIC_AUTH_ERROR", "Authentication error: $errorMessage")
                        }

                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            promise.resolve("Biometric login successful")
                            callback.run()
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            promise.reject("BIOMETRIC_AUTH_FAILED", "Authentication failed")
                        }
                    })

                // Build the BiometricPrompt promptInfo
                val promptInfo = BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Login")
                    .setDescription("Use your fingerprint or face to log in")
                    .setNegativeButtonText("Cancel")
                    .build()

                // Show the biometric prompt to the user
                biometricPrompt.authenticate(promptInfo)
            }
        }


        // Expose the generateOtpForAccountRestore method for account restoration
        @ReactMethod
        fun generateOtpForAccountRestore(email: String, promise: Promise) {
            restore?.generateOtp(email, object : Restore.OnSuccessListener {
                override fun onSuccess(message: String) {
                    promise.resolve(message)
                }
            }, object : Restore.OnErrorListener {
                override fun onError(error: String) {
                    promise.reject("GENERATE_OTP_ERROR", error)
                }
            })
        }

        // Expose the verifyOtpForAccountRestore method for account restoration
        @ReactMethod
        fun verifyOtpForAccountRestore(email: String, otp: String, promise: Promise) {
            restore?.verifyOtp(email, otp, object : Restore.OnSuccessListener {
                override fun onSuccess(message: String) {
                    promise.resolve(message)
                }
            }, object : Restore.OnErrorListener {
                override fun onError(error: String) {
                    promise.reject("VERIFY_OTP_ERROR", error)
                }
            })
        }

        // Add other methods for different HawcxAuth features

    }
   ```

4. **Create a Package for the Native Module**

   Create `HawcxPackage.kt` at `android\app\src\main\java\com\hawcxapp\`:

   ```java
    package com.hawcxapp

    import com.facebook.react.ReactPackage
    import com.facebook.react.bridge.NativeModule
    import com.facebook.react.bridge.ReactApplicationContext
    import com.facebook.react.uimanager.ViewManager

    class HawcxPackage : ReactPackage {

        override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
            return listOf(HawcxModule(reactContext))  // Register the HawcxModule
        }

        override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
            return emptyList()  // No ViewManagers to register
        }
    }
   ```

5. **Register the Package**

   In `MainApplication.kt`, add the HawcxPackage to the list of packages:

   ```java
    override val reactNativeHost: ReactNativeHost =
        object : DefaultReactNativeHost(this) {
        override fun getPackages(): List<ReactPackage> =
            PackageList(this).packages.apply {
                // Register the HawcxPackage Package
                add(HawcxPackage())
            }

        override fun getJSMainModuleName(): String = "index"

        override fun getUseDeveloperSupport(): Boolean = BuildConfig.DEBUG

        override val isNewArchEnabled: Boolean = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED
        override val isHermesEnabled: Boolean = BuildConfig.IS_HERMES_ENABLED
        }
   ```
6. **Add requirements in `android\app\src\main\AndroidManifest.xml`**

   You need to add biometric access to the to the `AndroidManifest.xml` as following:
   (you can copy the following xml config or check your config for xmlns:tools, permission, feature, android:supportsRt and tools:replace)

   ```xml
    <manifest xmlns:android="http://schemas.android.com/apk/res/android" xmlns:tools="http://schemas.android.com/tools">

    <!-- Permissions required for biometric authentication -->
    <uses-permission android:name="android.permission.USE_BIOMETRIC" />
    <uses-permission android:name="android.permission.USE_FINGERPRINT" />
    <uses-permission android:name="android.permission.INTERNET" />

    <!-- Features related to biometrics -->
    <uses-feature
        android:name="android.hardware.fingerprint"
        android:required="false" />
    <uses-feature
        android:name="android.hardware.biometrics"
        android:required="false" />

    <application
        android:name=".MainApplication"
        android:label="@string/app_name"
        android:icon="@mipmap/ic_launcher"
        android:allowBackup="false"
        android:theme="@style/AppTheme"
        android:supportsRtl="true"
        tools:replace="android:allowBackup, android:theme">

        <activity
        android:name=".MainActivity"
        android:label="@string/app_name"
        android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|screenSize|smallestScreenSize|uiMode"
        android:launchMode="singleTask"
        android:windowSoftInputMode="adjustResize"
        android:exported="true">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
        </activity>
    </application>
    </manifest>

   ```

7. **Use HawcxAuth in React Native**

   Now you can use HawcxAuth in your React Native components:

   ```javascript
    import { NativeModules } from 'react-native';

    const { HawcxModule } = NativeModules;

    // Example usage

    // Check last logged in user
    const SignupScreen: React.FC<Props> = ({ navigation }) => {
    const [email, setEmail] = useState('');
    const [loading, setLoading] = useState(false);

    // Check if a user exists on component mount and trigger biometric login if a user exists
    useEffect(() => {
        HawcxModule.checkLastUser()
        .then((result: string) => {
            if (result === 'Biometric login successful') {
            // Navigate to Home if biometric login is successful
            navigation.replace('Home');
            } else if (result === 'SHOW_EMAIL_SIGN_IN_SCREEN') {
            // If user exists but needs to sign in manually, navigate to the SignIn screen
            navigation.navigate('SignIn');
            }
        })
        .catch((error: any) => {
            console.error('Error checking last user', error);
        });
    }, []);

    const handleSignup = () => {
        setLoading(true);
        // Call the signUp method from HawcxModule
        HawcxModule.signUp(email)
        .then(() => {
            // Navigate to Verification screen if OTP is successfully generated
            navigation.navigate('Verification', { email });
            setLoading(false);
        })
        .catch((error: any) => {
            // Show an error message if signup fails
            Alert.alert('Signup Error', error.message || 'An error occurred');
            setLoading(false);
        });
    };

    return (
        <View style={styles.container}>
        <Text>Enter your Email to Sign Up:</Text>
        <TextInput
            style={styles.input}
            placeholder="Email"
            value={email}
            onChangeText={setEmail}
            keyboardType="email-address"
        />
        <Button title={loading ? 'Signing Up...' : 'Sign Up'} onPress={handleSignup} disabled={loading} />

        {/* Link to navigate to SignIn Screen */}
        <TouchableOpacity onPress={() => navigation.navigate('SignIn')}>
            <Text style={styles.link}>Already have an account? Sign In</Text>
        </TouchableOpacity>
        </View>
    );
    };
   ```

## Best Practices

- Always handle errors and exceptions from native methods.
- Use async/await for cleaner code when calling native methods.
- Implement proper error handling and user feedback in your React Native UI.

