package com.stupidplebs.log4jmonitor

import static org.hamcrest.CoreMatchers.is
import static org.hamcrest.CoreMatchers.not
import static org.junit.Assert.assertThat

import java.util.regex.Pattern

import org.apache.log4j.Level
import org.junit.Test

import spock.lang.Specification

class Log4jStatementSpec extends Specification {
    def "getLevel should return level used in constructor"() {
		when:
        def log4jStatement = new Log4jStatement(level, "log statement")

		then:
        log4jStatement.level == level

		where:
		level | _
		Level.DEBUG | _
		Level.INFO  | _
		Level.WARN  | _
		Level.ERROR | _
		Level.FATAL | _
		
    }

    def "getStatement should return statement used in constructor"() {
		given:
        def log4jStatement = new Log4jStatement(Level.ERROR, "log statement")

		expect:
        log4jStatement.statement == "log statement"

    }

    def "null level parameter to getInstance should throw IllegalArgumentException"() {
        when:
        Log4jStatement.getInstance(null, "log statement")

		then:
		IllegalArgumentException e = thrown()
		e.message == "level parameter cannot be null"

    }

    def "null statement parameter to getInstance should throw an IllegalArgumentException"() {
        when:
        Log4jStatement.getInstance(Level.ERROR, null)

		then:
		IllegalArgumentException e = thrown()
		e.message == "statement parameter cannot be null"

    }

    def "matchesShouldReturnTrueIfThePatternMatchesTheStatement"() {
        given:
        def log4jStatement = new Log4jStatement(Level.ERROR,
                "log statement")

        and:
        def pattern = Pattern.compile("l.g statement")
        
        expect:
        log4jStatement.matches(pattern)
        
    }
    
    def "matchesShouldReturnFalseIfThePatternDoesNotMatchTheStatement"() {
        given:
        def log4jStatement = new Log4jStatement(Level.ERROR,
                "log statement")

        and:
        def pattern = Pattern.compile("non-matching pattern")
        
        expect:
        !log4jStatement.matches(pattern)
        
    }

    def "isDebug should return true if level is DEBUG"() {
        given:
        def log4jStatement = new Log4jStatement(Level.DEBUG,
                "log statement")

        expect:
        log4jStatement.isDebug()
        
        and:
        !log4jStatement.isInfo()
        !log4jStatement.isWarn()
        !log4jStatement.isError()
        !log4jStatement.isFatal()
        
    }
    
    def "isInfo should return true if level is INFO"() {
        given:
        def log4jStatement = new Log4jStatement(Level.INFO,
                "log statement")

        expect:
        log4jStatement.isInfo()
        
        and:
        !log4jStatement.isDebug()
        !log4jStatement.isWarn()
        !log4jStatement.isError()
        !log4jStatement.isFatal()
        
    }
    
    def "isWarn should return true if level is WARN"() {
        given:
        def log4jStatement = new Log4jStatement(Level.WARN,
                "log statement")

        expect:
        log4jStatement.isWarn()
        
        and:
        !log4jStatement.isDebug()
        !log4jStatement.isInfo()
        !log4jStatement.isError()
        !log4jStatement.isFatal()
        
    }
    
    def "isError should return true if level is ERROR"() {
        given:
        def log4jStatement = new Log4jStatement(Level.ERROR,
                "log statement")

        expect:
        log4jStatement.isError()
        
        and:
        !log4jStatement.isDebug()
        !log4jStatement.isInfo()
        !log4jStatement.isWarn()
        !log4jStatement.isFatal()
        
    }
    
    def "isFatal should return true if level is FATAL"() {
        given:
        def log4jStatement = new Log4jStatement(Level.FATAL,
                "log statement")

        expect:
        log4jStatement.isFatal()
        
		and:
        !log4jStatement.isDebug()
        !log4jStatement.isInfo()
        !log4jStatement.isWarn()
        !log4jStatement.isError()
        
    }
    
    def "is should return true if level matches supplied level"() {
        given:
        def log4jStatement = new Log4jStatement(Level.FATAL,
                "log statement")

        expect:
        log4jStatement.is(Level.FATAL)
        
        and:
        !log4jStatement.is(Level.DEBUG)
        !log4jStatement.is(Level.INFO)
        !log4jStatement.is(Level.ERROR)
        !log4jStatement.is(Level.WARN)
       
    }
    
    def "instance should be unequal to null"() {
        given:
        def log4jStatement = new Log4jStatement(Level.ERROR, "log statement")

        and:
        def other = null

        expect:
        !log4jStatement.equals(other)

    }

    def "instance should be equal to itself"() {
        given:
        def log4jStatement = new Log4jStatement(Level.ERROR, "log statement")

        and:
        def other = log4jStatement

        expect:
        log4jStatement.equals(other)

    }

    def "instance should be unequal to a non-Log4jStatement object"() {
        given:
        def log4jStatement = new Log4jStatement(Level.ERROR, "log statement")

        and:
        def other = "this isn't a Log4jStatement instance"

        expect:
        !log4jStatement.equals(other)

    }

    def "instances differing only on Level should be unequal and have unequal hashCodes"() {
        given:
        def log4jStatement = new Log4jStatement(Level.ERROR,
                "log statement")

        and:
        def other = new Log4jStatement(Level.INFO, "log statement")

        expect:
        !log4jStatement.equals(other)

        and:
        log4jStatement.hashCode() != other.hashCode()

    }

    def "instances differing only on statement should be unequal and have unequal hashCodes"() {
        given:
        def log4jStatement = new Log4jStatement(Level.ERROR,
                "log statement")

        and:
        def other = new Log4jStatement(Level.ERROR,
                "different log statement")

        expect:
        !log4jStatement.equals(other)

        and:
        log4jStatement.hashCode() != other.hashCode()

    }

    def "instances with equal level and statement should be equal and have equal hashCodes"() {
        given:
        def log4jStatement = new Log4jStatement(Level.ERROR,
                "log statement")

        and:
        def other = new Log4jStatement(Level.ERROR, "log statement")

        expect:
        log4jStatement.equals(other)

        and:
        log4jStatement.hashCode() == other.hashCode()

    }
    
    def "toString should output full class name and level and statement"() {
        given:
        def level = Level.ERROR

        and:
        def statement = "log statement"

        and:
        def log4jStatement = new Log4jStatement(level, statement)

		expect:
        log4jStatement.toString() == 
                "com.stupidplebs.log4jmonitor.Log4jStatement[" + 
                        "level=ERROR,statement=log statement]"

    }

}
