FROM python:3-slim
COPY api.py /usr/src/app/
COPY requirements.txt /usr/src/app/
COPY data.json /etc/producer/data.json
WORKDIR /usr/src/app/
RUN pip install -r requirements.txt
EXPOSE 8080
ENTRYPOINT ["python"]
CMD ["api.py"]
