# JavaPowerUtils

The Project JavaPowerUtils is a collection of code with the purpose to simplify certain tasks of the programmer 
so that we're able to focus on the creative part of the programming process. It's strongly based on Java 8
concepts like functional programming to provide an unlimited extensibility and customizability.

## Package Description

All packages at mkz.utils are made to be undependend from others (there may be errors, please report) to
provid their usability as standalone library.

### IO

Basic Input-Output like a Logger with support of log-levels. The log output can be overriden by a functional
implementation to lead the output to a JFX/Swing TextArea or Log File or anything else.

Also holds a functional implementation of the tryCatch method (just set catch to null if not catch needed).

### JCLI

Basically made as a java command line tool that parses the users commands, but it can be used for alot more.
Here a few examples for what I've already used it:

* Command Line Loop (as stated)
* Parse call parameters
* Calculators (e.g. a+b) and other simple operations

### Math

An entirely generic math library to perform arithmetic operations with complex objects derived from the math
theory. Operations can be performed with any possible java type (given that you provide an operator with a 
few simple functions like addition subtraction etc. for this type). The following concepts are supported:

* Real Numbers: Greatest common divisor, Max, Min, etc.
* Complex numbers: Multiplication, division, magnitude etc.
* Matrices: Determinante, Matrix Multiplication, Inverse/Matrix divsion, Gauss LTM etc.

The Class math.AOP is a collection of predefined operators for Numbers/Matrices/Complex of basic Java Types 
like Integer, Double, BigDecimal etc. Operators are tools that hold all implementations used to perform 
arithmetic operation with these types, e.g. math.AOP.MTX Holds all Matrix Operators, and math.AOP.C holds all 
Complex number Operators. It is also possible to create your own Operator for new types (math.op.ArithmeticOperator).
