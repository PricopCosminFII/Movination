$(document).on('click', '#logout', function () {
    document.cookie = "name" + "=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/";
})