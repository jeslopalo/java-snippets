package es.sandbox.snippet.type.range

import spock.lang.Specification

/**
 * Created by jeslopalo on 26/9/17.
 */
class CompletelyBoundedRangeSpecs extends Specification {

    def "it has a start & end boundaries"() {
        when:
        def range = Range.between(a, b)

        then:
        range.from().get() == a
        range.to().get() == b

        where:
        a  | b
        -2 | -2
        -2 | -1
        -1 | 0
        0  | 0
        0  | 1
        1  | 2
        2  | 2
    }

    def "its boundaries may not be null"() {
        when:
        Range.between(from, to)

        then:
        def exception = thrown(NullPointerException)
        exception.message == expectedMessage

        where:
        from | to   || expectedMessage
        null | null || "Range start may not be null"
        null | 1    || "Range start may not be null"
        1    | null || "Range end may not be null"
    }

    def "it can be represented as string"() {
        when:
        def range = Range.between(a, b)

        then:
        range.toString() == "[$a, $b]"

        where:
        a  | b
        -2 | -2
        -2 | -1
        -1 | 0
        0  | 0
        0  | 1
        1  | 2
        2  | 2
    }
}
