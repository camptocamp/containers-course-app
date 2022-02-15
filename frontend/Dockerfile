FROM debian:stretch

RUN apt-get update \
    && apt-get dist-upgrade -y \
    && apt-get -y install apache2 libapache2-mod-php \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

RUN a2enmod php7.0 && /etc/init.d/apache2 start && /etc/init.d/apache2 stop

RUN sed -ri \
    -e 's!^(\s*CustomLog)\s+\S+!\1 /proc/self/fd/1!g' \
    -e 's!^(\s*ErrorLog)\s+\S+!\1 /proc/self/fd/2!g' \
    -e 's!^<VirtualHost \*:80>!<VirtualHost *:8080>!g' \
    "/etc/apache2/sites-available/000-default.conf"

RUN sed -i 's/80/8080/' /etc/apache2/ports.conf
RUN sed -i 's/display_errors = Off/display_errors = On/' /etc/php/7.0/apache2/php.ini

EXPOSE 8080

COPY *.php /var/www/html/
RUN rm -f /var/www/html/index.html

CMD . /etc/apache2/envvars && exec apache2 -D FOREGROUND
