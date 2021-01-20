# Go backend

# Requirements

* Go SDK
* Json formated list of products located in `/etc/backend/data.json`

# Usage

Run `go run main.go`.

List of products should be available at [http://localhost:8080/](http://localhost:8080/).

# How to build static binary

You can build a static binary:

```
CGO_ENABLED=1 GOOS=linux go build -trimpath \
  -ldflags "-linkmode external -extldflags -static" \
  -o backend main.go
```


# API

There is 3 API endpoints:

* `/`: list on products in JSON format without stats

Sample results :
```json
{"products":
  {"name": "geonetwork",
   "description": "GeoNetwork is a catalog application"},
  {"name": "georchestra",
   "description": "geOrchestra is free and modular"},
}
```
* `/product/<product>`: Details of <product> in JSON format
Sample results :
```json
{"name": "geonetwork",
 "description": "GeoNetwork is a catalog application",
 "view": 1,
 "buy": 0}
```
* `/buy/<product>`: Buy <product> and return HTTP 200 if it's ok
