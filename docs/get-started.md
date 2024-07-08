# Get Started

Welcome to HawcxFramework! This guide will help you integrate our security solution into your Android application. Whether you're adding HawcxFramework to an existing project or starting a new one, we've got you covered.

## 1. Integrate in Existing Application

If you have an existing Android application and want to enhance its security with HawcxFramework, follow these steps:

1. Download the HawcxFramework AAR file:
   [Download HawcxFramework AAR](https://github.com/hawcx/android_sdk/releases/download/v0.0.1/hawcx.aar)

2. In your Android Studio project, follow these steps to add the AAR:
   - Go to File > New > New Module
   - Select "Import .JAR/.AAR Package" and click Next
   - Browse and select the downloaded `hawcx.aar` file
   - Click Finish

3. Add the following to your app's `build.gradle` file:

```
  gradle
  dependencies {
      implementation project(':hawcx')
  }
```
4. Sync your project with Gradle files.
5. You're now ready to use HawcxFramework in your existing project!

For detailed instructions on how to use HawcxFramework's features in your application, please proceed to the First Steps section.

## 2. Create a New Application

If you're starting a new project and want to build it with HawcxFramework from the ground up, we recommend using our sample application as a starting point:

1. Clone our sample application repository:

```
  git clone https://github.com/hawcx/android_app.git
```
2. Open the project in Android Studio.
3. Rename the application package:
  - Right-click on the package name in the Project view
  - Select Refactor > Rename
  - Choose a new package name that suits your project
  - Click Refactor
4. Update the applicationId in your app's build.gradle file to match your new package name.
5. Customize the application name, icon, and other resources as needed for your project.
6. Start building your application using HawcxFramework's features!

For a detailed guide on the key features of HawcxFramework and how to implement them in your new application, please proceed to the First Steps section.