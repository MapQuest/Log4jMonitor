package com.stupidplebs.log4jmonitor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.junit.Test;

public class Log4jStatementTest {
    @Test
    public void getLevelShouldReturnLevelUsedInConstructor() {
        // Given a Level
        Level level = Level.ERROR;

        // And a statement
        String statement = "log statement";

        // And a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(level, statement);

        // Expect getLevel to return the level passed to the constructor
        assertThat(log4jStatement.getLevel(), is(level));

    }

    @Test
    public void getStatementShouldReturnStatementUsedInConstructor() {
        // Given a Level
        Level level = Level.ERROR;

        // And a statement
        String statement = "log statement";

        // And a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(level, statement);

        // Expect getStatement to return the statement passed to the constructor
        assertThat(log4jStatement.getStatement(), is("log statement"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void nullLevelParameterToGetInstanceShouldThrowIllegalArgumentException() {
        // Given a null Level
        Level level = null;

        // And a statement
        String statement = "log statement";

        // When getInstance is called
        Log4jStatement.getInstance(level, statement);

        // Then an IllegalArgumentException should be thrown

    }

    @Test(expected = IllegalArgumentException.class)
    public void nullStatementParameterToGetInstanceShouldThrowIllegalArgumentException() {
        // Given a Level
        Level level = Level.ERROR;

        // And a null statement
        String statement = null;

        // When getInstance is called
        Log4jStatement.getInstance(level, statement);

        // Then an IllegalArgumentException should be thrown

    }

    @Test
    public void toStringShouldOutputFullClassNameAndLevelAndStatement() {
        // Given a Level
        Level level = Level.ERROR;

        // And a statement
        String statement = "log statement";

        // And a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(level, statement);

        // Expect toString to output full class name and level and statement
        assertThat(log4jStatement.toString(),
                is("com.stupidplebs.log4jmonitor.Log4jStatement["
                        + "level=ERROR," + "statement=log statement" + "]"));

    }

    @Test
    public void comparisonToNullShouldReturnFalse() {
        // Given a Level
        Level level = Level.ERROR;

        // And a statement
        String statement = "log statement";

        // And a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(level, statement);

        // And a null Log4jStatement
        Log4jStatement other = null;

        // Expect equals to return false
        assertThat(log4jStatement.equals(other), is(false));

    }

    @Test
    public void comparisonToSameObjectShouldReturnTrue() {
        // Given a Level
        Level level = Level.ERROR;

        // And a statement
        String statement = "log statement";

        // And a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(level, statement);

        // And a pointer to the same Log4jStatement
        Log4jStatement other = log4jStatement;

        // Expect equals to return true
        assertThat(log4jStatement.equals(other), is(true));

    }

    @Test
    public void comparisonToNonLog4jStatementShouldReturnTrue() {
        // Given a Level
        Level level = Level.ERROR;

        // And a statement
        String statement = "log statement";

        // And a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(level, statement);

        // And a non-Log4jStatement object
        String other = "this isn't a Log4jStatement instance";

        // Expect equals to return false
        assertThat(log4jStatement.equals(other), is(false));

    }

    @Test
    public void comparisonToLog4jStatementWithDifferentLevelShouldReturnFalseAndHaveDifferentHashCodes() {
        // Given a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(Level.ERROR,
                "log statement");

        // And a Log4jStatement instance differing on level
        Log4jStatement other = new Log4jStatement(Level.INFO, "log statement");

        // Expect equals to return false
        assertThat(log4jStatement.equals(other), is(false));

        // And the hashCodes should be different
        assertThat(log4jStatement.hashCode(), is(not(other.hashCode())));

    }

    @Test
    public void comparisonToLog4jStatementWithDifferentStatementShouldReturnFalseAndHaveDifferentHashCodes() {
        // Given a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(Level.ERROR,
                "log statement");

        // And a Log4jStatement instance differing on statement
        Log4jStatement other = new Log4jStatement(Level.ERROR,
                "different log statement");

        // Expect equals to return false
        assertThat(log4jStatement.equals(other), is(false));

        // And the hashCodes should be different
        assertThat(log4jStatement.hashCode(), is(not(other.hashCode())));

    }

    @Test
    public void comparisonToLog4jStatementWithIdenticalLevelAndStatementShouldReturnTrueAndHaveIdenticalHashCodes() {
        // Given a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(Level.ERROR,
                "log statement");

        // And a Log4jStatement instance with same level and statement
        Log4jStatement other = new Log4jStatement(Level.ERROR, "log statement");

        // Expect equals to return false
        assertThat(log4jStatement.equals(other), is(true));

        // And the hashCodes should be identical
        assertThat(log4jStatement.hashCode(), is(other.hashCode()));

    }
    
    @Test
    public void matchesShouldReturnTrueIfThePatternMatchesTheStatement() {
        // Given a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(Level.ERROR,
                "log statement");

        // And a Pattern that matches the statement
        Pattern pattern = Pattern.compile("l.g statement");
        
        // Expect matches to return true
        assertThat(log4jStatement.matches(pattern), is(true));
        
    }
    
    @Test
    public void matchesShouldReturnFalseIfThePatternDoesNotMatchTheStatement() {
        // Given a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(Level.ERROR,
                "log statement");

        // And a Pattern that doesn't match the statement
        Pattern pattern = Pattern.compile("non-matching pattern");
        
        // Expect matches to return false
        assertThat(log4jStatement.matches(pattern), is(false));
        
    }

    @Test
    public void isDebugShouldReturnTrueIfLevelIsDEBUG() {
        // Given a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(Level.DEBUG,
                "log statement");

        // Expect isDebug to return true
        assertThat(log4jStatement.isDebug(), is(true));
        
        // And isInfo/Warn/Error/Fatal to return false
        assertThat(log4jStatement.isInfo(), is(false));
        assertThat(log4jStatement.isWarn(), is(false));
        assertThat(log4jStatement.isError(), is(false));
        assertThat(log4jStatement.isFatal(), is(false));
        
    }
    
    @Test
    public void isInfoShouldReturnTrueIfLevelIsINFO() {
        // Given a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(Level.INFO,
                "log statement");

        // Expect isInfo to return true
        assertThat(log4jStatement.isInfo(), is(true));
        
        // And isDebug/Warn/Error/Fatal to return false
        assertThat(log4jStatement.isDebug(), is(false));
        assertThat(log4jStatement.isWarn(), is(false));
        assertThat(log4jStatement.isError(), is(false));
        assertThat(log4jStatement.isFatal(), is(false));
        
    }
    
    @Test
    public void isWarnShouldReturnTrueIfLevelIsWARN() {
        // Given a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(Level.WARN,
                "log statement");

        // Expect isWarn to return true
        assertThat(log4jStatement.isWarn(), is(true));
        
        // And isDebug/Info/Warn/Error/Fatal to return false
        assertThat(log4jStatement.isDebug(), is(false));
        assertThat(log4jStatement.isInfo(), is(false));
        assertThat(log4jStatement.isError(), is(false));
        assertThat(log4jStatement.isFatal(), is(false));
        
    }
    
    @Test
    public void isErrorShouldReturnTrueIfLevelIsERROR() {
        // Given a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(Level.ERROR,
                "log statement");

        // Expect isError to return true
        assertThat(log4jStatement.isError(), is(true));
        
        // And isDebug/Info/Warn/Fatal to return false
        assertThat(log4jStatement.isDebug(), is(false));
        assertThat(log4jStatement.isInfo(), is(false));
        assertThat(log4jStatement.isWarn(), is(false));
        assertThat(log4jStatement.isFatal(), is(false));
        
    }
    
    @Test
    public void isFatalShouldReturnTrueIfLevelIsFATAL() {
        // Given a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(Level.FATAL,
                "log statement");

        // Expect isFatal to return true
        assertThat(log4jStatement.isFatal(), is(true));
        
        // And isDebug/Info/Warn/Error to return false
        assertThat(log4jStatement.isDebug(), is(false));
        assertThat(log4jStatement.isInfo(), is(false));
        assertThat(log4jStatement.isWarn(), is(false));
        assertThat(log4jStatement.isError(), is(false));
        
    }
    
    @Test
    public void isShouldReturnTrueIfLevelMatchesSuppliedLevel() {
        // Given a Log4jStatement instance
        Log4jStatement log4jStatement = new Log4jStatement(Level.FATAL,
                "log statement");

        // Expect is(Fatal) to return true
        assertThat(log4jStatement.is(Level.FATAL), is(true));
        
        // And isDebug/Info/Warn/Error to return false
        assertThat(log4jStatement.is(Level.DEBUG), is(false));
        assertThat(log4jStatement.is(Level.INFO), is(false));
        assertThat(log4jStatement.is(Level.ERROR), is(false));
        assertThat(log4jStatement.is(Level.WARN), is(false));
        
    }
    
}
