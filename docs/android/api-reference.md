# Hawcx SDK API Reference

The Hawcx SDK provides a comprehensive set of tools for user authentication, device management, secure key storage, and encryption. It is structured into public API components and internal framework components that work together to deliver these features.

## Table of Contents

- [Overview](#overview)
- [HawcxInitializer](#hawcxinitializer)
- [HawcxApi](#hawcxapi)
- [Authentication Components](#authentication-components)
  - [SignUp](#signup)
  - [SignIn](#signin)
  - [Restore](#restore)
- [Usage Example](#usage-example)

---

## Overview

The Hawcx SDK is designed to simplify user authentication and secure data handling within Android applications. It consists of two main layers:

1. **Public API Components:**  
   These expose functionality such as initialization, user sign-up, sign-in, and account restoration.

2. **Internal Framework:**  
   Implements the core logic for key generation, encryption/decryption, cipher operations, and asynchronous user creation.

The SDK also utilizes various request and response model classes to encapsulate data exchanged with the backend.

---

## HawcxInitializer

The `HawcxInitializer` class is a singleton responsible for initializing the SDK and providing access to the authentication components.

### Key Methods

- **`public static HawcxInitializer getInstance()`**  
  Returns the singleton instance of the initializer.

- **`public void init(@NonNull Context context, @NonNull String apiKey)`**  
  Initializes the SDK with the application context and API key.  
  - *Throws a RuntimeException if initialization fails.*

- **`public SignIn getSignIn()`**  
  Returns an instance of the `SignIn` class.  
  - *Ensures that the SDK is initialized before returning the instance.*

- **`public SignUp getSignUp()`**  
  Returns an instance of the `SignUp` class.

- **`public Restore getRestore()`**  
  Returns an instance of the `Restore` class.

- **`private void checkInitialization()`**  
  Internal method that throws an exception if the SDK is not initialized.

---

## HawcxApi

The `HawcxApi` class exposes core methods that interact with the internal framework. It serves as a bridge between the public API and the underlying implementation.

### Key Methods

- **`public static void initialize(Context context) throws Exception`**  
  Initializes the framework.  
  - *Must be called before any other API method.*

- **`public static String getAllKeys()`**  
  Retrieves all stored keys as a string.

- **`public static String getDeviceId(String username) throws Exception`**  
  Returns the device ID for a given username.

- **`public static String createDeviceId() throws Exception`**  
  Creates a new device ID.

- **`public static CompletableFuture<JSONObject> createUser(String username) throws Exception`**  
  Asynchronously creates a new user and returns a future that resolves to a JSON object with user data.

- **`public static boolean saveToKeychain(String username, String n, String encpr1r2)`**  
  Saves keychain data for a user.

- **`public static boolean saveLoginUser(String username)`**  
  Saves the login state for a user.

- **`public static JSONObject createCipher(String username) throws Exception`**  
  Creates and returns cipher data as a JSON object.

- **`public static JSONObject createCipherForUser(String username) throws Exception`**  
  Creates, saves, and returns cipher data for a user.

- **`public static String regenerateM2(String c2, String m1, String n, String g, String encpr1r2, String encpd1d2, String er3)`**  
  Regenerates the M2 value based on the provided parameters.

- **`public static boolean checkUserExists(Context context, String username) throws Exception`**  
  Checks whether a user exists.

---

## Authentication Components

The SDK’s authentication flow is handled by three main classes: **SignUp**, **SignIn**, and **Restore**.

### SignUp

Handles user registration, OTP generation, and verification.

#### Constructor
```java
SignUp(Context context, String apiKey)
```
- Initializes the API service and ensures the API is set up.

#### Methods

- **`void signUp(String username, Runnable onSuccess, OnErrorListener onError)`**  
  Initiates the sign-up process by sending an add-user request.  
  - On success, extracts verification parameters and calls `handleVerification`.
  - On failure, calls the error callback with an appropriate error code.

- **`private void handleErrorResponse(Response<ApiResponse> response, OnErrorListener onError)`**  
  Parses error responses and triggers the error callback.

- **`private void handleVerification(String baseUrl, String username, String Er1r2, String Hn, long adduser, Runnable onSuccess, OnErrorListener onError)`**  
  Verifies the user by generating hashes (`h1` and `h2`), then sends a verification request.  
  - On success, saves keychain data and timing information.
  
- **`private boolean saveToKeychain(String username, String hn, String er1r2)`**  
  Saves keychain data via `HawcxApi.saveToKeychain`.

- **`private String sha256(String base)`**  
  Utility method that computes the SHA-256 hash of the input string.

---

### SignIn

Manages user login, including device ID retrieval, cipher creation, and biometric login.

#### Constructor
```java
SignIn(Context context, String key)
```
- Sets up the API service and initializes the API.

#### Methods

- **`String getLastUser()`**  
  Retrieves the last logged-in user using `HawcxApi.getAllKeys()`.

- **`void checkLastUser(SignInCallback callback)`**  
  Checks if a user exists and, if so, initiates biometric login; otherwise, prompts for email sign-in.

- **`void signIn(String username, SignInCallback callback)`**  
  Initiates the sign-in process:
  - Retrieves and hashes the device ID.
  - Gathers device details and creates a cipher.
  - Calls `handleSignIn` to send the sign-in request.

- **`private void handleSignIn(String baseUrl, String username, String devdata, long ttst, SignInCallback callback)`**  
  Creates cipher data and sends a sign-in request to generate a cipher.

- **`private void handleVerifyUser(String baseUrl, String username, String m2, String devdata, long ttst, long cipherTime, long sendchipherTime, long rGenTime, SignInCallback callback)`**  
  Verifies the cipher response and saves the login user if verification succeeds.

- **`private JSONObject getCipherData(String username)`**  
  Obtains cipher data via `HawcxApi.createCipher(username)`.

- **`private String sha256(String base)`**  
  Computes a SHA-256 hash of the provided input.

---

### Restore

Handles account restoration via OTP generation and verification.

#### Constructor
```java
Restore(Context context, String apikey)
```
- Sets up the API service for restoration endpoints.

#### Methods

- **`void generateOtp(String email, OnSuccessListener onSuccess, OnErrorListener onError)`**  
  Checks if the user exists; if not, sends an OTP generation request.  
  - On success, calls `restoreAccount` to create user data.
  - On failure, triggers the error callback.

- **`void verifyOtp(String userid, String otp, OnSuccessListener onSuccess, OnErrorListener onError)`**  
  Verifies the provided OTP using user data stored in a `CompletableFuture`.
  - On success, saves data to the keychain and calls the success callback.
  - On failure, triggers the error callback.

- **`private void restoreAccount(String email, ResetPasswordResponse otpResponse, OnSuccessListener onSuccess, OnErrorListener onError)`**  
  Restores the user account by creating user data and saving it.

#### Callback Interfaces

- **`OnSuccessListener`**: Called on successful OTP generation/verification.
- **`OnErrorListener`**: Called when an error occurs, with an error message.

---

## Usage Example

Below is a simple example demonstrating how to initialize the SDK and sign up a new user:

```java
// Initialize the Hawcx SDK
HawcxInitializer initializer = HawcxInitializer.getInstance();
initializer.init(context, "YOUR_API_KEY");

// Sign up a new user
SignUp signUp = initializer.getSignUp();
signUp.signUp("user@example.com", new Runnable() {
    @Override
    public void run() {
        // Handle successful signup (e.g., navigate to verification screen)
    }
}, new SignUp.OnErrorListener() {
    @Override
    public void onError(String errorMessage) {
        // Handle signup error (e.g., display error message)
    }
});
```

---