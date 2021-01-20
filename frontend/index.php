<?php include 'include.php' ?>
<html>
    <head>
        <title>C2C Projects</title>
    </head>

    <body>
        <h1>Welcome to C2C</h1>
        <ul>
            <?php

            // Request products from backend
            $response = json_decode(file_get_contents("http://$backend:$backend_port/"));
            // Display results
            foreach ($response->products as $product)
                echo "<li><a href='/product.php?product=$product->name'>$product->name</a></li>\n";
            ?>
        </ul>

        <p>Served by the <?= backend_lang($http_response_header) ?> backend</p>
    </body>
</html>
