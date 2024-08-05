# Creating a New Project with HawcxFramework

This guide will walk you through the process of creating a new Android project with HawcxFramework integration from scratch.

## Prerequisites

- Android Studio installed on your development machine
- Basic knowledge of Android development

## Steps

1. **Create a New Android Project**
   - Open Android Studio
   - Click on "File" > "New" > "New Project"
   - Choose "Empty Activity" and click "Next"
   - Set your application name, package name, and minimum SDK (21 or higher)
   - Click "Finish" to create the project

2. **Add HawcxFramework AAR**
   - [Download](https://github.com/hawcx/android_sdk/releases/download/v0.2.1/hawcx.aar) the HawcxFramework AAR file.
   - Create a new folder named `libs` in your project's `app` directory
   - Copy the downloaded AAR file into the `libs` folder
   - Please make sure that names match with the names provided in the document.

3. **Update Gradle Configuration**
   - Open your app-level `build.gradle` file
   - Add the following to the `dependencies` section:

     ```gradle
     dependencies {
         implementation files('libs/hawcxframework.aar')
         // Other dependencies...
     }
     ```
   - Sync your project with Gradle files

4. **Initialize HawcxFramework**
   - Create a new Application class:

     ```java
     import android.app.Application;
     import com.hawcx.framework.HawcxFramework;

     public class MyApplication extends Application {
         @Override
         public void onCreate() {
             super.onCreate();
             HawcxFramework.init(this);
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

5. **Implement HawcxFramework Features**
   - Now you can start using HawcxFramework features in your activities and fragments. For example:

     ```java
     import com.hawcx.framework.auth.HawcxAuth;

     public class MainActivity extends AppCompatActivity {
         @Override
         protected void onCreate(Bundle savedInstanceState) {
             super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_main);

             // Example: Implement secure login
             HawcxAuth.login("username", new HawcxAuth.AuthCallback() {
                 @Override
                 public void onSuccess() {
                     // Handle successful login
                 }

                 @Override
                 public void onFailure(String errorMessage) {
                     // Handle login failure
                 }
             });
         }
     }
     ```

## Next Steps

- Explore our [Java Integration Guide](java-integration.md) for more detailed usage of HawcxFramework
- Learn about [Best Practices](best-practices.md) when using HawcxFramework
- Check out our [Sample Projects](sample-projects.md) for inspiration

Congratulations! You've successfully created a new Android project with HawcxFramework integration.