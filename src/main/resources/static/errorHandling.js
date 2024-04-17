document.addEventListener("DOMContentLoaded", function() {
    var errorMessage = document.querySelector('.error-message');
    if (errorMessage) {
        errorMessage.style.display = 'block';
        setTimeout(function() {
            errorMessage.style.display = 'none';
        }, 4000);
    }
});



