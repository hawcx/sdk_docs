# Feature Requests

We value your input! If you have an idea for a new feature or an improvement to an existing one, please let us know by submitting a feature request.

<form id="featureRequestForm" method="POST">
    <div class="form-group">
        <label for="topic">Topic:</label>
        <input type="text" id="topic" name="topic" required>
    </div>
    <div class="form-group">
        <label for="topic">Name:</label>
        <input type="text" id="name" name="name" required>
    </div>
    <div class="form-group">
        <label for="topic">Email:</label>
        <input type="text" id="email" name="email" required>
    </div>
    <div class="form-group">
        <label for="description">Description:</label>
        <textarea id="description" name="description" rows="8" required></textarea>
    </div>
    <div class="form-group">
        <button type="submit">Submit Request</button>
    </div>
</form>

<style>
    :root {
        --background-color: #eee;
        --text-color: #333333;
        --input-background: #f5f5f5;
        --input-border: #dddddd;
        --button-background: #4CAF50;
        --button-color: #ffffff;
        --button-hover: #45a049;
    }

    [data-md-color-scheme="slate"] {
        --background-color: #2e303e;
        --text-color: #ffffff;
        --input-background: #3e4051;
        --input-border: #5e616f;
        --button-background: #4CAF50;
        --button-color: #ffffff;
        --button-hover: #45a049;
    }

    #featureRequestForm {
        max-width: 500px;
        margin: 20px;
        margin-left: 0px;
        padding: 20px;
        background-color: var(--background-color);
        border-radius: 8px;
        color: var(--text-color);
    }

    .form-group {
        margin-bottom: 15px;
    }

    #featureRequestForm label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }

    #featureRequestForm input[type="text"],
    #featureRequestForm textarea {
        width: 100%;
        padding: 8px;
        border: 1px solid var(--input-border);
        border-radius: 4px;
        box-sizing: border-box;
        background-color: var(--input-background);
        color: var(--text-color);
    }

    #featureRequestForm button {
        background-color: var(--button-background);
        color: var(--button-color);
        padding: 10px 15px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
        transition: background-color 0.3s ease;
    }

    #featureRequestForm button:hover {
        background-color: var(--button-hover);
    }

    @media screen and (max-width: 600px) {
        #featureRequestForm {
            width: 90%;
            margin: 20px auto;
            padding: 15px;
        }
    }
</style>

<script>
document.getElementById('featureRequestForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    var topic = document.getElementById('topic').value;
    var description = document.getElementById('description').value;
    
    // Here you would typically send this data to your server
    // For this example, we'll just log it to the console
    console.log('Feature Request Submitted:');
    console.log('Topic:', topic);
    console.log('Description:', description);
    
    // Clear the form
    this.reset();
    
    // Show a confirmation message
    alert('Thank you for your feature request!');
});
</script>

## Guidelines for Submitting Feature Requests

1. **Be Specific**: Clearly describe the feature you're suggesting.
2. **Provide Context**: Explain why this feature would be useful and how it could be implemented.
3. **One Feature per Request**: If you have multiple ideas, please submit them separately.

We review all feature requests and greatly appreciate your input in making HawcxFramework even better!