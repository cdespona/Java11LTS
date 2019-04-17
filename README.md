# Java LTS
Playground for java 9,10,11 LTS

## Java 10

#### Type Inference

This feature is available only for local variables with the initializer. It cannot be used for member variables, method parameters, return types, etc – the initializer is required as without which compiler won’t be able to infer the type.

Examples on _TypeInferenceShould_


## Java 11

#### Type Inference

From this point on Java 11 allows us to use type inference into lambda definition like:

    (var something) -> something.do()

Examples on _TypeInferenceShould_