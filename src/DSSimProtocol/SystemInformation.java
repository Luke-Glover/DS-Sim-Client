package DSSimProtocol;

import java.util.ArrayList;
import java.util.Collections;

public class SystemInformation {

    public static boolean verbose = false;
    public static boolean debug = false;
    public static String configurationPath = "ds-system.xml";
    public static String algorithmName = "allToLargest";
    public static String remoteAddress = "127.0.0.1";
    public static int port = 50000;

    public static ArrayList<Server> serverList = new ArrayList<>();
    public static ArrayList<Job> pendingJobList = new ArrayList<>();

    public static boolean stable = false;

    private SystemInformation() {
    }

    /**
     * @deprecated
     * Adds a server to the internal model of the DSSim system
     *
     * @param serverType The name of the server type
     * @param limit      The number of servers of a particular type
     * @param bootupTime The time taken to bootup a server of this type
     * @param hourlyRate The cost per hour for renting a server of this type
     * @param core       The number of CPU cores
     * @param mem        The amount of memory in MB
     * @param disk       The amount of disk space in MB
     */
    public static void addServer(String serverType, int limit, int bootupTime, float hourlyRate, int core, int mem, int disk) {
        stable = false;
        Server newServer = new Server(serverType, limit, bootupTime, hourlyRate, core, mem, disk);
        serverList.add(newServer);
    }

    /**
     * Adds a server to the internal model of the DSSim system
     * @param server The server to add
     */
    public static void addServer(Server server) {
        stable = false;
        if (!serverList.contains(server)) {
            serverList.add(server);
        }
    }

    /**
     * Adds a job to the internal model of the DSSim system
     * @param job The job to add
     */
    public static void addJob(Job job) {
        if (!pendingJobList.contains(job)) {
            pendingJobList.add(job);
        }
    }

    /**
     * Finds the server with the largest core count.
     *
     * @return Returns server object with largest core count.
     */
    public static Server mostCores() {
        int highestId = 0;
        if (serverList.size() > 0) {
            for (int i = 0; i < serverList.size(); i++) {
                if (serverList.get(i).core > serverList.get(highestId).core)
                    highestId = i;
            }
            return serverList.get(highestId);
        }
        return null;
    }

    public static void sortServers() {
        if (!stable) {
            serverList.sort(new Server.ServerComparator());
            stable = true;
        }
    }

}