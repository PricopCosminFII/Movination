var obj = {};

function submitFormData(form) {
    for (var i = 0; i < form.length; i += 1) {
        if (form[i].name != "" && form[i].value != "") {
            obj[form[i].name] = form[i].value;
        }
    }
    var json = JSON.stringify(obj, null, 1);
    return json;
}


$(document).on('submit', '.updateMovieForm', function (event) {
    event.preventDefault();
    form = submitFormData(this);
    movieId = JSON.parse(form).id;
    $.ajax({
        url: 'http://localhost:8088/updateMovie',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: form,
        success: function () {
            localStorage.setItem("updatedMovie", movieId);
            localStorage.setItem("error", "");
            location.reload();
        },
        error: function (response) {
            let responseJSON = JSON.stringify(JSON.parse(JSON.stringify(response)).responseJSON);
            localStorage.setItem("updatedMovie", movieId);
            localStorage.setItem("error", JSON.parse(responseJSON).message);
            location.reload();
        }
    })
})

$(document).ready(function () {
    let updatedMovie = localStorage.getItem("updatedMovie");
    let error = localStorage.getItem("error");
    if (updatedMovie) {
        if (error === null) {
        } else if (error === "") {
            $('#movie' + updatedMovie).modal('show');
            $('.displayedMessage').append('<p>Movie successfully updated!</p>').css('visibility', 'visible').css('color', 'green');
            setTimeout(function () {
                $('.displayedMessage').children("p").remove();
            }, 3000);
            localStorage.removeItem("updatedMovie");
        } else {
            $('#movie' + updatedMovie).modal('show');
            $('.displayedMessage').append('<p>' + error + '</p>').css('visibility', 'visible').css('color', 'red');
            setTimeout(function () {
                $('.displayedMessage').children("p").remove();
            }, 3000);
            localStorage.removeItem("updatedMovie");
        }
        localStorage.removeItem("error");
    }
});


$(document).on('click', '.closeUp', function () {
    $('.displayedMessage').css('visibility', 'hidden');
});

$(document).on('click', '.closeDown', function () {
    $('.displayedMessage').css('visibility', 'hidden');
});


$(document).on('click', '.addCategories', function (event) {
    event.preventDefault()
    idOfMovie = $(this).val();
});


$(document).on('submit', '.movieCategories', function (event) {
    event.preventDefault();
    let elements = []
    var markedCheckbox = document.querySelectorAll('#check' + idOfMovie + ':checked');
    for (var checkbox of markedCheckbox) {
        elements.push(checkbox.value)
    }
    categoryText = $('#addNewCategory' + idOfMovie).val();
    if (categoryText !== "" && verifyInput(elements, categoryText)) {
        elements.push(categoryText)
    }
    uniq = [...new Set(elements)];
    categoryForm = submitFormData(this);
    const source = {"chosenCategories": uniq};
    returnedTarget = Object.assign(JSON.parse(categoryForm), source);
    formWithCategories = JSON.stringify(returnedTarget, null, 1);

    $.ajax({
        url: 'http://localhost:8088/addCategoriesToMovie?' + 'id=' + idOfMovie,
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: formWithCategories,
        success: function (response) {
            toastr.options = {
                "closeButton": false,
                "debug": false,
                "newestOnTop": false,
                "progressBar": false,
                "positionClass": "toast-bottom-right",
                "preventDuplicates": false,
                "onclick": null,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "1500",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut"
            }
            Command: toastr["success"](" ", "Categories added successfully!")

            setTimeout(function () {
                location.reload();
            }, 1600);
        },
        error: function (response) {
            location.reload();
        }
    })
});

function verifyInput(list, input) {
    for (i = 0; i < list.length; i++) {
        if (list[i].toUpperCase() === input.toUpperCase())
            return false;
    }
    return true;
}