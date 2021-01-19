FROM python:3-slim AS builder
COPY api.py /usr/src/app/
COPY requirements.txt /usr/src/app/
WORKDIR /usr/src/app/
RUN pip install --no-cache-dir -r requirements.txt
RUN apt-get update && \
    apt-get -y install binutils && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* && \
    pip install --no-cache-dir pyinstaller
RUN pyinstaller --onefile api.py
EXPOSE 8080
ENTRYPOINT ["python"]
CMD ["api.py"]

FROM debian
COPY --from=builder /usr/src/app/dist/api /usr/bin/api
COPY data.json /etc/backend/data.json
EXPOSE 8080
ENTRYPOINT ["/usr/bin/api"]
