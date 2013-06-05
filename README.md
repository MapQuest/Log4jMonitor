Log4jMonitor
============
utility to make testing log4j message writing easier

Basic Usage Guide
=================

See unit tests, but here's a quick example:

public class MyClass() {
    private Logger logger = Logger.getLogger(MyClass.class);
    
    public MyClass() {
        logger.info("MyClass instantiated");
    }

}

public class MyClassTest {
    @Test
    public void myClassInstantiationShouldLogINFOStatement() {
        Log4jMonitor log4jMonitor = new Log4jMonitor();
    
        MyClass myClass = new MyClass();
    
        assertThat(log4jMonitor.getStatementCount(Level.INFO), is(1));
        assertThat(log4jMonitor.hasStatements(Level.ERROR), is(false));
        assertThat(log4jMonitor.isInfoStatement("MyClass instantiated"), is(true));
    
    }
    
}
