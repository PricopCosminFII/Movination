(function ($) {
    $(function () {
        $.widget("zpd.paging", {
            options: {
                limit: 5,
                rowDisplayStyle: 'block',
                activePage: 0,
                rows: []
            },
            _create: function () {
                var rows = $("tbody", this.element).children();
                this.options.rows = rows;
                this.options.rowDisplayStyle = rows.css('display');
                var nav = this._getNavBar();
                this.element.after(nav);
                this.showPage(0);
            },
            _getNavBar: function () {
                var rows = this.options.rows;
                var nav = $('<div>', {class: 'paging-nav'});
                for (var i = 0; i < Math.ceil(rows.length / this.options.limit); i++) {
                    this._on($('<a>', {
                            href: '',
                            text: (i + 1),
                            "data-page": (i),
                            class: "page-item"
                        }).appendTo(nav),
                        {click: "pageClickHandler"});
                }
                //create previous link
                this._on($('<a>', {
                        href: '',
                        text: '<<',
                        "data-direction": -1,
                        class: "page-item"
                    }).prependTo(nav),
                    {click: "pageStepHandler"});
                //create next link
                this._on($('<a>', {
                        href: '',
                        text: '>>',
                        "data-direction": +1,
                        class: "page-item"
                    }).appendTo(nav),
                    {click: "pageStepHandler"});
                return nav;
            },
            showPage: function (pageNum) {
                var num = pageNum * 1; //it has to be numeric
                this.options.activePage = num;
                var rows = this.options.rows;
                var limit = this.options.limit;
                for (var i = 0; i < rows.length; i++) {
                    if (i >= limit * num && i < limit * (num + 1)) {
                        $(rows[i]).css('display', this.options.rowDisplayStyle);
                    } else {
                        $(rows[i]).css('display', 'none');
                    }
                }
            },
            pageClickHandler: function (event) {
                event.preventDefault();
                $(event.target).siblings().attr('class', "page-item");
                $(event.target).attr('class', "page-item selected-page");
                var pageNum = $(event.target).attr('data-page');
                this.showPage(pageNum);
            },
            pageStepHandler: function (event) {
                event.preventDefault();
                //get the direction and ensure it's numeric
                var dir = $(event.target).attr('data-direction') * 1;
                var pageNum = this.options.activePage + dir;
                //if we're in limit, trigger the requested pages link
                if (pageNum >= 0 && pageNum < this.options.rows.length) {
                    $("a[data-page=" + pageNum + "]", $(event.target).parent()).click();
                }
            }
        });
    });
})(jQuery);

$('#chooseCategory')
    .on('change', function (event) {
        let category = event.target.value;
        window.location.href = "admin?name=" + category + "";
    })
$(document).ready(function() {
    $('#tableData').paging({limit:10});
});
$('.delete').on('click', function (event) {
    let movieId = event.target.getAttribute("movie");
    $.ajax({
        url: LOCALHOST_URL + '/movie?' + 'id=' + movieId,
        type: 'delete',
        success: function () {

            location.reload()

        }
    });
})
