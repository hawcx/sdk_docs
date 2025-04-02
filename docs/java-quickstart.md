
### Java (Android) Quick Start

This guide will help you quickly integrate the Hawcx SDK into your Android project.

#### 1. Installation

- **Step 1:** [Download](https://github.com/hawcx/authenticator/releases/latest/download/hawcx.aar) the Hawcx AAR file
- **Step 2:** Create a new folder named `libs` in your project's `app` directory, and copy the downloaded AAR file into the `libs` folder
- **Step 3:** Update your `app/build.gradle` to include the AAR:
  
  ```gradle
    dependencies {
        implementation files('libs/hawcx.aar')
        // Other dependencies...
    }
  ```

#### 2. Initialization

Initialize the SDK in your Application class using the `HawcxInitializer`:

   - Create a new Application class:

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
   - Register the Application class in your `AndroidManifest.xml`:

     ```xml
        <application
            android:name=".MyApplication"
            ...>
            <!-- Your activities and other components -->
        </application>
     ```
#### 3. Using the Authentication Components

After initialization, you can access the authentication components (`SignUp`, `SignIn`, and `Restore`) as follows:

```java
    import com.hawcx.HawcxInitializer;
    import com.hawcx.auth.SignUp;
    import com.hawcx.auth.SignIn;
    import com.hawcx.auth.Restore;

    // Example usage in an Activity or other component
    HawcxInitializer initializer = HawcxInitializer.getInstance();
    SignUp signUp = initializer.getSignUp();
    SignIn signIn = initializer.getSignIn();
    Restore restore = initializer.getRestore();

    // Example: Sign up a new user
    signUp.signUp("user@example.com", new Runnable() {
        @Override
        public void run() {
            // Handle successful sign up
            System.out.println("Sign up successful!");
        }
    }, new SignUp.OnErrorListener() {
        @Override
        public void onError(String errorMessage) {
            // Handle sign up error
            System.err.println("Sign up error: " + errorMessage);
        }
    });
```

#### 4. Next Steps

- Explore the [API Reference](./api-reference/index.md) for detailed method descriptions.
- Review the [Authentication Components](./authentication/index.md) section for more advanced usage scenarios.
- For troubleshooting, see our [FAQ & Troubleshooting](./faq.md) guide.

---