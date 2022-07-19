const LOCALHOST_URL = 'http://localhost:8090'

$(document).on('click', '.fas', function (event) {
    let rate = parseInt(event.target.getAttribute("data-rate"));
    let movieId = event.target.getAttribute("movie");
    stars(rate, event.target);
    $("button.watchlist-btn[movie=\'" + movieId + "\']").attr("rating", rate)

})
$(document).on('click', '#close', function (event) {
    let movieId = event.target.getAttribute("movie");
    $.ajax({
        url: LOCALHOST_URL + '/watchlist/remove/movie?' + 'id=' + movieId,
        type: 'delete',
        success: function (response) {
            if (response === "success")
                location.reload()
            else
                window.location.replace(LOCALHOST_URL + response)
        }
    });
    setTimeout(function () {
        location.reload()
    }, 500);


})
$(".watchlist_movie").each(function () {
    loadForBeginning($(this).attr("rating"), $(this))
})
$(document).on('click', '.watchlist-btn', function (event) {
    let rate = parseFloat(event.target.getAttribute("rating"));
    let movieId = event.target.getAttribute("movie");
    $.ajax({
        url: LOCALHOST_URL + '/watchlist/rate/movie?' + 'rating=' + rate + '&id=' + movieId,
        type: 'post',
        success: function (response) {
            if (response === "success")
                location.reload()
            else
                window.location.replace(LOCALHOST_URL + response)
        }

    });

})
$(document).on('click', '#addMovie', function (event) {
    event.preventDefault()
    let movieId = event.target.getAttribute("movie");
    alert(movieId)
    $.ajax({
        url: LOCALHOST_URL + '/watchlist/add/movie?' + 'id=' + movieId,
        type: 'post',
        success: function (response) {
            alert(response)
            if (response === "success")
                location.reload()
            else
                window.location.replace(LOCALHOST_URL + response)
        }
    });


})
$(document).on('click', '#removeMovie', function (event) {
    event.preventDefault()
    let movieId = event.target.getAttribute("movie");
    $.ajax({
        url: LOCALHOST_URL + '/watchlist/remove/movie?' + 'id=' + movieId,
        type: 'delete',
        success: function (response) {
            if (response === "success")
                location.reload()
            else
                window.location.replace(LOCALHOST_URL + response)
        }
    });

})

function stars(rate, star) {
    for (let i = 1; i <= rate; i++) {
        $('i[data-rate=\"' + i + '\"]' + '[movie=\"' + star.getAttribute("movie") + '\"]').addClass('prv')

    }
    for (let i = rate + 1; i <= 5; i++) {
        $('i[data-rate=\"' + i + '\"]' + '[movie=\"' + star.getAttribute("movie") + '\"]').removeClass('prv')

    }
}

function loadForBeginning(rate, star) {
    for (let i = 1; i <= rate; i++) {
        $('i[data-rate=\"' + i + '\"]' + '[movie=\"' + star.attr("movie") + '\"]').addClass('prv')

    }
    for (let i = rate + 1; i <= 5; i++) {
        $('i[data-rate=\"' + i + '\"]' + '[movie=\"' + star.attr("movie") + '\"]').removeClass('prv')

    }
}



