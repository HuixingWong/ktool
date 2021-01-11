package com.example.ktool.test

import com.example.ktool.Bird


/**
 * 15 star
 *
Even though there are some answers here stating that the default implementation is "memory" based,
this is plain wrong.
This is not the case for a lot of years now.

Under java-8, you can do :

java -XX:+PrintFlagsFinal | grep hashCode
To get the exact algorithm that is used (5 being default).

0 == Lehmer random number generator,
1 == "somehow" based on memory address
2 ==  always 1
3 ==  increment counter
4 == memory based again ("somehow")
5 == read below
By default (5), it is using Marsaglia XOR-Shift algorithm, that has nothing to do with memory.

This is not very hard to prove, if you do:

System.out.println(new Object().hashCode());
multiple times, in a new VM all the time - you will get the same value,
so Marsaglia XOR-Shift starts with a seed (always the same, unless some other code does not alter it) and works from that.

But even if you switch to some hashCode that is memory based,
and Objects potentially move around (Garbage Collector calls),
how do you ensure that the same hashCode is taken after GC has moved this object?
Hint: indentityHashCode and Object headers.
 */
fun testHash() {
    val bird = Bird("haha")
    val bird2 = Bird("haha")
    print("haha".hashCode().toString())
//    print(bird.hashCode().toString())
//    print(bird2.hashCode().toString())
}