# Integrating HawcxFramework in Java Application

This guide will walk you through the process of integrating HawcxFramework into your Java Android application. We'll cover how to add the AAR, set up the necessary configurations, and implement signup and login functionality.

## Table of Contents

1. [Adding HawcxFramework AAR](#1-adding-hawcxframework-aar)
2. [Initializing HawcxFramework](#2-initializing-hawcxframework)
3. [Implementing Signup](#3-implementing-signup)
4. [Implementing Login](#4-implementing-login)
5. [Using the Demo Application](#5-using-the-demo-application)

## 1. Adding HawcxFramework AAR

### Option A: Starting from Scratch

1. Create a new Android project in Android Studio.
2. Copy the `hawcx.aar` file into your project's `app/libs` directory.
3. Open your app-level `build.gradle` file and add the following:

```gradle
dependencies {
    implementation files('libs/hawcx.aar')
    // Other dependencies...
}
```

4. Sync your project with Gradle files.

### Option B: Using Existing Project

If you're using an existing project:

1. Copy the ```hawcx.aar``` file into your project's ```app/libs``` directory.
2. Open your app-level ```build.gradle``` file and add the following if it's not already there:

```
dependencies {
    implementation files('libs/hawcx.aar')
    // Other dependencies...
}
```

3. Sync your project with Gradle files.


## 2. Initializing HawcxFramework

In your Application class or main Activity's ```onCreate``` method, initialize HawcxFramework:

```
import com.example.hawcx.HawcxFramework;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        HawcxFramework.init(this);
    }
}
```

Don't forget to declare this in your ```AndroidManifest.xml```:

```
<application
    android:name=".MyApplication"
    ...>
    <!-- Your other application components -->
</application>
```

## 3. Implementing Signup

To implement the signup functionality:

1. Create a new Activity for signup (e.g., ```SignupActivity.java``` ).
2. Add SignUpManager in your code to handle user registration.

```
  SignUpManager signUpManager = new SignUpManager(this, Constants.PRIVATE_KEY);
  signUpManager.handleSignUp( email, "true", new SignUpManager.SignupCallback() {
      @Override
      public void onSuccess(String message) {
          runOnUiThread(() -> {
              Toast.makeText(SignupActivity.this, "Signup successful: " + message, Toast.LENGTH_SHORT).show();
              // Navigate to login or home screen
          });
      }

      @Override
      public void onFailure(String errorMessage) {
          runOnUiThread(() -> {
              Toast.makeText(SignupActivity.this, "Signup failed: " + errorMessage, Toast.LENGTH_SHORT).show();
          });
      }
  });
```


<!-- The Signup activity file would look something like this -->


<!-- import com.example.hawcx.utils.Constants;
import com.example.hawcx.repository.SignUpManager;

public class SignupActivity extends AppCompatActivity {
    private EditText emailEditText;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        emailEditText = findViewById(R.id.email_edit_text);
        signupButton = findViewById(R.id.signup_button);

        signupButton.setOnClickListener(v -> performSignup());
    }

    private void performSignup() {
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }

        SignUpManager signUpManager = new SignUpManager(this, Constants.PRIVATE_KEY);
        signUpManager.handleSignUp(email, "true", new SignUpManager.SignupCallback() {
            @Override
            public void onSuccess(String message) {
                runOnUiThread(() -> {
                    Toast.makeText(SignupActivity.this, "Signup successful: " + message, Toast.LENGTH_SHORT).show();
                    // Navigate to login or home screen
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                runOnUiThread(() -> {
                    Toast.makeText(SignupActivity.this, "Signup failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
} -->



## 4. Implementing Login

To implement the login functionality:

1. Create a new Activity for login (e.g., ```LoginActivity.java```).
2. Add the following code to handle user login / authentication:

```
  SignInManager signInManager = new SignInManager(this, Constants.PRIVATE_KEY);
  try {
      if (signInManager.signIn(email, this)) {
          Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
          // Navigate to home screen
          startActivity(new Intent(LoginActivity.this, HomeActivity.class));
          finish();
      } else {
          Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
      }
  } catch (Exception e) {
      Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
  }
```

<!-- 3. The entire ```LoginActivity.java``` should look like this -->


<!-- import com.example.hawcx.utils.Constants;
import com.example.hawcx.repository.SignInManager;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email_edit_text);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> performLogin());
    }

    private void performLogin() {
        String email = emailEditText.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }

        SignInManager signInManager = new SignInManager(this, Constants.PRIVATE_KEY);
        try {
            if (signInManager.signIn(email, this)) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                // Navigate to home screen
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
} -->



## 5. Use our boilerplate

If you're starting a new application or want a starting point for your application, you can use our boilerplate and start building on it

1. Clone the demo application repository:

```
  git clone https://github.com/hawcx/android_app.git
```

2. Open the project in Android Studio.
3. Update the package name to match your application:
  - Right-click on the package name in the Project view
  - Select Refactor > Rename
  - Choose a new package name
  - Click Refactor
4. Update the ```applicationId``` in your app's ```build.gradle``` file to match your new package name.
5. Update the applicationId in your app's build.gradle file to match your new package name.
6. Customize the UI and functionality as needed for your specific application.