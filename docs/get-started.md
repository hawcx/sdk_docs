# Get Started with Hawcx

Ready to enhance your app's security with Hawcx? Choose the integration method that best suits your project:

<div class="grid-container">
  <div class="grid-item" onclick="location.href='/android/existing-project'">
    <i class="fa fa-plus-square"></i>
    <h3>Integrate in Existing Project</h3>
    <p>Add Hawcx to your current Android app</p>
  </div>

  <div class="grid-item" onclick="location.href='/android/new-project'">
    <i class="fa fa-file-code"></i>
    <h3>Integrate in New Project</h3>
    <p>Start a new Android project with Hawcx</p>
  </div>

  <div class="grid-item" onclick="location.href='/android/template-project'">
    <i class="fa fa-clone"></i>
    <h3>Start from Template</h3>
    <p>Use our pre-configured project template</p>
  </div>
</div>

<style>
  .grid-container {
    display: grid; /* Defines the container as a grid layout */
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); /* Responsive columns */
    gap: 20px; /* Adds space between grid items */
    padding: 20px; /* Adds padding inside the grid container */
    background-color: #f0f0f0; /* Light background color for the container */
    border-radius: 10px; /* Rounds the corners of the container */
  }

  .grid-item {
    background-color: #ffffff; /* White background for grid items */
    border-radius: 10px; /* Rounds the corners of each grid item */
    padding: 20px; /* Adds padding inside each grid item */
    text-align: center; /* Centers the text inside each grid item */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* Adds a subtle shadow */
    transition: transform 0.3s, box-shadow 0.3s; /* Smooth transitions for transform and shadow */
  }

  .grid-item:hover {
    transform: translateY(-5px); /* Moves the item up slightly on hover */
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15); /* Darker shadow on hover */
  }

  .grid-item i {
    font-size: 3rem; /* Sets the font size for icons inside grid items */
    color: #007bff; /* Primary color for icons */
    margin-bottom: 10px; /* Adds space below icons */
    opacity: 0.8; /* Slightly transparent for a modern look */
    transition: color 0.3s; /* Smooth transition for icon color */
  }

  .grid-item:hover i {
    color: #0056b3; /* Darker color for icons on hover */
  }

  .grid-item h3 {
    font-size: 1.2em; /* Sets the font size for headings */
    color: #333; /* Dark color for headings */
    margin: 0; /* Removes default margin */
  }

  .grid-item p {
    font-size: 0.9em; /* Sets the font size for text */
    color: #666; /* Medium grey color for text */
    margin: 10px 0 0; /* Adds margin above the text */
  }
</style>


## Next Steps

After integrating Hawcx, explore our documentation to learn how to:

1. Implement secure user authentication
2. Enable biometric login
3. Encrypt sensitive data
4. Secure API communications
5. Detect and prevent tampering

For platform-specific integration guides, check out:

- [Java Integration Guide](android/java-integration.md)
- [React Native Integration Guide](android/react-integration.md)
- [Flutter Integration Guide](android/flutter-integration.md)

If you encounter any issues during integration, refer to our [Troubleshooting Guide](troubleshoot.md) or contact our support team.
