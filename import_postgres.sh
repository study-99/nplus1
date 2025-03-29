#!/bin/bash

# PostgreSQL 컨테이너 이름
CONTAINER_NAME=nplus-postgres
USER=user
PASSWORD=user
DB_NAME=db1

# IS_COPY가 1일 때만 복사 및 import
IS_COPY=1  # 0으로 설정하면 import 안됨

if [ "$IS_COPY" -eq 1 ]; then
    echo "📥 Copying CSV files into PostgreSQL container..."

    # CSV 파일 복사
    docker cp $CSV_DIR/posts.csv $CONTAINER_NAME:/data/posts.csv
    docker cp $CSV_DIR/comments.csv $CONTAINER_NAME:/data/comments.csv

else
    echo "POST_COPY is not 1. Skipping insert post and comment."
fi

    # PostgreSQL로 CSV 파일 import
    echo "Importing posts.csv into PostgreSQL..."
    docker exec -i $CONTAINER_NAME psql -U $USER -d $DB_NAME -c "\
    COPY post(id, title, content, created_at, updated_at) FROM '/data/posts.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');"

    echo "Importing comments.csv into PostgreSQL..."
    docker exec -i $CONTAINER_NAME psql -U $USER -d $DB_NAME -c "\
    COPY comment(id, post_id, content, created_at, updated_at) FROM '/data/comments.csv' WITH (FORMAT csv, HEADER true, DELIMITER ',');"

