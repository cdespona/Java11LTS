# Java LTS
Playground for java 9,10,11 LTS

## Java 10

### Developer Experience

#### Type Inference

This feature is available only for local variables with the initializer. It cannot be used for member variables, method parameters, return types, etc – the initializer is required as without which compiler won’t be able to infer the type.

Examples on _TypeInferenceShould_

#### Docker awareness

**Official docker statement** - Until Java 9 the JVM did not recognize memory or cpu limits set by the container using flags. In Java 10, memory limits are automatically recognized and enforced.

Java defines a server class machine as having 2 CPUs and 2GB of memory and the default heap size is ¼ of the physical memory.

This just works with Linux based containers.

This new support is enabled by default and can be disabled in the command line with the JVM option:

    -XX:-UseContainerSupport
    
Also, this change adds a JVM option that provides the ability to specify the number of CPUs that the JVM will use:

    -XX:ActiveProcessorCount=count
Also, three new JVM options have been added to allow Docker container users to gain more fine-grained control over the amount of system memory that will be used for the Java Heap:

    -XX:InitialRAMPercentage
    -XX:MaxRAMPercentage
    -XX:MinRAMPercentage

**Real World** - Java 8 since version 191 is also aware, I guess this is because this is the most used version at the moment.
Evidence [JDK8Release191](https://www.oracle.com/technetwork/java/javase/8all-relnotes-2226344.html#R180_191)

With latest openJDK Java 8
    
    docker container run -it -m=1G --entrypoint sh java:openjdk-8u111-jdk-alpine
    
    root@74f1f0094b0d:/# docker-java-home/bin/java -XX:+PrintFlagsFinal -version | grep MaxHeapSize
        uintx MaxHeapSize                              := 268435456                           {product}
        
With old openJDK Java 8
    
    docker container run -it -m1G --entrypoint bash openjdk:8-jdk
    
    java -XX:+PrintFlagsFinal -version | grep MaxHeapSize
        uintx MaxHeapSize                              := 520093696                           {product}


With Java 10

    docker container run -it -m1G --entrypoint bash openjdk:10-jdk
    
    docker-java-home/bin/java -XX:+PrintFlagsFinal -version | grep MaxHeapSize
       size_t MaxHeapSize                              = 268435456                                {product} {ergonomic}
    
#### Collections

**List/Set/Map.copyOf** and **Collectors.toUnmodifiableList()**, **Collectors.toUnmodifiableSet()** and **Collectors.toUnmodifiableMap()** options added to create unmodifiable lists either from already existing Lists or streams.

Examples on _CollectionsShould_

#### Optional

**Optional.orElseThrow()** is now the preferred collect option over **Optional.get()**

Examples on _OptionalShould_

### Performance

#### Parallel Full GC for G1

In Java 9 G1 was made the default GC but it was running on **single threaded mark-sweep-compact** algorithm it is now changed to **parallel** reducing the stop-the-world timings.

#### Application Class-Data Sharing

Allows a set of classes to be pre-processed into a shared archive file that can then be memory-mapped at runtime to reduce startup time which can also reduce dynamic memory footprint when multiple JVMs share the same archive file.

**List the classes to archive**

    java -Xshare:off -XX:DumpLoadedClassList=app.lst -cp ./build/libs/Java11LTS-1.0-SNAPSHOT.jar HelloWorld
    
**Create AppCDS archive**

    java -Xshare:dump -XX:SharedClassListFile=app.lst -XX:SharedArchiveFile=app.jsa -cp ./build/libs/Java11LTS-1.0-SNAPSHOT.jar
    
**Use AppCDS archive**

    java -Xshare:on -XX:SharedArchiveFile=app.jsa -cp ./build/libs/Java11LTS-1.0-SNAPSHOT.jar HelloWorld
    
#### Java Based JIT Compiler

Graal is a dynamic compiler written in Java that integrates with the HotSpot JVM; it’s focused on high performance and extensibility. It’s also the basis of the experimental Ahead-of-Time (AOT) compiler introduced in JDK 9.

JDK 10 enables the Graal compiler, to be used as an experimental JIT compiler on the Linux/x64 platform.

To enable it:

    -XX:+UnlockExperimentalVMOptions -XX:+UseJVMCICompiler
    
Let's use an example provided by [GraalVM](https://www.graalvm.org/docs/examples/java-performance-examples/) themselves, it can be found in our source code _GraalExample_.

The example is based on the Streams API to demonstrate performance of the Graal compiler. This example counts the number of upper case characters in a body of text. To simulate a large load, the same sentence is processed 10 million times

To use Graal provided by Java

    javac GraalExample.java
    java -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler GraalExample
    
To use the top tier Compiler

    javac GraalExample.java
    java -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:-UseJVMCICompiler GraalExample

## Java 11

#### Type Inference

From this point on Java 11 allows us to use type inference into lambda definition like:

    (var something) -> something.do()

Examples on _TypeInferenceShould_