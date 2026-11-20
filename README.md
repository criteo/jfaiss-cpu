# JFaiss-CPU **(Linux only)**

Java/JNI binding of [facebook/faiss](https://github.com/facebookresearch/faiss/tree/v1.16.0) v1.16.0.
Supports CPU-only version of Faiss.

## Adding `jfaiss-cpu` to your project

Latest versions of `jfaiss-cpu` package are published on [Maven Central](https://central.sonatype.com/artifact/com.criteo.jfaiss/jfaiss-cpu).

A dependency on `jfaiss-cpu` using `Gradle` can be added with the line like this:

```groovy
implementation 'com.criteo.jfaiss:jfaiss-cpu:1.16.0-x'
```

## Runtime dependencies

`jfaiss-cpu` requires Java 8+ to run (class files target Java 8), the binary package bundles all it's dependencies and doesn't need anything extra to run.

Faiss native library is built under CentOS 8 and uses OpenBLAS, which is bundled with the package.
Faiss >= 1.15 requires C++20, so it is compiled with `gcc-toolset-13`, which still links against the CentOS 8 system C++ runtime: the native library runs on glibc >= 2.27 with a `libstdc++.so.6` providing `GLIBCXX_3.4.22`.

`jfaiss-cpu` depends on [com.github.victor-paltz:global-load-library](https://github.com/victor-paltz/global-load-library) v1.2.0 from Maven.
This dependency is used to correctly load Faiss native library with it's dependencies.

## Building from sources

### Requirement

- Java 11 JDK
- Apache Maven v3.6.3
- Docker v19.03.12

### Building from source (docker)

Checkout Git project and initialize it's submodules:

```sh
git clone https://github.com/criteo/jfaiss-cpu.git
cd jfaiss-cpu
git submodule update --init
```

### Build Docker image

```sh
docker build -t jfaiss-image .
```

This will create a docker image that builds Faiss native and generates Java-wrapper using SWIG.

### Copy Faiss binary files from Docker

Copy Faiss binary with it's dependencies into resources directory:

```sh
docker create --name jfaiss-container jfaiss-image
docker cp jfaiss-container:/opt/JFaiss/cpu/src/main/resources  src/main
```

Also, if Faiss was changed (e.g. version was updates), or some changes were made to the [SWIG-file](./jni/swigfaiss.swig), it is required to copy (overwrite) generated Java sources:

```sh
docker cp jfaiss-container:/opt/JFaiss/cpu/src/main/java/com/vectorsearch/faiss/swig  src/main/java/com/vectorsearch/faiss
```

### Build JAR (maven)

Run the test cases
```sh
mvn clean test
```

Creating a JAR
```sh
mvn package
```

If everything went well, the resulting Jar file will `jfaiss-cpu-1.16.0-x.jar` be created in `./target` subdirectory.

## To-do

* [x] Update to CentOS 8: build Faiss native with CentOS 8
* [x] Update to Java 11
* [ ] Runtime SIMD dispatch (AVX2 / AVX-512) using Faiss dynamic dispatch (`FAISS_OPT_LEVEL=dd`)
* [ ] Use Intel's MKL BLAS instead of OpenBLAS

## Reference

- <https://github.com/gameofdimension/jni-faiss>
- <https://github.com/victor-paltz/global-load-library>
- <https://github.com/adamheinrich/native-utils>
- <https://github.com/thenetcircle/faiss4j>
