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
    "/etc/apache2/sites-available/000-default.conf"

EXPOSE 80

COPY index.php /var/www/html/

ADD entrypoint.sh /entrypoint.sh

CMD . /etc/apache2/envvars && exec apache2 -D FOREGROUND
