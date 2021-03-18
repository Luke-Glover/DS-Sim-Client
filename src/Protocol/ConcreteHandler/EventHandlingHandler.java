package Protocol.ConcreteHandler;

import Protocol.*;

public class EventHandlingHandler implements Handler {

    @Override
    public Action enterState() {
        return new Action(Intent.SEND_MESSAGE, "REDY");
    }

    @Override
    public Action handleMessage(String message) throws UnrecognisedCommandException {
        switch (message) {
            case "OK" -> {
                return new Action(Intent.LOOP, State.EVENT_HANDLING);
            }
            default -> {
                throw new UnrecognisedCommandException("Unrecognised command: " + message);
            }
        }
    }
}