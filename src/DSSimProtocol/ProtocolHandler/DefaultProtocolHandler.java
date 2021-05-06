package DSSimProtocol.ProtocolHandler;

import DSSimProtocol.*;


/**
 * The DefaultHandler is the handler that is used at startup. It's only job is to immediately switch
 * to another handler to process messages. This is done because the enterState
 */
public class DefaultProtocolHandler implements AlgorithmProtocolHandler {

    @Override
    public Action onEnterState() {
        return new Action(Action.ActionIntent.SWITCH_STATE, ProtocolState.HANDSHAKING);
    }

    @Override
    public Action onReceiveMessage(String message) throws UnrecognisedCommandException {
        return new Action();
    }
}
