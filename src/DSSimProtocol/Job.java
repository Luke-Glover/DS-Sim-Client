
package DSSimProtocol;
public class Job {

    /**
     * Representation of a job as sent by the DSSim server, such as that sent by the JOBN command
     */


    public enum JobState {
        SUBMITTED(0),
        WAITING(1),
        RUNNING(2),
        SUSPENDED(3),
        COMPLETED(4),
        PRE_EMPTED(5),
        FAILED(6),
        KILLED(7);

        private final int index;

        JobState(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }


    public JobState jobState;
    public int submitTime;
    public int jobID;
    public int estRuntime;
    public int cpu;
    public int memory;
    public int disk;

    public Job() {

    }

    /**
     * Constructs a job object from individual variables
     *
     * @param submitTime The system time that the job was submitted
     * @param jobID      An arbitrary but unique ID for each job
     * @param estRuntime An estimate of the runtime of a job
     * @param cpu        The required number of CPU cores to run the job
     * @param memory     The required amount of memory to run the job
     * @param disk       The required amount of disk space to run the job
     */
    public Job(int submitTime, int jobID, int estRuntime, int cpu, int memory, int disk) {
        this.jobID = jobID;
        this.submitTime = submitTime;
        this.estRuntime = estRuntime;
        this.cpu = cpu;
        this.memory = memory;
        this.disk = disk;
    }

    /**
     * Constructs a job object from an array of strings sent by the DSSimServer, such as sent by the JOBN command
     *
     * @param messageParts An array of strings in the order: submitTime, jobID, estRuntime, cpu, memory, disk
     */
    public Job(String[] messageParts) {
        this.submitTime = Integer.parseInt(messageParts[1]);
        this.jobID = Integer.parseInt(messageParts[2]);
        this.estRuntime = Integer.parseInt(messageParts[3]);
        this.cpu = Integer.parseInt(messageParts[4]);
        this.memory = Integer.parseInt(messageParts[5]);
        this.disk = Integer.parseInt(messageParts[6]);
    }
}
