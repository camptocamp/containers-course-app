FROM python:3.12-bookworm

WORKDIR /app

ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1
#ENV FLASK_APP=app.py

RUN pip install gunicorn

COPY requirements.txt .
RUN pip install -r requirements.txt

COPY . .

EXPOSE 8000
ENTRYPOINT ["gunicorn", "--bind=0.0.0.0"]
CMD ["app:app"]
