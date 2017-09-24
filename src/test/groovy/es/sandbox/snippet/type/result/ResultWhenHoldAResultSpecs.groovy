package es.sandbox.snippet.type.result

import spock.lang.Specification

/**
 * Created by jeslopalo on 24/9/17.
 */
class ResultWhenHoldAResultSpecs extends Specification {

    def "it holds the executable result"() {
        when:
        def sus = Result.of({ events -> "result" })

        then:
        sus.present
        sus.getOrThrows() == "result"
    }

    def "it can consume the result"() {
        given:
        def sus = Result.of({ events -> "result" })

        when:
        sus.ifPresent({ result -> assert result == "result" })

        then:
        noExceptionThrown()
    }

    def "it is not failed"() {
        when:
        def sus = Result.of({ events -> "result" })

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
            "result"
        })

        when:
        def events = []
        sus.forEachEvent({ event -> events << event })

        then:
        events == [Event.ONE, Event.TWO, Event.ONE, Event.THREE]
    }

    def "it doesn't process events when there is not logged events"() {
        given:
        def sus = Result.of({ events -> "result" })

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
            "result"
        })

        when:
        def toString = sus.toString()

        then:
        toString == expectedString

        where:
        loggedEvents                      || expectedString
        []                                || "Result[result, {events=[]}]"
        [Event.ONE]                       || "Result[result, {events=[ONE]}]"
        [Event.ONE, Event.THREE]          || "Result[result, {events=[ONE, THREE]}]"
        [Event.ONE, Event.TWO, Event.TWO] || "Result[result, {events=[ONE, TWO, TWO]}]"
    }

    enum Event {
        ONE, TWO, THREE;
    }
}
