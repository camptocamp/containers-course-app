<?php
// Set producer URL
$producer = getenv('PRODUCER_HOST');
if(!$producer)
  $producer = 'producer';
$producer_port = getenv('PRODUCER_PORT');
if(!$producer_port)
  $producer_port = '8080';
?>
