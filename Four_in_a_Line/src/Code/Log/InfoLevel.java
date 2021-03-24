package Code.Log;

/**
 * The enum is used to explain the concrete priority of different information.
 * <p>
 * We just divide them into three different levels respectively, including INFO, TOP and WARN.
 * </p>
 * <p>
 * INFO information is the information just tell you what is happening in the black box.
 * I don't advise you to examine these information because they may be useless.
 * </p>
 * <p>
 * TOP information is the information that tell you the special and important things are happening.
 * Like the game just begins and your client is disconnected.
 * </p>
 * <p>
 * WARN information is to export some unexpected things occur in our programming environment.
 * Of course, our programmer would set a series of methods to attempt to fix them.
 * But we still need to write it down and know what may happen specifically.
 * </p>
 *
 * @version 1.0
 * @author Cutie
 */

public enum InfoLevel {

    INFO((byte)0),
    TOP((byte) 1),
    WARN((byte) 2);

    private final byte priorityValue;

    private InfoLevel(byte priorityValue){
        this.priorityValue = priorityValue;
    }

    /**
     * This method can compare the priority level of different pieces of information. Especially used to filter some unused information.
     * @param allowLevel The lowest priority information can be print out.
     * @return True if this level of information are allowed.
     */
    public boolean isPermitted(InfoLevel allowLevel){
        return this.priorityValue >= allowLevel.priorityValue;
    }

}
