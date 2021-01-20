package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"

	mux "github.com/gorilla/mux"
	"github.com/jessevdk/go-flags"
	log "github.com/sirupsen/logrus"
)

type config struct {
	Config string `short:"c" long:"config" env:"CONFIG" default:"/etc/backend/data.json"`
}

type products struct {
	Products []*product `json:"products"`
}

type product struct {
	Name        string `json:"name"`
	Description string `json:"description"`

	View int `json:"view"`
	Buy  int `json:"buy"`
}

func loadProducts(dataPath string) (*products, error) {
	byteProds, err := ioutil.ReadFile(dataPath)
	if err != nil {
		return nil, err
	}

	var prods products
	err = json.Unmarshal(byteProds, &prods)
	if err != nil {
		return nil, err
	}
	return &prods, err
}

func (prods *products) loadProduct(id string) (*product, error) {
	for _, p := range prods.Products {
		if p.Name == id {
			return p, nil
		}
	}

	return nil, fmt.Errorf("could not find product with id: %s", id)
}

func listProducts(w http.ResponseWriter, r *http.Request, p *products) {
	json, err := json.Marshal(p)
	if err != nil {
		http.Error(w, "failed to marshal json", http.StatusServiceUnavailable)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	fmt.Fprintf(w, string(json))
	return
}

func getProduct(w http.ResponseWriter, r *http.Request, p *products) {
	vars := mux.Vars(r)
	id := vars["id"]

	prod, err := p.loadProduct(id)
	if err != nil {
		http.Error(w, fmt.Sprintf("failed to get product: %v", err), http.StatusNotFound)
		return
	}

	prod.View++

	json, err := json.Marshal(prod)
	if err != nil {
		http.Error(w, "failed to marshal json", http.StatusServiceUnavailable)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	fmt.Fprintf(w, string(json))

	return
}

func buyProduct(w http.ResponseWriter, r *http.Request, p *products) {
	vars := mux.Vars(r)
	id := vars["id"]

	prod, err := p.loadProduct(id)
	if err != nil {
		http.Error(w, fmt.Sprintf("failed to get product: %v", err), http.StatusNotFound)
		return
	}

	prod.Buy++

	return
}

func apiHandler(apiF func(w http.ResponseWriter, r *http.Request, p *products), p *products) func(http.ResponseWriter, *http.Request) {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		apiF(w, r, p)
	})

}

func loadConfig() (*config, error) {
	var c config
	parser := flags.NewParser(&c, flags.Default)
	if _, err := parser.Parse(); err != nil {
		return nil, err
	}
	return &c, nil
}

func main() {
	conf, err := loadConfig()
	if err != nil {
		log.Fatalf("failed to load configuration: %v", err)
	}

	router := mux.NewRouter().StrictSlash(true)

	prods, err := loadProducts(conf.Config)
	if err != nil {
		log.Fatalf("failed to read products: %v", err)
	}

	router.HandleFunc("/", apiHandler(listProducts, prods))
	router.HandleFunc("/product/{id}", apiHandler(getProduct, prods))
	router.HandleFunc("/buy/{id}", apiHandler(buyProduct, prods))

	log.Infof("Starting app on port 8080")
	log.Fatal(http.ListenAndServe(":8080", router))
}
