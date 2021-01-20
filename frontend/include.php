<?php
// Set backend URL
$backend = getenv('BACKEND_HOST');
if(!$backend)
  $backend = 'backend';
$backend_port = getenv('BACKEND_PORT');
if(!$backend_port)
  $backend_port = '8080';

function backend_lang($http_response_header) {
  foreach($http_response_header as $header) {
    $parts = explode(':', $header);
    if ($parts[0] === 'X-Backend-Lang') {
      return trim($parts[1]);
    }
  }
  return 'unknown';
}
?>
