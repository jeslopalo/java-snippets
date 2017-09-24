package es.sandbox.snippet.type.result

import spock.lang.Specification

/**
 * Created by jeslopalo on 24/9/17.
 */
class ResultWhenThereIsAFailureSpecs extends Specification {

    def systemUnderSpecification(Event... events) {
        Result.of({ eventLogger ->
            events.each { e -> eventLogger.log e }
            throw new NullPointerException("exception message")
        })
    }

    def "there is no result"() {
        given:
        def sus = systemUnderSpecification()

        when:
        sus.ifPresent({ result -> throw new IllegalStateException() })

        then:
        !sus.isPresent()
        noExceptionThrown()
    }

    def "it is failed"() {
        given:
        def sus = systemUnderSpecification()

        expect:
        sus.isFailed()
    }

    def "it should not procces events on failure"() {
        given:
        def sus = systemUnderSpecification(Event.ONE, Event.TWO, Event.THREE)

        when:
        sus.forEachEvent({
            e -> throw new IllegalStateException()
        })

        then:
        noExceptionThrown()
    }

    def "it prints out exception"() {
        given:
        def sus = systemUnderSpecification(Event.ONE)

        expect:
        sus.toString() == "Result[exception=java.lang.NullPointerException: exception message]"
    }

    enum Event {
        ONE, TWO, THREE;
    }
}
