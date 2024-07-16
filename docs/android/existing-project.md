# Integrating HawcxFramework into an Existing Project

This guide will walk you through the process of adding HawcxFramework to your existing Android project. By following these steps, you'll be able to enhance your app's security features quickly and efficiently.

## Prerequisites

- Android Studio installed on your development machine
- An existing Android project
- Minimum SDK version 21 or higher

## Step 1: Add the HawcxFramework AAR

1. [Download](https://github.com/hawcx/android_sdk/releases/download/v0.2.1/hawcx.aar) the HawcxFramework AAR file

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

## Step 3: Initialize HawcxFramework

1. Open your main Application class. If you don't have one, create a new class that extends `Application`.

2. Add the following code to initialize HawcxFramework:

```java
import com.hawcx.framework.HawcxFramework;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HawcxFramework.init(this);
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

## Step 4: Implement HawcxFramework Features

Now that HawcxFramework is integrated into your project, you can start using its features. Here are a few examples:

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

### Encrypt Sensitive Data

```java
import com.hawcx.framework.crypto.HawcxCrypto;

String sensitiveData = "This is sensitive information";
String encryptedData = HawcxCrypto.encrypt(sensitiveData);
// Store encryptedData securely
```

### Secure API Calls

```java
import com.hawcx.framework.network.HawcxApiClient;

HawcxApiClient apiClient = new HawcxApiClient();
apiClient.get("https://api.example.com/data", new HawcxApiClient.ApiCallback() {
    @Override
    public void onResponse(String response) {
        // Handle API response
    }

    @Override
    public void onError(String errorMessage) {
        // Handle API error
    }
});
```

## Next Steps

- Explore the [HawcxFramework API Documentation](api-docs.md) for a complete list of available features and methods.
- Implement [Biometric Authentication](biometric-auth.md) for enhanced security.
- Learn about [Best Practices](best-practices.md) when using HawcxFramework.

If you encounter any issues during integration, please refer to our [Troubleshooting Guide](../troubleshoot.md) or contact our support team.

Congratulations! You've successfully integrated HawcxFramework into your existing Android project. Your app is now equipped with advanced security features to protect your users' data.