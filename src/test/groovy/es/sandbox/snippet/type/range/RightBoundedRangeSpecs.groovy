package es.sandbox.snippet.type.range

import spock.lang.Specification

/**
 * Created by jeslopalo on 26/9/17.
 */
class RightBoundedRangeSpecs extends Specification {

    def "it has only an end"() {
        when:
        def range = Range.to(to)

        then:
        !range.from().isPresent()
        range.to().get() == to

        where:
        to << [-1, 0, 1]
    }

    def "right boundary may not be null"() {
        when:
        Range.to(null)

        then:
        def exception = thrown(NullPointerException)
        exception.message == "Range end may not be null"
    }

    def "it can be represented as string"() {
        when:
        def range = Range.to(to)

        then:
        range.toString() == "[-, $to]"

        where:
        to << [-2, 0, 2]
    }
}
