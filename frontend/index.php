<?php include 'include.php' ?>
<?php include 'header.php' ?>
<?php include 'navbar.php' ?>

<div class="container">
  <div class="row">
  <ul>
      <?php

      // Request products from backend
      $response = json_decode(file_get_contents("http://$backend:$backend_port/"));
      // Display results
      foreach ($response->products as $product)
          echo "<li><a href='/product.php?product=$product->name'>$product->name</a></li>\n";
      ?>
  </ul>

  </div>
</div>

<?php include 'footer.php' ?>
