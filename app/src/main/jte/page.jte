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
    @for(String script: page.getScripts())
        <script src="/${script}"></script>
    @endfor
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
</body>
</html>