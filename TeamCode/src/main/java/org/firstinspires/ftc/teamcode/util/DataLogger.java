package org.firstinspires.ftc.teamcode.util;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple file-based logger.
 * <p>
 * Logs data into text files under {@code /sdcard/FIRST/SympleLogs/}.
 * Each log entry is timestamped and categorized by {@link DataType}.
 * </p>
 *
 * <p>Example log entry:</p>
 * <pre>
 * &lt;05-09-2025 | 14:30:15.123 / INFO&gt; Initialized drivetrain
 * </pre>
 */
public class DataLogger {

    /**
     * Default directory where log files are stored on the robot controller device.
     */
    @SuppressLint("SdCardPath")
    private static final String DIRECTORY_PATH = "/sdcard/FIRST/SympleLogs";

    /**
     * File writer used to append log entries to the log file.
     * Will be {@code null} if suppressed or file creation failed.
     */
    @Nullable
    private FileWriter fileWriter;

    /**
     * Creates a new {@code DataLogger} instance with an optional suppression flag.
     *
     * @param filePrefix a prefix for the log file name (e.g. "TeleOp", "Auto")
     * @param suppress   if {@code true}, logging is disabled and no file is created
     */
    public DataLogger(String filePrefix, boolean suppress) {
        String fileName =  createFileName(filePrefix);
        String filePath = DIRECTORY_PATH + "/" + fileName;

        new File(DIRECTORY_PATH).mkdir(); // create the directory if not exists

        try {
            if(!suppress) {
                this.fileWriter = new FileWriter(filePath, true);
                this.writeLine("// " + fileName);
            }
        } catch (IOException ignored) { }
    }

    /**
     * Creates a new {@code DataLogger} instance.
     *
     * @param filePrefix a prefix for the log file name (e.g. "TeleOp", "Auto")
     */
    public DataLogger(String filePrefix) {
        this(filePrefix, false);
    }

    /**
     * Generates a timestamped log file name.
     *
     * @param prefix the file name prefix
     * @return a file name in the format {@code prefix_dd-MM-yyyy@HH-mm-ss.txt}
     */
    private String createFileName(String prefix) {
        return prefix + "_" + getCurrentTime("dd-MM-yyyy@HH-mm-ss") + ".txt";
    }

    /**
     * Logs a message with the given {@link DataType}.
     *
     * @param dataType the category of the log entry {@link DataType}
     * @param data     the message to log
     */
    public void addData(DataType dataType, String data) {
        String linePrefix = "<" + getCurrentTime("dd-MM-yyyy | HH:mm:ss.SSS") + " / " + dataType.name() + "> ";
        try {
            this.writeLine(linePrefix + data);
        } catch (IOException ignored) { }
    }

    /** @see #addData(DataType, String) */
    public void addData(DataType dataType, boolean bool) {
        addData(dataType, Boolean.toString(bool));
    }

    /** @see #addData(DataType, String) */
    public void addData(DataType dataType, int i) {
        addData(dataType, Integer.toString(i));
    }

    /** @see #addData(DataType, String) */
    public void addData(DataType dataType, long l) {
        addData(dataType, Long.toString(l));
    }

    /** @see #addData(DataType, String) */
    public void addData(DataType dataType, double d) {
        addData(dataType, Double.toString(d));
    }

    /** @see #addData(DataType, String) */
    public void addData(DataType dataType, float f) {
        addData(dataType, Float.toString(f));
    }

    /** @see #addData(DataType, String) */
    public void addData(DataType dataType, Object o) {
        addData(dataType, String.valueOf(o));
    }

    /**
     * Logs a full stack trace from a {@link Throwable}.
     *
     * @param throwable the exception or error to log
     */
    public void addThrowable(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String sStackTrace = sw.toString();
        addData(DataType.ERROR, sStackTrace);
    }

    /**
     * Closes the current log file.
     * Should be called at the end of the OpMode to release resources.
     */
    public void closeFile() {
        if(fileWriter == null) return;
        try {
            fileWriter.close();
        } catch (IOException ignored) { }
    }

    /**
     * Writes a raw line into the log file.
     *
     * @param data the data string to write
     * @throws IOException if writing fails
     */
    private void writeLine(String data) throws IOException {
        if(fileWriter == null) return;
        fileWriter.write(data + System.lineSeparator());
        flushData();
    }

    /**
     * Forces the buffered writer to flush its contents to disk.
     *
     * @throws IOException if flushing fails
     */
    private void flushData() throws IOException {
        if(fileWriter == null) return;
        fileWriter.flush();
    }

    /**
     * Returns the current time formatted according to the given pattern.
     * Time zone is set to {@code Asia/Jerusalem}.
     *
     * @param format the {@link SimpleDateFormat} pattern string
     * @return the formatted timestamp
     */
    private String getCurrentTime(String format) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Jerusalem"));

        return formatter.format(new Date());
    }

    /**
     * Ensures the file writer is properly closed and flushed
     * when this object is garbage collected.
     */
    @Override
    protected void finalize() throws Throwable {
        closeFile();
        flushData();
        super.finalize();
    }

    /**
     * Defines the severity/category of a log entry.
     */
    public enum DataType {
        /** Informational messages, normal operation logs. */
        INFO,
        /** Warnings, non-critical issues. */
        WARN,
        /** Errors or exceptions, critical issues. */
        ERROR
    }
}
