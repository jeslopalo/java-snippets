package es.sandbox.snippet.pattern.visitor.enumeration

import spock.lang.Specification

/**
 * Created by jeslopalo on 22/9/17.
 */
class EnumTypeWhenVisitingSpecs extends Specification {

    def "it raises an exception without visitor"() {
        when:
        EnumType.FIRST.accept(null)

        then:
        thrown(NullPointerException)
    }

    def "it should visit"() {
        given:
        def visitor = new HasBeenVisited()
        assert !visitor.visited

        when:
        EnumType.FIRST.accept(visitor)

        then:
        visitor.visited
    }

    def "it should get the visit result"() {
        expect:
        EnumType.SECOND.accept(new EnumTypeDescriptor()) == "second"
    }

    def "it should visit directly from visitor"() {
        given:
        def visitor = new HasBeenVisited()
        assert !visitor.visited

        when:
        visitor.visit(EnumType.FIRST)

        then:
        visitor.visited
    }

    def "it should get the visit result directly from visitor"() {
        expect:
        new EnumTypeDescriptor().visit(EnumType.SECOND) == "second"
    }


    class HasBeenVisited implements EnumType.Visitor<Boolean> {

        def visited = false;

        @Override
        Boolean visitFirst() {
            this.visited = true
        }

        @Override
        Boolean visitSecond() {
            this.visited = true
        }
    }

    class EnumTypeDescriptor implements EnumType.Visitor<String> {

        @Override
        String visitFirst() {
            EnumType.FIRST.name().toLowerCase()
        }

        @Override
        String visitSecond() {
            EnumType.SECOND.name().toLowerCase()
        }
    }
}
