package rest

import (
	"encoding/json"
	"net/http"

	"github.com/c0rby/continuity/pkg/continuity"
	"github.com/go-chi/chi"
	"github.com/go-chi/chi/middleware"
)

func Handler(svc *continuity.Service) http.Handler {
	r := chi.NewRouter()
	r.Use(middleware.Logger)
	r.Use(middleware.RealIP)

	r.Post("/open", func(w http.ResponseWriter, r *http.Request) {
		var m continuity.Medium
		if err := json.NewDecoder(r.Body).Decode(&m); err != nil {
			w.WriteHeader(http.StatusInternalServerError)
			w.Write([]byte(err.Error()))
			return
		}

		if err := svc.Open(m); err != nil {
			w.WriteHeader(http.StatusInternalServerError)
			w.Write([]byte(err.Error()))
			return
		}

		w.WriteHeader(http.StatusOK)
	})

	return r
}
