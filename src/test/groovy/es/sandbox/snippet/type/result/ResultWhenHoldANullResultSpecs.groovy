package es.sandbox.snippet.type.result

import spock.lang.Specification

/**
 * Created by jeslopalo on 24/9/17.
 */
class ResultWhenHoldANullResultSpecs extends Specification {

    def "it holds null value"() {
        given:
        def sus = Result.of({ events -> null })

        when:
        def value = sus.getOrThrows()

        then:
        value == null
        !sus.isPresent()
    }

    def "it can not be consumed"() {
        given:
        def sus = Result.of({ events -> null })

        when:
        sus.ifPresent({ value -> assert value != null })

        then:
        noExceptionThrown()
    }

    def "it is not failed"() {
        when:
        def sus = Result.of({ events -> null })

        then:
        !sus.failed
        !sus.throwable.present
    }

    def "it process events when there is logged events"() {
        given:
        def sus = Result.of({ events ->
            events.log(Event.ONE)
            events.log(Event.TWO)
            events.log(Event.ONE)
            events.log(Event.THREE)
            null
        })

        when:
        def events = []
        sus.forEachEvent({ event -> events << event })

        then:
        events == [Event.ONE, Event.TWO, Event.ONE, Event.THREE]
    }

    def "it doesn't process events when there is not logged events"() {
        given:
        def sus = Result.of({ events -> null })

        when:
        def events = []
        sus.forEachEvent({ event -> events << event })

        then:
        events.empty
    }

    def "it prints out result and events"() {
        given:
        def sus = Result.of({ events ->
            loggedEvents.each { e -> events.log e }
            null
        })

        when:
        def toString = sus.toString()

        then:
        toString == expectedString

        where:
        loggedEvents                      || expectedString
        []                                || "Result[empty, {events=[]}]"
        [Event.ONE]                       || "Result[empty, {events=[ONE]}]"
        [Event.ONE, Event.THREE]          || "Result[empty, {events=[ONE, THREE]}]"
        [Event.ONE, Event.TWO, Event.TWO] || "Result[empty, {events=[ONE, TWO, TWO]}]"
    }

    enum Event {
        ONE, TWO, THREE;
    }
}
