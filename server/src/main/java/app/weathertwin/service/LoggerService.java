package app.weathertwin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggerService {

  public final Logger logger;

  /** Default constructor */
  public LoggerService() {
    this.logger = LoggerFactory.getLogger("ApplicationDefaultLogger");
  }

  /** Alternative constructor that allows specifying the logger name */
  public LoggerService(String loggerName) {
    this.logger = LoggerFactory.getLogger(loggerName);
  }

  /** Convenience methods that delegate to the logger */
  public void info(String message) {
    logger.info(message);
  }

  public void info(String format, Object... arguments) {
    logger.info(format, arguments);
  }

  public void warn(String message) {
    logger.warn(message);
  }

  public void error(String message) {
    logger.error(message);
  }

  public void error(String message, Throwable throwable) {
    logger.error(message, throwable);
  }

  public void debug(String message) {
    logger.debug(message);
  }
}
