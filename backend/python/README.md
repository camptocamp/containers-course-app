# Python backend

# Requirements

* Python 3
* Install python modules with `pip install -r requirements.txt`
* Json formated list of products located in `/etc/backend/data.json`

# Usage

Run `python api.py` or `python3 api.py`.

List of products should be available at [http://localhost:8080/](http://localhost:8080/).

# How to build static binary

You can build a static binary with PyInstaller:

```
pip install pyinstaller
pyinstaller --onefile api.py
```

The binary is build in subfolder `dist/`, so you should have `dist/api` binary

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
