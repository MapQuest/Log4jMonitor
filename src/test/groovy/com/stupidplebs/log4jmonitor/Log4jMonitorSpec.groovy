package com.stupidplebs.log4jmonitor

import java.util.regex.Pattern
import java.util.regex.PatternSyntaxException

import org.apache.log4j.Level
import org.apache.log4j.Logger

import com.sun.org.apache.xalan.internal.xsltc.runtime.Parameter;

import spock.lang.Specification

class Log4jMonitorSpec extends Specification {
	def "null level constructorparameter should throw an IllegalArgumentException"() {
		when:
		new Log4jMonitor(null)

		then:
		IllegalArgumentException e = thrown()
		e.message == "level parameter cannot be null"

	}

	def "zero parameter constructor should log statements at all levels"() {
		given:
		def log4jMonitor = new Log4jMonitor()

		and:
		def logger = Logger.getLogger(Log4jMonitor.class)

		when: "DEBUG, INFO, WARN, ERROR, and FATAL statements are logged"
		logger.debug("debug statement")
		logger.info("info statement")
		logger.warn("warn statement")
		logger.error("error statement")
		logger.fatal("fatal statement")

		then:
		log4jMonitor.statementCount == 5

		and:
		log4jMonitor.statements == [
			Log4jStatement.getInstance(Level.DEBUG, "debug statement"),
			Log4jStatement.getInstance(Level.INFO, "info statement"),
			Log4jStatement.getInstance(Level.WARN, "warn statement"),
			Log4jStatement.getInstance(Level.ERROR, "error statement"),
			Log4jStatement.getInstance(Level.FATAL, "fatal statement")
		]
		
	}

	def "getDebugInstance should log statements at all levels"() {
		given:
		def log4jMonitor = Log4jMonitor.debugInstance

		and:
		def logger = Logger.getLogger(Log4jMonitor.class)

		when: "DEBUG, INFO, WARN, ERROR, and FATAL statements are logged"
		logger.debug("debug statement")
		logger.info("info statement")
		logger.warn("warn statement")
		logger.error("error statement")
		logger.fatal("fatal statement")

		then:
		log4jMonitor.statementCount == 5

		and:
		log4jMonitor.statements == [
			Log4jStatement.getInstance(Level.DEBUG, "debug statement"),
			Log4jStatement.getInstance(Level.INFO, "info statement"),
			Log4jStatement.getInstance(Level.WARN, "warn statement"),
			Log4jStatement.getInstance(Level.ERROR, "error statement"),
			Log4jStatement.getInstance(Level.FATAL, "fatal statement")
		]

	}

	def "getInfoInstance should log statements at INFO, WARN, ERROR, and FATAL levels"() {
		given:
		def log4jMonitor = Log4jMonitor.infoInstance

		and:
		def logger = Logger.getLogger(Log4jMonitor.class)

		when: "DEBUG, INFO, WARN, ERROR, and FATAL statements are logged"
		logger.debug("debug statement")
		logger.info("info statement")
		logger.warn("warn statement")
		logger.error("error statement")
		logger.fatal("fatal statement")

		then:
		log4jMonitor.statementCount == 4

		and:
		log4jMonitor.statements == [
			Log4jStatement.getInstance(Level.INFO, "info statement"),
			Log4jStatement.getInstance(Level.WARN, "warn statement"),
			Log4jStatement.getInstance(Level.ERROR, "error statement"),
			Log4jStatement.getInstance(Level.FATAL, "fatal statement")
		]

	}

	def "getWarnInstance should only log statements at WARN, ERROR, and FATAL levels"() {
		given:
		def log4jMonitor = Log4jMonitor.warnInstance

		and:
		def logger = Logger.getLogger(Log4jMonitor.class)

		when: "DEBUG, INFO, WARN, ERROR, and FATAL statements are logged"
		logger.debug("debug statement")
		logger.info("info statement")
		logger.warn("warn statement")
		logger.error("error statement")
		logger.fatal("fatal statement")

		then:
		log4jMonitor.statementCount == 3

		and:
		log4jMonitor.statements == [
			Log4jStatement.getInstance(Level.WARN, "warn statement"),
			Log4jStatement.getInstance(Level.ERROR, "error statement"),
			Log4jStatement.getInstance(Level.FATAL, "fatal statement")
		]

	}

	def "getErrorInstance should only log statements at ERROR and FATAL levels"() {
		given:
		def log4jMonitor = Log4jMonitor.errorInstance

		and:
		def logger = Logger.getLogger(Log4jMonitor.class)

		when: "DEBUG, INFO, WARN, ERROR, and FATAL statements are logged"
		logger.debug("debug statement")
		logger.info("info statement")
		logger.warn("warn statement")
		logger.error("error statement")
		logger.fatal("fatal statement")

		then:
		log4jMonitor.statementCount == 2

		and:
		log4jMonitor.statements == [
			Log4jStatement.getInstance(Level.ERROR, "error statement"),
			Log4jStatement.getInstance(Level.FATAL, "fatal statement")
		]

	}

	def "getFatalInstance should only log statements at FATAL levels"() {
		given:
		def log4jMonitor = Log4jMonitor.fatalInstance

		and:
		def logger = Logger.getLogger(Log4jMonitor.class)

		when: "DEBUG, INFO, WARN, ERROR, and FATAL statements are logged"
		logger.debug("debug statement")
		logger.info("info statement")
		logger.warn("warn statement")
		logger.error("error statement")
		logger.fatal("fatal statement")

		then:
		log4jMonitor.statementCount == 1

		and:
		log4jMonitor.statements == [
			Log4jStatement.getInstance(Level.FATAL, "fatal statement")
		]

	}

    def "getStatements should delimit on newLine concatenated with levels"() {
        given:
        def log4jMonitor = Log4jMonitor.debugInstance

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        when: 
        def debugStatement = String.format(
                "debug statement%swith embedded%snewlines",
                Log4jMonitor.LINE_SEPARATOR, Log4jMonitor.LINE_SEPARATOR)
        def infoStatement = String.format(
                "info statement%swith embedded%snewlines",
                Log4jMonitor.LINE_SEPARATOR, Log4jMonitor.LINE_SEPARATOR)
        def warnStatement = String.format(
                "warn statement%swith embedded%snewlines",
                Log4jMonitor.LINE_SEPARATOR, Log4jMonitor.LINE_SEPARATOR)
        def errorStatement = String.format(
                "error statement%swith embedded%snewlines",
                Log4jMonitor.LINE_SEPARATOR, Log4jMonitor.LINE_SEPARATOR)
        def fatalStatement = String.format(
                "fatal statement%swith embedded%snewlines",
                Log4jMonitor.LINE_SEPARATOR, Log4jMonitor.LINE_SEPARATOR)

        logger.debug(debugStatement)
        logger.info(infoStatement)
        logger.warn(warnStatement)
        logger.error(errorStatement)
        logger.fatal(fatalStatement)

        then:
        log4jMonitor.statementCount == 5

		and:
		log4jMonitor.statements == [
			Log4jStatement.getInstance(Level.DEBUG, debugStatement),
			Log4jStatement.getInstance(Level.INFO, infoStatement),
			Log4jStatement.getInstance(Level.WARN, warnStatement),
			Log4jStatement.getInstance(Level.ERROR, errorStatement),
			Log4jStatement.getInstance(Level.FATAL, fatalStatement)
		]

    }

    def "dumpToStdError should write all messages to StdError"() {
        given:
        def log4jMonitor = Log4jMonitor.getDebugInstance()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement")

        and:
        def byteArrayOutputStream = new ByteArrayOutputStream()
        def printStream = new PrintStream(byteArrayOutputStream)
        System.setErr(printStream)

        when:
        log4jMonitor.dumpToStdError()

        then:
        def statements = new String(byteArrayOutputStream.toByteArray())
                .split(Log4jMonitor.LINE_SEPARATOR)
	
		statements == [
			"com.stupidplebs.log4jmonitor.Log4jStatement" + 
				"[level=DEBUG,statement=debug statement]",
			"com.stupidplebs.log4jmonitor.Log4jStatement" +
				"[level=INFO,statement=info statement]",
			"com.stupidplebs.log4jmonitor.Log4jStatement" +
				"[level=WARN,statement=warn statement]",
			"com.stupidplebs.log4jmonitor.Log4jStatement" +
				"[level=ERROR,statement=error statement]",
			"com.stupidplebs.log4jmonitor.Log4jStatement" +
				"[level=FATAL,statement=fatal statement]",
		]
				
    }

    def "dumpToStdError should write only messages of supplied level to StdError"() {
        given:
        def log4jMonitor = Log4jMonitor.getDebugInstance()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement")

        and:
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
        PrintStream printStream = new PrintStream(byteArrayOutputStream)
        System.setErr(printStream)

        when:
        log4jMonitor.dumpToStdError(Level.WARN)

        then:
        def statements = new String(byteArrayOutputStream.toByteArray())
                .split(Log4jMonitor.LINE_SEPARATOR)
        statements == ["com.stupidplebs.log4jmonitor.Log4jStatement" + 
			"[level=WARN,statement=warn statement]"]

    }

    def "dumpToOutputStream should write all messages to outputStream"() {
        given:
        def log4jMonitor = Log4jMonitor.getDebugInstance()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement")

        and:
        def byteArrayOutputStream = new ByteArrayOutputStream()

        when:
        log4jMonitor.dumpToOutputStream(byteArrayOutputStream)

        then:
        def statements = new String(byteArrayOutputStream.toByteArray()).split(Log4jMonitor.LINE_SEPARATOR)
        
		statements == [
			"com.stupidplebs.log4jmonitor.Log4jStatement" + 
				"[level=DEBUG,statement=debug statement]",
			"com.stupidplebs.log4jmonitor.Log4jStatement" +
				"[level=INFO,statement=info statement]",
			"com.stupidplebs.log4jmonitor.Log4jStatement" +
				"[level=WARN,statement=warn statement]",
			"com.stupidplebs.log4jmonitor.Log4jStatement" +
				"[level=ERROR,statement=error statement]",
			"com.stupidplebs.log4jmonitor.Log4jStatement" +
				"[level=FATAL,statement=fatal statement]",
		]

    }

    def "dumpToOutputStream with broken outputStream should rethrow IOException"() {
        given:
        def log4jMonitor = Log4jMonitor.getDebugInstance()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        // And statements are logged
        logger.debug("debug statement")

        // And a broken OutputStream
        def brokenOutputStream = new OutputStream() {
            @Override
            void write(int b) throws IOException {
                throw new IOException("purposeful fail")
            }
            
        }
        
        when:
        log4jMonitor.dumpToOutputStream(brokenOutputStream)

        then:
		thrown(IOException)
        
    }
    
    def "getStatementCount should return the number of statements logged at the specified level"() {
        given:
        def log4jMonitor = Log4jMonitor.getDebugInstance()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        when:
        logger.debug("debug statement 1")
        logger.info("info statement 1")
        logger.info("info statement 2")
        logger.warn("warn statement 1")
        logger.warn("warn statement 2")
        logger.warn("warn statement 3")
        logger.error("error statement 1")
        logger.error("error statement 2")
        logger.error("error statement 3")
        logger.error("error statement 4")
        logger.fatal("fatal statement 1")
        logger.fatal("fatal statement 2")
        logger.fatal("fatal statement 3")
        logger.fatal("fatal statement 4")
        logger.fatal("fatal statement 5")

        then:
        log4jMonitor.getStatementCount(Level.DEBUG) == 1
        log4jMonitor.hasStatements(Level.DEBUG)

        and:
        log4jMonitor.getStatementCount(Level.INFO) == 2
        log4jMonitor.hasStatements(Level.INFO)

        and:
        log4jMonitor.getStatementCount(Level.WARN) == 3
        log4jMonitor.hasStatements(Level.WARN)

        and:
        log4jMonitor.getStatementCount(Level.ERROR) == 4
        log4jMonitor.hasStatements(Level.ERROR)

        and:
        log4jMonitor.getStatementCount(Level.FATAL) == 5
        log4jMonitor.hasStatements(Level.FATAL)

    }

    def "isDebugStatement should return true if a statement was logged at DEBUG level"() {
        given:
        def log4jMonitor = Log4jMonitor.getDebugInstance()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        when:
        logger.debug("this is a debug statement")

        then:
        log4jMonitor.isDebugStatement("this is a debug statement")
        log4jMonitor.isStatement(Level.DEBUG, "this is a debug statement")

    }

    def "isDebugStatement should return false if a statement was not logged at DEBUG level"() {
        given:
        def log4jMonitor = Log4jMonitor.getDebugInstance()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        when:
        logger.debug("this is a debug statement")

        then:
        !log4jMonitor.isDebugStatement("this is a different debug statement")
        !log4jMonitor.isStatement(Level.DEBUG, "this is a different debug statement")

    }

    def "isInfoStatementShouldReturnTrueIfAStatementWasLoggedAtInfoLevel"() {
        given:
        def log4jMonitor = Log4jMonitor.getInfoInstance()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        when:
        logger.info("this is a info statement")

        then:
        log4jMonitor.isInfoStatement("this is a info statement")
        log4jMonitor.isStatement(Level.INFO, "this is a info statement")

    }

    def "isInfoStatement should return false if a statement was not logged at INFO level"() {
        given:
        def log4jMonitor = Log4jMonitor.getInfoInstance()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        when:
        logger.info("this is a info statement")

        then:
        !log4jMonitor.isInfoStatement("this is a different info statement")
        !log4jMonitor.isStatement(Level.INFO, "this is a different info statement")

    }

    def "isWarnStatementShouldReturnTrueIfAStatementWasLoggedAtWarnLevel"() {
        given:
        def log4jMonitor = Log4jMonitor.getWarnInstance()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        when:
        logger.warn("this is a warn statement")

        then:
        log4jMonitor.isWarnStatement("this is a warn statement")
        log4jMonitor.isStatement(Level.WARN, "this is a warn statement")

    }

    def "isWarnStatement should return false if a statement was not logged at WARN level"() {
        given:
        def log4jMonitor = Log4jMonitor.getWarnInstance()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        when:
        logger.warn("this is a warn statement")

        then:
        !log4jMonitor.isWarnStatement("this is a different warn statement")
        !log4jMonitor.isStatement(Level.WARN, "this is a different warn statement")

    }

    def "isErrorStatementShouldReturnTrueIfAStatementWasLoggedAtErrorLevel"() {
        given:
        def log4jMonitor = Log4jMonitor.getErrorInstance()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        when:
        logger.error("this is an error statement")

        then:
        log4jMonitor.isErrorStatement("this is an error statement")
        log4jMonitor.isStatement(Level.ERROR, "this is an error statement")

    }

    def "isErrorStatement should return false if a statement was not logged at ERROR level"() {
        given:
        def log4jMonitor = Log4jMonitor.getErrorInstance()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        when:
        logger.error("this is a error statement")

        then:
        !log4jMonitor.isErrorStatement("this is a different error statement")
        !log4jMonitor.isStatement(Level.ERROR, "this is a different error statement")

    }

    def "isFatalStatementShouldReturnTrueIfAStatementWasLoggedAtFatalLevel"() {
        given:
        def log4jMonitor = Log4jMonitor.getFatalInstance()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        when:
        logger.fatal("this is a fatal statement")

        then:
        log4jMonitor.isFatalStatement("this is a fatal statement")
        log4jMonitor.isStatement(Level.FATAL, "this is a fatal statement")

    }

    def "isFatalStatement should return false if a statement was not logged at FATAL level"() {
        given:
        def log4jMonitor = Log4jMonitor.getFatalInstance()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        when:
        logger.fatal("this is a fatal statement")

        then:
        !log4jMonitor.isFatalStatement("this is a different fatal statement")
        !log4jMonitor.isStatement(Level.FATAL, "this is a different fatal statement")

    }

    def "null level parameter to level-specific getStatements should return an empty unmodifiable list"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement")

        and:
        def level = null

        and:
        def pattern = Pattern.compile(".*")

        when:
        def statements = log4jMonitor.getStatements(level, pattern)

        then:
        statements == []

        when:
        statements.clear()
		
		then:
		thrown(UnsupportedOperationException)

    }

    def "null pattern parameter to level-specific getStatements should return an empty unmodifiable list"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement")

        when:
        def statements = log4jMonitor.getStatements(Level.DEBUG,
                (Pattern)null)

        then:
        statements == []

        when:
        statements.clear()
		
		then:
		thrown(UnsupportedOperationException)

    }

    def "only statements matching pattern and level should be returned in an unmodifiable list"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement")
        logger.info("info statement")
        logger.warn("warn statement 1")
        logger.warn("warn statement 2")
        logger.warn("warn statement 3")
        logger.error("error statement")
        logger.fatal("fatal statement")

        // And a Pattern that only matches warn statements 1 and 3
        def pattern = Pattern.compile("[a-z]+ statement [13]")

        when:
        def statements = log4jMonitor.getStatements(Level.WARN,
                pattern)

        then:
        statements == ["warn statement 1", "warn statement 3"]

        when:
        statements.clear()
		
		then:
		thrown(UnsupportedOperationException)

    }

    def "nullLevelParameterToLevelSpecificRawPatternGetStatementsShouldReturnAnEmptyUnmodifiableList"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement")

        and:
        def level = null

        and:
        def rawPattern = ".*"

        when:
        def statements = log4jMonitor.getStatements(level, rawPattern)

        then:
        statements == []

        when:
        statements.clear()
		
		then:
		thrown(UnsupportedOperationException)

    }

    def "nullRawPatternParameterToLevelSpecificRawPatternGetStatementsShouldReturnAnEmptyUnmodifiableList"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement")

        when:
        def statements = log4jMonitor.getStatements(Level.DEBUG,
                (Pattern)null)

        then:
        statements == []

        when:
        statements.clear()
		
		then:
		thrown(UnsupportedOperationException)

    }

    def "rawPatternNotCompileableAsPatternShouldThrow"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def rawPattern = "["

        when:
        log4jMonitor.getStatements(Level.WARN, rawPattern)

        then:
		thrown(PatternSyntaxException)

    }

    def "onlyStatementsMatchingRawPatternAndLevelShouldBeReturnedInAnUnmodifiableList"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement")
        logger.info("info statement")
        logger.warn("warn statement 1")
        logger.warn("warn statement 2")
        logger.warn("warn statement 3")
        logger.error("error statement")
        logger.fatal("fatal statement")

        and: "a regex that only matches warn statements 1 and 3"
        def rawPattern = "[a-z]+ statement [13]"

        when:
        def statements = log4jMonitor.getStatements(Level.WARN,
                rawPattern)

        then:
        statements == ["warn statement 1", "warn statement 3"]

        when:
        statements.clear()

		then:
		thrown(UnsupportedOperationException)
		
    }

    def "isDebugStatementShouldReturnTrueIfPatternMatchesAtLeastOneDEBUGLevelStatement"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement 1")
        logger.debug("debug statement 2")
        logger.debug("debug statement 3")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement")

        and: "a regex that only matches statements suffixed with 1 or 3"
        def pattern = Pattern.compile("[a-z]+ statement [13]")

		expect:
        log4jMonitor.isDebugStatement(pattern)
        
    }
    
    def "isInfoStatementShouldReturnTrueIfPatternMatchesAtLeastOneINFOLevelStatement"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement")
        logger.info("info statement 1")
        logger.info("info statement 2")
        logger.info("info statement 3")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement")

        and: "a regex that only matches statements suffixed with 1 or 3"
        def pattern = Pattern.compile("[a-z]+ statement [13]")

		expect:
        log4jMonitor.isInfoStatement(pattern)
        
    }
    
    def "isWarnStatementShouldReturnTrueIfPatternMatchesAtLeastOneWARNLevelStatement"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement")
        logger.info("info statement")
        logger.warn("warn statement 1")
        logger.warn("warn statement 2")
        logger.warn("warn statement 3")
        logger.error("error statement")
        logger.fatal("fatal statement")

        and: "a regex that only matches statements suffixed with 1 or 3"
        def pattern = Pattern.compile("[a-z]+ statement [13]")

		expect:
        log4jMonitor.isWarnStatement(pattern)
        
    }
    
    def "isErrorStatementShouldReturnTrueIfPatternMatchesAtLeastOneERRORLevelStatement"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.error("debug statement")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement 1")
        logger.error("error statement 2")
        logger.error("error statement 3")
        logger.fatal("fatal statement")

        and: "a regex that only matches statements suffixed with 1 or 3"
        def pattern = Pattern.compile("[a-z]+ statement [13]")

		expect:
        log4jMonitor.isErrorStatement(pattern)
        
    }
    
    def "isFatalStatementShouldReturnTrueIfPatternMatchesAtLeastOneFATALLevelStatement"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.fatal("debug statement")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement 1")
        logger.fatal("fatal statement 2")
        logger.fatal("fatal statement 3")

        and: "a regex that only matches statements suffixed with 1 or 3"
        def pattern = Pattern.compile("[a-z]+ statement [13]")

		expect:
        log4jMonitor.isFatalStatement(pattern)
        
    }
    
    def "isDebugStatementShouldReturnFalseIfPatternDoesNotMatchAnyDEBUGLevelStatements"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement")

        and: "a regex that doesn't match anything"
        def pattern = Pattern.compile("pattern that doesn't match anything")

		expect:
        !log4jMonitor.isDebugStatement(pattern)
        
    }
    
    def "isInfoStatementShouldReturnFalseIfPatternDoesNotMatchAnyINFOLevelStatements"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement")

        and: "a regex that doesn't match anything"
        def pattern = Pattern.compile("pattern that doesn't match anything")

		expect:
        !log4jMonitor.isInfoStatement(pattern)
        
    }
    
    def "isWarnStatementShouldReturnFalseIfPatternDoesNotMatchAnyWARNLevelStatements"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement")

        and: "a regex that doesn't match anything"
        def pattern = Pattern.compile("pattern that doesn't match anything")

		expect:
        !log4jMonitor.isWarnStatement(pattern)
        
    }
    
    def "isErrorStatementShouldReturnFalseIfPatternDoesNotMatchAnyERRORLevelStatements"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.error("debug statement")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement")

        and: "a regex that doesn't match anything"
        def pattern = Pattern.compile("pattern that doesn't match anything")

		expect:
        !log4jMonitor.isErrorStatement(pattern)
        
    }
    
    def "isFatalStatementShouldReturnFalseIfPatternDoesNotMatchAnyFATALLevelStatements"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.fatal("debug statement")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement")

        and: "a regex that doesn't match anything"
        def pattern = Pattern.compile("pattern that doesn't match anything")

		expect:
        !log4jMonitor.isFatalStatement(pattern)
        
    }
    
    def "isStatementShouldReturnTrueIfAtLeastOneStatementMatchesThePattern"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.fatal("debug statement")
        logger.info("info statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("fatal statement")

		expect:
		log4jMonitor.isStatement(Pattern.compile("d.bug statement"))
		log4jMonitor.isStatement(Pattern.compile("i.fo statement"))
		log4jMonitor.isStatement(Pattern.compile("w.rn statement"))
		log4jMonitor.isStatement(Pattern.compile("e.ror statement"))
		log4jMonitor.isStatement(Pattern.compile("f.tal statement"))
		!log4jMonitor.isStatement(Pattern.compile("doesn't match any statements"))

    }
    
    def "isStatementShouldReturnTrueIfAtLeastOneStatementEqualsTheInputStatement"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.info("info statement")
        logger.fatal("fatal statement")
        logger.warn("warn statement")
        logger.error("error statement")
        logger.fatal("debug statement")

		expect:
        log4jMonitor.isStatement(Pattern.compile("debug statement"))
        log4jMonitor.isStatement(Pattern.compile("info statement"))
        log4jMonitor.isStatement(Pattern.compile("warn statement"))
        log4jMonitor.isStatement(Pattern.compile("error statement"))
        log4jMonitor.isStatement(Pattern.compile("fatal statement"))
        !log4jMonitor.isStatement(Pattern.compile("unknown statement"))
        
    }
    
}
