package DSSimProtocol.ProtocolHandler;

import DSSimProtocol.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class BestFitEventLoopProtocolHandler implements ProtocolHandler {

    boolean multipartMessage = false;
    int numberOfParts = 0;
    ArrayList<Server> tempServerList = new ArrayList<>();
    ArrayList<Job> tempJobList = new ArrayList<>();

    @Override
    public Action onEnterState() {

        // Read system information XML if possible
        try {
            XMLParser.parse(SystemInformation.configurationPath);
        } catch (FileNotFoundException e) {
            System.out.println("WARNING: XML file " + SystemInformation.configurationPath + " does not exist");
            //java.lang.System.exit(-1); // No using GETS, not a fatal error if the XML file is not found
        }

        if (SystemInformation.debug) System.out.println("USING: best fit");

        return new Action(Action.ActionIntent.SEND_MESSAGE, "REDY");
    }

    @Override
    public Action onReceiveMessage(String message) throws UnrecognisedCommandException {

        String[] messageParts = message.split(" ");

        if (multipartMessage) {

            Server capableServer = new Server();
            capableServer.serverType = messageParts[0];
            capableServer.serverID = Integer.parseInt(messageParts[1]);
            capableServer.serverState = Server.ServerState.valueOf(messageParts[2].toUpperCase());
            capableServer.curStartTime = Integer.parseInt(messageParts[3]);
            capableServer.core = Integer.parseInt(messageParts[4]);
            capableServer.mem = Integer.parseInt(messageParts[5]);
            capableServer.disk = Integer.parseInt(messageParts[6]);
            tempServerList.add(capableServer);

            numberOfParts -= 1;
            if (numberOfParts <= 0) {
                multipartMessage = false;
                return new Action(Action.ActionIntent.SEND_MESSAGE, "OK");
            }

            return new Action(Action.ActionIntent.PASS);

        } else {

            switch (messageParts[0]) {

                case "OK", "JCPL" -> {
                    return new Action(Action.ActionIntent.SEND_MESSAGE, "REDY");
                }

                case "JOBN" -> {
                    Job job = new Job();
                    job.submitTime = Integer.parseInt(messageParts[1]);
                    job.jobID = Integer.parseInt(messageParts[2]);
                    job.estRuntime = Integer.parseInt(messageParts[3]);
                    job.cpu = Integer.parseInt(messageParts[4]);
                    job.memory = Integer.parseInt(messageParts[5]);
                    job.disk = Integer.parseInt(messageParts[6]);
                    tempJobList.add(job);
                    return new Action(Action.ActionIntent.COMMAND_GETS_AVAIL, job);
                }

                case "DATA" -> {
                    tempServerList.clear();
                    numberOfParts = Integer.parseInt(messageParts[1]);
                    if (numberOfParts > 0) multipartMessage = true;
                    return new Action(Action.ActionIntent.SEND_MESSAGE, "OK");
                }

                case "." -> {
                    Job tempJob = tempJobList.get(0);

                    if (!tempServerList.isEmpty()) {
                        tempServerList.sort(new Server.ServerComparator());
                        Server tempServer = tempServerList.get(0);
                        tempServerList.clear();

                        tempJobList.clear();
                        return new Action(Action.ActionIntent.COMMAND_SCHD, tempJob, tempServer);

                    } else {
                        return new Action(Action.ActionIntent.COMMAND_GETS_CAPABLE, tempJob);
                    }
                }

                case "NONE" -> {
                    return new Action(Action.ActionIntent.SWITCH_STATE, ProtocolState.QUITTING);
                }

                default -> {
                    throw new UnrecognisedCommandException("Unrecognised command: " + message);
                }
            }
        }
    }
}
