package DSSimProtocol;

public class Action {

    public enum ActionIntent {
        SEND_MESSAGE,
        SWITCH_STATE,
        COMMAND_SCHD,
        COMMAND_OK,
        COMMAND_CNTJ,
        COMMAND_GETS_CAPABLE,
        COMMAND_GETS_AVAIL,
        PASS,
        QUIT;
    }

    public ActionIntent intent;
    public ProtocolState state;
    public String message;
    public Job job;
    public Server server;

    public Job.JobState jobState;


    public Action() {
    }

    public Action(ActionIntent intent) {
        this.intent = intent;
    }

    public Action(ActionIntent intent, String message) {
        this.intent = intent;
        this.message = message;
    }

    public Action(ActionIntent intent, Job job) {
        this.intent = intent;
        this.job = job;
    }

    public Action(ActionIntent intent, Job job, Server server) {
        this.intent = intent;
        this.job = job;
        this.server = server;
    }

    public Action(ActionIntent intent, Server server, Job job) {
        this.intent = intent;
        this.job = job;
        this.server = server;
    }

    public Action(ActionIntent intent, Job job, Server server, Job.JobState jobState) {
        this.intent = intent;
        this.job = job;
        this.server = server;
        this.jobState = jobState;
    }

    public Action(ActionIntent intent, ProtocolState state) {
        this.intent = intent;
        this.state = state;
    }
}
