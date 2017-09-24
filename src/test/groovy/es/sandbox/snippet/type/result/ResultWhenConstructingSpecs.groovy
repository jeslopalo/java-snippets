package es.sandbox.snippet.type.result

import spock.lang.Specification

/**
 * Created by jeslopalo on 23/9/17.
 */
class ResultWhenConstructingSpecs extends Specification {

    def "executable may not be null"() {
        when:
        Result.of(null)

        then:
        def exception = thrown(NullPointerException)
        exception.message == "Executable may not be null"
    }
}
