ARG BASE_TAG=3.8
FROM python:${BASE_TAG} AS builder
COPY api.py requirements.txt /usr/src/app/
COPY data.json /etc/backend/data.json
WORKDIR /usr/src/app/
RUN pip install --no-cache-dir -r requirements.txt
RUN apt-get update && \
    apt-get -y install binutils curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* && \
    pip install --no-cache-dir pyinstaller
RUN pyinstaller --onefile api.py
HEALTHCHECK --interval=1s CMD curl -s -o /dev/null -w "%{http_code}" localhost:8080 | grep 200 || exit 1
EXPOSE 8080
ENTRYPOINT ["python"]
CMD ["api.py"]

FROM debian
RUN apt-get update && \
    apt-get -y install curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*
COPY --from=builder /usr/src/app/dist/api /usr/bin/api
COPY data.json /etc/backend/data.json
HEALTHCHECK --interval=1s CMD curl -s -o /dev/null -w "%{http_code}" localhost:8080 | grep 200 || exit 1
EXPOSE 8080
ENTRYPOINT ["/usr/bin/api"]
