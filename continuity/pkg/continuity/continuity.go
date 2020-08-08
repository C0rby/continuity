package continuity

import (
	"fmt"

	"github.com/skratchdot/open-golang/open"
)

type MediumType string

const (
	TypeURL MediumType = "URL"
)

type Medium struct {
	Type  MediumType `json:"type"`
	Value string     `json:"value"`
}

type Service struct {
}

func (s *Service) Open(m Medium) error {
	switch m.Type {
	case TypeURL:
		return openURL(m.Value)
	default:
		return fmt.Errorf("invalid medium type: %s", m.Type)
	}
}

func openURL(url string) error {
	return open.Run(url)
}
