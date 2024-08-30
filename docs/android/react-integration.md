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
       implementation files('libs/hawcx.aar')
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
   import com.hawcx.HawcxInitializer;
   import com.hawcx.auth.SignIn;

    public class HawcxModule extends ReactContextBaseJavaModule implements SignIn.SignInCallback {
        private ReactApplicationContext reactContext;
        private Promise currentPromise;

        public HawcxModule(ReactApplicationContext reactContext) {
            super(reactContext);
            HawcxInitializer.getInstance().init(reactContext, "YOUR_API_KEY_HERE");
        }

        @Override
        public String getName() {
            return "HawcxModule";
        }


        @ReactMethod
        public void checkLastUser(Promise promise) {
            SignIn loginAct = HawcxInitializer.getInstance().getSignIn();
            loginAct.checkLastUser(reactContext);
            promise.resolve(null);
        }

        @ReactMethod
            public void login(String email, Promise promise) {
                currentPromise = promise;
                SignIn loginAct = HawcxInitializer.getInstance().getSignIn();
                loginAct.signIn(email, this);
            }

            @Override
            public void onSuccessfulLogin(String loggedInEmail) {
                if (currentPromise != null) {
                    currentPromise.resolve(loggedInEmail);
                    currentPromise = null;
                }
            }

            @Override
            public void showError(String errorMessage) {
                if (currentPromise != null) {
                    currentPromise.reject("LOGIN_ERROR", errorMessage);
                    currentPromise = null;
                }
            }
       // Add other methods for different HawcxAuth features
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

6. **Use HawcxAuth in React Native**

   Now you can use HawcxAuth in your React Native components:

   ```javascript
    import { NativeModules } from 'react-native';

    const { HawcxModule } = NativeModules;

    // Example usage

    // Check last logged in user
    HawcxModule.checkLastUser()
    .then(() => {
        console.log('Checked last user');
    })
    .catch((error) => {
        console.error(error);
    });

    // Login
    HawcxModule.login('user@example.com')
    .then((loggedInEmail) => {
        console.log('Login successful:', loggedInEmail);
    })
    .catch((error) => {
        console.error('Login failed:', error);
    });
   ```

## Best Practices

- Always handle errors and exceptions from native methods.
- Use async/await for cleaner code when calling native methods.
- Implement proper error handling and user feedback in your React Native UI.

