#!/usr/bin/env bash

wget https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-2.1.1-linux-x86_64.tar.bz2

FILE_NAME='phantomjs-2.1.1-linux-x86_64.tar.bz2'

PHANTOM_DIR='/usr/tmp/phantomjs/'

mkdir -p ${PHANTOM_DIR}

tar jvxf ${FILE_NAME} -C ${PHANTOM_DIR}

rm ${FILE_NAME}
