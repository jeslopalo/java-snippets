package es.sandbox.snippet.type.range

import spock.lang.Specification

/**
 * Created by jeslopalo on 26/9/17.
 */
class LeftBoundedRangeSpecs extends Specification {

    def "it has only a start"() {
        when:
        def range = Range.from(from)

        then:
        range.from().get() == from
        !range.to().isPresent()

        where:
        from << [-1, 0, 1]
    }

    def "left boundary may not be null"() {
        when:
        Range.from(null)

        then:
        def exception = thrown(NullPointerException)
        exception.message == "Range start may not be null"
    }

    def "it can be represented as string"() {
        when:
        def range = Range.from(from)

        then:
        range.toString() == "[$from, -]"

        where:
        from << [-2, 0, 2]
    }
}
