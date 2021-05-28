ARG BASE_TAG=1.15
FROM golang:${BASE_TAG} as builder
WORKDIR /opt/build
COPY . .
RUN CGO_ENABLED=1 GOOS=linux go build -trimpath \
  -ldflags "-linkmode external -extldflags -static" \
  -o backend main.go
RUN strip backend

FROM scratch
WORKDIR /
COPY --from=builder /opt/build/backend /usr/bin/api
COPY data.json /etc/backend/data.json
EXPOSE 8080
ENTRYPOINT ["/usr/bin/api"]
