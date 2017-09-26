package es.sandbox.snippet.type.range

import spock.lang.Specification

import java.time.Instant

/**
 * Created by jeslopalo on 26/9/17.
 */
class RangeBoundConsumerSpecs extends Specification {

    def "it can consume left boundary"() {
        given:
        def sus = Range.between(Instant.now(), Instant.now().plusSeconds(3600))

        when:
        sus.ifLeftBoundary({ from -> assert from != null })

        then:
        noExceptionThrown()
    }

    def "it can consume right boundary"() {
        given:
        def sus = Range.between(Instant.now(), Instant.now().plusSeconds(3600))

        when:
        sus.ifRightBoundary({ to -> assert to != null })

        then:
        noExceptionThrown()
    }
}
