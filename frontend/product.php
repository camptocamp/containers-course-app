<?php include 'include.php' ?>
<html>
    <head>
        <title>C2C Projects</title>
    </head>

<?php
# Call backend /product/<product> to show details
$product_id = $_REQUEST['product'];
$product = json_decode(file_get_contents("http://$backend:$backend_port/product/$product_id"));
?>

    <body>
      <h1><?= $product->name ?></h1>
      <p><?= $product->description ?></p>

      <ul>
      <li>Product view: <?= $product->view ?></li>
      <li>Product buy: <?= $product->buy ?></li>
      </ul>

      <form action="/buy.php?product=<?= $product_id ?>" method="get">
      <input type="hidden" name="product" value="<?= $product_id ?>" >
      <input type="submit" value="Buy it Now !">
      </form>

      <a href="/">Go back to products list<a>
    </body>
</html>
