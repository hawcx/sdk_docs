# Starting from a Template Project

Get up and running quickly with our pre-configured HawcxFramework template project. This approach is perfect for developers who want to start with a solid foundation and best practices already in place.

## Prerequisites

- Android Studio installed on your development machine
- Git installed on your system

## Steps

1. **Clone the Template Repository**
   - Open a terminal or command prompt
   - Run the following command:
     ```
     git clone https://github.com/hawcx/android-template.git
     ```

2. **Open the Project in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned repository and select it

3. **Rename the Project (Optional)**
   - Right-click on the root folder in the Project view
   - Select Refactor > Rename
   - Enter your desired project name
   - Click "Refactor"
   - Update the `applicationId` in your app's `build.gradle` file to match your new package name

4. **Update Configuration**
   - Open `app/src/main/java/com/hawcx/template/MyApplication.java`
   - Replace the placeholder API key with your actual HawcxFramework API key:
     ```java
     HawcxFramework.init(this, "YOUR_API_KEY_HERE");
     ```

5. **Customize the Template**
   - The template includes basic implementations of:
     - User registration (`SignupActivity.java`)
     - User login (`LoginActivity.java`)
     - Secure data storage (`SecureStorageManager.java`)
   - Modify these files and add your own business logic as needed

6. **Run the Project**
   - Connect an Android device or start an emulator
   - Click the "Run" button in Android Studio

## Template Structure

- `app/src/main/java/com/hawcx/template/`
  - `MyApplication.java`: Application class with HawcxFramework initialization
  - `MainActivity.java`: Main activity demonstrating basic HawcxFramework usage
  - `LoginActivity.java`: Example implementation of secure login
  - `SignupActivity.java`: Example implementation of secure user registration
  - `SecureStorageManager.java`: Utility class for secure data storage
- `app/src/main/res/layout/`
  - XML layout files for activities

## Next Steps

- Review the [Java Integration Guide](java-integration.md) to understand the template code better
- Explore additional [HawcxFramework Features](features.md) to enhance your app's security
- Check our [Best Practices](best-practices.md) guide for optimal usage of HawcxFramework

Congratulations! You now have a fully functional Android project with HawcxFramework integration, ready for you to build upon.