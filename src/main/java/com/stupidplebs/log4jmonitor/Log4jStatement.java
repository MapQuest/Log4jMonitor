package com.stupidplebs.log4jmonitor;

import java.util.regex.Pattern;

import org.apache.log4j.Level;

public class Log4jStatement {
    private final Level level;
    private final String statement;

    protected Log4jStatement(final Level level, final String statement) {
        this.level = level;
        this.statement = statement;
    }

    public static Log4jStatement getInstance(final Level level,
            final String statement) {
        if (null == level) {
            throw new IllegalArgumentException("level parameter cannot be null");
        }
        if (null == statement) {
            throw new IllegalArgumentException(
                    "statement parameter cannot be null");
        }

        return new Log4jStatement(level, statement);
    }

    public Level getLevel() {
        return level;
    }

    public String getStatement() {
        return statement;
    }

    public Boolean isDebug() {
        return is(Level.DEBUG);
    }
    
    public Boolean isInfo() {
        return is(Level.INFO);
    }
    
    public Boolean isWarn() {
        return is(Level.WARN);
    }
    
    public Boolean isError() {
        return is(Level.ERROR);
    }
    
    public Boolean isFatal() {
        return is(Level.FATAL);
    }
    
    public Boolean is(final Level level) {
        return this.level.equals(level);
    }
    
    public Boolean matches(final Pattern pattern) {
    	return pattern.matcher(statement).matches();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getName());
        sb.append('[').append("level").append('=').append(level).append(',');
        sb.append("statement").append('=').append(statement).append(']');

        return sb.toString();

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + level.hashCode();
        result = prime * result + statement.hashCode();
        return result;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (getClass() != o.getClass()) {
            return false;
        }

        final Log4jStatement other = (Log4jStatement) o;

        if (!level.equals(other.level)) {
            return false;
        }

        if (!statement.equals(other.statement)) {
            return false;
        }

        return true;

    }

}
