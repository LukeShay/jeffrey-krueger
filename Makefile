IMAGE_NAME = docker.pkg.github.com/lukeshay/jeffrey-krueger/jeffrey-krueger
TAG = $(shell git rev-parse --short HEAD)
CMD ?= check

build:
	docker build -f Dockerfile -t $(IMAGE_NAME):$(TAG) .
.PHONY: build

push:
	docker push $(IMAGE_NAME):$(TAG)
.PHONY: push

login:
	echo -n "$(GITHUB_TOKEN)" | docker login https://docker.pkg.github.com -u Actions --password-stdin
.PHONY: login

run:
	docker run --rm \
		-v "$(PWD)":/usr/src/myapp \
		-w /usr/src/myapp \
		openjdk:11-jdk \
		./gradlew --no-daemon $(CMD)
.PHONY: run
