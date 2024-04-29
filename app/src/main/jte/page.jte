@import gg.jte.Content
@import com.mu_bball_stats.web.Page

@param Page page
@param Content content

<!DOCTYPE html>
<html lang="en">
<head>
    <title>${page.getTitle()}</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/styles.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- JQuery -->
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.7.1.slim.min.js"></script>
    <script type="text/javascript" src="https://mottie.github.io/tablesorter/dist/js/jquery.tablesorter.min.js"></script>
    <script type="text/javascript" src="https://mottie.github.io/tablesorter/js/jquery.tablesorter.widgets.js"></script>
    <script src="https://mottie.github.io/tablesorter/js/parsers/parser-date-range.js"></script>
    <script type="text/javascript" src="https://mottie.github.io/tablesorter/js/widgets/widget-filter-type-insideRange.js"></script>
    <!-- Date Range Picker -->
    <script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
</head>
<body>
    <header>
        <h1><a href="/">Moravian University Women's Basketball</a></h1>
    </header>
    <main>
        ${content}
    </main>
    <footer>
    </footer>
    @for(String script: page.getScripts())
        <script src="/${script}"></script>
    @endfor
</body>
</html>