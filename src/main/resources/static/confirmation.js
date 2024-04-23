
var deleteTypeLinks = document.querySelectorAll('.action-button[href^="/type/delete/"]');

deleteTypeLinks.forEach(function(link) {
    link.addEventListener('click', function(event) {
        event.preventDefault();
        showModal(link.getAttribute('href'));
    });
});

var deleteCurrencyLinks = document.querySelectorAll('.action-button[href^="/currency/delete/"]');

deleteCurrencyLinks.forEach(function(link) {
    link.addEventListener('click', function(event) {
        event.preventDefault();
        showModal(link.getAttribute('href'));
    });
});

var deleteDealLinks = document.querySelectorAll('.action-button[href^="/deal/delete/"]');

deleteDealLinks.forEach(function(link) {
    link.addEventListener('click', function(event) {
        event.preventDefault();
        showModal(link.getAttribute('href'));
    });
});

var deletePlaceLinks = document.querySelectorAll('.action-button[href^="/place/delete/"]');

deletePlaceLinks.forEach(function(link) {
    link.addEventListener('click', function(event) {
        event.preventDefault();
        showModal(link.getAttribute('href'));
    });
});

function showModal(deleteUrl) {
    document.getElementById('confirmModal').style.display = 'block';

    document.getElementById('confirmYes').onclick = function() {
        window.location.href = deleteUrl;
    };

    document.getElementById('confirmNo').onclick = function() {
        document.getElementById('confirmModal').style.display = 'none';
    };
}



