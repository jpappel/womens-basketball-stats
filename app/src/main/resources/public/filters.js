document.getElementById('activityFilter').addEventListener('change', function () {
    let showInactive = this.checked;
    let inactiveRows = document.querySelectorAll('.inactive');
    inactiveRows.forEach(function (row) {
        row.style.display = showInactive ? '' : 'none';
    });
});
$(function () {
    $('table').tablesorter({
        widgets: ["zebra", "filter"],
        widgetOptions: {
            zebra: ["alt-row", "normal-row"],
            filter_ignoreCase: true,
            filter_liveSearch: true,
        }
    });
})