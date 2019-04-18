# Java LTS
Playground for java 9,10,11 LTS

## Java 10

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


## Java 11

#### Type Inference

From this point on Java 11 allows us to use type inference into lambda definition like:

    (var something) -> something.do()

Examples on _TypeInferenceShould_