FROM python:3-slim AS builder
COPY api.py /usr/src/app/
COPY requirements.txt /usr/src/app/
COPY data.json /etc/producer/data.json
WORKDIR /usr/src/app/
RUN pip install -r requirements.txt
RUN apt-get update && \
    apt-get -y install binutils && \
    pip install pyinstaller
RUN pyinstaller --onefile api.py
EXPOSE 8080
ENTRYPOINT ["python"]
CMD ["api.py"]

FROM debian
COPY --from=builder /usr/src/app/dist/api /usr/bin/api
COPY data.json /etc/producer/data.json
EXPOSE 8080
ENTRYPOINT ["/usr/bin/api"]
