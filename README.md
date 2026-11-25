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

## SIMD level (runtime dispatch)

The native library is built with Faiss dynamic dispatch (`FAISS_OPT_LEVEL=dd`): the same `libswigfaiss.so` holds a generic, an AVX2 and several AVX-512 implementations of the hot loops.
One of them is selected once, when the library is loaded:

- by default, the best level the CPU supports;
- or the level named by the environment variable `FAISS_SIMD_LEVEL`, which must be set before the JVM starts.

| `FAISS_SIMD_LEVEL` | Code used |
|---|---|
| `NONE` | generic code (SSE4.2 and POPCNT only); the fallback when a SIMD level misbehaves |
| `AVX2` | AVX2 + FMA + BMI2 |
| `AVX512` | AVX-512 F/CD/VL/DQ/BW |
| `AVX512_VPOPCNT` | `AVX512` + VPOPCNTDQ/BITALG |
| `AVX512_SPR` | `AVX512_VPOPCNT` + VNNI/BF16/FP16 (Intel Sapphire Rapids and later) |

Every level returns the same neighbours; distances can differ in their last digits because the additions are done in a different order.
`JFaissInitializer.initialize()` logs the selected level (`Faiss SIMD level: ...`), and `SIMDConfig.get_level_name()` returns it.

Two things to know before setting the variable:

- The value is case-sensitive and an unknown or empty value aborts the JVM while the library is loading (`Invalid SIMD level string`).
- Faiss does not check a forced level against the CPU: forcing a level the CPU lacks crashes with an illegal instruction at the first search. Only force a level that every machine of the deployment supports.

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

## Checking a build

`mvn verify` runs the tests three times: at the SIMD level Faiss picks for the machine, with `FAISS_SIMD_LEVEL=NONE` and with `FAISS_SIMD_LEVEL=AVX2`.

The Docker build also runs two checks on the native library, which can be run by hand on any build:

- `ci/check-abi.sh src/main/resources target/classes`: the library needs no newer glibc / libstdc++ than CentOS 8, finds its bundled dependencies, and the classes load on Java 8;
- `ci/check-simd.py faiss/build src/main/resources/libswigfaiss.so jni/swigfaiss.o`: the AVX2 and AVX-512 code is there, and only where a CPU without it never goes.

One more check looks at the Java side:

- `java ci/ApiCheck.java target/classes compat/required-api.txt`: the methods that code compiled against `jfaiss-cpu` 1.7.0 uses still exist with the same signature.

## To-do

* [x] Update to CentOS 8: build Faiss native with CentOS 8
* [x] Update to Java 11
* [x] Runtime SIMD dispatch (AVX2 / AVX-512) using Faiss dynamic dispatch (`FAISS_OPT_LEVEL=dd`)
* [ ] Use Intel's MKL BLAS instead of OpenBLAS

## Reference

- <https://github.com/gameofdimension/jni-faiss>
- <https://github.com/victor-paltz/global-load-library>
- <https://github.com/adamheinrich/native-utils>
- <https://github.com/thenetcircle/faiss4j>
