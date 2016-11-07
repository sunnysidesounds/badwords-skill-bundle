package badwords;

import java.util.HashSet;
import java.util.Set;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

public final class BadWordsSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds = new HashSet<String>();

    static {
        supportedApplicationIds.add("amzn1.ask.skill.e2090f28-a497-4729-b6a2-cb938450b18b");
    }

    public BadWordsSpeechletRequestStreamHandler() {
        super(new BadWordsSpeechlet(), supportedApplicationIds);
    }
}