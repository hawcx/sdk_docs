<!-- End Cloudflare Web Analytics -->

<script async src="https://www.googletagmanager.com/gtag/js?id=G-B89K3ZN1LX"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-B89K3ZN1LX');
</script>

# Integrating Hawcx into an Existing Project

This guide will walk you through the process of adding Hawcx to your existing Android project. By following these steps, you'll be able to enhance your app's security features quickly and efficiently.

## Prerequisites

- Android Studio installed on your development machine
- An existing Android project
- Minimum SDK version 26 or higher

## Step 1: Add the Hawcx AAR

1. [Download](https://github.com/hawcx/authenticator/releases/download/app/hawcx-1.0.aar) the Hawcx AAR file

2. In your Android project, create a new folder named `libs` in the `app` directory if it doesn't already exist.

3. Copy the downloaded AAR file into the `libs` folder.

4. Please make sure that names match with the names provided in the document.

## Step 2: Update Gradle Configuration

1. Open your app-level `build.gradle` file.

2. Add the following to the `dependencies` section:

```gradle
dependencies {
    implementation files('libs/hawcx.aar')
    // Other dependencies...
}
```

3. Sync your project with the Gradle files.

## Step 3: Initialize Hawcx

1. Open your main Application class. If you don't have one, create a new class that extends `Application`.

2. Add the following code to initialize Hawcx:

```java
import com.hawcx.HawcxInitializer;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HawcxInitializer.getInstance().init(this, "YOUR_API_KEY_HERE");    }
}
```

3. If you created a new Application class, make sure to register it in your `AndroidManifest.xml`:

```xml
<application
    android:name=".MyApplication"
    ...>
    <!-- Your existing application components -->
</application>
```

## Step 4: Implement Hawcx Features

Now that Hawcx is integrated into your project, you can start using its features. Here are a few examples:

### Secure User Authentication

```java
import com.hawcx.auth.SignIn;

// In your login activity or fragment
SignIn loginAct = HawcxInitializer.getInstance().getSignIn();
// Check last logged in user and signal biometric auth if applicable
loginAct.checkLastUser(this);

loginAct.signIn(email, this);

@Override
public void onSuccessfulLogin(String loggedInEmail) {
    // Handle successful login
}

@Override
public void showError(String errorMessage) {
    // Handle login failure
}

```

<!-- 

## Next Steps

- Explore the [Hawcx API Documentation](api-docs.md) for a complete list of available features and methods.
- Implement [Biometric Authentication](biometric-auth.md) for enhanced security.
- Learn about [Best Practices](best-practices.md) when using Hawcx.

If you encounter any issues during integration, please refer to our [Troubleshooting Guide](../troubleshoot.md) or contact our support team.

Congratulations! You've successfully integrated Hawcx into your existing Android project. Your app is now equipped with advanced security features to protect your users' data. -->
