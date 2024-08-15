# Troubleshooting Guide

This guide provides solutions for common issues you might encounter while using Hawcx Authentication, along with a comprehensive list of error codes.

## General Troubleshooting Tips

1. Ensure you're using the latest version of Hawcx Authentication.
2. Check that Hawcx Authentication is properly initialized in your application.
3. Verify that all required permissions are set in your AndroidManifest.xml.
4. Review the logcat output for any Hawcx Authentication-related warnings or errors.

## Common Issues and Solutions

### Issue: Hawcx Authentication not initializing
Solution: Make sure you're calling `Hawcx Authentication.init()` in your Application class's `onCreate()` method.

### Issue: Biometric authentication not working
Solution: Ensure the device has biometric hardware and that the user has enrolled biometrics.

### Issue: Network requests failing
Solution: Check your internet connection and verify that you're using the correct API endpoints.

## Error Codes

Here's a comprehensive list of Hawcx Authentication error codes and their meanings:

| Error Code | Description | Solution |
|------------|-------------|----------|
| HWCX-AUTH-001 | User not found | Ensure the user is registered |
| HWCX-AUTH-002 | Account locked | Contact support for account unlock |
| HWCX-CRYPTO-001 | Encryption failed | Check data format and encryption key |
| HWCX-CRYPTO-002 | Decryption failed | Verify the encrypted data and key |
| HWCX-NET-001 | Network connection error | Check internet connectivity |
| HWCX-NET-002 | API request timeout | Retry the request or check server status |
| HWCX-STORE-001 | Secure storage write error | Ensure sufficient device storage |
| HWCX-STORE-002 | Secure storage read error | Verify the key used for storage |
| HWCX-BIO-001 | Biometric hardware not available | Use alternative authentication method |
| HWCX-BIO-002 | Biometric not enrolled | Prompt user to set up biometrics |
| HWCX-INIT-001 | Framework initialization failed | Check initialization parameters |
| HWCX-TAMPER-001 | App integrity check failed | Potential app tampering detected |

## How to Report an Issue

If you encounter an issue not covered in this guide:

1. Gather relevant information (error messages, steps to reproduce, device info).
2. Check if the issue is already reported in our [GitHub Issues](https://github.com/hawcx/authenticator/issues).
3. If not, create a new issue with a detailed description of the problem.

For urgent issues or security vulnerabilities, please contact our support team directly at support@hawcx.com.

Remember, when reporting issues, never share sensitive information like API keys or user credentials.