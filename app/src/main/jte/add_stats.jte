@param String title
@param String heading
@param String content

<!DOCTYPE html>
<html lang="en">
<head>
    <title>${title}</title>
</head>
<body>
    <header>
        <h1>${heading}</h1>
    </header>
    <main>
        <p>${content}</p>
        <table>
            <th>jdakldwjkl</th>
            ${foreach $row in $rows}
                <tr>
                    <td>${$row['name']}</td>
                    <td>${$row['age']}</td>
                </tr>
        </table>
    </main>
    <footer>
        <p>Footer content goes here</p>
    </footer>
</body>
</html>