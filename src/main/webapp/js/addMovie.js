let objects = {};

function submitFormData(form) {
    for (var i = 0; i < form.length; i += 1) {
        if (form[i].name) {
            objects[form[i].name] = form[i].value;
        }
    }
    var json = JSON.stringify(objects, null, 1);
    return json;
}


$(document).on('submit', '.addNewMovieForm', function (event) {
    event.preventDefault();
    form = submitFormData(this);
    $.ajax({
        url: 'http://localhost:8088/movie',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: form,
        success: function () {
            localStorage.setItem("error", "");
            location.reload();
        },
        error: function (response) {
            let responseJSON = JSON.stringify(JSON.parse(JSON.stringify(response)).responseJSON)
            localStorage.setItem("error", JSON.parse(responseJSON).message);
            location.reload();
        }
    })
})

$(document).ready(function () {
    let error = localStorage.getItem("error");
    if (error === null) {
    } else if (error === "") {
        $('.idNullError').append('<p> Movie successfully added!</p>').css('visibility', 'visible').css('color', 'green');
        setTimeout(function () {
            $('.idNullError').children("p").remove();
        }, 3000);
    } else {
        $('.idNullError').append('<p>' + error + '</p>').css('visibility', 'visible').css('color', 'red');
        setTimeout(function () {
            $('.idNullError').children("p").remove();
        }, 3000);
    }
    localStorage.removeItem("error");
});