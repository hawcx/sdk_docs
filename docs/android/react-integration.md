# React Native Integration Guide for HawcxFramework

This guide will walk you through the process of integrating HawcxFramework into your React Native application.

## Prerequisites

- React Native development environment set up
- Basic knowledge of React Native and Android development

## Integration Steps

1. **Add HawcxFramework to your Android project**

   In your React Native project, navigate to the `android/app` directory and create a `libs` folder if it doesn't exist. Place the HawcxFramework AAR file in this folder.

2. **Update build.gradle**

   In `android/app/build.gradle`, add the following:

   ```gradle
   dependencies {
       implementation files('libs/hawcxframework.aar')
   }
   ```

3. **Create a Native Module**

   Create a new Java file named `HawcxModule.java` in `android/app/src/main/java/com/yourproject/`:

   ```java
   package com.yourproject;

   import com.facebook.react.bridge.ReactApplicationContext;
   import com.facebook.react.bridge.ReactContextBaseJavaModule;
   import com.facebook.react.bridge.ReactMethod;
   import com.facebook.react.bridge.Promise;
   import com.hawcx.framework.HawcxFramework;
   import com.hawcx.framework.auth.HawcxAuth;

   public class HawcxModule extends ReactContextBaseJavaModule {
       public HawcxModule(ReactApplicationContext reactContext) {
           super(reactContext);
           HawcxFramework.init(reactContext);
       }

       @Override
       public String getName() {
           return "HawcxModule";
       }

       @ReactMethod
       public void login(String username, String password, Promise promise) {
           HawcxAuth.login(username, password, new HawcxAuth.AuthCallback() {
               @Override
               public void onSuccess() {
                   promise.resolve("Login successful");
               }

               @Override
               public void onFailure(String errorMessage) {
                   promise.reject("LOGIN_ERROR", errorMessage);
               }
           });
       }

       // Add other methods for different HawcxFramework features
   }
   ```

4. **Create a Package for the Native Module**

   Create `HawcxPackage.java`:

   ```java
   package com.yourproject;

   import com.facebook.react.ReactPackage;
   import com.facebook.react.bridge.NativeModule;
   import com.facebook.react.bridge.ReactApplicationContext;
   import com.facebook.react.uimanager.ViewManager;

   import java.util.ArrayList;
   import java.util.Collections;
   import java.util.List;

   public class HawcxPackage implements ReactPackage {
       @Override
       public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
           return Collections.emptyList();
       }

       @Override
       public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
           List<NativeModule> modules = new ArrayList<>();
           modules.add(new HawcxModule(reactContext));
           return modules;
       }
   }
   ```

5. **Register the Package**

   In `MainApplication.java`, add the HawcxPackage to the list of packages:

   ```java
   @Override
   protected List<ReactPackage> getPackages() {
     List<ReactPackage> packages = new PackageList(this).getPackages();
     packages.add(new HawcxPackage()); // Add this line
     return packages;
   }
   ```

6. **Use HawcxFramework in React Native**

   Now you can use HawcxFramework in your React Native components:

   ```javascript
   import { NativeModules } from 'react-native';

   const { HawcxModule } = NativeModules;

   // Example usage
   HawcxModule.login('username', 'password')
     .then(result => console.log(result))
     .catch(error => console.error(error));
   ```

## Best Practices

- Always handle errors and exceptions from native methods.
- Use async/await for cleaner code when calling native methods.
- Implement proper error handling and user feedback in your React Native UI.

For more advanced features and detailed API usage, refer to our [complete API reference](api-reference.md).