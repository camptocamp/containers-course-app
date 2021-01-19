<?php include 'include.php' ?>
<html>
    <head>
        <title>C2C Projects</title>
    </head>

    <!-- Call backend /buy/<product>  -->
    <?php file_get_contents("http://$backend:$backend_port/buy/$_REQUEST[product]"); ?>

    <body>
        <h1>Congratulation</h1>
        <p>You just buy an opensource product : <?= $_REQUEST['product'] ?></p>
        <a href="/product.php?product=<?= $_REQUEST['product'] ?>">Go back to <?= $_REQUEST['product'] ?> page<a><br>
        <a href="/">Go back to products list<a>
    </body>
</html>
