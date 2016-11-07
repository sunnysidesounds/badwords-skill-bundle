package badwords;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.Speechlet;
import com.amazon.speech.speechlet.SpeechletException;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import com.amazon.speech.ui.*;

import java.util.ArrayList;
import java.util.Random;

public class BadWordsSpeechlet implements Speechlet {
    private static final Logger log = LoggerFactory.getLogger(BadWordsSpeechlet.class);
    private static ArrayList<String> usedBadWordsList = new ArrayList<>();

    @Override
    public void onSessionStarted(final SessionStartedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionStarted requestId={}, sessionId={}", request.getRequestId(),
            session.getSessionId());
        // any initialization logic goes here
    }

    @Override
    public SpeechletResponse onLaunch(final LaunchRequest request, final Session session)
            throws SpeechletException {
        log.info("onLaunch requestId={}, sessionId={}", request.getRequestId(),
            session.getSessionId());
        return getWelcomeResponse();
    }

    @Override
    public void onSessionEnded(final SessionEndedRequest request, final Session session)
            throws SpeechletException {
        log.info("onSessionEnded requestId={}, sessionId={}", request.getRequestId(),
            session.getSessionId());
    }

    @Override
    public SpeechletResponse onIntent(final IntentRequest request, final Session session)
            throws SpeechletException {
        log.info("onIntent requestId={}, sessionId={}", request.getRequestId(), session.getSessionId());
        Intent intent = request.getIntent();
        String intentName = intent.getName();
        if("InitialBadWordsIntent".equals(intentName)) {
            return initialBadWordsRequest(intent);
        } else if ("AMAZON.StopIntent".equals(intentName)) {
            return getAmazonStopResponse();
        } else if ("AMAZON.HelpIntent".equals(intentName)) {
            return getAmazonHelpResponse();
        } else if ("AMAZON.CancelIntent".equals(intentName)) {
            return getAmazonCancelResponse();
        } else {
            throw new SpeechletException("Invalid Intent");
        }
    }

    private SpeechletResponse initialBadWordsRequest(Intent intent) {
        String speechOutput = this.getCrudeRemark();
        String repromptText = this.getCrudeRemark();
        // Add your logic here...
        return askForResponse(speechOutput, false, "<speak>" + repromptText + "</speak>", true);
    }

    private SpeechletResponse getWelcomeResponse() {
        String speechOutput = this.getCrudeRemark();
        String repromptText = this.getCrudeRemark();
        return askForResponse(speechOutput, false, repromptText, false);
    }


    private SpeechletResponse askForResponse(String stringOutput, boolean isOutputSsml,
        String repromptText, boolean isRepromptSsml) {
        OutputSpeech outputSpeech, repromptOutputSpeech;
        if (isOutputSsml) {
            outputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) outputSpeech).setSsml(stringOutput);
        } else {
            outputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) outputSpeech).setText(stringOutput);
        }
        if (isRepromptSsml) {
            repromptOutputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) repromptOutputSpeech).setSsml(repromptText);
        } else {
            repromptOutputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) repromptOutputSpeech).setText(repromptText);
        }
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptOutputSpeech);
        return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
    }

    private SpeechletResponse getAmazonHelpResponse() {
        String speechOutput = "Ask a Bad Word, by saying \"Bad Word\"";
        String repromptText = "Again, Ask a Bad Word, by saying \"Bad Word\\";
        return askForResponse(speechOutput, false, repromptText, false);
    }

    private SpeechletResponse getAmazonStopResponse() {
        return SpeechletResponse.newTellResponse(getGoodByeText());
    }

    private SpeechletResponse getAmazonCancelResponse() {
        return SpeechletResponse.newTellResponse(getGoodByeText());
    }

    private PlainTextOutputSpeech getGoodByeText() {
        PlainTextOutputSpeech outputSpeech = new PlainTextOutputSpeech();
        outputSpeech.setText("Goodbye");
        return outputSpeech;
    }

    private String getCrudeRemark(){
        return getRandomStartPhrased() + " " + getRandomBadWord();
    }

    private String getRandomBadWord() {
        String joke = BAD_WORDS[getRandomIndex()];
        if(!usedBadWordsList.contains(joke)){
            usedBadWordsList.add(joke);
        } else {
            joke = getRandomBadWord();
        }
        return joke;
    }

    private String getRandomStartPhrased() {
        return START_PHRASE[getRandomPhraseIndex()];
    }


    private int getRandomIndex(){
        return new Random().nextInt(BAD_WORDS.length);
    }
    private int getRandomPhraseIndex(){
        return new Random().nextInt(START_PHRASE.length);
    }

    private static final String[] START_PHRASE = {
        "you",
        "you are a",
        "piece of",
        "piss off",
        "screw you"
    };


    private static final String[] BAD_WORDS = {
        "anus",
        "arse",
        "arsehole",
        "ass",
        "ass-hat",
        "ass-pirate",
        "assbag",
        "assbandit",
        "assbanger",
        "assbite",
        "assclown",
        "asscock",
        "asscracker",
        "asses",
        "assface",
        "assfuck",
        "assfucker",
        "assgoblin",
        "asshat",
        "asshead",
        "asshole",
        "asshopper",
        "assjacker",
        "asslick",
        "asslicker",
        "assmonkey",
        "assmunch",
        "assmuncher",
        "assnigger",
        "asspirate",
        "assshit",
        "assshole",
        "asssucker",
        "asswad",
        "asswipe",
        "bampot",
        "bastard",
        "beaner",
        "bitch",
        "bitchass",
        "bitches",
        "bitchtits",
        "bitchy",
        "blow job",
        "blowjob",
        "bollocks",
        "bollox",
        "boner",
        "brotherfucker",
        "bullshit",
        "bumblefuck",
        "butt plug",
        "butt-pirate",
        "buttfucka",
        "buttfucker",
        "camel toe",
        "carpetmuncher",
        "chinc",
        "chink",
        "choad",
        "chode",
        "clit",
        "clitface",
        "clitfuck",
        "clusterfuck",
        "cock",
        "cockass",
        "cockbite",
        "cockburger",
        "cockface",
        "cockfucker",
        "cockhead",
        "cockjockey",
        "cockknoker",
        "cockmaster",
        "cockmongler",
        "cockmongruel",
        "cockmonkey",
        "cockmuncher",
        "cocknose",
        "cocknugget",
        "cockshit",
        "cocksmith",
        "cocksmoker",
        "cocksucker",
        "coochie",
        "coochy",
        "coon",
        "cooter",
        "cracker",
        "cum",
        "cumbubble",
        "cumdumpster",
        "cumguzzler",
        "cumjockey",
        "cumslut",
        "cumtart",
        "cunnie",
        "cunnilingus",
        "cunt",
        "cuntface",
        "cunthole",
        "cuntlicker",
        "cuntrag",
        "cuntslut",
        "dago",
        "damn",
        "deggo",
        "dick",
        "dickbag",
        "dickbeaters",
        "dickface",
        "dickfuck",
        "dickfucker",
        "dickhead",
        "dickhole",
        "dickjuice",
        "dickmilk",
        "dickmonger",
        "dicks",
        "dickslap",
        "dicksucker",
        "dicksucking",
        "dickwad",
        "dickweasel",
        "dickweed",
        "dickwod",
        "dike",
        "dildo",
        "dipshit",
        "doochbag",
        "dookie",
        "douche",
        "douche-fag",
        "douchebag",
        "douchewaffle",
        "dumass",
        "dumb ass",
        "dumbass",
        "dumbfuck",
        "dumbshit",
        "dumshit",
        "dyke",
        "fag",
        "fagbag",
        "fagfucker",
        "faggit",
        "faggot",
        "faggotcock",
        "fagtard",
        "fatass",
        "fellatio",
        "feltch",
        "flamer",
        "fuck",
        "fuckass",
        "fuckbag",
        "fuckboy",
        "fuckbrain",
        "fuckbutt",
        "fucked",
        "fucker",
        "fuckersucker",
        "fuckface",
        "fuckhead",
        "fuckhole",
        "fuckin",
        "fucking",
        "fucknut",
        "fucknutt",
        "fuckoff",
        "fucks",
        "fuckstick",
        "fucktard",
        "fuckup",
        "fuckwad",
        "fuckwit",
        "fuckwitt",
        "fudgepacker",
        "gay",
        "gayass",
        "gaybob",
        "gaydo",
        "gayfuck",
        "gayfuckist",
        "gaylord",
        "gaytard",
        "gaywad",
        "goddamn",
        "goddamnit",
        "gooch",
        "gook",
        "gringo",
        "guido",
        "handjob",
        "hard on",
        "heeb",
        "hell",
        "ho",
        "hoe",
        "homo",
        "homodumbshit",
        "honkey",
        "humping",
        "jackass",
        "jap",
        "jerk off",
        "jigaboo",
        "jizz",
        "jungle bunny",
        "junglebunny",
        "kike",
        "kooch",
        "kootch",
        "kunt",
        "kyke",
        "lesbian",
        "lesbo",
        "lezzie",
        "mcfagget",
        "mick",
        "minge",
        "mothafucka",
        "motherfucker",
        "motherfucking",
        "muff",
        "muffdiver",
        "munging",
        "negro",
        "nigga",
        "nigger",
        "niggers",
        "niglet",
        "nut sack",
        "nutsack",
        "paki",
        "panooch",
        "pecker",
        "peckerhead",
        "penis",
        "penisfucker",
        "penispuffer",
        "piss",
        "pissed",
        "pissed off",
        "pissflaps",
        "polesmoker",
        "pollock",
        "poon",
        "poonani",
        "poonany",
        "poontang",
        "porch monkey",
        "porchmonkey",
        "poser",
        "prick",
        "punanny",
        "punta",
        "pussies",
        "pussy",
        "pussylicking",
        "puto",
        "queef",
        "queer",
        "queerbait",
        "queerhole",
        "renob",
        "rimjob",
        "ruski",
        "sand nigger",
        "sandnigger",
        "schlong",
        "scrote",
        "shit",
        "shitass",
        "shitbag",
        "shitbagger",
        "shitbrains",
        "shitbreath",
        "shitcunt",
        "shitdick",
        "shitface",
        "shitfaced",
        "shithead",
        "shithole",
        "shithouse",
        "shitspitter",
        "shitstain",
        "shitter",
        "shittiest",
        "shitting",
        "shitty",
        "shiz",
        "shiznit",
        "skank",
        "skeet",
        "skullfuck",
        "slut",
        "slutbag",
        "smeg",
        "snatch",
        "spic",
        "spick",
        "splooge",
        "spook",
        "tard",
        "testicle",
        "thundercunt",
        "tit",
        "titfuck",
        "tits",
        "tittyfuck",
        "twat",
        "twatlips",
        "twats",
        "twatwaffle",
        "uncle fucker",
        "va-j-j",
        "vag",
        "vagina",
        "vjayjay",
        "wank",
        "wetback",
        "whore",
        "whorebag",
        "whoreface",
        "wop"
    };




}