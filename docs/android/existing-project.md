# Integrating Hawcx into an Existing Project

This guide will walk you through the process of adding Hawcx to your existing Android project. By following these steps, you'll be able to enhance your app's security features quickly and efficiently.

## Prerequisites

- Android Studio installed on your development machine
- An existing Android project
- Minimum SDK version 21 or higher

## Step 1: Add the Hawcx AAR

1. [Download](https://github.com/hawcx/android_sdk/releases/download/v0.2.1/hawcx.aar) the Hawcx AAR file

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
import com.hawcx.framework.Hawcx;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Hawcx.init(this);
    }
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
import com.hawcx.framework.auth.HawcxAuth;

// In your login activity or fragment
HawcxAuth.login(username, password, new HawcxAuth.AuthCallback() {
    @Override
    public void onSuccess() {
        // Handle successful login
    }

    @Override
    public void onFailure(String errorMessage) {
        // Handle login failure
    }
});
```



## Next Steps

- Explore the [Hawcx API Documentation](api-docs.md) for a complete list of available features and methods.
- Implement [Biometric Authentication](biometric-auth.md) for enhanced security.
- Learn about [Best Practices](best-practices.md) when using Hawcx.

If you encounter any issues during integration, please refer to our [Troubleshooting Guide](../troubleshoot.md) or contact our support team.

Congratulations! You've successfully integrated Hawcx into your existing Android project. Your app is now equipped with advanced security features to protect your users' data.
