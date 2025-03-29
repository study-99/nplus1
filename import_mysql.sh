#!/bin/bash

CONTAINER_NAME="nplus-mysql"
DB_NAME="db1"
USER="root"
PASSWORD="root"

# 0이면 insert
POST_COPY=0
COMMENT_COPY=0

# IS_COPY가 0일 때만 파일을 복사
IS_COPY=1

if [ "$IS_COPY" -eq 0 ]; then
    echo "📦 Copying CSV files into MySQL container..."
    docker cp posts.csv $CONTAINER_NAME:/posts.csv
    docker cp comments.csv $CONTAINER_NAME:/comments.csv
else
    echo -e "\nIS_COPY is not 1. Skipping file copy.\n"
fi

if [ "$POST_COPY" -eq 0 ]; then
    echo "📥 Importing posts.csv into MySQL..."
    docker exec -i $CONTAINER_NAME mysql --local-infile=1 -u$USER -p$PASSWORD $DB_NAME -e "
    SET foreign_key_checks = 0;
    truncate comment;
    truncate post;
    SET foreign_key_checks = 1;
    LOAD DATA INFILE '/posts.csv'
    INTO TABLE post
    CHARACTER SET 'utf8'
    FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '\"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES
    (id, title, content, created_at, updated_at);
    "
else
    echo -e "\n[POST] insert is not 1. Skipping insert post\n"
fi

if [ "$COMMENT_COPY" -eq 0 ]; then
    echo "📥 Importing comments.csv into MySQL..."
    docker exec -i $CONTAINER_NAME mysql --local-infile=1 -u$USER -p$PASSWORD $DB_NAME -e "
    truncate comment;
    LOAD DATA INFILE '/comments.csv'
    INTO TABLE comment
    CHARACTER SET 'utf8'
    FIELDS TERMINATED BY ','
    OPTIONALLY ENCLOSED BY '\"'
    LINES TERMINATED BY '\n'
    IGNORE 1 LINES (id, post_id, content, created_at, updated_at);
    "
else
    echo -e "\n[COMMENT] insert is not 1. Skipping insert comment\n"
fi

echo "✅ MySQL CSV import done!"
