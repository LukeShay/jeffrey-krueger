IMAGE_NAME = docker.pkg.github.com/lukeshay/discord-bot/app
TAG = $(shell git rev-parse --short HEAD)

build:
	docker build -f Dockerfile -t $(IMAGE_NAME):$(TAG) .
.PHONY: build

push:
	docker push $(IMAGE_NAME):$(TAG)
.PHONY: push

login:
	echo -n "$(GITHUB_TOKEN)" | docker login https://docker.pkg.github.com -u Actions --password-stdin
.PHONY: login
