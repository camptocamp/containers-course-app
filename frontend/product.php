<?php include 'include.php' ?>
<?php include 'header.php' ?>
<?php include 'navbar.php' ?>

<?php
# Call backend /product/<product> to show details
$product_id = $_REQUEST['product'];
$product = json_decode(file_get_contents("http://$backend:$backend_port/product/$product_id"));
?>


<div class="container">
  <div class="row">
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
  </div>
</div>

<?php include 'footer.php' ?>
