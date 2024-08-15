# Feedback
We value your feedback! Please use the form below to share your thoughts, report issues, or provide any other comments about Hawcx Authentication.

<form id="feedbackForm" action="https://api.hawcx.com/feedback" method="POST" enctype="multipart/form-data">
    <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" required>
    </div>
    <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
    </div>
    <div class="form-group">
        <label for="topic">Topic:</label>
        <input type="text" id="topic" name="topic" required>
    </div>
    <div class="form-group">
        <label for="details">Details:</label>
        <textarea id="details" name="details" rows="8" required></textarea>
    </div>
    <div class="form-group">
        <label for="fileUpload">Upload Image / PDF:</label>
        <div id="dropzone" class="dropzone">
            <p>Drag & Drop files here or click to select</p>
            <input type="file" id="fileUpload" name="fileUpload" accept="image/*,.pdf" style="display: none;">
        </div>
    </div>
    <div class="form-group">
        <button type="submit">Submit Feedback</button>
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
        --dropzone-border: #cccccc;
        --dropzone-background: #f9f9f9;
    }

    [data-md-color-scheme="slate"] {
        --background-color: #2e303e;
        --text-color: #ffffff;
        --input-background: #3e4051;
        --input-border: #5e616f;
        --button-background: #4CAF50;
        --button-color: #ffffff;
        --button-hover: #45a049;
        --dropzone-border: #5e616f;
        --dropzone-background: #3e4051;
    }

    #feedbackForm {
        max-width: 600px;
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

    #feedbackForm label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }

    #feedbackForm input[type="text"],
    #feedbackForm input[type="email"],
    #feedbackForm textarea {
        width: 100%;
        padding: 8px;
        border: 1px solid var(--input-border);
        border-radius: 4px;
        box-sizing: border-box;
        background-color: var(--input-background);
        color: var(--text-color);
    }

    #feedbackForm button {
        background-color: var(--button-background);
        color: var(--button-color);
        padding: 10px 15px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 16px;
        transition: background-color 0.3s ease;
    }

    #feedbackForm button:hover {
        background-color: var(--button-hover);
    }

    .dropzone {
        border: 2px dashed var(--dropzone-border);
        border-radius: 4px;
        padding: 60px;
        text-align: center;
        cursor: pointer;
        margin-top: 10px;
        background-color: var(--dropzone-background);
        transition: background-color 0.3s ease;
    }

    .dropzone:hover {
        background-color: var(--input-background);
    }

    @media screen and (max-width: 600px) {
        #feedbackForm {
            width: 90%;
            margin: 20px auto;
            padding: 15px;
        }
    }
</style>

<script>
document.addEventListener('DOMContentLoaded', function() {
    var dropzone = document.getElementById('dropzone');
    var fileInput = document.getElementById('fileUpload');

    dropzone.addEventListener('click', function() {
        fileInput.click();
    });

    dropzone.addEventListener('dragover', function(e) {
        e.preventDefault();
        dropzone.style.backgroundColor = 'var(--input-background)';
    });

    dropzone.addEventListener('dragleave', function(e) {
        e.preventDefault();
        dropzone.style.backgroundColor = 'var(--dropzone-background)';
    });

    dropzone.addEventListener('drop', function(e) {
        e.preventDefault();
        dropzone.style.backgroundColor = 'var(--dropzone-background)';
        fileInput.files = e.dataTransfer.files;
        updateDropzoneText();
    });

    fileInput.addEventListener('change', updateDropzoneText);

    function updateDropzoneText() {
        var files = fileInput.files;
        if (files.length > 0) {
            dropzone.innerHTML = '<p>Selected file: ' + files[0].name + '</p>';
        } else {
            dropzone.innerHTML = '<p>Drag & Drop files here or click to select</p>';
        }
    }

    document.getElementById('feedbackForm').addEventListener('submit', function(e) {
        e.preventDefault();
        
        var formData = new FormData(this);
        
        // Here you would typically send this data to your server
        // For this example, we'll just log it to the console
        console.log('Feedback Submitted:');
        for (var pair of formData.entries()) {
            console.log(pair[0] + ': ' + pair[1]);
        }
        
        // Clear the form
        this.reset();
        updateDropzoneText();
        
        // Show a confirmation message
        alert('Thank you for your feedback!');
    });
});
</script>

## Guidelines for Submitting Feedback

1. **Be Specific**: Provide clear and concise details about your feedback.
2. **Include Examples**: If reporting an issue, include steps to reproduce it.
3. **Suggest Improvements**: If possible, include suggestions for how we can address your feedback.

We appreciate your time in helping us improve Hawcx Authentication!