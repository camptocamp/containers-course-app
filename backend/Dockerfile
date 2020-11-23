FROM python:3-onbuild
COPY api.py /usr/src/app/
COPY requirements.txt /usr/src/app/
COPY data.json /etc/producer/data.json
EXPOSE 8080
ENTRYPOINT ["python"]
CMD ["api.py"]
