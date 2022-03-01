# Slow Consuming Demo

## Usage

```
$ lein run
```

Or

```
docker run -it --rm --network host -v $(pwd):/working ardoq/leiningen:jdk11-2.9.4-mp /bin/bash -c "cd /working && lein run"
```

## Configuration

In `src/bugger/core.clj`.
