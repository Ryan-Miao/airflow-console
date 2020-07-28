FROM nginx
LABEL author="Ryan Miao"
LABEL Description="web前端静态页面部署"

ARG NGINX_CONF
COPY ${NGINX_CONF} /etc/nginx/conf.d/default.conf

COPY dist/  /data/web/