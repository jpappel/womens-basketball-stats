$(function () {
    $('table.tablesorter').tablesorter({
        widgets: ["zebra", "filter"],
        widgetOptions: {
            zebra: ["alt-row", "normal-row"],

            filter_ignoreCase: true,
            filter_liveSearch: true,
            filter_columnFilters: false,
            filter_reset: '#resetFilters',
            filter_external: '.filterInput',

        }
    });
    $('#dateRange').daterangepicker({
        opens: "center",
        showDropdowns: true,
        autoApply: true
    })
})