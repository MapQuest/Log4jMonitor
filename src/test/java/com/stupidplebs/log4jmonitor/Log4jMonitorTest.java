package com.stupidplebs.log4jmonitor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

public class Log4jMonitorTest {
    @Test(expected = IllegalArgumentException.class)
    public void nullLevelConstructorParameterShouldThrowIllegalArgumentException() {
        // Given a null Level
        Level level = null;

        // When the single parameter constructor is called
        new Log4jMonitor(level);

        // Then an IllegalArgumentException should be thrown

    }

    @Test
    public void zeroParameterConstructorShouldLogDebugAndInfoAndWarnAndErrorAndFatalStatements() {
        // Given a zero-parameter Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When DEBUG, INFO, WARN, ERROR, and FATAL statements are logged
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // Then 5 statements should have been logged
        assertThat(log4jMonitor.getStatementCount(), is(5));

        // And the statements should be as logged in order
        List<Log4jStatement> statements = log4jMonitor.getStatements();
        assertThat(statements.get(0), is(Log4jStatement.getInstance(Level.DEBUG, "debug statement")));
        assertThat(statements.get(1), is(Log4jStatement.getInstance(Level.INFO, "info statement")));
        assertThat(statements.get(2), is(Log4jStatement.getInstance(Level.WARN, "warn statement")));
        assertThat(statements.get(3), is(Log4jStatement.getInstance(Level.ERROR, "error statement")));
        assertThat(statements.get(4), is(Log4jStatement.getInstance(Level.FATAL, "fatal statement")));

    }

    @Test
    public void getDebugInstanceShouldLogDebugAndInfoAndWarnAndErrorAndFatalStatements() {
        // Given a Log4jMonitor instance returned by getDebugInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getDebugInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When DEBUG, INFO, WARN, ERROR, and FATAL statements are logged
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // Then 5 statements should have been logged
        assertThat(log4jMonitor.getStatementCount(), is(5));

        // And the only the DEBUG, INFO, WARN, ERROR, and FATAL statements
        // should be as logged in order
        List<Log4jStatement> statements = log4jMonitor.getStatements();
        assertThat(statements.get(0), is(Log4jStatement.getInstance(Level.DEBUG, "debug statement")));
        assertThat(statements.get(1), is(Log4jStatement.getInstance(Level.INFO, "info statement")));
        assertThat(statements.get(2), is(Log4jStatement.getInstance(Level.WARN, "warn statement")));
        assertThat(statements.get(3), is(Log4jStatement.getInstance(Level.ERROR, "error statement")));
        assertThat(statements.get(4), is(Log4jStatement.getInstance(Level.FATAL, "fatal statement")));

    }

    @Test
    public void getInfoInstanceShouldOnlyLogInfoAndWarnAndErrorAndFatalStatements() {
        // Given a Log4jMonitor instance returned by getInfoInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getInfoInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When DEBUG, INFO, WARN, ERROR, and FATAL statements are logged
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // Then 4 statements should have been logged
        assertThat(log4jMonitor.getStatementCount(), is(4));

        // And the only the INFO, WARN, ERROR, and FATAL statements should be as
        // logged in order
        List<Log4jStatement> statements = log4jMonitor.getStatements();
        assertThat(statements.get(0), is(Log4jStatement.getInstance(Level.INFO, "info statement")));
        assertThat(statements.get(1), is(Log4jStatement.getInstance(Level.WARN, "warn statement")));
        assertThat(statements.get(2), is(Log4jStatement.getInstance(Level.ERROR, "error statement")));
        assertThat(statements.get(3), is(Log4jStatement.getInstance(Level.FATAL, "fatal statement")));

    }

    @Test
    public void getWarnInstanceShouldOnlyLogWarnAndErrorAndFatalStatements() {
        // Given a Log4jMonitor instance returned by getWarnInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getWarnInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When DEBUG, INFO, WARN, ERROR, and FATAL statements are logged
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // Then 3 statements should have been logged
        assertThat(log4jMonitor.getStatementCount(), is(3));

        // And the only the WARN, ERROR, and FATAL statements should be as
        // logged in order
        List<Log4jStatement> statements = log4jMonitor.getStatements();
        assertThat(statements.get(0), is(Log4jStatement.getInstance(Level.WARN, "warn statement")));
        assertThat(statements.get(1), is(Log4jStatement.getInstance(Level.ERROR, "error statement")));
        assertThat(statements.get(2), is(Log4jStatement.getInstance(Level.FATAL, "fatal statement")));

    }

    @Test
    public void getErrorInstanceShouldOnlyLogErrorAndFatalStatements() {
        // Given a Log4jMonitor instance returned by getErrorInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getErrorInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When DEBUG, INFO, WARN, ERROR, and FATAL statements are logged
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // Then 2 statements should have been logged
        assertThat(log4jMonitor.getStatementCount(), is(2));

        // And the only the ERROR and FATAL statements should be as logged in
        // order
        List<Log4jStatement> statements = log4jMonitor.getStatements();
        assertThat(statements.get(0), is(Log4jStatement.getInstance(Level.ERROR, "error statement")));
        assertThat(statements.get(1), is(Log4jStatement.getInstance(Level.FATAL, "fatal statement")));

    }

    @Test
    public void getFatalInstanceShouldOnlyLogFatalStatements() {
        // Given a Log4jMonitor instance returned by getFatalInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getFatalInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When DEBUG, INFO, WARN, ERROR, and FATAL statements are logged
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // Then 1 statements should have been logged
        assertThat(log4jMonitor.getStatementCount(), is(1));

        // And the logged statement should be the FATAL statement
        List<Log4jStatement> statements = log4jMonitor.getStatements();
        assertThat(statements.get(0), is(Log4jStatement.getInstance(Level.FATAL, "fatal statement")));

    }

    @Test
    public void dumpToStdErrorShouldWriteAllMessagesToStdError() {
        // Given a Log4jMonitor instance returned by getDebugInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getDebugInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And statements are logged
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // And a ByteArrayOutputStream that will be written to by stderr
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setErr(printStream);

        // And the newline character
        String LINE_SEPARATOR = System.getProperty("line.separator");

        // When dumpToStdError is called
        log4jMonitor.dumpToStdError();

        // Then
        String[] statements = new String(byteArrayOutputStream.toByteArray())
                .split(LINE_SEPARATOR);
        assertThat(statements.length, is(5));
        assertThat(statements[0], is("com.stupidplebs.log4jmonitor.Log4jStatement" +
        		"[level=DEBUG,statement=debug statement]"));
        assertThat(statements[1], is("com.stupidplebs.log4jmonitor.Log4jStatement" +
        		"[level=INFO,statement=info statement]"));
        assertThat(statements[2], is("com.stupidplebs.log4jmonitor.Log4jStatement" +
        		"[level=WARN,statement=warn statement]"));
        assertThat(statements[3], is("com.stupidplebs.log4jmonitor.Log4jStatement" +
        		"[level=ERROR,statement=error statement]"));
        assertThat(statements[4], is("com.stupidplebs.log4jmonitor.Log4jStatement" +
        		"[level=FATAL,statement=fatal statement]"));

    }

    @Test
    public void getStatementCountShouldReturnTheNumberOfStatementsLoggedAtTheSpecifiedLevel() {
        // Given a Log4jMonitor instance returned by getDebugInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getDebugInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When statements are logged
        logger.debug("debug statement 1");
        logger.info("info statement 1");
        logger.info("info statement 2");
        logger.warn("warn statement 1");
        logger.warn("warn statement 2");
        logger.warn("warn statement 3");
        logger.error("error statement 1");
        logger.error("error statement 2");
        logger.error("error statement 3");
        logger.error("error statement 4");
        logger.fatal("fatal statement 1");
        logger.fatal("fatal statement 2");
        logger.fatal("fatal statement 3");
        logger.fatal("fatal statement 4");
        logger.fatal("fatal statement 5");

        // Then getStatementCount should return 1 for DEBUG level
        assertThat(log4jMonitor.getStatementCount(Level.DEBUG), is(1));

        // And hasStatements should return true for DEBUG level
        assertThat(log4jMonitor.hasStatements(Level.DEBUG), is(true));

        // And getStatementCount should return 2 for INFO level
        assertThat(log4jMonitor.getStatementCount(Level.INFO), is(2));

        // And hasStatements should return true for INFO level
        assertThat(log4jMonitor.hasStatements(Level.INFO), is(true));

        // And getStatementCount should return 3 for WARN level
        assertThat(log4jMonitor.getStatementCount(Level.WARN), is(3));

        // And hasStatements should return true for WARN level
        assertThat(log4jMonitor.hasStatements(Level.WARN), is(true));

        // And getStatementCount should return 4 for ERROR level
        assertThat(log4jMonitor.getStatementCount(Level.ERROR), is(4));

        // And hasStatements should return true for ERROR level
        assertThat(log4jMonitor.hasStatements(Level.ERROR), is(true));

        // And getStatementCount should return 5 for FATAL level
        assertThat(log4jMonitor.getStatementCount(Level.FATAL), is(5));

        // And hasStatements should return true for FATAL level
        assertThat(log4jMonitor.hasStatements(Level.FATAL), is(true));

    }

    @Test
    public void isDebugStatementShouldReturnTrueIfAStatementWasLoggedAtDebugLevel() {
        // Given a Log4jMonitor instance returned by getDebugInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getDebugInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When a DEBUG statements logged
        logger.debug("this is a debug statement");

        // Then isDebugStatement should true
        assertThat(log4jMonitor.isDebugStatement("this is a debug statement"),
                is(true));

        // And isStatement should return true
        assertThat(log4jMonitor.isStatement(Level.DEBUG,
                "this is a debug statement"), is(true));

    }

    @Test
    public void isDebugStatementShouldReturnFalseIfAStatementWasNotLoggedAtDebugLevel() {
        // Given a Log4jMonitor instance returned by getDebugInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getDebugInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When statements are logged
        logger.debug("this is a debug statement");

        // Then isDebugStatement should true
        assertThat(
                log4jMonitor
                        .isDebugStatement("this is a different debug statement"),
                is(false));

        // And isStatement should return false
        assertThat(log4jMonitor.isStatement(Level.DEBUG,
                "this is a different debug statement"), is(false));

    }

    @Test
    public void isInfoStatementShouldReturnTrueIfAStatementWasLoggedAtInfoLevel() {
        // Given a Log4jMonitor instance returned by getInfoInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getInfoInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When a INFO statements logged
        logger.info("this is a info statement");

        // Then isInfoStatement should true
        assertThat(log4jMonitor.isInfoStatement("this is a info statement"),
                is(true));

        // And isStatement should return true
        assertThat(log4jMonitor.isStatement(Level.INFO,
                "this is a info statement"), is(true));

    }

    @Test
    public void isInfoStatementShouldReturnFalseIfAStatementWasNotLoggedAtInfoLevel() {
        // Given a Log4jMonitor instance returned by getInfoInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getInfoInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When statements are logged
        logger.info("this is a info statement");

        // Then isInfoStatement should true
        assertThat(
                log4jMonitor
                        .isInfoStatement("this is a different info statement"),
                is(false));

        // And isStatement should return false
        assertThat(log4jMonitor.isStatement(Level.INFO,
                "this is a different info statement"), is(false));

    }

    @Test
    public void isWarnStatementShouldReturnTrueIfAStatementWasLoggedAtWarnLevel() {
        // Given a Log4jMonitor instance returned by getWarnInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getWarnInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When a WARN statements logged
        logger.warn("this is a warn statement");

        // Then isWarnStatement should true
        assertThat(log4jMonitor.isWarnStatement("this is a warn statement"),
                is(true));

        // And isStatement should return true
        assertThat(log4jMonitor.isStatement(Level.WARN,
                "this is a warn statement"), is(true));

    }

    @Test
    public void isWarnStatementShouldReturnFalseIfAStatementWasNotLoggedAtWarnLevel() {
        // Given a Log4jMonitor instance returned by getWarnInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getWarnInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When statements are logged
        logger.warn("this is a warn statement");

        // Then isWarnStatement should true
        assertThat(
                log4jMonitor
                        .isWarnStatement("this is a different warn statement"),
                is(false));

        // And isStatement should return false
        assertThat(log4jMonitor.isStatement(Level.WARN,
                "this is a different warn statement"), is(false));

    }

    @Test
    public void isErrorStatementShouldReturnTrueIfAStatementWasLoggedAtErrorLevel() {
        // Given a Log4jMonitor instance returned by getErrorInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getErrorInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When a ERROR statements logged
        logger.error("this is a error statement");

        // Then isErrorStatement should true
        assertThat(log4jMonitor.isErrorStatement("this is a error statement"),
                is(true));

        // And isStatement should return true
        assertThat(log4jMonitor.isStatement(Level.ERROR,
                "this is a error statement"), is(true));

    }

    @Test
    public void isErrorStatementShouldReturnFalseIfAStatementWasNotLoggedAtErrorLevel() {
        // Given a Log4jMonitor instance returned by getErrorInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getErrorInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When statements are logged
        logger.error("this is a error statement");

        // Then isErrorStatement should true
        assertThat(
                log4jMonitor
                        .isErrorStatement("this is a different error statement"),
                is(false));

        // And isStatement should return false
        assertThat(log4jMonitor.isStatement(Level.ERROR,
                "this is a different error statement"), is(false));

    }

    @Test
    public void isFatalStatementShouldReturnTrueIfAStatementWasLoggedAtFatalLevel() {
        // Given a Log4jMonitor instance returned by getFatalInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getFatalInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When a FATAL statements logged
        logger.fatal("this is a fatal statement");

        // Then isFatalStatement should true
        assertThat(log4jMonitor.isFatalStatement("this is a fatal statement"),
                is(true));

        // And isStatement should return true
        assertThat(log4jMonitor.isStatement(Level.FATAL,
                "this is a fatal statement"), is(true));

    }

    @Test
    public void isFatalStatementShouldReturnFalseIfAStatementWasNotLoggedAtFatalLevel() {
        // Given a Log4jMonitor instance returned by getFatalInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getFatalInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When statements are logged
        logger.fatal("this is a fatal statement");

        // Then isFatalStatement should true
        assertThat(
                log4jMonitor
                        .isFatalStatement("this is a different fatal statement"),
                is(false));

        // And isStatement should return false
        assertThat(log4jMonitor.isStatement(Level.FATAL,
                "this is a different fatal statement"), is(false));

    }

}
