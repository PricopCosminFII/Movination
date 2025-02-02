function range(start, end) {
    return Array.from(Array(end - start + 1), (_, i) => i + start);
}

function getPageList(totalPages, page, maxLength) {

    let sideWidth = maxLength < 9 ? 1 : 2;
    let leftWidth = (maxLength - sideWidth * 2 - 3) >> 1;
    let rightWidth = (maxLength - sideWidth * 2 - 3) >> 1;

    if (totalPages <= maxLength) {
        return range(1, totalPages);
    }

    if (page <= maxLength - sideWidth - 1 - rightWidth) {
        return range(1, maxLength - sideWidth - 1).concat(0, range(totalPages - sideWidth + 1, totalPages));
    }

    if (page >= totalPages - sideWidth - 1 - rightWidth) {
        return range(1, sideWidth).concat(0, range(totalPages - sideWidth - 1 - rightWidth - leftWidth, totalPages));
    }

    return range(1, sideWidth).concat(0, range(page - leftWidth, page + rightWidth), 0, range(totalPages - sideWidth + 1, totalPages));
}


$(function () {
    let list_element = document.querySelectorAll("#col").length;
    let limitPerPage = 5;
    let totalPages = Math.ceil(list_element / limitPerPage);
    var currentPage;

    function showPage(pageNumber) {
        if (pageNumber < 1 || pageNumber > totalPages) return false;

        currentPage = pageNumber;
        $(".box-container .col.mx-2").hide().slice((currentPage - 1) * limitPerPage, currentPage * limitPerPage).show();
        $(".pagination li").slice(1, -1).remove();

        getPageList(totalPages, currentPage, 5).forEach(item => {
            $("<li>").addClass("page-item").addClass(item ? "current-page" : "dots").toggleClass("active", item === currentPage)
                .append($("<a>").addClass("page-link").attr({href: "javascript:void(0)"}).text(item || "...")).insertBefore(".next-page");
        });

        $(".previous-page").toggleClass("disabled", currentPage === 1);
        $(".next-page").toggleClass("disabled", currentPage === totalPages);

        return true;
    }

    $(".pagination").append(
        $("<li>").addClass("page-item").addClass("previous-page").append($("<a>").addClass("page-link").attr({href: "javascript:void(0)"}).text("«")),
        $("<li>").addClass("page-item").addClass("next-page").append($("<a>").addClass("page-link").attr({href: "javascript:void(0)"}).text("»"))
    );
    showPage(1);

    $(document).on("click", ".pagination li.current-page:not(.active)", function () {
        return showPage(+$(this).text());
    });

    $(".next-page").on("click", function () {
        return showPage(currentPage + 1);
    });

    $(".previous-page").on("click", function () {
        return showPage(currentPage - 1);
    });
});

$(document).ready(function () {
    $(".details-btn").click(function () {
        var idMovie = $(this).val();
        var datastring = 'id=' + idMovie;
        window.location.href = "details?" + datastring + "";
    })

    $(document).on('submit', '#searchform', function () {
        var searchTerm = 'searchTerm=' + $(this).serialize();
        window.location.href = "search?" + searchTerm + "";
    })
})

$('div.category-navbar > span').each(function (i) {
    if (i % 10 == 0) {
        $(this).nextAll().andSelf().slice(0, 10).wrapAll('<div class="category-menu-sub"></div>');
    }
});

$(document).on('click', '.categories', function (event) {
    event.preventDefault()
    var categoryName = $(this).text();
    var dataString = 'name=' + categoryName;
    window.location.href = "category?" + dataString + "";
});




