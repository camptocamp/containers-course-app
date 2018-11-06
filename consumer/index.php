<html>
    <head>
        <title>C2C Projects</title>
    </head>

    <body>
        <h1>Welcome to C2C</h1>
        <ul>
            <?php
            // Set producer URL
            $producer = getenv('PRODUCER_SERVICE');
            if(!$producer)
              $producer = 'producer';
            $producer_port = getenv('PRODUCER_PORT');
            if(!$producer_port)
              $producer_port = '8080';

            // Request products from producer
            $response = json_decode(file_get_contents("http://$producer:$producer_port/"));
            // Display results
            foreach ($response->products as $product)
                echo "<li>$product</li>\n";
            ?>
        </ul>
    </body>
</html>
