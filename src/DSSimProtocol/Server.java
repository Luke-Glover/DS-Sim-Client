package DSSimProtocol;

import java.util.Comparator;
import java.util.Locale;

public class Server {

    /**
     * Represents a server that can execute jobs in the simulation. This class does not represent the DSSimServer
     * that runs the simulation.
     */

    public enum ServerState {
        INACTIVE,
        BOOTING,
        IDLE,
        ACTIVE,
        UNAVAILABLE
    }

    public static class ServerComparator implements Comparator<Server> {

        @Override
        public int compare(Server o1, Server o2) {
            if (o1.core > o2.core) {
                return 1;
            } else if (o1.core < o2.core) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public String serverType;
    public int serverID;
    public ServerState serverState;
    public int curStartTime;
    public int core;
    public int mem;
    public int disk;
    public int limit;
    public int bootupTime;
    public float hourlyRate;

    public Server() {

    }

    /**
     * This constructor is best suited for creating server objects from the response to a GETS command
     *
     * @param serverType   The name of the server type
     * @param serverID     An arbitrary but unique ID for each server
     * @param serverState  The state of the server, either INACTIVE, BOOTING, IDLE, ACTIVE, or UNAVAILABLE
     * @param curStartTime The start time of the currently running task
     * @param core         The available CPU core
     * @param mem          The available memory
     * @param disk         The available disk space
     */
    public Server(String serverType, int serverID, ServerState serverState, int curStartTime, int core, int mem, int disk) {
        this.serverType = serverType;
        this.serverID = serverID;
        this.serverState = serverState;
        this.curStartTime = curStartTime;
        this.core = core;
        this.mem = mem;
        this.disk = disk;
    }

    /**
     * This constructor is best suited for creating server objects from the ds-system.xml file
     *
     * @param serverType The name of the server type
     * @param limit      The number of servers of a particular type
     * @param bootupTime The time taken to bootup a server of this type
     * @param hourlyRate The cost per hour for renting a server of this type
     * @param core       The number of CPU cores
     * @param mem        The amount of memory in MB
     * @param disk       The amount of disk space in MB
     */
    public Server(String serverType, int limit, int bootupTime, float hourlyRate, int core, int mem, int disk) {
        this.serverType = serverType;
        this.limit = limit;
        this.bootupTime = bootupTime;
        this.hourlyRate = hourlyRate;
        this.core = core;
        this.mem = mem;
        this.disk = disk;
    }
}
