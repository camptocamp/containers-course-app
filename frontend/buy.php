<?php include 'include.php' ?>
<?php include 'header.php' ?>
<?php include 'navbar.php' ?>

<!-- Call backend /buy/<product>  -->
<?php
file_get_contents("http://$backend:$backend_port/buy/$_REQUEST[product]");
?>

<div class="container">
  <div class="row">

<h1>Congratulation</h1>
<p>You just buy an opensource product : <?= $_REQUEST['product'] ?></p>
<a href="/product.php?product=<?= $_REQUEST['product'] ?>">Go back to <?= $_REQUEST['product'] ?> page<a><br>

  </div>
</div>

<?php include 'footer.php' ?>
