# Java Integration Guide for Hawcx

This guide provides detailed instructions on how to use Hawcx in your Java Android application. We'll cover key features and best practices to help you make the most of Hawcx's security capabilities.

## Table of Contents

1. [Initialization](#initialization)
2. [User Authentication](#user-authentication)
3. [Biometric Authentication](#biometric-authentication)

## Initialization

Before using any Hawcx features, you need to initialize it in your Application class:

```java
import android.app.Application;
import com.hawcx.HawcxInitializer;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HawcxInitializer.getInstance().init(this, "YOUR_API_KEY_HERE");
    }
}
```

Don't forget to register this Application class in your AndroidManifest.xml.

## User Authentication

HawcxInitializer provides secure user authentication methods:

```java
import com.hawcx.auth.SignUp;
import com.hawcx.auth.SignIn;
import com.hawcx.HawcxInitializer;

// User Registration
SingUp registerAct = HawcxInitializer.getInstance().getSignUp();

registerAct.signUp("username", this::onSuccessHandler, this::onFailureHandler);


// User Login
SignIn loginAct = HawcxInitializer.getInstance().getSignIn();

// Check last logged in user and signal biometric auth if applicable
loginAct.checkLastUser(this);

loginAct.signIn(email, this);

// implement the class with SignIn.SignInCallback
@Override
public void onSuccessfulLogin(String loggedInEmail) {
    // Handle successful login
}

@Override
public void showError(String errorMessage) {
    // Handle login failure
}

```

## Biometric Authentication

HawcxInitializer's signIn object provides biometric authentication to be implemented:

```java
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import com.hawcx.auth.SignIn;


public class LoginActivity extends AppCompatActivity implements SignIn.SignInCallback {

 @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        signIn = HawcxInitializer.getInstance().getSignIn();
        // Check last logged in user and signal biometric auth if applicable
        signIn.checkLastUser(this);
    }

// If lastuser found
@Override
    public void initiateBiometricLogin(Runnable onSuccess) {
        // Handle Biometric Auth 
    }

// If no last user is found 
@Override
    public void showEmailSignInScreen() {
        // Handle the Email screen
    }
}

```


## Best Practices

1. Always initialize Hawcx before using any of its features.
2. Use strong, unique passwords for each user.
3. Implement multi-factor authentication when possible.
4. Regularly update Hawcx to the latest version.
5. Use SecureStorage for all sensitive data.
6. Implement proper error handling for all Hawcx methods.
7. Use HTTPS for all network communications.
8. Regularly check for app tampering.

## Conclusion

This guide covers the basic usage of Hawcx in a Java Android application.

If you encounter any issues or have questions, please check our [FAQ](../faqs.md) or contact our support team.
