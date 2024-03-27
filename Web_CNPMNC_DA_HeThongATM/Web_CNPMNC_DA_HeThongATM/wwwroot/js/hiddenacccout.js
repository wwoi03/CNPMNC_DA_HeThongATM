document.addEventListener('DOMContentLoaded', function () {

    var sensitiveElements = document.querySelectorAll('.sensitive');

    sensitiveElements.forEach(function (element) {
        var fullInfo = element.textContent;
        var maskedInfo = '*'.repeat(fullInfo.length);

        element.textContent = maskedInfo;
    });

    var revealButtons = document.querySelectorAll('.reveal');

    revealButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            var sensitiveElement = this.previousElementSibling;
            var originalInfo = sensitiveElement.dataset.original;
            var isMasked = sensitiveElement.dataset.masked === 'true';
            var maskedInfo = '*'.repeat(originalInfo.length);

            if (isMasked) {
                sensitiveElement.textContent = originalInfo;
                sensitiveElement.dataset.masked = 'false';
                button.className = 'fa-solid fa-eye-low-vision';
            } else {
                sensitiveElement.textContent = maskedInfo;
                sensitiveElement.dataset.masked = 'true';
                button.className = 'fa-solid fa-eye';
            }
        });
    });


});