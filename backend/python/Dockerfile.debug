FROM python:3.8 AS builder
COPY api.py requirements.txt /usr/src/app/
COPY data.json /etc/backend/data.json
WORKDIR /usr/src/app/
RUN pip install --no-cache-dir -r requirements.txt
RUN apt-get update && \
    apt-get -y install binutils curl && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/* && \
    pip install --no-cache-dir pyinstaller && \
    pip install --no-cache-dir debugpy
RUN pyinstaller --onefile api.py
EXPOSE 8080 5678
ENTRYPOINT ["python", "-m", "debugpy", "--listen", "0.0.0.0:5678"]
CMD ["api.py"]
