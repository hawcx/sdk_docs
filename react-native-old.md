# Integrating HawcxFramework in React Native Application

This guide will walk you through the process of integrating HawcxFramework into your React Native application. We'll cover how to set up the necessary native modules, bridge the functionality to JavaScript, and implement signup and login features.

## Table of Contents

1. [Prerequisites](#1-prerequisites)
2. [Setting Up the Native Module](#2-setting-up-the-native-module)
3. [Bridging to JavaScript](#3-bridging-to-javascript)
4. [Implementing Signup](#4-implementing-signup)
5. [Implementing Login](#5-implementing-login)
6. [Error Handling](#6-error-handling)

## 1. Prerequisites

Before you begin, ensure you have:

- A React Native project set up
- The HawcxFramework AAR file
- Basic knowledge of React Native and Android development

## 2. Setting Up the Native Module

1. Place the `hawcx.aar` file in your Android project's `app/libs` directory.

2. In your app's `build.gradle` file, add:

```
  dependencies {
      implementation files('libs/hawcx.aar')
      // Other dependencies...
  }
```

3. Create a new Java class called ```HawcxModule.java``` in your Android project:

```
package com.yourproject;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

import com.example.hawcx.repository.SignUpManager;
import com.example.hawcx.repository.SignInManager;
import com.example.hawcx.utils.Constants;

public class HawcxModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public HawcxModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "HawcxModule";
    }

    @ReactMethod
    public void signup(String email, Promise promise) {
        SignUpManager signUpManager = new SignUpManager(reactContext, Constants.PRIVATE_KEY);
        signUpManager.handleSignUp(email, "true", new SignUpManager.SignupCallback() {
            @Override
            public void onSuccess(String message) {
                promise.resolve(message);
            }

            @Override
            public void onFailure(String errorMessage) {
                promise.reject("SIGNUP_ERROR", errorMessage);
            }
        });
    }

    @ReactMethod
    public void login(String email, Promise promise) {
        SignInManager signInManager = new SignInManager(reactContext, Constants.PRIVATE_KEY);
        try {
            boolean success = signInManager.signIn(email, reactContext);
            if (success) {
                promise.resolve("Login successful");
            } else {
                promise.reject("LOGIN_ERROR", "Login failed");
            }
        } catch (Exception e) {
            promise.reject("LOGIN_ERROR", e.getMessage());
        }
    }
}
```

4. Create a ```HawcxPackage.java``` file:

```
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

5. In your ```MainApplication.java```, add the HawcxPackage:

```
@Override
protected List<ReactPackage> getPackages() {
  List<ReactPackage> packages = new PackageList(this).getPackages();
  packages.add(new HawcxPackage()); // Add this line
  return packages;
}
```

## 3. Bridging to JavaScript

Create a JavaScript wrapper for the native module:

```
// HawcxBridge.js
import { NativeModules } from 'react-native';

const { HawcxModule } = NativeModules;

export default {
  signup: (email) => HawcxModule.signup(email),
  login: (email) => HawcxModule.login(email),
};
```

## 4. Implementing Signup

Use the bridge in your React Native components:

```
import React, { useState } from 'react';
import { View, TextInput, Button, Alert } from 'react-native';
import HawcxBridge from './HawcxBridge';

const SignupScreen = () => {
  const [email, setEmail] = useState('');

  const handleSignup = async () => {
    try {
      const result = await HawcxBridge.signup(email);
      Alert.alert('Success', result);
    } catch (error) {
      Alert.alert('Error', error.message);
    }
  };

  return (
    <View>
      <TextInput
        value={email}
        onChangeText={setEmail}
        placeholder="Enter email"
        keyboardType="email-address"
      />
      <Button title="Sign Up" onPress={handleSignup} />
    </View>
  );
};

export default SignupScreen;
```


## 5. Implementing Login

Similarly, implement the login functionality:

```
import React, { useState } from 'react';
import { View, TextInput, Button, Alert } from 'react-native';
import HawcxBridge from './HawcxBridge';

const LoginScreen = () => {
  const [email, setEmail] = useState('');

  const handleLogin = async () => {
    try {
      const result = await HawcxBridge.login(email);
      Alert.alert('Success', result);
      // Navigate to home screen or perform post-login actions
    } catch (error) {
      Alert.alert('Error', error.message);
    }
  };

  return (
    <View>
      <TextInput
        value={email}
        onChangeText={setEmail}
        placeholder="Enter email"
        keyboardType="email-address"
      />
      <Button title="Log In" onPress={handleLogin} />
    </View>
  );
};

export default LoginScreen;
```

## 6. Error Handling

Proper error handling is crucial for a smooth user experience. Always wrap your HawcxBridge method calls in try-catch blocks and provide user-friendly error messages.

```
try {
  // HawcxBridge method calls
} catch (error) {
  console.error('HawcxFramework Error:', error);
  Alert.alert('Error', 'An unexpected error occurred. Please try again.');
}
```

Remember to replace ```Constants.PRIVATE_KEY``` with your actual private key in the native module. This should be securely stored and not hardcoded in your application.
