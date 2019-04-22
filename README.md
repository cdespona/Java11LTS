# Java LTS
Playground for java 9,10,11 LTS

## Oracle License

From now on Java has to be payed for its production usage, any other purpose is free to go.

LTS happens every 3 years, current one is Java 11 LTS. Java 9 and Java 10 are already abandoned.

Support roadmap can be checked [here](https://www.oracle.com/technetwork/java/java-se-support-roadmap.html)

Each new version is released every 6 months (March and September).

Each release is delivered at the same time as OpenJDK and OracleJDK, however OpenJDK will just receive updates during the 6 month window the version is live, while the OracleJDK LTS will keep receiving the updates for 3 years, plus the extended support.

There are some other vendors creating its own JDK like **RedHat** (RedHat JDK), **Azul** (Zulu JDK), no sure which fares do they have. One of them [**AdoptOpenJDK**](https://adoptopenjdk.net/) is fully free based on the openJDK but it depends on their good will to keep it updated. 

From Java 9 onwards was the first time Java really remove features from the JDK, which means Java is not backwards compatible anymore.

Based on that we can say we can choose between 3 options:

1) Keep using an old Java version, like J8. This way we can keep using it for **free** and it remains **stable**, but you must say goodbye to **security** patches.
2) Use the OpenJDK, which means we can use it for **free** in production and it gets the **security** updates for 6 months, but we would need to upgrade to the newer versions losing potential **stability**.
3) **Pay** the OracleJDK license, so you can get **stability** for 3 years..., and you keep getting the **security** updates without moving to newer versions.   

## Java 10

[Release notes](https://www.oracle.com/technetwork/java/javase/10-relnote-issues-4108729.html)

Is the first version iteration where the 6 months release happened.

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
Evidence [JDK8Release191](https://www.oracle.com/technetwork/java/javase/10all-relnotes-4108743.html)

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

    javac GraalExample.java (this is not needed in J11)
    java -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:+UseJVMCICompiler GraalExample
    
To use the top tier Compiler

    javac GraalExample.java (this is not needed in J11)
    java -XX:+UnlockExperimentalVMOptions -XX:+EnableJVMCI -XX:-UseJVMCICompiler GraalExample
    
To add more iterations
    
    -Diterations=N

To print the graal compilations

    -Dgraal.PrintCompilation=true
    
There is another example that blends colors from black into _Blender_

### Main Removals

These removal were marked as deprecated from Java 1.1 and 1.2.

**Fields**

    java.lang.SecurityManager.inCheck

**Methods**

    java.lang.Runtime.getLocalizedInputStream(java.io.InputStream)
    java.lang.Runtime.getLocalizedOutputStream(java.io.OutputStream)
    java.lang.SecurityManager.classDepth(java.lang.String)
    java.lang.SecurityManager.classLoaderDepth()
    java.lang.SecurityManager.currentClassLoader()
    java.lang.SecurityManager.currentLoadedClass()
    java.lang.SecurityManager.getInCheck()
    java.lang.SecurityManager.inClass(java.lang.String)
    java.lang.SecurityManager.inClassLoader()

## Java 11

[Release notes](https://www.oracle.com/technetwork/java/javase/11-relnotes-5012447.html)

### Developer experience

#### Type Inference

From this point on Java 11 allows us to use type inference into lambda definition like:

    (var something) -> something.do()

Examples on _TypeInferenceShould_

#### Strings

New methods have been added into the String API. Apache commons, Oracle is after you.

* repeat
* strip
* isBlank ;)
* lines

Examples on _StringShould_

#### Collections

There is an easy way to transform Collections into Arrays **Collection.toArray(IntFunction)**. However there was another method **Collection.toArray(T[])**, that means we cannot use it now as follows

    Collection.toArray(null);
    
Because the compiler will not be able to choose between both of the options.

#### Predicate

Predice now allows to use RegExp from Pattern class and to negate using method reference

Examples on _PredicateShould_

#### IO Nullified

Sometimes there is a need to use input/output without a real implementation but the constructor like

    FileInputStream(null)
    
Throws **NullPointerException**, this is no longer a problem because now in J11 there a set of null implementations.

Calling read()/skip()/transferTo() methods on the returned input streams behave as end of the stream has reached.

Calling write()/append()/flush() methods on the returned output streams do nothing.

After a stream has been closed, calling read/write methods will throw IOException.

Examples on _IONullShould_

#### Path

Instead of **Path.get()** there is now a method **Path.of**, this is a renaming.

#### Optional

**Optional.isEmpty()** is now available as part of the API. So we don't have to negate **Optional.isPresent()**

Examples on _OptionalShould_

#### HttpClient

Now Java offers an standard HttpClient, not sure which pro & cons it does have in detail, maybe for very simple scenarios it suits perfectly but not sure how it handles retries, circuit breakers, etc...

What's sure, it is build on top of the reactive java API so it can work asynchronously.

Examples on _HttpClientShould_

Some other examples with Synchronous, asynchronous and reactive rest calls are included into _http/WikipediaSearch_

### Performance

#### New Garbage Collectors

1) [Epsilon](https://openjdk.java.net/jeps/318) (No-Op)
2) [ZGC](https://openjdk.java.net/jeps/333) New algorithm maximum 10ms allocation, with 15% trade off performance compared with G1.

### Main Removals

* Removal of com.sun.awt.AWTUtilities Class 
* Removal of Lucida Fonts from Oracle JDK 
* Removal of appletviewer Launcher 
* Oracle JDK's javax.imageio JPEG Plugin No Longer Supports Images with alpha
* Removal of sun.misc.Unsafe.defineClass 

    _Users should use the public replacement, java.lang.invoke.MethodHandles.Lookup.defineClass, added in Java SE 9_
    
* Removal of Thread.destroy() and Thread.stop(Throwable) Methods 

    _Thread.stop() is unaffected by this change_
    
* Removal of sun.nio.ch.disableSystemWideOverlappingFileLockCheck Property
* Removal of sun.locale.formatasdefault Property 
* Removal of JVM-MANAGEMENT-MIB.mib
* Removal of SNMP Agent 
    
    _As a result, the following com.sun.management.snmp.* properties are no-op when set by using the -D option or the management.properties configuration._
    
* Removal of Java Deployment Technologies

    _Note that the Java Control Panel, which was used for configuring the deployment technologies, has also been removed along with the shared system JRE (but not the server JRE) and the JRE Auto Update mechanism_
    
* Removal of JMC (Mission Control) from the Oracle JDK. 

    _Exists an standalone version compatible with oracleJDK and openJDK_
    
* Removal of JavaFX from the Oracle JDK.

    _Will be available as separate modules_
    
* JEP 320 Remove the Java EE and CORBA Modules.

    _JAXB, JAX-WS, CORBA... out of the JDK, so no SOAP, no XML binding_ 
