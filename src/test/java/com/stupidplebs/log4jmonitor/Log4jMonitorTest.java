package com.stupidplebs.log4jmonitor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

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
        assertThat(statements.get(0),
                is(Log4jStatement.getInstance(Level.DEBUG, "debug statement")));
        assertThat(statements.get(1),
                is(Log4jStatement.getInstance(Level.INFO, "info statement")));
        assertThat(statements.get(2),
                is(Log4jStatement.getInstance(Level.WARN, "warn statement")));
        assertThat(statements.get(3),
                is(Log4jStatement.getInstance(Level.ERROR, "error statement")));
        assertThat(statements.get(4),
                is(Log4jStatement.getInstance(Level.FATAL, "fatal statement")));

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
        assertThat(statements.get(0),
                is(Log4jStatement.getInstance(Level.DEBUG, "debug statement")));
        assertThat(statements.get(1),
                is(Log4jStatement.getInstance(Level.INFO, "info statement")));
        assertThat(statements.get(2),
                is(Log4jStatement.getInstance(Level.WARN, "warn statement")));
        assertThat(statements.get(3),
                is(Log4jStatement.getInstance(Level.ERROR, "error statement")));
        assertThat(statements.get(4),
                is(Log4jStatement.getInstance(Level.FATAL, "fatal statement")));

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
        assertThat(statements.get(0),
                is(Log4jStatement.getInstance(Level.INFO, "info statement")));
        assertThat(statements.get(1),
                is(Log4jStatement.getInstance(Level.WARN, "warn statement")));
        assertThat(statements.get(2),
                is(Log4jStatement.getInstance(Level.ERROR, "error statement")));
        assertThat(statements.get(3),
                is(Log4jStatement.getInstance(Level.FATAL, "fatal statement")));

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
        assertThat(statements.get(0),
                is(Log4jStatement.getInstance(Level.WARN, "warn statement")));
        assertThat(statements.get(1),
                is(Log4jStatement.getInstance(Level.ERROR, "error statement")));
        assertThat(statements.get(2),
                is(Log4jStatement.getInstance(Level.FATAL, "fatal statement")));

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
        assertThat(statements.get(0),
                is(Log4jStatement.getInstance(Level.ERROR, "error statement")));
        assertThat(statements.get(1),
                is(Log4jStatement.getInstance(Level.FATAL, "fatal statement")));

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
        assertThat(statements.get(0),
                is(Log4jStatement.getInstance(Level.FATAL, "fatal statement")));

    }

    @Test
    public void getStatementsShouldDelimitOnNewLineConcatenatedWithLevels() {
        // Given a Log4jMonitor instance returned by getDebugInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getDebugInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // When DEBUG, INFO, WARN, ERROR, and FATAL statements are logged
        String debugStatement = String.format(
                "debug statement%swith embedded%snewlines",
                Log4jMonitor.LINE_SEPARATOR, Log4jMonitor.LINE_SEPARATOR);
        String infoStatement = String.format(
                "info statement%swith embedded%snewlines",
                Log4jMonitor.LINE_SEPARATOR, Log4jMonitor.LINE_SEPARATOR);
        String warnStatement = String.format(
                "warn statement%swith embedded%snewlines",
                Log4jMonitor.LINE_SEPARATOR, Log4jMonitor.LINE_SEPARATOR);
        String errorStatement = String.format(
                "error statement%swith embedded%snewlines",
                Log4jMonitor.LINE_SEPARATOR, Log4jMonitor.LINE_SEPARATOR);
        String fatalStatement = String.format(
                "fatal statement%swith embedded%snewlines",
                Log4jMonitor.LINE_SEPARATOR, Log4jMonitor.LINE_SEPARATOR);

        logger.debug(debugStatement);
        logger.info(infoStatement);
        logger.warn(warnStatement);
        logger.error(errorStatement);
        logger.fatal(fatalStatement);

        // Then 5 statements should have been logged
        assertThat(log4jMonitor.getStatementCount(), is(5));

        // And the only the DEBUG, INFO, WARN, ERROR, and FATAL statements
        // should be as logged in order
        List<Log4jStatement> statements = log4jMonitor.getStatements();
        assertThat(statements.get(0),
                is(Log4jStatement.getInstance(Level.DEBUG, debugStatement)));
        assertThat(statements.get(1),
                is(Log4jStatement.getInstance(Level.INFO, infoStatement)));
        assertThat(statements.get(2),
                is(Log4jStatement.getInstance(Level.WARN, warnStatement)));
        assertThat(statements.get(3),
                is(Log4jStatement.getInstance(Level.ERROR, errorStatement)));
        assertThat(statements.get(4),
                is(Log4jStatement.getInstance(Level.FATAL, fatalStatement)));

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

        // When dumpToStdError is called
        log4jMonitor.dumpToStdError();

        // Then all statements should have been dumped to STDERR
        String[] statements = new String(byteArrayOutputStream.toByteArray())
                .split(Log4jMonitor.LINE_SEPARATOR);
        assertThat(statements.length, is(5));
        assertThat(statements[0],
                is("com.stupidplebs.log4jmonitor.Log4jStatement"
                        + "[level=DEBUG,statement=debug statement]"));
        assertThat(statements[1],
                is("com.stupidplebs.log4jmonitor.Log4jStatement"
                        + "[level=INFO,statement=info statement]"));
        assertThat(statements[2],
                is("com.stupidplebs.log4jmonitor.Log4jStatement"
                        + "[level=WARN,statement=warn statement]"));
        assertThat(statements[3],
                is("com.stupidplebs.log4jmonitor.Log4jStatement"
                        + "[level=ERROR,statement=error statement]"));
        assertThat(statements[4],
                is("com.stupidplebs.log4jmonitor.Log4jStatement"
                        + "[level=FATAL,statement=fatal statement]"));

    }

    @Test
    public void dumpToStdErrorShouldWriteOnlyMessagesOfSuppliedLevelToStdError() {
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

        // When dumpToStdError is called
        log4jMonitor.dumpToStdError(Level.WARN);

        // Then all statements should have been dumped to STDERR
        String[] statements = new String(byteArrayOutputStream.toByteArray())
                .split(Log4jMonitor.LINE_SEPARATOR);
        assertThat(statements.length, is(1));
        assertThat(statements[0],
                is("com.stupidplebs.log4jmonitor.Log4jStatement"
                        + "[level=WARN,statement=warn statement]"));

    }

    @Test
    public void dumpToOutputStreamShouldWriteAllMessagesToOutputStream() throws IOException {
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

        // When dumpToOutputStream is called
        log4jMonitor.dumpToOutputStream(byteArrayOutputStream);

        // Then all statements should have been written to the OutputStream
        String[] statements = new String(byteArrayOutputStream.toByteArray()).split(Log4jMonitor.LINE_SEPARATOR);
        
        assertThat(statements.length, is(5));
        assertThat(statements[0],
                is("com.stupidplebs.log4jmonitor.Log4jStatement"
                        + "[level=DEBUG,statement=debug statement]"));
        assertThat(statements[1],
                is("com.stupidplebs.log4jmonitor.Log4jStatement"
                        + "[level=INFO,statement=info statement]"));
        assertThat(statements[2],
                is("com.stupidplebs.log4jmonitor.Log4jStatement"
                        + "[level=WARN,statement=warn statement]"));
        assertThat(statements[3],
                is("com.stupidplebs.log4jmonitor.Log4jStatement"
                        + "[level=ERROR,statement=error statement]"));
        assertThat(statements[4],
                is("com.stupidplebs.log4jmonitor.Log4jStatement"
                        + "[level=FATAL,statement=fatal statement]"));

    }

    @Test(expected=IOException.class)
    public void dumpToOutputStreamWithBrokenOutputStreamShouldRethrowIOException() 
    throws IOException {
        // Given a Log4jMonitor instance returned by getDebugInstance
        Log4jMonitor log4jMonitor = Log4jMonitor.getDebugInstance();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And statements are logged
        logger.debug("debug statement");

        // And a broken OutputStream
        OutputStream brokenOutputStream = new OutputStream() {

            @Override
            public void write(int b) throws IOException {
                throw new IOException("purposeful fail");
            }
            
        };
        
        // When dumpToOutputStream is called
        log4jMonitor.dumpToOutputStream(brokenOutputStream);

        // Then an IOException should be thrown
        
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

    @Test(expected = UnsupportedOperationException.class)
    public void nullLevelParameterToLevelSpecificGetStatementsShouldReturnAnEmptyUnmodifiableList() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // And a null Level
        Level level = null;

        // And a Pattern that matches everything
        Pattern pattern = Pattern.compile(".*");

        // When getStatements is called
        List<String> statements = log4jMonitor.getStatements(level, pattern);

        // Then an empty list should have been returned
        assertThat(statements.isEmpty(), is(true));

        // And attempting to modify that list should throw an
        // UnsupportedOperationException
        statements.clear();

    }

    @Test(expected = UnsupportedOperationException.class)
    public void nullPatternParameterToLevelSpecificGetStatementsShouldReturnAnEmptyUnmodifiableList() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // And a null Pattern
        Pattern pattern = null;

        // When getStatements is called
        List<String> statements = log4jMonitor.getStatements(Level.DEBUG,
                pattern);

        // Then an empty list should have been returned
        assertThat(statements.isEmpty(), is(true));

        // And attempting to modify that list should throw an
        // UnsupportedOperationException
        statements.clear();

    }

    @Test(expected = UnsupportedOperationException.class)
    public void onlyStatementsMatchingPatternAndLevelShouldBeReturnedInAnUnmodifiableList() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement 1");
        logger.warn("warn statement 2");
        logger.warn("warn statement 3");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // And a Pattern that only matches warn statements 1 and 3
        Pattern pattern = Pattern.compile("[a-z]+ statement [13]");

        // When getStatements is called
        List<String> statements = log4jMonitor.getStatements(Level.WARN,
                pattern);

        // Then a 2-element list should have been returned
        assertThat(statements.size(), is(2));

        // And the first statement should be "warn statement 1"
        assertThat(statements.get(0), is("warn statement 1"));

        // And the second statement should be "warn statement 3"
        assertThat(statements.get(1), is("warn statement 3"));

        // And attempting to modify that list should throw an
        // UnsupportedOperationException
        statements.clear();

    }

    @Test(expected = UnsupportedOperationException.class)
    public void nullLevelParameterToLevelSpecificRawPatternGetStatementsShouldReturnAnEmptyUnmodifiableList() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // And a null Level
        Level level = null;

        // And a pattern that matches everything
        String rawPattern = ".*";

        // When getStatements is called
        List<String> statements = log4jMonitor.getStatements(level, rawPattern);

        // Then an empty list should have been returned
        assertThat(statements.isEmpty(), is(true));

        // And attempting to modify that list should throw an
        // UnsupportedOperationException
        statements.clear();

    }

    @Test(expected = UnsupportedOperationException.class)
    public void nullRawPatternParameterToLevelSpecificRawPatternGetStatementsShouldReturnAnEmptyUnmodifiableList() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // And a null rawPattern
        String rawPattern = null;

        // When getStatements is called
        List<String> statements = log4jMonitor.getStatements(Level.DEBUG,
                rawPattern);

        // Then an empty list should have been returned
        assertThat(statements.isEmpty(), is(true));

        // And attempting to modify that list should throw an
        // UnsupportedOperationException
        statements.clear();

    }

    @Test(expected = PatternSyntaxException.class)
    public void rawPatternNotCompileableAsPatternShouldThrow() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a regex that only matches warn statements 1 and 3
        String rawPattern = "[";

        // When getStatements is called
        log4jMonitor.getStatements(Level.WARN, rawPattern);

        // Then a PatternSyntaxException should have been thrown

    }

    @Test(expected = UnsupportedOperationException.class)
    public void onlyStatementsMatchingRawPatternAndLevelShouldBeReturnedInAnUnmodifiableList() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement 1");
        logger.warn("warn statement 2");
        logger.warn("warn statement 3");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // And a regex that only matches warn statements 1 and 3
        String rawPattern = "[a-z]+ statement [13]";

        // When getStatements is called
        List<String> statements = log4jMonitor.getStatements(Level.WARN,
                rawPattern);

        // Then a 2-element list should have been returned
        assertThat(statements.size(), is(2));

        // And the first statement should be "warn statement 1"
        assertThat(statements.get(0), is("warn statement 1"));

        // And the second statement should be "warn statement 3"
        assertThat(statements.get(1), is("warn statement 3"));

        // And attempting to modify that list should throw an
        // UnsupportedOperationException
        statements.clear();

    }

    @Test
    public void isDebugStatementShouldReturnTrueIfPatternMatchesAtLeastOneDEBUGLevelStatement() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.debug("debug statement 1");
        logger.debug("debug statement 2");
        logger.debug("debug statement 3");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // And a regex that only matches statements suffixed with 1 or 3
        Pattern pattern = Pattern.compile("[a-z]+ statement [13]");

        // Expect isDebugStatement to return true since the pattern matches at least 1 DEBUG statement
        assertThat(log4jMonitor.isDebugStatement(pattern), is(true));
        
    }
    
    @Test
    public void isInfoStatementShouldReturnTrueIfPatternMatchesAtLeastOneINFOLevelStatement() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.debug("debug statement");
        logger.info("info statement 1");
        logger.info("info statement 2");
        logger.info("info statement 3");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // And a regex that only matches statements suffixed with 1 or 3
        Pattern pattern = Pattern.compile("[a-z]+ statement [13]");

        // Expect isDebugStatement to return true since the pattern matches at least 1 DEBUG statement
        assertThat(log4jMonitor.isInfoStatement(pattern), is(true));
        
    }
    
    @Test
    public void isWarnStatementShouldReturnTrueIfPatternMatchesAtLeastOneWARNLevelStatement() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement 1");
        logger.warn("warn statement 2");
        logger.warn("warn statement 3");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // And a regex that only matches statements suffixed with 1 or 3
        Pattern pattern = Pattern.compile("[a-z]+ statement [13]");

        // Expect isDebugStatement to return true since the pattern matches at least 1 DEBUG statement
        assertThat(log4jMonitor.isWarnStatement(pattern), is(true));
        
    }
    
    @Test
    public void isErrorStatementShouldReturnTrueIfPatternMatchesAtLeastOneERRORLevelStatement() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.error("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement 1");
        logger.error("error statement 2");
        logger.error("error statement 3");
        logger.fatal("fatal statement");

        // And a regex that only matches statements suffixed with 1 or 3
        Pattern pattern = Pattern.compile("[a-z]+ statement [13]");

        // Expect isDebugStatement to return true since the pattern matches at least 1 DEBUG statement
        assertThat(log4jMonitor.isErrorStatement(pattern), is(true));
        
    }
    
    @Test
    public void isFatalStatementShouldReturnTrueIfPatternMatchesAtLeastOneFATALLevelStatement() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.fatal("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement 1");
        logger.fatal("fatal statement 2");
        logger.fatal("fatal statement 3");

        // And a regex that only matches statements suffixed with 1 or 3
        Pattern pattern = Pattern.compile("[a-z]+ statement [13]");

        // Expect isDebugStatement to return true since the pattern matches at least 1 DEBUG statement
        assertThat(log4jMonitor.isFatalStatement(pattern), is(true));
        
    }
    
    @Test
    public void isDebugStatementShouldReturnFalseIfPatternDoesNotMatchAnyDEBUGLevelStatements() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // And a regex that doesn't match anything
        Pattern pattern = Pattern.compile("pattern that doesn't match anything");

        // Expect isDebugStatement to return false since the pattern doesn't match any DEBUG statements
        assertThat(log4jMonitor.isDebugStatement(pattern), is(false));
        
    }
    
    @Test
    public void isInfoStatementShouldReturnFalseIfPatternDoesNotMatchAnyINFOLevelStatements() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // And a regex that doesn't match anything
        Pattern pattern = Pattern.compile("pattern that doesn't match anything");

        // Expect isInfoStatement to return false since the pattern doesn't match any INFO statements
        assertThat(log4jMonitor.isInfoStatement(pattern), is(false));
        
    }
    
    @Test
    public void isWarnStatementShouldReturnFalseIfPatternDoesNotMatchAnyWARNLevelStatements() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.debug("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // And a regex that doesn't match anything
        Pattern pattern = Pattern.compile("pattern that doesn't match anything");

        // Expect isWarnStatement to return false since the pattern doesn't match any WARN statements
        assertThat(log4jMonitor.isWarnStatement(pattern), is(false));
        
    }
    
    @Test
    public void isErrorStatementShouldReturnFalseIfPatternDoesNotMatchAnyERRORLevelStatements() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.error("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // And a regex that doesn't match anything
        Pattern pattern = Pattern.compile("pattern that doesn't match anything");

        // Expect isErrorStatement to return false since the pattern doesn't match any ERROR statements
        assertThat(log4jMonitor.isErrorStatement(pattern), is(false));
        
    }
    
    @Test
    public void isFatalStatementShouldReturnFalseIfPatternDoesNotMatchAnyFATALLevelStatements() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.fatal("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // And a regex that doesn't match anything
        Pattern pattern = Pattern.compile("pattern that doesn't match anything");

        // Expect isFatalStatement to return false since the pattern doesn't match any FATAL statements
        assertThat(log4jMonitor.isFatalStatement(pattern), is(false));
        
    }
    
    @Test
    public void isStatementShouldReturnTrueIfAtLeastOneStatementMatchesThePattern() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.fatal("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // Expect isStatement to return true since the pattern matches the DEBUG statement
        assertThat(log4jMonitor.isStatement(Pattern.compile("d.bug statement")), is(true));

        // And isStatement to return true since the pattern matches the INFO statement
        assertThat(log4jMonitor.isStatement(Pattern.compile("i.fo statement")), is(true));

        // And isStatement to return true since the pattern matches the WARN statement
        assertThat(log4jMonitor.isStatement(Pattern.compile("w.rn statement")), is(true));

        // And isStatement to return true since the pattern matches the ERROR statement
        assertThat(log4jMonitor.isStatement(Pattern.compile("e.ror statement")), is(true));

        // And isStatement to return true since the pattern matches the FATAL statement
        assertThat(log4jMonitor.isStatement(Pattern.compile("f.tal statement")), is(true));
           
        // And isStatement to return false since the pattern doesn't match any statement
        assertThat(log4jMonitor.isStatement(Pattern.compile("doesn't match any statements")), is(false));
        
    }
    
    @Test
    public void isStatementShouldReturnTrueIfAtLeastOneStatementEqualsTheInputStatement() {
        // Given a Log4jMonitor instance
        Log4jMonitor log4jMonitor = new Log4jMonitor();

        // And a logger
        Logger logger = Logger.getLogger(Log4jMonitorTest.class);

        // And logged DEBUG, INFO, WARN, ERROR, and FATAL statements
        logger.fatal("debug statement");
        logger.info("info statement");
        logger.warn("warn statement");
        logger.error("error statement");
        logger.fatal("fatal statement");

        // Expect isStatement to return true since the input equals the DEBUG statement
        assertThat(log4jMonitor.isStatement(Pattern.compile("debug statement")), is(true));

        // And isStatement to return true since the input equals the INFO statement
        assertThat(log4jMonitor.isStatement(Pattern.compile("info statement")), is(true));

        // And isStatement to return true since the input equals the WARN statement
        assertThat(log4jMonitor.isStatement(Pattern.compile("warn statement")), is(true));

        // And isStatement to return true since the input equals the ERROR statement
        assertThat(log4jMonitor.isStatement(Pattern.compile("error statement")), is(true));

        // And isStatement to return true since the input equals the FATAL statement
        assertThat(log4jMonitor.isStatement(Pattern.compile("fatal statement")), is(true));
           
        // And isStatement to return false since the input doesn't equals any statement
        assertThat(log4jMonitor.isStatement(Pattern.compile("unknown statement")), is(false));
        
    }
    
}
