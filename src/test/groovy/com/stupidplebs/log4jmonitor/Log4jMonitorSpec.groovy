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

    def "getLevel should return the level supplied to constructor"() {
        given:
        def log4jMonitor = new Log4jMonitor(inputLevel)

        when: 
        def actualLevel = log4jMonitor.level

        then:
        actualLevel == inputLevel

        where:
        inputLevel << [Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR, Level.FATAL]
        
    }

    def "zero-parameter constructor should return DEBUG for getLevel"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        expect:
        log4jMonitor.level == Level.DEBUG

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

        and:
        log4jMonitor.getStatementCount(Level.INFO) == 2

        and:
        log4jMonitor.getStatementCount(Level.WARN) == 3

        and:
        log4jMonitor.getStatementCount(Level.ERROR) == 4

        and:
        log4jMonitor.getStatementCount(Level.FATAL) == 5

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

    def "isInfoStatement should return true if a statement was logged at INFO level"() {
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

    def "isWarnStatement should return true if a statement was logged at WARN level"() {
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

    def "isErrorStatement should return true if a statement was logged at ERROR level"() {
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

    def "null level parameter to level-specific raw pattern getStatements should return an empty unmodifiable list"() {
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

    def "null raw pattern parameter to level-specific raw pattern getStatements should return an empty unmodifiable list"() {
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
                (String)null)

        then:
        statements == []

        when:
        statements.clear()
        
        then:
        thrown(UnsupportedOperationException)

    }

    def "raw pattern not compileable as pattern should throw root exception"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def rawPattern = "["

        when:
        log4jMonitor.getStatements(Level.WARN, rawPattern)

        then:
        thrown(PatternSyntaxException)

    }

    def "only statements matching raw pattern and level should be returned in an unmodifiable list"() {
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

    def "isDebugStatement should return true if pattern matches at least one FATAL-level statement"() {
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
    
    def "isInfoStatement should return true if pattern matches at least one INFO-level statement"() {
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
    
    def "isWarnStatement should return true if pattern matches at least one WARN-level statement"() {
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
    
    def "isErrorStatement should return true if pattern matches at least one ERROR-level statement"() {
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
    
    def "isFatalStatement should return true if pattern matches at least one FATAL-level statement"() {
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
    
    def "isDebugStatement should return false if pattern does not match any DEBUG-level statements"() {
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
    
    def "isInfoStatement should return false if pattern does not match any INFO-level statements"() {
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
    
    def "isWarnStatement should return false if pattern does not match any WARN-level statements"() {
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
    
    def "isErrorStatement should return false if pattern does not match any ERROR-level statements"() {
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
    
    def "isFatalStatement should return false if pattern does not match any FATAL-level statements"() {
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
    
    def "isStatement should return true if at least one statement matches the pattern"() {
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
    
    def "isStatement should return true if at least one statement equals the input statement"() {
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
        log4jMonitor.isStatement("debug statement")
        log4jMonitor.isStatement("info statement")
        log4jMonitor.isStatement("warn statement")
        log4jMonitor.isStatement("error statement")
        log4jMonitor.isStatement("fatal statement")
        !log4jMonitor.isStatement("unknown statement")
        
    }
    
    def "getDebugStatements should only return DEBUG-level statements"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement 1")
        logger.info("info statement 1")
        logger.warn("warn statement 1")
        logger.error("error statement 1")
        logger.fatal("fatal statement 1")
        logger.debug("debug statement 2")
        logger.info("info statement 2")
        logger.warn("warn statement 2")
        logger.error("error statement 2")
        logger.fatal("fatal statement 2")

        expect:
        log4jMonitor.debugStatements == ["debug statement 1", "debug statement 2"]
        
    }
    
    def "getInfoStatements should only return INFO-level statements"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement 1")
        logger.info("info statement 1")
        logger.warn("warn statement 1")
        logger.error("error statement 1")
        logger.fatal("fatal statement 1")
        logger.debug("debug statement 2")
        logger.info("info statement 2")
        logger.warn("warn statement 2")
        logger.error("error statement 2")
        logger.fatal("fatal statement 2")

        expect:
        log4jMonitor.infoStatements == ["info statement 1", "info statement 2"]
        
    }
    
    def "getWarnStatements should only return WARN-level statements"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement 1")
        logger.info("info statement 1")
        logger.warn("warn statement 1")
        logger.error("error statement 1")
        logger.fatal("fatal statement 1")
        logger.debug("debug statement 2")
        logger.info("info statement 2")
        logger.warn("warn statement 2")
        logger.error("error statement 2")
        logger.fatal("fatal statement 2")

        expect:
        log4jMonitor.warnStatements == ["warn statement 1", "warn statement 2"]
        
    }
    
    def "getErrorStatements should only return ERROR-level statements"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement 1")
        logger.info("info statement 1")
        logger.warn("warn statement 1")
        logger.error("error statement 1")
        logger.fatal("fatal statement 1")
        logger.debug("debug statement 2")
        logger.info("info statement 2")
        logger.warn("warn statement 2")
        logger.error("error statement 2")
        logger.fatal("fatal statement 2")

        expect:
        log4jMonitor.errorStatements == ["error statement 1", "error statement 2"]
        
    }
    
    def "getFatalStatements should only return FATAL-level statements"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        and:
        logger.debug("debug statement 1")
        logger.info("info statement 1")
        logger.warn("warn statement 1")
        logger.error("error statement 1")
        logger.fatal("fatal statement 1")
        logger.debug("debug statement 2")
        logger.info("info statement 2")
        logger.warn("warn statement 2")
        logger.error("error statement 2")
        logger.fatal("fatal statement 2")

        expect:
        log4jMonitor.fatalStatements == ["fatal statement 1", "fatal statement 2"]
        
    }
    
    def "hasStatements should return false if there are no statements at the supplied level"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)

        when:
        def hasStatements = log4jMonitor.hasStatements(level)
        
        then:
        !hasStatements
        
        where:
        level << [Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR, Level.FATAL]

    }
    
    def "hasStatements should return true if there is at least one statement at the supplied level"() {
        given:
        def log4jMonitor = new Log4jMonitor()

        and:
        def logger = Logger.getLogger(Log4jMonitor.class)
        logger.log(level, "statement")

        when:
        def hasStatements = log4jMonitor.hasStatements(level)
        
        then:
        hasStatements
        
        where:
        level << [Level.DEBUG, Level.INFO, Level.WARN, Level.ERROR, Level.FATAL]

    }
    
}
