<!-- End Cloudflare Web Analytics -->

<script async src="https://www.googletagmanager.com/gtag/js?id=G-B89K3ZN1LX"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'G-B89K3ZN1LX');
</script>


# Creating a New Project with Hawcx

This guide will walk you through the process of creating a new Android project with Hawcx integration from scratch.

## Prerequisites

- Android Studio installed on your development machine
- Basic knowledge of Android development

## Steps

1. **Create a New Android Project**
   - Open Android Studio
   - Click on "File" > "New" > "New Project"
   - Choose "Empty Activity" and click "Next"
   - Set your application name, package name, and minimum SDK (26 or higher)
   - Click "Finish" to create the project

2. **Add Hawcx AAR**
   - [Download](https://github.com/hawcx/authenticator/releases/download/app/hawcx-1.0.aar) the Hawcx AAR file.
   - Create a new folder named `libs` in your project's `app` directory
   - Copy the downloaded AAR file into the `libs` folder
   - Please make sure that names match with the names provided in the document.

3. **Update Gradle Configuration**
   - Open your app-level `build.gradle` file
   - Add the following to the `dependencies` section:

     ```gradle
     dependencies {
         implementation files('libs/hawcx.aar')
         // Other dependencies...
     }
     ```
   - Sync your project with Gradle files

4. **Initialize Hawcx Authentication**
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

5. **Implement Hawcx Features**
   - Now you can start using Hawcx features in your activities and fragments. 

  ```java
    import com.hawcx.auth.SignIn;
    import com.hawcx.HawcxInitializer;

    public class MainActivity extends AppCompatActivity implements SignIn.SignInCallback {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // Example: Implement secure login
            // User Login
            SignIn loginAct = HawcxInitializer.getInstance().getSignIn();

            // Check last logged in user and signal biometric auth if applicable
            loginAct.checkLastUser(this);

            loginAct.signIn(email, this);

            }
            @Override
            public void onSuccessfulLogin(String loggedInEmail) {
                // Handle successful login
            }

            @Override
            public void showError(String errorMessage) {
                // Handle login failure
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
    }
  ```

<!-- 
## Next Steps

- Explore our [Java Integration Guide](java-integration.md) for more detailed usage of Hawcx
- Learn about [Best Practices](best-practices.md) when using Hawcx
- Check out our [Sample Projects](sample-projects.md) for inspiration

Congratulations! You've successfully created a new Android project with Hawcx integration. -->
