#!/bin/sh

. /etc/apache2/envvars
exec apache2 -D FOREGROUND
