# Bugger Demo

## Usage

```
$ lein with-profile write run
```

Or

```
docker run -it --rm --network host -v $(pwd):/working ardoq/leiningen:jdk11-2.9.4-mp /bin/bash -c "cd /working && lein with-profile write run"
```

## Configuration

In `src/bugger/write.clj` and `src/bugger/read.clj`.
