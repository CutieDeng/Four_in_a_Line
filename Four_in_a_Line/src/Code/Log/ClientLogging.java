package Code.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * ClientLogging is a class to create the client logging machine.
 * <p>
 *     You need to use the {@code new ClientLogging()} constructor to create an instance.
 *     In the actual game program, you just only need to create one instance to run. More than one objects are useless and may
 *     create some unexpected situations.
 * <p>
 *     You can use the method {@code log(String content)} to print some words in your logging txt file.
 *     By the way, this method would also print the current time when you print the information in the logging txt.
 * <p>
 *     If the Logging cannot find the path in our file, it would attempt to create some directories and solve these problems.
 * <p>
 *     Notice that, if you cannot create this file actually, you would get into a unstopped while loop.
 *     Please be cautious to use it.
 *
 * @see Code.Log.Logging
 * @version 1.0
 * @author Cutie
 */
public class ClientLogging extends Logging{
    /**
     * The method would create a client logging.
     * <p>
     * When the client is created, the log content would be printed in the file whose name is the [client(number).txt].
     * The number is the same as {@code id}.
     * Below is the example.
     * <p>
     * If you just write down these codes:
     * <blockquote><prep>
     * ClientLogging clientLogging = new ClientLogging(1314520);
     * </prep></blockquote>
     * <p>
     * Then this object would print everything down in a special txt file named "client1314520.txt".
     * <p>
     * If you like, you can just double click and open the file to read the concrete process happening in the program.
     * <p>
     * Or in the future, we would design another application to help you read these information quickly.
     *
     * @param id It's the id of the server, and also the unique identifier of the server.
     */
    public ClientLogging(long id) {
        logFile = new File("ClientLog\\" +
                "client" + id +
                ".txt");
        boolean dirCreated = false;
        while (printWriter == null) {
            try {
                printWriter = new PrintWriter(new FileOutputStream(logFile, true));
            } catch (FileNotFoundException e) {
                File dir = logFile.getParentFile();
                if (!dir.exists()) {
                    dirCreated = dir.mkdirs();
                }
            }
        }
        if (dirCreated) {
            log("Can't find the appropriate directory but create one.", InfoLevel.WARN);
        }
    }
}
