package main

import (
	"fmt"
	"net/http"

	"github.com/c0rby/continuity/pkg/continuity"
	"github.com/c0rby/continuity/pkg/rest"
)

func main() {
	svc := continuity.Service{}
	restHandler := rest.Handler(&svc)

	fmt.Println("Listen on 0.0.0.0:8080")
	http.ListenAndServe(":8080", restHandler)
}
