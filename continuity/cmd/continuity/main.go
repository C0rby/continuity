package main

import (
	"fmt"
	"net/http"

	"github.com/c0rby/continuity/pkg/continuity"
	"github.com/c0rby/continuity/pkg/rest"

	"github.com/grandcat/zeroconf"
)

func main() {
	svc := continuity.Service{}
	restHandler := rest.Handler(&svc)

	fmt.Println("Listening on 0.0.0.0:8080")

	server, err := zeroconf.Register("Yocto", "_continuity._tcp", "local.", 8080, []string{"txtv=0", "lo=1", "la=2"}, nil)
	if err != nil {
		panic(err)
	}
	defer server.Shutdown()

	http.ListenAndServe(":8080", restHandler)
}
