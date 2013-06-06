package com.stupidplebs.log4jmonitor;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.WriterAppender;

/**
 * Utility class to help test log4j statements
 * 
 * This thing isn't even remotely thread-safe, so be aware of that and only use
 * in the context of single-threaded tests
 * 
 * @author stephenkhess
 * 
 */
public class Log4jMonitor {
    // this ByteArrayOutputStream is used to interrogate for log4j messages
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    // save off the newline character of the environment the test is running in
    public final static String LINE_SEPARATOR = System
            .getProperty("line.separator");

    /**
     * Constructor that sets up log4j to log all statements matching the
     * supplied level or higher in importance
     * 
     */
    public Log4jMonitor(final Level level) {
        if (null == level) {
            throw new IllegalArgumentException("level parameter cannot be null");
        }

        // first, grab the root logger
        final Logger rootLogger = Logger.getRootLogger();

        // second, clear all appenders on the root logger
        rootLogger.removeAllAppenders();

        // third, setup the OutputStream to be written to by the logger
        rootLogger.addAppender(new WriterAppender(new SimpleLayout(),
                outputStream));

        // fourth, set the level
        rootLogger.setLevel(level);

    }

    /**
     * Constructor that sets up log4j to log everything
     * 
     */
    public Log4jMonitor() {
        this(Level.DEBUG);
    }

    /**
     * Helper factory method for returning an instance that only logs DEBUG,
     * INFO, WARN, ERROR, and FATAL statements
     * 
     * @return
     */
    public static Log4jMonitor getDebugInstance() {
        return new Log4jMonitor(Level.DEBUG);
    }

    /**
     * Helper factory method for returning an instance that only logs INFO,
     * WARN, ERROR, and FATAL statements
     * 
     * @return
     */
    public static Log4jMonitor getInfoInstance() {
        return new Log4jMonitor(Level.INFO);
    }

    /**
     * Helper factory method for returning an instance that only logs WARN,
     * ERROR, and FATAL statements
     * 
     * @return
     */
    public static Log4jMonitor getWarnInstance() {
        return new Log4jMonitor(Level.WARN);
    }

    /**
     * Helper factory method for returning an instance that only logs ERROR and
     * FATAL statements
     * 
     * @return
     */
    public static Log4jMonitor getErrorInstance() {
        return new Log4jMonitor(Level.ERROR);
    }

    /**
     * Helper factory method for returning an instance that only logs FATAL
     * statements
     * 
     * @return
     */
    public static Log4jMonitor getFatalInstance() {
        return new Log4jMonitor(Level.FATAL);
    }

    /**
     * Get all statements written to the logger
     * 
     * @return a List of all logged statements
     */
    public List<Log4jStatement> getStatements() {
        final List<Log4jStatement> log4jStatements = new ArrayList<Log4jStatement>();

        // convert the outputstream to a string and split on newline
        final String concatenatedStatements = new String(
                outputStream.toByteArray());

        // this matches:
        // - any of the log4j levels
        // - a space-dash-space
        // - reluctant anything
        // - positive-lookahead for any of:
        // - the log4j levels with space-dash-space
        // - end-of-string
        final Pattern p = Pattern.compile("(DEBUG|INFO|WARN|ERROR|FATAL) - "
                + "(.*?)" + LINE_SEPARATOR
                + "(?=DEBUG - |INFO - |WARN - |ERROR - |FATAL - |$)",
                Pattern.DOTALL);

        final Matcher matcher = p.matcher(concatenatedStatements);

        // allow the java regex library to iterate until all statements are
        // consumed
        while (matcher.find()) {
            final Level level = Level.toLevel(matcher.group(1));
            final String statement = matcher.group(2);

            final Log4jStatement log4jStatement = new Log4jStatement(level,
                    statement);

            log4jStatements.add(log4jStatement);

        }

        return log4jStatements;

    }

    /**
     * Get all logged statements of a specific severity level
     * 
     * @param level
     * @return a List of all logged statements of a specific severity level
     */
    public List<String> getStatements(final Level level) {
        // select only those statements matching the designated level and return
        // as a list
        final List<String> levelSpecificStatements = new ArrayList<String>();

        // loop over all statements, adding if logged at the specified level
        for (final Log4jStatement statement : getStatements()) {
            if (statement.getLevel() == level) {
                levelSpecificStatements.add(statement.getStatement());
            }
        }

        return Collections.unmodifiableList(levelSpecificStatements);

    }

    /**
     * Get all logged statements of a specific severity level matching a pattern
     * 
     * @param level
     * @param pattern
     * @return a List of all logged statements of a specific severity level
     */
    public List<String> getStatements(final Level level, final Pattern pattern) {
        if (null == level || null == pattern) {
            return Collections.unmodifiableList(new ArrayList<String>());
        }

        final List<String> statementsMatchingPattern = new ArrayList<String>();

        for (final String levelSpecificStatement : getStatements(level)) {
            final Matcher matcher = pattern.matcher(levelSpecificStatement);

            if (matcher.matches()) {
                statementsMatchingPattern.add(levelSpecificStatement);
            }

        }

        return Collections.unmodifiableList(statementsMatchingPattern);

    }

    /**
     * Get all logged statements of a specific severity level matching a pattern
     * 
     * @param level
     * @param pattern
     * @return a List of all logged statements of a specific severity level
     */
    public List<String> getStatements(final Level level, final String rawPattern) {
        if (null == rawPattern) {
            return Collections.unmodifiableList(new ArrayList<String>());
        }

        final Pattern pattern = Pattern.compile(rawPattern);

        return getStatements(level, pattern);

    }

    /**
     * Get the number of statements logged
     * 
     * @param level
     * @return the number of statements logged of a specific severity level
     */
    public Integer getStatementCount() {
        return getStatements().size();
    }

    /**
     * Get the number of statements logged of a specific severity level
     * 
     * @param level
     * @return the number of statements logged of a specific severity level
     */
    public Integer getStatementCount(final Level level) {
        return getStatements(level).size();
    }

    /**
     * Return whether any statements were logged at the specified level
     * 
     * @param level
     * @return true if at least 1 statement was logged at the specified level,
     *         false otherwise
     */
    public Boolean hasStatements(final Level level) {
        return !getStatements(level).isEmpty();
    }

    /**
     * Helper method that just dumps all the logged statements to stderr (useful
     * for debugging tests)
     * 
     */
    public void dumpToStdError() {
        for (final Log4jStatement statement : getStatements()) {
            System.err.println(statement);
        }

    }

    /**
     * Return whether a statement was logged at the specific level
     * 
     * @param level
     * @param statement
     * @return
     */
    public Boolean isStatement(final Level level, final String statement) {
        for (final String loggedStatement : getStatements(level)) {
            if (loggedStatement.equals(statement)) {
                return true;
            }

        }

        return false;

    }

    /**
     * Verify if the supplied statement was logged at the DEBUG level
     * 
     * @param statement
     * @return true if the statement was logged at the DEBUG level, false
     *         otherwise
     */
    public Boolean isDebugStatement(final String statement) {
        return isStatement(Level.DEBUG, statement);
    }

    /**
     * Verify if the supplied statement was logged at the INFO level
     * 
     * @param statement
     * @return true if the statement was logged at the INFO level, false
     *         otherwise
     */
    public Boolean isInfoStatement(final String statement) {
        return isStatement(Level.INFO, statement);
    }

    /**
     * Verify if the supplied statement was logged at the WARN level
     * 
     * @param statement
     * @return true if the statement was logged at the WARN level, false
     *         otherwise
     */
    public Boolean isWarnStatement(final String statement) {
        return isStatement(Level.WARN, statement);
    }

    /**
     * Verify if the supplied statement was logged at the ERROR level
     * 
     * @param statement
     * @return true if the statement was logged at the ERROR level, false
     *         otherwise
     */
    public Boolean isErrorStatement(final String statement) {
        return isStatement(Level.ERROR, statement);
    }

    /**
     * Verify if the supplied statement was logged at the FATAL level
     * 
     * @param statement
     * @return true if the statement was logged at the FATAL level, false
     *         otherwise
     */
    public Boolean isFatalStatement(final String statement) {
        return isStatement(Level.FATAL, statement);
    }

}
