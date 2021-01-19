<?php
// Set backend URL
$backend = getenv('BACKEND_HOST');
if(!$backend)
  $backend = 'backend';
$backend_port = getenv('BACKEND_PORT');
if(!$backend_port)
  $backend_port = '8080';
?>
